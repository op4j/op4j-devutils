package org.op4j.devutils.doc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.javaruntype.type.Types;
import org.op4j.Op;
import org.op4j.functions.ExecCtx;
import org.op4j.functions.FnString;
import org.op4j.functions.IFunction;
import org.op4j.ognl.functions.FnOgnl;

public class FnsDocGenerator {

    //JavaDoc: will include only data before either @param, @since or @return. Any text after that tokens will be ignored

    private final static String XDOC_EXTENSION = "xml";

    private final static String EVEN_ROW_CLASS = "b";
    private final static String ODD_ROW_CLASS = "a";

    static void generateAllFnsDoc(File outputFile, List<String> fileNames) {
        for (String fileName : fileNames) {
            generateFnsDoc(outputFile, new File(fileName));
        }				
    }

    static void generateFnsDoc(File outputFile, File file) {
        try {
            System.out.println("Working with file: " + file.getName());

//          Pattern pattern = Pattern.compile("[\\{|\\}]\\s*(?://.*?)*?\\s*(\\/\\*[\\w\\W]*?\\*\\/)?" +
            
            
            Pattern pattern = Pattern.compile("[\\{|\\}]\\s*(?:\\/\\/.*?\\s*)*?\\s*(\\/\\*[\\w\\W]*?\\*\\/)?" +
                    "\\s*public\\s+(?:static|final)?\\s*(?:static|final)?\\s*" + //public static final
                    "(?:\\<[\\w\\,\\?\\s\\[\\]]*\\>){0,1}" + // type parameters
                    "\\s+((?:\\<[\\w\\,\\?\\s\\<\\>\\[\\]]+\\>|[a-zA-Z\\[\\]])+)" + //return type
                    "\\s+([\\w]+)" + //function name
            "\\s*\\(\\s*([\\w\\d\\?\\<\\>\\,\\s\\[\\]\\.]*)\\)\\s*\\{"); //parameters

            Matcher matcher = pattern.matcher(FileUtils
                    .readFileToString(file));

            if (StringUtils.endsWith(outputFile.getName(), XDOC_EXTENSION)) {
                // Get lines
                List<Line> lines = new ArrayList<Line>();
                while (matcher.find()) {
                    System.out.println("Match: " + matcher.group());
                    lines.add(getLine(matcher));
                }
                // Sort lines
                System.out.println("Sorting lines...");
                List<Line> linesSorted = Op.on(lines).sort(new Comparator<Line>() {
                    public int compare(Line o1, Line o2) {
                        if (o2 == null) {
                            return -1;
                        }
                        if (o1 == null) {
                            return 1;
                        }
                        if (o1.getFunctionName().compareTo(o2.getFunctionName()) == 0) {
                            return Integer.valueOf(o1.getParams().size())
                            .compareTo(Integer.valueOf(o2.getParams().size()));
                        } 
                        return o1.getFunctionName().compareTo(o2.getFunctionName());                                              
                    }
                }).get();
                System.out.println("Lines sorted");
                
                IOUtils.writeLines(Arrays.asList(new String[] {"\r\n"}), null, new FileOutputStream(outputFile, true));
                IOUtils.writeLines(Arrays.asList(new String[] {"<table border=\"0\">"}), null, new FileOutputStream(outputFile, true));				
                createXdocTableHeader(outputFile);
                IOUtils.writeLines(Arrays.asList(new String[] {"<tbody>"}), null, new FileOutputStream(outputFile, true));
                String fnName = null;
                int index = 0;				
                for (Line line : linesSorted) {				
                    if (fnName != null && !StringUtils.equals(fnName, line.getFunctionName())) {
                        IOUtils.writeLines(Arrays.asList(new String[] {"<tr bgcolor=\"#A4A4A4\" class=\"\"><td bgcolor=\"#A4A4A4\" colspan=\"4\"></td></tr>"}), 
                                null, new FileOutputStream(outputFile, true));
                    } 
                    fnName = line.getFunctionName();
                    addXdocLine(line, outputFile, index % 2 == 0 ? EVEN_ROW_CLASS : ODD_ROW_CLASS);
                    index++;
                }		
                IOUtils.writeLines(Arrays.asList(new String[] {"</tbody></table>"}), null, 
                        new FileOutputStream(outputFile, true));				
            } else {
                throw new UnsupportedOperationException("Can't generate documentation in file without xdoc extension");
            }

            System.out.println("done");		
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    static String getJavaDoc(Matcher matcher) {
        if (StringUtils.isEmpty(matcher.group(1))) {
            return "";
        }
        
        return StringUtils.substringBefore(
                StringUtils.substringBefore(
                        StringUtils.substringBefore(matcher.group(1), "@param"), "@return"), "@since") 
                        .replaceFirst("\\/\\*\\*", "")
                        .replaceFirst("\\*\\/", "")
                        .replaceAll("\\n\\s*\\*", "\n")
                        .replaceAll("\\{\\@link\\s*#([\\w\\W]*?)\\}", "$1")
                        .replaceAll("\\{\\@link([\\w\\W]*?)\\}", "$1")
                        .trim()
                        .replaceAll("\r\n", "")
                        .replace("\n", "");		
    }

    static String getType(Matcher matcher) {
        System.out.println("Type: " + matcher.group(2));
        return matcher.group(2);
    }
    static String getFunctionName(Matcher matcher) {
        System.out.println("Fn name: " + matcher.group(3));
        return matcher.group(3);
    }
    static List<String> getParams(Matcher matcher) {
        List<String> params = getParams(matcher.group(4));
        System.out.println("Params: " + params);
        return params;
    }

    static List<String> getParams(String params) {
        System.out.println("Extract params from: " + params);

        List<String> output = new ArrayList<String>();

        StringBuffer remaining = new StringBuffer(params);
        if (remaining.indexOf(">") != -1) {
            int nextParamStartIndex = 0;
            int opened = 0;
            for (int index = 0; index < remaining.length(); index++) {
                if (remaining.charAt(index) == '<') {
                    opened++;
                    continue;
                }
                if (remaining.charAt(index) == '>') {
                    opened--;
                    continue;
                }
                if (remaining.charAt(index) == ',' && opened == 0) {
                    output.add(remaining.substring(nextParamStartIndex, index));
                    nextParamStartIndex = index + 1;
                }
            }	
            if (opened == 0) {
                output.add(remaining.substring(nextParamStartIndex));
            }
        } else {
            output.addAll(Op.onArrayOf(Types.STRING, remaining.toString().split(",")).toList().get());
        }

        return Op.onList(output).forEach().exec(FnOgnl.asString("trim()"))
        .exec(new IFunction<String, String>() {
            public String execute(String object, ExecCtx ctx)
            throws Exception {
                if (StringUtils.startsWith(object, "final")) {
                    return StringUtils.substringAfter(object, "final").trim();
                } 
                return object;
            }
        }).endFor().get();
    }

    static String escapeXdoc(String input) {
        if (StringUtils.isNotEmpty(input)) {
            return Op.on(input).exec(FnString.escapeHTML()).get();
        } 
        return "";
    }
    static List<String> escapeXdoc(List<String> input) {
        return Op.on(input).forEach().exec(FnString.escapeHTML()).endFor().get();
    }

    static void createXdocTableHeader(File outputFile) {
        try {
            IOUtils.writeLines(Arrays.asList(new String[] {
                    "<thead><tr><th>Function name</th>" +
                    "<th>Type</th>" +
                    "<th width=\"240px\">Params</th>" +
            "<th>Description</th></tr></thead>"}), null,
            new FileOutputStream(outputFile, true));			
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    static void addXdocLine(Line line, File outputFile, String rowClass) {
        try {
            IOUtils.writeLines(Arrays.asList(new String[] {
                    "<tr class=\"" + rowClass + "\"><td><b>" + line.getFunctionName() 
                    + "</b></td><td>" + line.getType() 
                    + "</td><td>" + StringUtils.join(line.getParams(), "<br />")
                    + "</td><td>" + line.getJavadoc()
                    + "</td></tr>"}), null, new FileOutputStream(outputFile, true));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    static Line getLine(Matcher matcher) {        
        Line line = new Line(
                escapeXdoc(getFunctionName(matcher)),
                escapeXdoc(getType(matcher)),
                escapeXdoc(getParams(matcher)),
                getJavaDoc(matcher)
        );
        return line;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        // TODO Use input parameters instead
        //		String inputFilePrefix = "C:\\Documents and Settings\\Daniel\\workspace\\op4j\\src\\main\\java\\org\\op4j\\functions\\";

        String outputXdocFilePrefix = "C:\\Development\\workspace-galileo\\op4j-dist\\src\\site\\xdoc\\";

        

        // op4j Generation
//        String op4jInputFilePrefix = "C:\\Development\\workspace-galileo\\op4j\\src\\main\\java\\org\\op4j\\functions\\";
//        
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "fnstring.xml"), Arrays.asList(new String[] {
//                op4jInputFilePrefix + "FnString.java"}));
//
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "fnfunc.xml"), Arrays.asList(new String[] {
//                op4jInputFilePrefix + "FnFunc.java"}));
//
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "fnboolean.xml"), Arrays.asList(new String[] {
//                op4jInputFilePrefix + "FnBoolean.java"}));
//
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "fncalendar.xml"), Arrays.asList(new String[] {
//                op4jInputFilePrefix + "FnCalendar.java"}));
//
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "fndate.xml"), Arrays.asList(new String[] {
//                op4jInputFilePrefix + "FnDate.java"}));
//
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "fnnumber.xml"), Arrays.asList(new String[] {
//                op4jInputFilePrefix + "FnNumber.java"}));
//
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "fnlong.xml"), Arrays.asList(new String[] {
//                op4jInputFilePrefix + "FnLong.java"}));
//
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "fnfloat.xml"), Arrays.asList(new String[] {
//                op4jInputFilePrefix + "FnFloat.java"}));
//
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "fninteger.xml"), Arrays.asList(new String[] {
//                op4jInputFilePrefix + "FnInteger.java"}));
//
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "fndouble.xml"), Arrays.asList(new String[] {
//                op4jInputFilePrefix + "FnDouble.java"}));
//
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "fnbigdecimal.xml"), Arrays.asList(new String[] {
//                op4jInputFilePrefix + "FnBigDecimal.java"}));
//
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "fnbiginteger.xml"), Arrays.asList(new String[] {
//                op4jInputFilePrefix + "FnBigInteger.java"}));
//
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "fnobject.xml"), Arrays.asList(new String[] {
//                op4jInputFilePrefix + "FnObject.java"}));
//
//
//
//
//
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "fnreduce.all.xml"), Arrays.asList(new String[] {
//                op4jInputFilePrefix + "FnReduceOnBigDecimal.java"}));
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "fnreduce.all.xml"), Arrays.asList(new String[] {
//                op4jInputFilePrefix + "FnReduceOnBigInteger.java"}));
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "fnreduce.all.xml"), Arrays.asList(new String[] {
//                op4jInputFilePrefix + "FnReduceOnBoolean.java"}));
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "fnreduce.all.xml"), Arrays.asList(new String[] {
//                op4jInputFilePrefix + "FnReduceOnByte.java"}));
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "fnreduce.all.xml"), Arrays.asList(new String[] {
//                op4jInputFilePrefix + "FnReduceOnDouble.java"}));
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "fnreduce.all.xml"), Arrays.asList(new String[] {
//                op4jInputFilePrefix + "FnReduceOnFloat.java"}));
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "fnreduce.all.xml"), Arrays.asList(new String[] {
//                op4jInputFilePrefix + "FnReduceOnInteger.java"}));
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "fnreduce.all.xml"), Arrays.asList(new String[] {
//                op4jInputFilePrefix + "FnReduceOnLong.java"}));
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "fnreduce.all.xml"), Arrays.asList(new String[] {
//                op4jInputFilePrefix + "FnReduceOnShort.java"}));
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "fnreduce.all.xml"), Arrays.asList(new String[] {
//                op4jInputFilePrefix + "FnReduceOnString.java"}));
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "fnreduce.all.xml"), Arrays.asList(new String[] {
//                op4jInputFilePrefix + "FnReduceOn.java"}));
//
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "fnarray.all.xml"), Arrays.asList(new String[] {
//                op4jInputFilePrefix + "FnArrayOf.java"}));
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "fnarray.all.xml"), Arrays.asList(new String[] {
//                op4jInputFilePrefix + "FnArrayOfArrayOf.java"}));
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "fnarray.all.xml"), Arrays.asList(new String[] {
//                op4jInputFilePrefix + "FnArrayOfListOf.java"}));
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "fnarray.all.xml"), Arrays.asList(new String[] {
//                op4jInputFilePrefix + "FnArrayOfSetOf.java"}));
//
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "fnlist.all.xml"), Arrays.asList(new String[] {
//                op4jInputFilePrefix + "FnListOf.java"}));
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "fnlist.all.xml"), Arrays.asList(new String[] {
//                op4jInputFilePrefix + "FnListOfArrayOf.java"}));
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "fnlist.all.xml"), Arrays.asList(new String[] {
//                op4jInputFilePrefix + "FnListOfListOf.java"}));
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "fnlist.all.xml"), Arrays.asList(new String[] {
//                op4jInputFilePrefix + "FnListOfSetOf.java"}));
//
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "fnset.all.xml"), Arrays.asList(new String[] {
//                op4jInputFilePrefix + "FnSetOf.java"}));
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "fnset.all.xml"), Arrays.asList(new String[] {
//                op4jInputFilePrefix + "FnSetOfArrayOf.java"}));
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "fnset.all.xml"), Arrays.asList(new String[] {
//                op4jInputFilePrefix + "FnSetOfListOf.java"}));
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "fnset.all.xml"), Arrays.asList(new String[] {
//                op4jInputFilePrefix + "FnSetOfSetOf.java"}));
//
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "fnmap.xml"), Arrays.asList(new String[] {
//                op4jInputFilePrefix + "FnMapOf.java"}));

        // op4j-jodatime generation
        String jodatimeInputFilePrefix = "C:\\Development\\workspace-galileo\\op4j-jodatime\\src\\main\\java\\org\\op4j\\jodatime\\functions\\";

//        generateAllFnsDoc(new File(outputXdocFilePrefix, "jodatime-fnjodatimeutils.all.xml"), Arrays.asList(new String[] {
//                jodatimeInputFilePrefix + "FnJodaTimeUtils.java"}));
//
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "jodatime-fnjodatostring.xml"), Arrays.asList(new String[] {
//                jodatimeInputFilePrefix + "FnJodaToString.java"}));
//
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "jodatime-fntodatemidnight.xml"), Arrays.asList(new String[] {
//                jodatimeInputFilePrefix + "FnToDateMidnight.java"}));
//
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "jodatime-fntodatetime.xml"), Arrays.asList(new String[] {
//                jodatimeInputFilePrefix + "FnToDateTime.java"}));
//
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "jodatime-fntointerval.xml"), Arrays.asList(new String[] {
//                jodatimeInputFilePrefix + "FnToInterval.java"}));
//
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "jodatime-fntolocaldate.xml"), Arrays.asList(new String[] {
//                jodatimeInputFilePrefix + "FnToLocalDate.java"}));
//
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "jodatime-fntolocaltime.xml"), Arrays.asList(new String[] {
//                jodatimeInputFilePrefix + "FnToLocalTime.java"}));
//
//        generateAllFnsDoc(new File(outputXdocFilePrefix, "jodatime-fntoperiod.xml"), Arrays.asList(new String[] {
//                jodatimeInputFilePrefix + "FnToPeriod.java"}));

        System.out.println("All files have been generated");



    }

}
