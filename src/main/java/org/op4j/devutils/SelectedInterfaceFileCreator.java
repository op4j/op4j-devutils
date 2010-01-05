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

package org.op4j.devutils;

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
    private static final String REP_STRUCTUREPACKAGE = "%%STRUCTUREPACKAGE%%";
    private static final String REP_TARGETTYPE = "%%TARGETTYPE%%";
    private static final String REP_TARGETTYPEINLEVEL = "%%TARGETTYPEINLEVEL%%";
    private static final String REP_TARGETELEMENTINLEVEL = "%%TARGETELEMENTINLEVEL%%";
    private static final String REP_TARGETELEMENT = "%%TARGETELEMENT%%";
    private static final String REP_TARGETELEMENTKEY = "%%TARGETELEMENTKEY%%";
    private static final String REP_TARGETELEMENTVALUE = "%%TARGETELEMENTVALUE%%";
    private static final String REP_TARGETELEMENTVALUEINLEVEL = "%%TARGETELEMENTVALUEINLEVEL%%";
    private static final String REP_IMPORT = "%%IMPORT%%";
    
    
    private static int filesCreated = 0;
    
    
    private static final String[][] ONE_LEVEL_STRUCTURES =
    	{ 
    		new String[] {"Array", "T[]", "T"}, 
    		new String[] {"List", "List<T>", "T"}, 
    		new String[] {"Set", "Set<T>", "T"} 
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
    		"Level0%%STRUCTURE%%SelectedOperator.java",
    		"Level1%%STRUCTURE%%ElementsSelectedOperator.java",
    		"Level1%%STRUCTURE%%SelectedElementsOperator.java",
    		"Level1%%STRUCTURE%%SelectedElementsSelectedOperator.java"
    	};

    private static final int[] ONE_LEVEL_STRUCTURE_TARGETTYPE_IN_LEVEL_INDEXES =
    	{
    		1,
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
    

    
    

    
    
    private static final String[][] TWO_LEVEL_STRUCTURES =
    	{ 
    		new String[] {"ArrayOfArray", "T[][]", "T[]", "T"}, 
    		new String[] {"ArrayOfList", "List<T>[]", "List<T>", "T"}, 
    		new String[] {"ArrayOfSet", "Set<T>[]", "Set<T>", "T"}, 
    		new String[] {"ListOfArray", "List<T[]>", "T[]", "T"}, 
    		new String[] {"ListOfList", "List<List<T>>", "List<T>", "T"}, 
    		new String[] {"ListOfSet", "List<Set<T>>", "Set<T>", "T"}, 
    		new String[] {"SetOfArray", "Set<T[]>", "T[]", "T"}, 
    		new String[] {"SetOfList", "Set<List<T>>", "List<T>", "T"}, 
    		new String[] {"SetOfSet", "Set<Set<T>>", "Set<T>", "T"} 
    	};
    
    private static final String[] TWO_LEVEL_STRUCTURES_IMPORT =
    	{ 
    		"", 
    		"import java.util.List;", 
    		"import java.util.Set;", 
    		"import java.util.List;", 
    		"import java.util.List;", 
    		"import java.util.List;\nimport java.util.Set;", 
    		"import java.util.Set;", 
    		"import java.util.List;\nimport java.util.Set;", 
    		"import java.util.Set;", 
    	};
    
    private static final String[] TWO_LEVEL_STRUCTURE_TEMPLATES =
    	{ 
    		"TwoLevelStructureLevel0SelectedOperator.txt",
            "TwoLevelStructureLevel1ElementsSelectedOperator.txt",
            "TwoLevelStructureLevel1SelectedElementsOperator.txt",
            "TwoLevelStructureLevel1SelectedElementsSelectedOperator.txt",
            "TwoLevelStructureLevel2ElementsElementsSelectedOperator.txt",
            "TwoLevelStructureLevel2ElementsSelectedElementsOperator.txt",
            "TwoLevelStructureLevel2ElementsSelectedElementsSelectedOperator.txt",
            "TwoLevelStructureLevel2SelectedElementsElementsOperator.txt",
            "TwoLevelStructureLevel2SelectedElementsElementsSelectedOperator.txt",
            "TwoLevelStructureLevel2SelectedElementsSelectedElementsOperator.txt",
            "TwoLevelStructureLevel2SelectedElementsSelectedElementsSelectedOperator.txt"
    	};

    private static final String[] TWO_LEVEL_STRUCTURE_RESULTS =
    	{
    		"Level0%%STRUCTURE%%SelectedOperator.java",
            "Level1%%STRUCTURE%%ElementsSelectedOperator.java",
            "Level1%%STRUCTURE%%SelectedElementsOperator.java",
            "Level1%%STRUCTURE%%SelectedElementsSelectedOperator.java",
            "Level2%%STRUCTURE%%ElementsElementsSelectedOperator.java",
            "Level2%%STRUCTURE%%ElementsSelectedElementsOperator.java",
            "Level2%%STRUCTURE%%ElementsSelectedElementsSelectedOperator.java",
            "Level2%%STRUCTURE%%SelectedElementsElementsOperator.java",
            "Level2%%STRUCTURE%%SelectedElementsElementsSelectedOperator.java",
            "Level2%%STRUCTURE%%SelectedElementsSelectedElementsOperator.java",
            "Level2%%STRUCTURE%%SelectedElementsSelectedElementsSelectedOperator.java"
    	};

    private static final int[] TWO_LEVEL_STRUCTURE_TARGETTYPE_IN_LEVEL_INDEXES =
    	{
    		1,
    		2,
    		2,
    		2,
    		3,
    		3,
    		3,
    		3,
    		3,
    		3,
    		3
    	};

    private static final int[] TWO_LEVEL_STRUCTURE_TARGETELEMENT_IN_LEVEL_INDEXES =
    	{
    		2,
    		3,
    		3,
    		3,
    		0,
    		0,
    		0,
    		0,
    		0,
    		0,
    		0
    	};
   
    
    
    
    private static final String[][] TWO_LEVEL_MAP_STRUCTURES =
        { 
            new String[] {"ArrayOfMap", "Map<K,V>[]", "K", "V", "Map<K,V>", "Map.Entry<K,V>", "K", "V"}, 
            new String[] {"ListOfMap", "List<Map<K,V>>", "K", "V", "Map<K,V>", "Map.Entry<K,V>", "K", "V"}, 
            new String[] {"SetOfMap", "Set<Map<K,V>>", "K", "V", "Map<K,V>", "Map.Entry<K,V>", "K", "V"}, 
        };
    
    private static final String[] TWO_LEVEL_MAP_STRUCTURES_IMPORT =
    	{ 
    		"", 
    		"import java.util.List;", 
    		"import java.util.Set;", 
    	};
    
    private static final String[] TWO_LEVEL_MAP_STRUCTURE_TEMPLATES =
        { 
            "TwoLevelMapStructureLevel0SelectedOperator.txt",
            "TwoLevelMapStructureLevel1ElementsSelectedOperator.txt",
            "TwoLevelMapStructureLevel1SelectedElementsOperator.txt",
            "TwoLevelMapStructureLevel1SelectedElementsSelectedOperator.txt",
            "TwoLevelMapStructureLevel2ElementsEntriesSelectedOperator.txt",
            "TwoLevelMapStructureLevel2ElementsSelectedEntriesOperator.txt",
            "TwoLevelMapStructureLevel2ElementsSelectedEntriesSelectedOperator.txt",
            "TwoLevelMapStructureLevel2SelectedElementsEntriesOperator.txt",
            "TwoLevelMapStructureLevel2SelectedElementsEntriesSelectedOperator.txt",
            "TwoLevelMapStructureLevel2SelectedElementsSelectedEntriesOperator.txt",
            "TwoLevelMapStructureLevel2SelectedElementsSelectedEntriesSelectedOperator.txt",
            "TwoLevelMapStructureLevel3ElementsEntriesKeySelectedOperator.txt",
            "TwoLevelMapStructureLevel3ElementsEntriesSelectedKeyOperator.txt",
            "TwoLevelMapStructureLevel3ElementsEntriesSelectedKeySelectedOperator.txt",
            "TwoLevelMapStructureLevel3ElementsEntriesSelectedValueOperator.txt",
            "TwoLevelMapStructureLevel3ElementsEntriesSelectedValueSelectedOperator.txt",
            "TwoLevelMapStructureLevel3ElementsEntriesValueSelectedOperator.txt",
            "TwoLevelMapStructureLevel3ElementsSelectedEntriesKeyOperator.txt",
            "TwoLevelMapStructureLevel3ElementsSelectedEntriesKeySelectedOperator.txt",
            "TwoLevelMapStructureLevel3ElementsSelectedEntriesSelectedKeyOperator.txt",
            "TwoLevelMapStructureLevel3ElementsSelectedEntriesSelectedKeySelectedOperator.txt",
            "TwoLevelMapStructureLevel3ElementsSelectedEntriesSelectedValueOperator.txt",
            "TwoLevelMapStructureLevel3ElementsSelectedEntriesSelectedValueSelectedOperator.txt",
            "TwoLevelMapStructureLevel3ElementsSelectedEntriesValueOperator.txt",
            "TwoLevelMapStructureLevel3ElementsSelectedEntriesValueSelectedOperator.txt",
            "TwoLevelMapStructureLevel3SelectedElementsEntriesKeyOperator.txt",
            "TwoLevelMapStructureLevel3SelectedElementsEntriesKeySelectedOperator.txt",
            "TwoLevelMapStructureLevel3SelectedElementsEntriesSelectedKeyOperator.txt",
            "TwoLevelMapStructureLevel3SelectedElementsEntriesSelectedKeySelectedOperator.txt",
            "TwoLevelMapStructureLevel3SelectedElementsEntriesSelectedValueOperator.txt",
            "TwoLevelMapStructureLevel3SelectedElementsEntriesSelectedValueSelectedOperator.txt",
            "TwoLevelMapStructureLevel3SelectedElementsEntriesValueOperator.txt",
            "TwoLevelMapStructureLevel3SelectedElementsEntriesValueSelectedOperator.txt",
            "TwoLevelMapStructureLevel3SelectedElementsSelectedEntriesKeyOperator.txt",
            "TwoLevelMapStructureLevel3SelectedElementsSelectedEntriesKeySelectedOperator.txt",
            "TwoLevelMapStructureLevel3SelectedElementsSelectedEntriesSelectedKeyOperator.txt",
            "TwoLevelMapStructureLevel3SelectedElementsSelectedEntriesSelectedKeySelectedOperator.txt",
            "TwoLevelMapStructureLevel3SelectedElementsSelectedEntriesSelectedValueOperator.txt",
            "TwoLevelMapStructureLevel3SelectedElementsSelectedEntriesSelectedValueSelectedOperator.txt",
            "TwoLevelMapStructureLevel3SelectedElementsSelectedEntriesValueOperator.txt",
            "TwoLevelMapStructureLevel3SelectedElementsSelectedEntriesValueSelectedOperator.txt"
        };

    private static final String[] TWO_LEVEL_MAP_STRUCTURE_RESULTS =
        {
            "Level0%%STRUCTURE%%SelectedOperator.java",
            "Level1%%STRUCTURE%%ElementsSelectedOperator.java",
            "Level1%%STRUCTURE%%SelectedElementsOperator.java",
            "Level1%%STRUCTURE%%SelectedElementsSelectedOperator.java",
            "Level2%%STRUCTURE%%ElementsEntriesSelectedOperator.java",
            "Level2%%STRUCTURE%%ElementsSelectedEntriesOperator.java",
            "Level2%%STRUCTURE%%ElementsSelectedEntriesSelectedOperator.java",
            "Level2%%STRUCTURE%%SelectedElementsEntriesOperator.java",
            "Level2%%STRUCTURE%%SelectedElementsEntriesSelectedOperator.java",
            "Level2%%STRUCTURE%%SelectedElementsSelectedEntriesOperator.java",
            "Level2%%STRUCTURE%%SelectedElementsSelectedEntriesSelectedOperator.java",
            "Level3%%STRUCTURE%%ElementsEntriesKeySelectedOperator.java",
            "Level3%%STRUCTURE%%ElementsEntriesSelectedKeyOperator.java",
            "Level3%%STRUCTURE%%ElementsEntriesSelectedKeySelectedOperator.java",
            "Level3%%STRUCTURE%%ElementsEntriesSelectedValueOperator.java",
            "Level3%%STRUCTURE%%ElementsEntriesSelectedValueSelectedOperator.java",
            "Level3%%STRUCTURE%%ElementsEntriesValueSelectedOperator.java",
            "Level3%%STRUCTURE%%ElementsSelectedEntriesKeyOperator.java",
            "Level3%%STRUCTURE%%ElementsSelectedEntriesKeySelectedOperator.java",
            "Level3%%STRUCTURE%%ElementsSelectedEntriesSelectedKeyOperator.java",
            "Level3%%STRUCTURE%%ElementsSelectedEntriesSelectedKeySelectedOperator.java",
            "Level3%%STRUCTURE%%ElementsSelectedEntriesSelectedValueOperator.java",
            "Level3%%STRUCTURE%%ElementsSelectedEntriesSelectedValueSelectedOperator.java",
            "Level3%%STRUCTURE%%ElementsSelectedEntriesValueOperator.java",
            "Level3%%STRUCTURE%%ElementsSelectedEntriesValueSelectedOperator.java",
            "Level3%%STRUCTURE%%SelectedElementsEntriesKeyOperator.java",
            "Level3%%STRUCTURE%%SelectedElementsEntriesKeySelectedOperator.java",
            "Level3%%STRUCTURE%%SelectedElementsEntriesSelectedKeyOperator.java",
            "Level3%%STRUCTURE%%SelectedElementsEntriesSelectedKeySelectedOperator.java",
            "Level3%%STRUCTURE%%SelectedElementsEntriesSelectedValueOperator.java",
            "Level3%%STRUCTURE%%SelectedElementsEntriesSelectedValueSelectedOperator.java",
            "Level3%%STRUCTURE%%SelectedElementsEntriesValueOperator.java",
            "Level3%%STRUCTURE%%SelectedElementsEntriesValueSelectedOperator.java",
            "Level3%%STRUCTURE%%SelectedElementsSelectedEntriesKeyOperator.java",
            "Level3%%STRUCTURE%%SelectedElementsSelectedEntriesKeySelectedOperator.java",
            "Level3%%STRUCTURE%%SelectedElementsSelectedEntriesSelectedKeyOperator.java",
            "Level3%%STRUCTURE%%SelectedElementsSelectedEntriesSelectedKeySelectedOperator.java",
            "Level3%%STRUCTURE%%SelectedElementsSelectedEntriesSelectedValueOperator.java",
            "Level3%%STRUCTURE%%SelectedElementsSelectedEntriesSelectedValueSelectedOperator.java",
            "Level3%%STRUCTURE%%SelectedElementsSelectedEntriesValueOperator.java",
            "Level3%%STRUCTURE%%SelectedElementsSelectedEntriesValueSelectedOperator.java"
        };

    private static final int[] TWO_LEVEL_MAP_STRUCTURE_TARGETTYPE_IN_LEVEL_INDEXES =
        {
            1,
            4,
            4,
            4,
            5,
            5,
            5,
            5,
            5,
            5,
            5,
            6,
            6,
            6,
            7,
            7,
            7,
            6,
            6,
            6,
            6,
            7,
            7,
            7,
            7,
            6,
            6,
            6,
            6,
            7,
            7,
            7,
            7,
            6,
            6,
            6,
            6,
            7,
            7,
            7,
            7
        };

    private static final int[] TWO_LEVEL_MAP_STRUCTURE_TARGETELEMENT_IN_LEVEL_INDEXES =
        {
            4,
            5,
            5,
            5,
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
            0,
            0,
            0,
            0,
            0
        };
   

    
    
    
    
    
    
    
    
   
    
    
    
    private static final String[][] ONE_LEVEL_MAP_STRUCTURES =
        { 
            new String[] {"Map", "Map<K,V>", "K", "V", "Map.Entry<K,V>", "K", "V"}, 
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
            "Level0%%STRUCTURE%%SelectedOperator.java",
            "Level1%%STRUCTURE%%EntriesSelectedOperator.java",
            "Level1%%STRUCTURE%%SelectedEntriesOperator.java",
            "Level1%%STRUCTURE%%SelectedEntriesSelectedOperator.java",
            "Level2%%STRUCTURE%%EntriesKeySelectedOperator.java",
            "Level2%%STRUCTURE%%EntriesSelectedKeyOperator.java",
            "Level2%%STRUCTURE%%EntriesSelectedKeySelectedOperator.java",
            "Level2%%STRUCTURE%%EntriesSelectedValueOperator.java",
            "Level2%%STRUCTURE%%EntriesSelectedValueSelectedOperator.java",
            "Level2%%STRUCTURE%%EntriesValueSelectedOperator.java",
            "Level2%%STRUCTURE%%SelectedEntriesKeyOperator.java",
            "Level2%%STRUCTURE%%SelectedEntriesKeySelectedOperator.java",
            "Level2%%STRUCTURE%%SelectedEntriesSelectedKeyOperator.java",
            "Level2%%STRUCTURE%%SelectedEntriesSelectedKeySelectedOperator.java",
            "Level2%%STRUCTURE%%SelectedEntriesSelectedValueOperator.java",
            "Level2%%STRUCTURE%%SelectedEntriesSelectedValueSelectedOperator.java",
            "Level2%%STRUCTURE%%SelectedEntriesValueOperator.java",
            "Level2%%STRUCTURE%%SelectedEntriesValueSelectedOperator.java",
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
    
    
    

    
    
    
    
    
    
    
    
    
   
    
    
    
    private static final String[][] TWO_LEVEL_MAP_OF_STRUCTURES =
        { 
            new String[] {"MapOfArray", "Map<K,V[]>", "K", "V", "Map.Entry<K,V[]>", "V[]", "K", "V[]", "V"}, 
            new String[] {"MapOfList", "Map<K,List<V>>", "K", "V", "Map.Entry<K,List<V>>", "List<V>", "K", "List<V>", "V"}, 
            new String[] {"MapOfSet", "Map<K,Set<V>>", "K", "V", "Map.Entry<K,Set<V>>", "Set<V>", "K", "Set<V>", "V"} 
        };
    
    private static final String[] TWO_LEVEL_MAP_OF_STRUCTURES_IMPORT =
    	{ 
    		"", 
    		"import java.util.List;", 
    		"import java.util.Set;" 
    	};
    
    private static final String[] TWO_LEVEL_MAP_OF_STRUCTURE_TEMPLATES =
        { 
            "TwoLevelMapOfStructureLevel0SelectedOperator.txt",
            "TwoLevelMapOfStructureLevel1EntriesSelectedOperator.txt",
            "TwoLevelMapOfStructureLevel1SelectedEntriesOperator.txt",
            "TwoLevelMapOfStructureLevel1SelectedEntriesSelectedOperator.txt",
            "TwoLevelMapOfStructureLevel2EntriesKeySelectedOperator.txt",
            "TwoLevelMapOfStructureLevel2EntriesSelectedKeyOperator.txt",
            "TwoLevelMapOfStructureLevel2EntriesSelectedKeySelectedOperator.txt",
            "TwoLevelMapOfStructureLevel2EntriesSelectedValueOperator.txt",
            "TwoLevelMapOfStructureLevel2EntriesSelectedValueSelectedOperator.txt",
            "TwoLevelMapOfStructureLevel2EntriesValueSelectedOperator.txt",
            "TwoLevelMapOfStructureLevel2SelectedEntriesKeyOperator.txt",
            "TwoLevelMapOfStructureLevel2SelectedEntriesKeySelectedOperator.txt",
            "TwoLevelMapOfStructureLevel2SelectedEntriesSelectedKeyOperator.txt",
            "TwoLevelMapOfStructureLevel2SelectedEntriesSelectedKeySelectedOperator.txt",
            "TwoLevelMapOfStructureLevel2SelectedEntriesSelectedValueOperator.txt",
            "TwoLevelMapOfStructureLevel2SelectedEntriesSelectedValueSelectedOperator.txt",
            "TwoLevelMapOfStructureLevel2SelectedEntriesValueOperator.txt",
            "TwoLevelMapOfStructureLevel2SelectedEntriesValueSelectedOperator.txt",
            "TwoLevelMapOfStructureLevel3EntriesSelectedValueElementsOperator.txt",
            "TwoLevelMapOfStructureLevel3EntriesSelectedValueElementsSelectedOperator.txt",
            "TwoLevelMapOfStructureLevel3EntriesSelectedValueSelectedElementsOperator.txt",
            "TwoLevelMapOfStructureLevel3EntriesSelectedValueSelectedElementsSelectedOperator.txt",
            "TwoLevelMapOfStructureLevel3EntriesValueElementsSelectedOperator.txt",
            "TwoLevelMapOfStructureLevel3SelectedEntriesSelectedValueElementsOperator.txt",
            "TwoLevelMapOfStructureLevel3SelectedEntriesSelectedValueElementsSelectedOperator.txt",
            "TwoLevelMapOfStructureLevel3SelectedEntriesSelectedValueSelectedElementsOperator.txt",
            "TwoLevelMapOfStructureLevel3SelectedEntriesSelectedValueSelectedElementsSelectedOperator.txt",
            "TwoLevelMapOfStructureLevel3SelectedEntriesValueElementsOperator.txt",
            "TwoLevelMapOfStructureLevel3SelectedEntriesValueElementsSelectedOperator.txt",
            "TwoLevelMapOfStructureLevel3SelectedEntriesValueSelectedElementsOperator.txt",
            "TwoLevelMapOfStructureLevel3SelectedEntriesValueSelectedElementsSelectedOperator.txt"
        };

    private static final String[] TWO_LEVEL_MAP_OF_STRUCTURE_RESULTS =
        {
            "Level0%%STRUCTURE%%SelectedOperator.java",
            "Level1%%STRUCTURE%%EntriesSelectedOperator.java",
            "Level1%%STRUCTURE%%SelectedEntriesOperator.java",
            "Level1%%STRUCTURE%%SelectedEntriesSelectedOperator.java",
            "Level2%%STRUCTURE%%EntriesKeySelectedOperator.java",
            "Level2%%STRUCTURE%%EntriesSelectedKeyOperator.java",
            "Level2%%STRUCTURE%%EntriesSelectedKeySelectedOperator.java",
            "Level2%%STRUCTURE%%EntriesSelectedValueOperator.java",
            "Level2%%STRUCTURE%%EntriesSelectedValueSelectedOperator.java",
            "Level2%%STRUCTURE%%EntriesValueSelectedOperator.java",
            "Level2%%STRUCTURE%%SelectedEntriesKeyOperator.java",
            "Level2%%STRUCTURE%%SelectedEntriesKeySelectedOperator.java",
            "Level2%%STRUCTURE%%SelectedEntriesSelectedKeyOperator.java",
            "Level2%%STRUCTURE%%SelectedEntriesSelectedKeySelectedOperator.java",
            "Level2%%STRUCTURE%%SelectedEntriesSelectedValueOperator.java",
            "Level2%%STRUCTURE%%SelectedEntriesSelectedValueSelectedOperator.java",
            "Level2%%STRUCTURE%%SelectedEntriesValueOperator.java",
            "Level2%%STRUCTURE%%SelectedEntriesValueSelectedOperator.java",
            "Level3%%STRUCTURE%%EntriesSelectedValueElementsOperator.java",
            "Level3%%STRUCTURE%%EntriesSelectedValueElementsSelectedOperator.java",
            "Level3%%STRUCTURE%%EntriesSelectedValueSelectedElementsOperator.java",
            "Level3%%STRUCTURE%%EntriesSelectedValueSelectedElementsSelectedOperator.java",
            "Level3%%STRUCTURE%%EntriesValueElementsSelectedOperator.java",
            "Level3%%STRUCTURE%%SelectedEntriesSelectedValueElementsOperator.java",
            "Level3%%STRUCTURE%%SelectedEntriesSelectedValueElementsSelectedOperator.java",
            "Level3%%STRUCTURE%%SelectedEntriesSelectedValueSelectedElementsOperator.java",
            "Level3%%STRUCTURE%%SelectedEntriesSelectedValueSelectedElementsSelectedOperator.java",
            "Level3%%STRUCTURE%%SelectedEntriesValueElementsOperator.java",
            "Level3%%STRUCTURE%%SelectedEntriesValueElementsSelectedOperator.java",
            "Level3%%STRUCTURE%%SelectedEntriesValueSelectedElementsOperator.java",
            "Level3%%STRUCTURE%%SelectedEntriesValueSelectedElementsSelectedOperator.java"
        };

    private static final int[] TWO_LEVEL_MAP_OF_STRUCTURE_TARGETTYPE_IN_LEVEL_INDEXES =
        {
            1,
            4,
            4,
            4,
            6,
            6,
            6,
            7,
            7,
            7,
            6,
            6,
            6,
            6,
            7,
            7,
            7,
            7,
            8,
            8,
            8,
            8,
            8,
            8,
            8,
            8,
            8,
            8,
            8,
            8,
            8
        };

    private static final int[] TWO_LEVEL_MAP_OF_STRUCTURE_TARGETELEMENT_IN_LEVEL_INDEXES =
        {
	        4,
            0,
            0,
            0,
            7,
            7,
            7,
            8,
            8,
            8,
            7,
            7,
            7,
            7,
            8,
            8,
            8,
            8,
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

    private static final int[] TWO_LEVEL_MAP_OF_STRUCTURE_TARGETELEMENTVALUE_IN_LEVEL_INDEXES =
        {
	        5,
	        5,
	        5,
	        5,
	        5,
	        5,
	        5,
	        5,
	        5,
	        5,
	        5,
	        5,
	        5,
	        5,
	        5,
	        5,
	        5,
	        5,
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
    
    
    
    private static List<String> setFileNamePrefixes = 
        Arrays.asList(
                new String[] {
                        "Level0SetOperator",
                        "Level0SetSelected",
                        "Level1ArrayOfSet",
                        "Level1ListOfSet",
                        "Level2MapOfSet",
                        "Level0SetOf",
                        "Level1SetOfSet",
                });
    
    
    
    
    
    
    
    
    

    private static String replacePlaceholders(final String line,
    		final String structureName, final String targetType, 
    		final String targetTypeInLevel,
    		final String targetElement, final String targetElementKey, final String targetElementValue, final String targetElementInLevel, final String targetElementValueInLevel, 
    		final String structureImport) {
    	
		return line.
				replaceAll(REP_STRUCTURE, structureName).
                replaceAll(REP_STRUCTUREPACKAGE, structureName.toLowerCase()).
				replaceAll(REP_TARGETTYPE, targetType).
				replaceAll(REP_TARGETTYPEINLEVEL, targetTypeInLevel).
				replaceAll(REP_TARGETELEMENTINLEVEL, targetElementInLevel).
				replaceAll(REP_TARGETELEMENT, targetElement).
                replaceAll(REP_TARGETELEMENTKEY, targetElementKey).
                replaceAll(REP_TARGETELEMENTVALUE, targetElementValue).
                replaceAll(REP_TARGETELEMENTVALUEINLEVEL, targetElementValueInLevel).
                replaceAll(REP_IMPORT, structureImport);
		
    }
    
	
    
	private static void createFile(
			final String templateName, final String resultFileName,
			final String structureName, final String targetType, final String targetTypeInLevel, 
			final String targetElement, final String targetElementKey, final String targetElementValue, final String targetElementInLevel, final String targetElementValueInLevel,
			final String structureImport) 
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
		
		String line = null;
		final StringBuilder contentBuilder = new StringBuilder();
		while ((line = reader.readLine()) != null) {
			String newLine = 
				replacePlaceholders(line, structureName, targetType, targetTypeInLevel, targetElement, targetElementKey, targetElementValue, targetElementInLevel, targetElementValueInLevel, structureImport);
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
        		
    			createFile(
    					ONE_LEVEL_STRUCTURE_TEMPLATES[j],
    					ONE_LEVEL_STRUCTURE_RESULTS[j].replaceAll(REP_STRUCTURE, structureName),
    					structureName, 
    					firstLevelTargetType, 
    					structure[ONE_LEVEL_STRUCTURE_TARGETTYPE_IN_LEVEL_INDEXES[j]], 
    					lastLevelTargetType,
    					null,
    					null,
    					structure[ONE_LEVEL_STRUCTURE_TARGETELEMENT_IN_LEVEL_INDEXES[j]],
    					null,
    					structureImport);
    			
    		}
    		
    	}
    	
    }
	
    
    
    private static void createTwoLevelStructures() throws Exception {
    	
    	for (int i = 0; i< TWO_LEVEL_STRUCTURES.length; i++) {
    		
    		final String[] structure = TWO_LEVEL_STRUCTURES[i];
    		
    		final String structureName = structure[0];
    		final String firstLevelTargetType = structure[1];
    		final String lastLevelTargetType = structure[3];
    		
    		final String structureImport = TWO_LEVEL_STRUCTURES_IMPORT[i];

    		for (int j = 0; j < TWO_LEVEL_STRUCTURE_TEMPLATES.length; j++) {
        		
    			createFile(
    					TWO_LEVEL_STRUCTURE_TEMPLATES[j],
    					TWO_LEVEL_STRUCTURE_RESULTS[j].replaceAll(REP_STRUCTURE, structureName),
    					structureName, 
    					firstLevelTargetType, 
    					structure[TWO_LEVEL_STRUCTURE_TARGETTYPE_IN_LEVEL_INDEXES[j]], 
    					lastLevelTargetType,
    					null,
    					null,
    					structure[TWO_LEVEL_STRUCTURE_TARGETELEMENT_IN_LEVEL_INDEXES[j]],
    					null,
    					structureImport);
    			
    		}
    		
    	}
    	
    }
	
    
    
    
    
    private static void createTwoLevelMapStructures() throws Exception {
        
        for (int i = 0; i< TWO_LEVEL_MAP_STRUCTURES.length; i++) {
            
            final String[] structure = TWO_LEVEL_MAP_STRUCTURES[i];
            
            final String structureName = structure[0];
            final String firstLevelTargetType = structure[1];
            final String lastLevelTargetTypeKey = structure[2];
            final String lastLevelTargetTypeValue = structure[3];
    		
    		final String structureImport = TWO_LEVEL_MAP_STRUCTURES_IMPORT[i];

            for (int j = 0; j < TWO_LEVEL_MAP_STRUCTURE_TEMPLATES.length; j++) {
                
                createFile(
                        TWO_LEVEL_MAP_STRUCTURE_TEMPLATES[j],
                        TWO_LEVEL_MAP_STRUCTURE_RESULTS[j].replaceAll(REP_STRUCTURE, structureName),
                        structureName, 
                        firstLevelTargetType, 
                        structure[TWO_LEVEL_MAP_STRUCTURE_TARGETTYPE_IN_LEVEL_INDEXES[j]], 
                        null,
                        lastLevelTargetTypeKey,
                        lastLevelTargetTypeValue,
                        structure[TWO_LEVEL_MAP_STRUCTURE_TARGETELEMENT_IN_LEVEL_INDEXES[j]],
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
                
                createFile(
                        ONE_LEVEL_MAP_STRUCTURE_TEMPLATES[j],
                        ONE_LEVEL_MAP_STRUCTURE_RESULTS[j].replaceAll(REP_STRUCTURE, structureName),
                        structureName, 
                        firstLevelTargetType, 
                        structure[ONE_LEVEL_MAP_STRUCTURE_TARGETTYPE_IN_LEVEL_INDEXES[j]], 
                        null,
                        lastLevelTargetTypeKey,
                        lastLevelTargetTypeValue,
                        structure[ONE_LEVEL_MAP_STRUCTURE_TARGETELEMENT_IN_LEVEL_INDEXES[j]],
    					null,
                        structureImport);
                
            }
            
        }
        
    }
    

    
    
    

    
    
    
    private static void createTwoLevelMapOfStructures() throws Exception {
        
        for (int i = 0; i< TWO_LEVEL_MAP_OF_STRUCTURES.length; i++) {
            
            final String[] structure = TWO_LEVEL_MAP_OF_STRUCTURES[i];
            
            final String structureName = structure[0];
            final String firstLevelTargetType = structure[1];
            final String lastLevelTargetTypeKey = structure[2];
            final String lastLevelTargetTypeValue = structure[3];
    		
    		final String structureImport = TWO_LEVEL_MAP_OF_STRUCTURES_IMPORT[i];

            for (int j = 0; j < TWO_LEVEL_MAP_OF_STRUCTURE_TEMPLATES.length; j++) {
                
                createFile(
                		TWO_LEVEL_MAP_OF_STRUCTURE_TEMPLATES[j],
                		TWO_LEVEL_MAP_OF_STRUCTURE_RESULTS[j].replaceAll(REP_STRUCTURE, structureName),
                        structureName, 
                        firstLevelTargetType, 
                        structure[TWO_LEVEL_MAP_OF_STRUCTURE_TARGETTYPE_IN_LEVEL_INDEXES[j]], 
                        null,
                        lastLevelTargetTypeKey,
                        lastLevelTargetTypeValue,
                        structure[TWO_LEVEL_MAP_OF_STRUCTURE_TARGETELEMENT_IN_LEVEL_INDEXES[j]],
                        structure[TWO_LEVEL_MAP_OF_STRUCTURE_TARGETELEMENTVALUE_IN_LEVEL_INDEXES[j]],
                        structureImport);
                
            }
            
        }
        
    }
    
    
    
    
	
	public static void main(String[] args) {
	
		
		try {

			createOneLevelStructures();
			createTwoLevelStructures();
			createTwoLevelMapStructures();
			createOneLevelMapStructures();
			createTwoLevelMapOfStructures();

			System.out.println("\n " + filesCreated + " files created.");
			
		} catch (final Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
}
