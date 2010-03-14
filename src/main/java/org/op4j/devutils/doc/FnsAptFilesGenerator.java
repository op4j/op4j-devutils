package org.op4j.devutils.doc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.op4j.functions.Ognl;

public class FnsAptFilesGenerator {

	//JavaDoc: will include only data before either @param or @return. Any text after that tokens will be ignored
	
	private final static String APT_EXTENSION = "apt";
	private final static String XDOC_EXTENSION = "xml";
	
	static void generateAllFnsDoc(File outputFile, List<String> fileNames) {
		for (String fileName : fileNames) {
			generateFnsDoc(outputFile, new File(fileName));
		}				
	}
	
	static void generateFnsDoc(File outputFile, File file) {
		try {
			System.out.println("Working with file: " + file.getName());
			
			Pattern pattern = Pattern.compile("[\\{|\\}]\\s*(\\/\\*[\\w\\W]*?\\*\\/)?" +
					"\\s*public\\s+(?:static|final)?\\s*(?:static|final)?\\s*" + //public static final
					"(?:\\<[\\w\\,\\?\\s\\[\\]]*\\>){0,1}" + // type parameters
					"\\s+((?:\\<[\\w\\,\\?\\s\\<\\>\\[\\]]+\\>|[a-zA-Z\\[\\]])+)" + //return type
					"\\s+([\\w]+)" + //function name
					"\\s*\\(\\s*([\\w\\?\\<\\>\\,\\s\\[\\]]*)\\)\\s*\\{"); //parameters
			
			Matcher matcher = pattern.matcher(FileUtils
					.readFileToString(file));

			if (StringUtils.endsWith(outputFile.getName(), APT_EXTENSION)) {
				createAptTableHeader(outputFile);			
				while (matcher.find()) {				
					System.out.println("Match: " + matcher.group());
					
					addAptRow(outputFile, matcher);				
				}			
				addAptSeparator(outputFile);
			} else if (StringUtils.endsWith(outputFile.getName(), XDOC_EXTENSION)) {
				IOUtils.writeLines(Arrays.asList(new String[] {"\r\n"}), null, new FileOutputStream(outputFile, true));
				IOUtils.writeLines(Arrays.asList(new String[] {"<table>"}), null, new FileOutputStream(outputFile, true));				
				createXdocTableHeader(outputFile);
				IOUtils.writeLines(Arrays.asList(new String[] {"<tbody>"}), null, new FileOutputStream(outputFile, true));
				while (matcher.find()) {				
					System.out.println("Match: " + matcher.group());
					addXdocRow(outputFile, matcher);				
				}		
				IOUtils.writeLines(Arrays.asList(new String[] {"</tbody></table>"}), null, new FileOutputStream(outputFile, true));				
			} else {
				//TODO Error
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
		return StringUtils
				.substringBefore(StringUtils
						.substringBefore(matcher.group(1), "@param"), "@return") 
			.replaceFirst("\\/\\*\\*", "")
			.replaceFirst("\\*\\/", "")
			.replaceAll("\\n\\s*\\*", "\n")
			.replaceAll("\\{\\@link([\\sa-zA-Z\\.]*)\\}", "$1")
			.trim()
			.replaceAll("\r\n", "")
			.replace("\n", "");		
	}
	
	static String getReturnType(Matcher matcher) {
		System.out.println("Return type: " + matcher.group(2));
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
		
		return Op.onList(output).forEach().exec(Ognl.asString("trim()"))
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
	
	static String escapeApt(String input) {
		if (StringUtils.isNotEmpty(input)) {
			return input.replaceAll("<", "\\\\<")
				.replaceAll(">", "\\\\>");
		} 
		return "";
	}
	static List<String> escapeApt(List<String> input) {
		
			return Op.on(input).forEach().exec(Ognl.asString("replaceAll(\"<\", \"\\\\<\")"))
				.exec(Ognl.asString("replaceAll(\">\", \"\\\\>\")")).get();
		
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
					"<th>Return type</th>" +
					"<th width=\"240px\">Params</th>" +
					"<th>Description</th></tr></thead>"}), null,
					new FileOutputStream(outputFile, true));			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static void createAptTableHeader(File outputFile) {
		try {
			IOUtils.writeLines(Arrays.asList(new String[] {"\r\n"}), null, new FileOutputStream(outputFile, true));
			addAptSeparator(outputFile);			
			IOUtils.writeLines(Arrays.asList(new String[] {"| <<Function name>> | <<Return type>> | <<Params>> | <<Description>> |"}), null,
					new FileOutputStream(outputFile, true));			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static void addAptRow(File outputFile, Matcher matcher) {
		
		//FIXME HTML in JavaDoc (only one line at this time)
		
		try {
			addAptSeparator(outputFile);
			
			List<String> params = getParams(matcher);
			List<String> javadoc = Arrays.asList(new String[] {escapeApt(getJavaDoc(matcher))});
			int totalLines = (params.size() > javadoc.size()) ? params.size() : javadoc.size();
			if (totalLines == 0) {
				IOUtils.writeLines(Arrays.asList(new String[] {
						"| " + escapeApt(getFunctionName(matcher)) 
						+ " | " + escapeApt(getReturnType(matcher)) 
						+ " |  |  |"}), null, new FileOutputStream(outputFile, true));	
			} else {
				for(int currentLine = 0; currentLine < totalLines; currentLine++) {
					IOUtils.writeLines(Arrays.asList(new String[] {
							"| " + ((currentLine == 0) ? escapeApt(getFunctionName(matcher)) : "") 
							+ " | " + ((currentLine == 0) ? escapeApt(getReturnType(matcher)) : "") 
							+ " | " + ((currentLine < params.size()) ? escapeApt(params.get(currentLine)) : "")
							+ " | " + ((currentLine < javadoc.size()) ? escapeApt(javadoc.get(currentLine)) : "")
							+ " |"}), null, new FileOutputStream(outputFile, true));
				}
			}	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static void addXdocRow(File outputFile, Matcher matcher) {
		try {
			List<String> params = getParams(matcher);
			String javadoc = getJavaDoc(matcher);
			IOUtils.writeLines(Arrays.asList(new String[] {
					"<tr><td>" + escapeXdoc(getFunctionName(matcher)) 
					+ "</td><td>" + escapeXdoc(getReturnType(matcher)) 
					+ "</td><td>" + StringUtils.join(escapeXdoc(params), "<br />")
					+ "</td><td>" + javadoc
					+ "</td></tr>"}), null, new FileOutputStream(outputFile, true));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static void addAptSeparator(File outputFile) {
		try {
			IOUtils.writeLines(Arrays.asList(new String[] {
				"*-----------+-----------+-----------+-----------+"}), null,
				new FileOutputStream(outputFile, true));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		// TODO Use input parameters instead
//		String inputFilePrefix = "C:\\Documents and Settings\\Daniel\\workspace\\op4j\\src\\main\\java\\org\\op4j\\functions\\";
//		String outputAptFilePrefix = "C:\\Documents and Settings\\Daniel\\workspace\\op4j\\src\\site\\apt\\";
//		String outputXdocFilePrefix = "C:\\Documents and Settings\\Daniel\\workspace\\op4j\\src\\site\\xdoc\\";
		
		String inputFilePrefix = "C:\\Development\\workspace-galileo\\op4j\\src\\main\\java\\org\\op4j\\functions\\";
		String outputAptFilePrefix = "C:\\Development\\workspace-galileo\\op4j\\src\\site\\apt\\";
		String outputXdocFilePrefix = "C:\\Development\\workspace-galileo\\op4j\\src\\site\\xdoc\\";
			
		
		// Generation
		
//		generateAllFnsDoc(new File(outputXdocFilePrefix, "fnstring.xml"), Arrays.asList(new String[] {
//				inputFilePrefix + "FnString.java"}));
		
//		generateAllFnsDoc(new File(outputXdocFilePrefix, "fnboolean.xml"), Arrays.asList(new String[] {
//				inputFilePrefix + "FnBoolean.java"}));
		
//		generateAllFnsDoc(new File(outputXdocFilePrefix, "fncalendar.xml"), Arrays.asList(new String[] {
//				inputFilePrefix + "FnCalendar.java"}));
		
//		generateAllFnsDoc(new File(outputXdocFilePrefix, "fndate.xml"), Arrays.asList(new String[] {
//				inputFilePrefix + "FnDate.java"}));
		
//		generateAllFnsDoc(new File(outputXdocFilePrefix, "fnnumber.xml"), Arrays.asList(new String[] {
//				inputFilePrefix + "FnNumber.java"}));
		
		System.out.println("All files have been generated");
		
		
	}

}
