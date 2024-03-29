/*
 * =============================================================================
 * 
 *   Copyright (c) 2008, The OP4J team (http://www.op4j.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */

package org.op4j.devutils.selected;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;


/**
 * 
 * @since 1.0
 * 
 * @author Daniel Fern&aacute;ndez
 *
 */
public class SelectedInterfaceFileCreator {

    private static final String REP_STRUCTURE = "%%STRUCTURE%%";
    private static final String REP_EQUIVALENTSTRUCTURE = "%%EQUIVALENTSTRUCTURE%%";
    private static final String REP_STRUCTUREPACKAGE = "%%STRUCTUREPACKAGE%%";
    private static final String REP_TARGETTYPE = "%%TARGETTYPE%%";
    private static final String REP_TARGETTYPEINLEVEL = "%%TARGETTYPEINLEVEL%%";
    private static final String REP_FLEXIBLETARGETTYPEINLEVEL = "%%FLEXIBLETARGETTYPEINLEVEL%%";
    private static final String REP_TARGETELEMENTINLEVEL = "%%TARGETELEMENTINLEVEL%%";
    private static final String REP_TARGETELEMENT = "%%TARGETELEMENT%%";
    private static final String REP_TARGETELEMENTKEY = "%%TARGETELEMENTKEY%%";
    private static final String REP_TARGETELEMENTVALUE = "%%TARGETELEMENTVALUE%%";
    private static final String REP_TARGETELEMENTVALUEINLEVEL = "%%TARGETELEMENTVALUEINLEVEL%%";
    private static final String REP_IMPORT = "%%IMPORT%%";
    private static final String REP_FOREACHELEMENTTYPE = "%%FOREACHELEMENTTYPE%%";
    
    
    private static int filesCreated = 0;
    

    
    
    private static final String[][] ONE_LEVEL_STRUCTURES =
    	{ 
    		new String[] {"Array", "T[]", "T", "T[]"}, 
    		new String[] {"List", "List<T>", "T", "List<? extends T>"}, 
    		new String[] {"Set", "Set<T>", "T", "Set<? extends T>"} 
    	};
    
    private static final String[][] ONE_LEVEL_STRUCTURE_EQUIVALENCES =
        { 
            new String[] {"Array", ""}, 
            new String[] {"List", ""}, 
            new String[] {"Set", ""} 
        };
    
    private static final String[] ONE_LEVEL_STRUCTURES_IMPORT =
    	{ 
    		"", 
    		"import java.util.List;", 
    		"import java.util.Set;" 
    	};
    
    private static final String[] ONE_LEVEL_STRUCTURE_TEMPLATES =
    	{ 
    		"OneLevelStructureLevel0SelectedOperator.txt",
    		"OneLevelStructureLevel1ElementsSelectedOperator.txt",
    		"OneLevelStructureLevel1SelectedElementsOperator.txt",
    		"OneLevelStructureLevel1SelectedElementsSelectedOperator.txt"
    	};

    private static final String[] ONE_LEVEL_STRUCTURE_RESULTS =
    	{
    		"ILevel0%%STRUCTURE%%SelectedOperator.java",
    		"ILevel1%%STRUCTURE%%ElementsSelectedOperator.java",
    		"ILevel1%%STRUCTURE%%SelectedElementsOperator.java",
    		"ILevel1%%STRUCTURE%%SelectedElementsSelectedOperator.java"
    	};

    private static final int[] ONE_LEVEL_STRUCTURE_TARGETTYPE_IN_LEVEL_INDEXES =
    	{
    		1,
    		2,
    		2,
    		2
    	};

    private static final int[] ONE_LEVEL_STRUCTURE_FLEXIBLETARGETTYPE_IN_LEVEL_INDEXES =
        {
            3,
            2,
            2,
            2
        };

    private static final int[] ONE_LEVEL_STRUCTURE_TARGETELEMENT_IN_LEVEL_INDEXES =
    	{
    		2,
    		0,
    		0,
    		0
    	};
    

    
    
   

    
    
    
    
    
    
    
    
   
    
    
    
    private static final String[][] ONE_LEVEL_MAP_STRUCTURES =
        { 
            new String[] {"Map", "Map<K,V>", "K", "V", "Map.Entry<K,V>", "K", "V", "Map<? extends K,? extends V>", "Map.Entry<? extends K,? extends V>"}, 
        };
    
    private static final String[][] ONE_LEVEL_MAP_STRUCTURE_EQUIVALENCES =
        { 
            new String[] {"Map", "MapEntry", ""}, 
        };
    
    private static final String[] ONE_LEVEL_MAP_STRUCTURES_IMPORT =
    	{ 
    		"", 
    	};
    
    private static final String[] ONE_LEVEL_MAP_STRUCTURE_TEMPLATES =
        { 
            "OneLevelMapStructureLevel0SelectedOperator.txt",
            "OneLevelMapStructureLevel1EntriesSelectedOperator.txt",
            "OneLevelMapStructureLevel1SelectedEntriesOperator.txt",
            "OneLevelMapStructureLevel1SelectedEntriesSelectedOperator.txt",
            "OneLevelMapStructureLevel2EntriesKeySelectedOperator.txt",
            "OneLevelMapStructureLevel2EntriesSelectedKeyOperator.txt",
            "OneLevelMapStructureLevel2EntriesSelectedKeySelectedOperator.txt",
            "OneLevelMapStructureLevel2EntriesSelectedValueOperator.txt",
            "OneLevelMapStructureLevel2EntriesSelectedValueSelectedOperator.txt",
            "OneLevelMapStructureLevel2EntriesValueSelectedOperator.txt",
            "OneLevelMapStructureLevel2SelectedEntriesKeyOperator.txt",
            "OneLevelMapStructureLevel2SelectedEntriesKeySelectedOperator.txt",
            "OneLevelMapStructureLevel2SelectedEntriesSelectedKeyOperator.txt",
            "OneLevelMapStructureLevel2SelectedEntriesSelectedKeySelectedOperator.txt",
            "OneLevelMapStructureLevel2SelectedEntriesSelectedValueOperator.txt",
            "OneLevelMapStructureLevel2SelectedEntriesSelectedValueSelectedOperator.txt",
            "OneLevelMapStructureLevel2SelectedEntriesValueOperator.txt",
            "OneLevelMapStructureLevel2SelectedEntriesValueSelectedOperator.txt",
        };

    private static final String[] ONE_LEVEL_MAP_STRUCTURE_RESULTS =
        {
            "ILevel0%%STRUCTURE%%SelectedOperator.java",
            "ILevel1%%STRUCTURE%%EntriesSelectedOperator.java",
            "ILevel1%%STRUCTURE%%SelectedEntriesOperator.java",
            "ILevel1%%STRUCTURE%%SelectedEntriesSelectedOperator.java",
            "ILevel2%%STRUCTURE%%EntriesKeySelectedOperator.java",
            "ILevel2%%STRUCTURE%%EntriesSelectedKeyOperator.java",
            "ILevel2%%STRUCTURE%%EntriesSelectedKeySelectedOperator.java",
            "ILevel2%%STRUCTURE%%EntriesSelectedValueOperator.java",
            "ILevel2%%STRUCTURE%%EntriesSelectedValueSelectedOperator.java",
            "ILevel2%%STRUCTURE%%EntriesValueSelectedOperator.java",
            "ILevel2%%STRUCTURE%%SelectedEntriesKeyOperator.java",
            "ILevel2%%STRUCTURE%%SelectedEntriesKeySelectedOperator.java",
            "ILevel2%%STRUCTURE%%SelectedEntriesSelectedKeyOperator.java",
            "ILevel2%%STRUCTURE%%SelectedEntriesSelectedKeySelectedOperator.java",
            "ILevel2%%STRUCTURE%%SelectedEntriesSelectedValueOperator.java",
            "ILevel2%%STRUCTURE%%SelectedEntriesSelectedValueSelectedOperator.java",
            "ILevel2%%STRUCTURE%%SelectedEntriesValueOperator.java",
            "ILevel2%%STRUCTURE%%SelectedEntriesValueSelectedOperator.java",
        };

    private static final int[] ONE_LEVEL_MAP_STRUCTURE_TARGETTYPE_IN_LEVEL_INDEXES =
        {
            1,
            4,
            4,
            4,
            5,
            5,
            5,
            6,
            6,
            6,
            5,
            5,
            5,
            5,
            6,
            6,
            6,
            6
        };

    private static final int[] ONE_LEVEL_MAP_STRUCTURE_FLEXIBLETARGETTYPE_IN_LEVEL_INDEXES =
        {
            7,
            8,
            8,
            8,
            5,
            5,
            5,
            6,
            6,
            6,
            5,
            5,
            5,
            5,
            6,
            6,
            6,
            6
        };

    private static final int[] ONE_LEVEL_MAP_STRUCTURE_TARGETELEMENT_IN_LEVEL_INDEXES =
        {
            4,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0
        };
    
    
    

    
    
    
    
    
    
    
    
    
   
    
    
    
    private static List<String> deletablePatternsForSets = 
        Arrays.asList(
                new String[] {
                        "import org\\.op4j\\.operators\\.qualities\\.DistinguishableOperator;\\s*\\n\\s*",
                        "DistinguishableOperator,\\s*\\n\\s*",
                        "public (.*?) distinct\\(\\);\\s*\\n\\s*"
                });
    
    
    
    private static final List<String> setFileNamePrefixes = 
        Arrays.asList(
                new String[] {
                        "ILevel0SetOperator",
                        "ILevel0SetSelected",
                        "ILevel1ArrayOfSet",
                        "ILevel1ListOfSet",
                        "ILevel2MapOfSet",
                        "ILevel0SetOf",
                        "ILevel1SetOfSet",
                });
    
    
    
    private static final List<String> navigableArrayPrefixes =
        Arrays.asList(
            new String[] { 
                "ILevel0ArrayOperator",
                "ILevel0ArraySelected",
                "ILevel0ArrayOfArray",
                "ILevel1ArrayOfArray",
                "ILevel1ListOfArray",
                "ILevel2MapOfArray",
                "ILevel1SetOfArray"
            });
    
    
    private static final List<String> navigatingArrayPrefixes =
        Arrays.asList(
            new String[] { 
                "ILevel1ArrayOperator",
                "ILevel1ArraySelected",
                "ILevel1ArrayElements",
                "ILevel1ArrayOfArray",
                "ILevel2ArrayOfArray",
                "ILevel2ListOfArray",
                "ILevel3MapOfArray",
                "ILevel2SetOfArray"
            });
    
    
    
    
    
    
    
    
    

    private static String replacePlaceholders(final String line,
    		final String structureName, final String equivalentStructure, final String targetType, 
    		final String targetTypeInLevel, final String flexibleTargetTypeInLevel,
    		final String targetElement, final String targetElementKey, final String targetElementValue, final String targetElementInLevel, final String targetElementValueInLevel, 
    		final String structureImport, final String forEachElementType) {
    	
		return line.
		        replaceAll(REP_FOREACHELEMENTTYPE, forEachElementType).
				replaceAll(REP_STRUCTURE, structureName).
                replaceAll(REP_EQUIVALENTSTRUCTURE, equivalentStructure).
                replaceAll(REP_STRUCTUREPACKAGE, structureName.toLowerCase()).
				replaceAll(REP_TARGETTYPE, targetType).
				replaceAll(REP_TARGETTYPEINLEVEL, targetTypeInLevel).
				replaceAll(REP_FLEXIBLETARGETTYPEINLEVEL, flexibleTargetTypeInLevel).
				replaceAll(REP_TARGETELEMENTINLEVEL, targetElementInLevel).
				replaceAll(REP_TARGETELEMENT, targetElement).
                replaceAll(REP_TARGETELEMENTKEY, targetElementKey).
                replaceAll(REP_TARGETELEMENTVALUE, targetElementValue).
                replaceAll(REP_TARGETELEMENTVALUEINLEVEL, targetElementValueInLevel).
                replaceAll(REP_IMPORT, structureImport);
		
    }
    
	
    
	private static void createFile(
			final String templateName, final String resultFileName,
			final String structureName, final String equivalentStructure, final String targetType, final String targetTypeInLevel, final String flexibleTargetTypeInLevel,  
			final String targetElement, final String targetElementKey, final String targetElementValue, final String targetElementInLevel, final String targetElementValueInLevel,
			String structureImport) 
			throws Exception {
		
		final URL templateFileURL = Thread.currentThread().getContextClassLoader().getResource(templateName);
		final BufferedReader reader = new BufferedReader(new InputStreamReader(templateFileURL.openStream()));
		
		final String operatorsFolderName =
		    (StringUtils.substringBeforeLast(StringUtils.substringBeforeLast(templateFileURL.getPath(), "/"),"/") + "/operators").replaceAll("%20", " ");
		
		final File operatorsFolder = new File(operatorsFolderName);
		operatorsFolder.mkdir();
        
        
        final File intfFolder = new File(operatorsFolderName + "/intf");
        intfFolder.mkdir();
		
		
        final File structureFolder = new File(operatorsFolderName + "/intf/" + structureName.toLowerCase());
        structureFolder.mkdir();
        
		final File resultFile = new File(operatorsFolderName + "/intf/" + structureName.toLowerCase() + "/" + resultFileName);
		final FileWriter writer = new FileWriter(resultFile);
		
		final boolean isSetFile = isSetFile(resultFileName);
		final boolean isNavigableArray = isNavigableArrayFile(resultFileName);
        final boolean isNavigatingArray = isNavigatingArrayFile(resultFileName);

        if (isNavigableArray) {
            structureImport += "import org.javaruntype.type.Type;\n";
        }
        
		String forEachElementType = "";
		String line = null;
		final StringBuilder contentBuilder = new StringBuilder();
		while ((line = reader.readLine()) != null) {
/*
 * These lines are commented out while testing the removal of the Type parameter in forEach()
 */
/*
		    if (isNavigableArray) {
		        line = line.replaceAll("NavigableCollectionOperator", "NavigableArrayOperator");
                forEachElementType = "final Type<%%TARGETELEMENTINLEVEL%%> elementType";
		    }
            if (isNavigatingArray) {
                line = line.replaceAll("NavigatingCollectionOperator", "NavigatingArrayOperator");
            }
*/
			String newLine = 
				replacePlaceholders(line, structureName, equivalentStructure, targetType, targetTypeInLevel, flexibleTargetTypeInLevel, targetElement, targetElementKey, targetElementValue, targetElementInLevel, targetElementValueInLevel, structureImport, forEachElementType);
			contentBuilder.append(newLine + "\n");
		}
		
		String content = contentBuilder.toString();
		
        if (isSetFile) {
            content = applySetFileDeletions(content);
        }
        
        writer.write(content);
        
		reader.close();
		writer.close();
		
		System.out.println("Created: " + resultFile.getAbsolutePath());
		filesCreated++;
		
	}
	
	
	
	private static boolean isSetFile(final String resultFileName) {
	    for (final String setFileNamePrefix : setFileNamePrefixes) {
	        if (resultFileName.startsWith(setFileNamePrefix)) {
	            return true;
	        }
	    }
	    return false;
	}


	
	
    private static boolean isNavigableArrayFile(final String resultFileName) {
        for (final String navigableArrayFileNamePrefix : navigableArrayPrefixes) {
            if (resultFileName.startsWith(navigableArrayFileNamePrefix)) {
                return true;
            }
        }
        return false;
    }

    
    
    private static boolean isNavigatingArrayFile(final String resultFileName) {
        for (final String navigatingArrayFileNamePrefix : navigatingArrayPrefixes) {
            if (resultFileName.startsWith(navigatingArrayFileNamePrefix)) {
                return true;
            }
        }
        return false;
    }
    
    
    
    private static String applySetFileDeletions(final String line) {
        String newLine = line;
        for (final String pattern : deletablePatternsForSets) {
            newLine = newLine.replaceAll(pattern,"");
        }
        return newLine;
    }
    
    
    
    private static void createOneLevelStructures() throws Exception {
    	
    	for (int i = 0; i< ONE_LEVEL_STRUCTURES.length; i++) {
    		
    		final String[] structure = ONE_LEVEL_STRUCTURES[i];
    		
    		final String structureName = structure[0];
    		final String firstLevelTargetType = structure[1];
    		final String lastLevelTargetType = structure[2];
    		
    		final String structureImport = ONE_LEVEL_STRUCTURES_IMPORT[i];

    		for (int j = 0; j < ONE_LEVEL_STRUCTURE_TEMPLATES.length; j++) {

    		    final String resultsFileName = 
    		        ONE_LEVEL_STRUCTURE_RESULTS[j].replaceAll(REP_STRUCTURE, structureName);
    		    final int level =
    		        Integer.valueOf(resultsFileName.substring(6, 7));
    		    
    			createFile(
    					ONE_LEVEL_STRUCTURE_TEMPLATES[j],
    					resultsFileName,
    					structureName, 
                        ONE_LEVEL_STRUCTURE_EQUIVALENCES[i][level],
    					firstLevelTargetType, 
    					structure[ONE_LEVEL_STRUCTURE_TARGETTYPE_IN_LEVEL_INDEXES[j]], 
                        structure[ONE_LEVEL_STRUCTURE_FLEXIBLETARGETTYPE_IN_LEVEL_INDEXES[j]], 
    					lastLevelTargetType,
    					null,
    					null,
    					structure[ONE_LEVEL_STRUCTURE_TARGETELEMENT_IN_LEVEL_INDEXES[j]],
    					null,
    					structureImport);
    			
    		}
    		
    	}
    	
    }
	

    
    
    
    
    
    private static void createOneLevelMapStructures() throws Exception {
        
        for (int i = 0; i< ONE_LEVEL_MAP_STRUCTURES.length; i++) {
            
            final String[] structure = ONE_LEVEL_MAP_STRUCTURES[i];
            
            final String structureName = structure[0];
            final String firstLevelTargetType = structure[1];
            final String lastLevelTargetTypeKey = structure[2];
            final String lastLevelTargetTypeValue = structure[3];
    		
    		final String structureImport = ONE_LEVEL_MAP_STRUCTURES_IMPORT[i];

            for (int j = 0; j < ONE_LEVEL_MAP_STRUCTURE_TEMPLATES.length; j++) {
                
                final String resultsFileName = 
                    ONE_LEVEL_MAP_STRUCTURE_RESULTS[j].replaceAll(REP_STRUCTURE, structureName);
                final int level =
                    Integer.valueOf(resultsFileName.substring(6, 7));
                
                createFile(
                        ONE_LEVEL_MAP_STRUCTURE_TEMPLATES[j],
                        resultsFileName,
                        structureName, 
                        ONE_LEVEL_MAP_STRUCTURE_EQUIVALENCES[i][level],
                        firstLevelTargetType, 
                        structure[ONE_LEVEL_MAP_STRUCTURE_TARGETTYPE_IN_LEVEL_INDEXES[j]], 
                        structure[ONE_LEVEL_MAP_STRUCTURE_FLEXIBLETARGETTYPE_IN_LEVEL_INDEXES[j]], 
                        null,
                        lastLevelTargetTypeKey,
                        lastLevelTargetTypeValue,
                        structure[ONE_LEVEL_MAP_STRUCTURE_TARGETELEMENT_IN_LEVEL_INDEXES[j]],
    					null,
                        structureImport);
                
            }
            
        }
        
    }
    

    
    
    
    
	
	public static void main(String[] args) {
	
		
		try {

			createOneLevelStructures();
			createOneLevelMapStructures();

			System.out.println("\n " + filesCreated + " files created.");
			
		} catch (final Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
}
