package org.op4j.devutils.doc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
			
			//TODO Can have \s between parameters..as FnNumber has
			
			Pattern pattern = Pattern.compile("(\\/\\*[\\w\\/\\*\\s\\{\\@\\}\\(\\)\\.]*\\*\\/)?" +
					"\\s*public\\s+static\\s+(?:final)?(?:\\<[a-zA-Z\\,\\s]*\\>){0,1}\\s+((?:\\<[a-zA-Z\\,\\s]+\\>|[a-zA-Z])+)\\s+([a-zA-Z]+)\\s*" +
					"\\(\\s*((?:.*,?)*)\\)\\s*\\{");
			
			Matcher matcher = pattern.matcher(FileUtils
					.readFileToString(file));

			createTableHeader(outputFile);			
			while (matcher.find()) {				
				System.out.println("Match: " + matcher.group());
				System.out.println("Method name: " + getFunctionName(matcher));
				System.out.println("Return type: " + getReturnType(matcher));
				System.out.println("Params: " + getParams(matcher));
				
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
		return escape(matcher.group(2));
	}
	static String getFunctionName(Matcher matcher) {
		return escape(matcher.group(3));
	}
	static List<String> getParams(Matcher matcher) {
		//TODO Take into account Map<String, Object> map, boolean valid
		// Trim + remove final
		return Op.onArrayOf(Types.STRING, escape(matcher.group(4)).split(",")).forEach()
			.exec(Ognl.asString("trim()")).exec(new IFunction<String, String>() {
				public String execute(String object, ExecCtx ctx)
						throws Exception {
					if (StringUtils.startsWith(object, "final")) {
						return StringUtils.substringAfter(object, "final");
					} 
					return object;
				}				
			}).endFor().toList().get();
		
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
		
		
		System.out.println("All apt files have been generated");
		
		
	}

}
