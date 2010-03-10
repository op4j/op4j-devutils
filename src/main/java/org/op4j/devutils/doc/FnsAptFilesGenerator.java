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
import org.op4j.functions.IFunction;
import org.op4j.functions.Ognl;

public class FnsAptFilesGenerator {

	static void generateAllFnsDoc(File outputFile, List<String> fileNames) {
		for (String fileName : fileNames) {
			generateFnsDoc(outputFile, new File(fileName));
		}				
	}
	
	static void generateFnsDoc(File outputFile, File file) {
		try {
			System.out.println("Working with file: " + file.getName());
			
			Pattern pattern = Pattern.compile("(\\/\\*[\\w\\/\\*\\s\\{\\@\\}\\(\\)\\.]*\\*\\/)?" +
					"\\s*public\\s+static\\s+(?:final)?(?:\\<[a-zA-Z\\,\\s]*\\>){0,1}\\s+((?:\\<[a-zA-Z\\,\\s\\<\\>]+\\>|[a-zA-Z])+)\\s+([a-zA-Z]+)\\s*" +
					"\\(\\s*([a-zA-Z\\?\\<\\>\\,\\s]*)\\)\\s*\\{");
			
			Matcher matcher = pattern.matcher(FileUtils
					.readFileToString(file));

			createTableHeader(outputFile);			
			while (matcher.find()) {				
				System.out.println("Match: " + matcher.group());
				
				addRow(outputFile, matcher);				
			}			
			addSeparator(outputFile);
			
			System.out.println("done");		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static List<String> getJavaDocLines(Matcher matcher) {
		//TODO Remove @link
		//TODO Params
		return Arrays.asList(escape(matcher.group(1))
			.replaceFirst("\\/\\*\\*", "")
			.replaceFirst("\\*\\/", "")
			.replaceAll("\\n\\s*\\*", "\n")
			.trim()
			.split("\r\n"));
	}
	static String getReturnType(Matcher matcher) {
		System.out.println("Return type: " + matcher.group(2));
		return escape(matcher.group(2));
	}
	static String getFunctionName(Matcher matcher) {
		System.out.println("Fn name: " + matcher.group(3));
		return escape(matcher.group(3));
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
						return escape(StringUtils.substringAfter(object, "final").trim());
					} 
					return escape(object);
				}
			}).endFor().get();
	}
	
	static String escape(String input) {
		if (StringUtils.isNotEmpty(input)) {
			return input.replaceAll("<", "\\\\<")
				.replaceAll(">", "\\\\>");
		} 
		return "";
	}
	
	static void createTableHeader(File outputFile) {
		try {
			IOUtils.writeLines(Arrays.asList(new String[] {"\r\n"}), null, new FileOutputStream(outputFile, true));
			addSeparator(outputFile);
			IOUtils.writeLines(Arrays.asList(new String[] {"| <<Function name>> | <<Return type>> | <<Params>> | <<Description>> |"}), null,
					new FileOutputStream(outputFile, true));				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static void addRow(File outputFile, Matcher matcher) {
		try {
			addSeparator(outputFile);
			
			List<String> params = getParams(matcher);
			List<String> javadoc = getJavaDocLines(matcher);
			int totalLines = (params.size() > javadoc.size()) ? params.size() : javadoc.size();
			if (totalLines == 0) {
				IOUtils.writeLines(Arrays.asList(new String[] {
						"| " + getFunctionName(matcher) 
						+ " | " + getReturnType(matcher) 
						+ " |  |  |"}), null, new FileOutputStream(outputFile, true));	
			} else {
				for(int currentLine = 0; currentLine < totalLines; currentLine++) {
					IOUtils.writeLines(Arrays.asList(new String[] {
							"| " + ((currentLine == 0) ? getFunctionName(matcher) : "") 
							+ " | " + ((currentLine == 0) ? getReturnType(matcher) : "") 
							+ " | " + ((currentLine < params.size()) ? params.get(currentLine) : "")
							+ " | " + ((currentLine < javadoc.size()) ? javadoc.get(currentLine) : "")
							+ " |"}), null, new FileOutputStream(outputFile, true));
				}
			}	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static void addSeparator(File outputFile) {
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
//		String outputFilePrefix = "C:\\Documents and Settings\\Daniel\\workspace\\op4j\\src\\site\\apt\\";
		
		String inputFilePrefix = "C:\\Development\\workspace-galileo\\op4j\\src\\main\\java\\org\\op4j\\functions\\";
		String outputFilePrefix = "C:\\Development\\workspace-galileo\\op4j\\src\\site\\apt\\";
				
		generateAllFnsDoc(new File(outputFilePrefix, "fnarray.apt"), Arrays.asList(new String[] {
				inputFilePrefix + "FnArray.java"
		}));
		
		generateAllFnsDoc(new File(outputFilePrefix, "fnboolean.apt"), Arrays.asList(new String[] {
				inputFilePrefix + "FnBoolean.java"
		}));
		
		generateAllFnsDoc(new File(outputFilePrefix, "fncalendar.apt"), Arrays.asList(new String[] {
				inputFilePrefix + "FnCalendar.java"
		}));
		
		generateAllFnsDoc(new File(outputFilePrefix, "fndate.apt"), Arrays.asList(new String[] {
				inputFilePrefix + "FnDate.java"
		}));
		
		generateAllFnsDoc(new File(outputFilePrefix, "fnlist.apt"), Arrays.asList(new String[] {
				inputFilePrefix + "FnList.java"
		}));
		
		generateAllFnsDoc(new File(outputFilePrefix, "fnmap.apt"), Arrays.asList(new String[] {
				inputFilePrefix + "FnMap.java"
		}));
		
		generateAllFnsDoc(new File(outputFilePrefix, "fnmath.apt"), Arrays.asList(new String[] {
				inputFilePrefix + "FnMath.java"
		}));
		
		generateAllFnsDoc(new File(outputFilePrefix, "fnnumber.apt"), Arrays.asList(new String[] {
				inputFilePrefix + "FnNumber.java"
		}));
		
		generateAllFnsDoc(new File(outputFilePrefix, "fnobject.apt"), Arrays.asList(new String[] {
				inputFilePrefix + "FnObject.java"
		}));
		
		generateAllFnsDoc(new File(outputFilePrefix, "fnset.apt"), Arrays.asList(new String[] {
				inputFilePrefix + "FnSet.java"
		}));
		
		generateAllFnsDoc(new File(outputFilePrefix, "fnstring.apt"), Arrays.asList(new String[] {
				inputFilePrefix + "FnString.java"
		}));
		
		generateAllFnsDoc(new File(outputFilePrefix, "team.apt"), Arrays.asList(new String[] {
				inputFilePrefix + "FnMapOf.java"
		}));
		
		System.out.println("All apt files have been generated");
		
		
	}

}
