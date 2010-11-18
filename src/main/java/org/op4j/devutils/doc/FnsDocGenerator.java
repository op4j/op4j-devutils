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

    // JavaDoc: will include only data before either @param, @since or @return.
    // Any text after that tokens will be ignored
    // The expression does not match deprecated methods so there is no need to take
    //that into account when checking the content of the methods javadoc

    private final static String XDOC_EXTENSION = "xml";

    private final static String EVEN_ROW_CLASS = "b";
    private final static String ODD_ROW_CLASS = "a";

    
    // Folder where the generated documentation will be stored
    private final static String OUTPUT_XDOC_FILE_PREFIX = 
        "C:\\Development\\workspace-galileo\\op4j-dist\\src\\site\\xdoc\\";

    // Location of the op4j files from which documentation must be generated
    private final static String OP4J_INPUT_FILE_PREFIX = 
        "C:\\Development\\workspace-galileo\\op4j\\src\\main\\java\\org\\op4j\\functions\\";

    // Location of the op4j-jodatime files from which documentation must be generated
    private final static String JODATIME_INPUT_FILE_PREFIX = 
        "C:\\Development\\workspace-galileo\\op4j-jodatime\\src\\main\\java\\org\\op4j\\jodatime\\functions\\";

    
    
    static void generateAllFnsDoc(File outputFile, List<String> fileNames) {
        for (String fileName : fileNames) {
            generateFnsDoc(outputFile, new File(fileName));
        }
    }

    static void generateFnsDoc(File outputFile, File file) {
        try {
            System.out.println("Working with file: " + file.getName());

            // Pattern pattern =
            // Pattern.compile("[\\{|\\}]\\s*(?://.*?)*?\\s*(\\/\\*[\\w\\W]*?\\*\\/)?"
            // +

            Pattern pattern = Pattern
                    .compile("[\\{|\\}]\\s*(?:\\/\\/.*?\\s*)*?\\s*(\\/\\*[\\w\\W]*?\\*\\/)?"
                            + "\\s*public\\s+(?:static|final)?\\s*(?:static|final)?\\s*"
                            + // public static final
                            "(?:\\<[\\w\\,\\?\\s\\[\\]]*\\>){0,1}"
                            + // type parameters
                            "\\s+((?:\\<[\\w\\,\\?\\s\\<\\>\\[\\]]+\\>|[a-zA-Z\\[\\]])+)"
                            + // return type
                            "\\s+([\\w]+)" + // function name
                            "\\s*\\(\\s*([\\w\\d\\?\\<\\>\\,\\s\\[\\]\\.]*)\\)\\s*\\{"); // parameters

            Matcher matcher = pattern.matcher(FileUtils.readFileToString(file));

            if (StringUtils.endsWith(outputFile.getName(), XDOC_EXTENSION)) {
                // Get lines
                List<Line> lines = new ArrayList<Line>();
                while (matcher.find()) {
                    System.out.println("Match " + matcher.group() + " is valid.");
                    lines.add(getLine(matcher));
                }
                // Sort lines
                System.out.println("Sorting lines...");
                List<Line> linesSorted = Op.on(lines).sort(
                        new Comparator<Line>() {
                            public int compare(Line o1, Line o2) {
                                if (o2 == null) {
                                    return -1;
                                }
                                if (o1 == null) {
                                    return 1;
                                }
                                if (o1.getFunctionName().compareTo(
                                        o2.getFunctionName()) == 0) {
                                    return Integer.valueOf(
                                            o1.getParams().size()).compareTo(
                                            Integer.valueOf(o2.getParams()
                                                    .size()));
                                }
                                return o1.getFunctionName().compareTo(
                                        o2.getFunctionName());
                            }
                        }).get();
                System.out.println("Lines sorted");

                IOUtils.writeLines(Arrays.asList(new String[] { "\r\n" }),
                        null, new FileOutputStream(outputFile, true));
                IOUtils.writeLines(Arrays
                        .asList(new String[] { "<table border=\"0\">" }), null,
                        new FileOutputStream(outputFile, true));
                createXdocTableHeader(outputFile);
                IOUtils.writeLines(Arrays.asList(new String[] { "<tbody>" }),
                        null, new FileOutputStream(outputFile, true));
                String fnName = null;
                int index = 0;
                for (Line line : linesSorted) {
                    if (fnName != null
                            && !StringUtils.equals(fnName, line
                                    .getFunctionName())) {
                        IOUtils
                                .writeLines(
                                        Arrays
                                                .asList(new String[] { "<tr bgcolor=\"#A4A4A4\" class=\"\"><td bgcolor=\"#A4A4A4\" colspan=\"4\"></td></tr>" }),
                                        null, new FileOutputStream(outputFile,
                                                true));
                    }
                    fnName = line.getFunctionName();
                    addXdocLine(line, outputFile,
                            index % 2 == 0 ? EVEN_ROW_CLASS : ODD_ROW_CLASS);
                    index++;
                }
                IOUtils.writeLines(Arrays
                        .asList(new String[] { "</tbody></table>" }), null,
                        new FileOutputStream(outputFile, true));
            } else {
                throw new UnsupportedOperationException(
                        "Can't generate documentation in file without xdoc extension");
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
                StringUtils.substringBefore(StringUtils.substringBefore(matcher
                        .group(1), "@param"), "@return"), "@since")
                .replaceFirst("\\/\\*\\*", "").replaceFirst("\\*\\/", "")
                .replaceAll("\\n\\s*\\*", "\n").replaceAll(
                        "\\{\\@link\\s*#([\\w\\W]*?)\\}", "$1").replaceAll(
                        "\\{\\@link([\\w\\W]*?)\\}", "$1").trim().replaceAll(
                        "\r\n", "").replace("\n", "");
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
            output.addAll(Op.onArrayOf(Types.STRING,
                    remaining.toString().split(",")).toList().get());
        }

        return Op.onList(output).forEach().exec(FnOgnl.evalForString("trim()"))
                .exec(new IFunction<String, String>() {
                    public String execute(String object, ExecCtx ctx)
                            throws Exception {
                        if (StringUtils.startsWith(object, "final")) {
                            return StringUtils.substringAfter(object, "final")
                                    .trim();
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
        return Op.on(input).forEach().exec(FnString.escapeHTML()).endFor()
                .get();
    }

    static void createXdocTableHeader(File outputFile) {
        try {
            IOUtils.writeLines(Arrays
                    .asList(new String[] { "<thead><tr><th>Function name</th>"
                            + "<th>Type</th>"
                            + "<th width=\"240px\">Params</th>"
                            + "<th>Description</th></tr></thead>" }), null,
                    new FileOutputStream(outputFile, true));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    static void addXdocLine(Line line, File outputFile, String rowClass) {
        try {
            IOUtils.writeLines(Arrays.asList(new String[] { "<tr class=\""
                    + rowClass + "\"><td><b>" + line.getFunctionName()
                    + "</b></td><td>" + line.getType() + "</td><td>"
                    + StringUtils.join(line.getParams(), "<br />")
                    + "</td><td>" + line.getJavadoc() + "</td></tr>" }), null,
                    new FileOutputStream(outputFile, true));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    static Line getLine(Matcher matcher) {
        Line line = new Line(escapeXdoc(getFunctionName(matcher)),
                escapeXdoc(getType(matcher)), escapeXdoc(getParams(matcher)),
                getJavaDoc(matcher));
        return line;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        System.out.println("******** OP4J *********");
//        generateOp4jDocumentation();
        System.out.println("***********************");
        
        System.out.println("**** OP4J-JODATIME ****");
        generateOp4jJodaTimeDocumentation();
        System.out.println("***********************");


         

    }
    
    /**
     * It generates all the op4j related stuff
     */
    static void generateOp4jDocumentation() {
        
        generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX, "fnstring.xml"),
                Arrays.asList(new String[] { 
                        OP4J_INPUT_FILE_PREFIX + "FnString.java" }));

        generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX, "fnfunc.xml"),
                Arrays.asList(new String[] {
                        OP4J_INPUT_FILE_PREFIX + "FnFunc.java"}));

        generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX, "fnboolean.xml"),
                Arrays.asList(new String[] { 
                        OP4J_INPUT_FILE_PREFIX + "FnBoolean.java" }));

        generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX, "fncalendar.xml"),
                Arrays.asList(new String[] {
                        OP4J_INPUT_FILE_PREFIX + "FnCalendar.java"}));

        generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX, "fndate.xml"),
                Arrays.asList(new String[] {
                        OP4J_INPUT_FILE_PREFIX + "FnDate.java"}));



        generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX, "fnnumber.xml"),
                Arrays.asList(new String[] { 
                        OP4J_INPUT_FILE_PREFIX + "FnNumber.java"}));

        generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX, "fnlong.xml"),
                Arrays.asList(new String[] { 
                        OP4J_INPUT_FILE_PREFIX + "FnLong.java"}));

        generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX, "fnfloat.xml"),
                Arrays.asList(new String[] { 
                        OP4J_INPUT_FILE_PREFIX + "FnFloat.java"}));

        generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX, "fninteger.xml"),
                Arrays.asList(new String[] { 
                        OP4J_INPUT_FILE_PREFIX + "FnInteger.java"}));

        generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX, "fndouble.xml"),
                Arrays.asList(new String[] { 
                        OP4J_INPUT_FILE_PREFIX + "FnDouble.java"}));

        generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX, "fnbigdecimal.xml"),
                Arrays.asList(new String[] { 
                        OP4J_INPUT_FILE_PREFIX + "FnBigDecimal.java"}));

        generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX, "fnbiginteger.xml"),
                Arrays.asList(new String[] { 
                        OP4J_INPUT_FILE_PREFIX + "FnBigInteger.java"}));
         
        
        generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX, "fnobject.xml"), 
                Arrays.asList(new String[] { 
                        OP4J_INPUT_FILE_PREFIX + "FnObject.java"}));



        
         generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX, "fnreduce.all.xml"), 
                Arrays.asList(new String[] {
                        OP4J_INPUT_FILE_PREFIX + "FnReduceOnBigDecimal.java"}));
         generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX, "fnreduce.all.xml"),
                Arrays.asList(new String[] {
                        OP4J_INPUT_FILE_PREFIX + "FnReduceOnBigInteger.java"}));
         generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX, "fnreduce.all.xml"),
                Arrays.asList(new String[] {
                        OP4J_INPUT_FILE_PREFIX + "FnReduceOnBoolean.java"}));
         generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX, "fnreduce.all.xml"),
                 Arrays.asList(new String[] {
                         OP4J_INPUT_FILE_PREFIX + "FnReduceOnByte.java"}));
         generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX, "fnreduce.all.xml"),
                 Arrays.asList(new String[] {
                         OP4J_INPUT_FILE_PREFIX + "FnReduceOnDouble.java"}));
         generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX, "fnreduce.all.xml"),
                 Arrays.asList(new String[] {
                         OP4J_INPUT_FILE_PREFIX + "FnReduceOnFloat.java"}));
         generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX, "fnreduce.all.xml"),
                 Arrays.asList(new String[] {
                         OP4J_INPUT_FILE_PREFIX + "FnReduceOnInteger.java"}));
         generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX, "fnreduce.all.xml"),
                 Arrays.asList(new String[] {
                         OP4J_INPUT_FILE_PREFIX + "FnReduceOnLong.java"}));
         generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX, "fnreduce.all.xml"),
                 Arrays.asList(new String[] {
                         OP4J_INPUT_FILE_PREFIX + "FnReduceOnShort.java"}));
         generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX, "fnreduce.all.xml"),
                 Arrays.asList(new String[] {
                         OP4J_INPUT_FILE_PREFIX + "FnReduceOnString.java"}));
         generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX, "fnreduce.all.xml"),
                 Arrays.asList(new String[] {
                         OP4J_INPUT_FILE_PREFIX + "FnReduceOn.java"}));

         generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX, "fnarray.all.xml"),
                 Arrays.asList(new String[] {
                         OP4J_INPUT_FILE_PREFIX + "FnArrayOf.java"}));
         generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX, "fnarray.all.xml"),
                 Arrays.asList(new String[] {
                         OP4J_INPUT_FILE_PREFIX + "FnArrayOfArrayOf.java"}));
         generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX, "fnarray.all.xml"),
                 Arrays.asList(new String[] {
                         OP4J_INPUT_FILE_PREFIX + "FnArrayOfListOf.java"}));
         generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX, "fnarray.all.xml"),
                 Arrays.asList(new String[] {
                         OP4J_INPUT_FILE_PREFIX + "FnArrayOfSetOf.java"}));

         generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX, "fnlist.all.xml"),
                 Arrays.asList(new String[] {
                         OP4J_INPUT_FILE_PREFIX + "FnListOf.java"}));
         generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX, "fnlist.all.xml"),
                 Arrays.asList(new String[] {
                         OP4J_INPUT_FILE_PREFIX + "FnListOfArrayOf.java"}));
         generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX, "fnlist.all.xml"),
                 Arrays.asList(new String[] {
                         OP4J_INPUT_FILE_PREFIX + "FnListOfListOf.java"}));
         generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX, "fnlist.all.xml"),
                 Arrays.asList(new String[] {
                         OP4J_INPUT_FILE_PREFIX + "FnListOfSetOf.java"}));

         generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX, "fnset.all.xml"),
                 Arrays.asList(new String[] {
                         OP4J_INPUT_FILE_PREFIX + "FnSetOf.java"}));
         generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX, "fnset.all.xml"),
                 Arrays.asList(new String[] {
                         OP4J_INPUT_FILE_PREFIX + "FnSetOfArrayOf.java"}));
         generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX, "fnset.all.xml"),
                 Arrays.asList(new String[] {
                         OP4J_INPUT_FILE_PREFIX + "FnSetOfListOf.java"}));
         generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX, "fnset.all.xml"),
                 Arrays.asList(new String[] {
                         OP4J_INPUT_FILE_PREFIX + "FnSetOfSetOf.java"}));

         generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX, "fnmap.xml"),
                 Arrays.asList(new String[] {
                         OP4J_INPUT_FILE_PREFIX + "FnMapOf.java"}));
    }
    
    /**
     * It generates all the op4j-jodatime related stuff
     */
    static void generateOp4jJodaTimeDocumentation() {
        
        generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX,
            "jodatime-fndatemidnight.xml"), Arrays.asList(new String[] {
                    JODATIME_INPUT_FILE_PREFIX + "FnDateMidnight.java"}));

        generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX,
            "jodatime-fndatetime.xml"), Arrays.asList(new String[] {
                    JODATIME_INPUT_FILE_PREFIX + "FnDateTime.java"}));
       
        generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX,
            "jodatime-fninterval.xml"), Arrays.asList(new String[] {
                    JODATIME_INPUT_FILE_PREFIX + "FnInterval.java"}));
       
        generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX,
            "jodatime-fnjodastring.xml"), Arrays.asList(new String[] {
                    JODATIME_INPUT_FILE_PREFIX + "FnJodaString.java"}));
       
         generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX,
             "jodatime-fnjodatimeutils.all.xml"), Arrays.asList(new String[] {
                     JODATIME_INPUT_FILE_PREFIX + "FnJodaTimeUtils.java"}));
        
         generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX,
             "jodatime-fnlocaldate.xml"), Arrays.asList(new String[] {
                     JODATIME_INPUT_FILE_PREFIX + "FnLocalDate.java"}));
        
         generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX,
             "jodatime-fnlocaltime.xml"), Arrays.asList(new String[] {
                     JODATIME_INPUT_FILE_PREFIX + "FnLocalTime.java"}));
        
         generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX,
             "jodatime-fnmutabledatetime.xml"), Arrays.asList(new String[] {
                     JODATIME_INPUT_FILE_PREFIX + "FnMutableDateTime.java"}));
    
         generateAllFnsDoc(new File(OUTPUT_XDOC_FILE_PREFIX,
             "jodatime-fnperiod.xml"), Arrays.asList(new String[] {
                     JODATIME_INPUT_FILE_PREFIX + "FnPeriod.java"}));
    }

}
