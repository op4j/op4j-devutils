package org.op4j.devutils;

import java.lang.reflect.Array;
import java.util.LinkedHashMap;
import java.util.Map;

import org.op4j.devutils.ImplFile.LevelStructure;

public class MethodImplementor {

    
    
    
    private static Map<String,String> methods;
    
    
    
    static {
        
        methods = new LinkedHashMap<String, String>();


        
        methods.put(
                "public (.*?)<(.*?)> ifIndex\\(final int\\.\\.\\. indices\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifIndex(final int... indices) {\n        return new $1Impl<$2>(%%ARRAYOF%%getTarget().selectIndex(indices));\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifIndexNot\\(final int\\.\\.\\. indices\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifIndexNot(final int... indices) {\n        return new $1Impl<$2>(%%ARRAYOF%%getTarget().selectIndexNot(indices));\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifMatching\\(final IEvaluator<Boolean,\\? super (.*?)> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifMatching(final IEvaluator<Boolean, ? super $3> eval) {\n        return new $1Impl<$2>(%%ARRAYOF%%getTarget().selectMatching(eval));\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifNotMatching\\(final IEvaluator<Boolean,\\? super (.*?)> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifNotMatching(final IEvaluator<Boolean, ? super $3> eval) {\n        return new $1Impl<$2>(%%ARRAYOF%%getTarget().selectNotMatching(eval));\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifNull\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifNull() {\n        return new $1Impl<$2>(%%ARRAYOF%%getTarget().selectNull());\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifNotNull\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifNotNull() {\n        return new $1Impl<$2>(%%ARRAYOF%%getTarget().selectNotNull());\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifNotNullMatching\\(final IEvaluator<Boolean,\\? super (.*?)> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifNotNullMatching(final IEvaluator<Boolean, ? super $3> eval) {\n        return new $1Impl<$2>(%%ARRAYOF%%getTarget().selectNotNullAndMatching(eval));\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifNotNullNotMatching\\(final IEvaluator<Boolean,\\? super (.*?)> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifNotNullNotMatching(final IEvaluator<Boolean, ? super $3> eval) {\n        return new $1Impl<$2>(%%ARRAYOF%%getTarget().selectNotNullAndNotMatching(eval));\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifNullOrMatching\\(final IEvaluator<Boolean,\\? super (.*?)> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifNullOrMatching(final IEvaluator<Boolean, ? super $3> eval) {\n        return new $1Impl<$2>(%%ARRAYOF%%getTarget().selectNullOrMatching(eval));\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifNullOrNotMatching\\(final IEvaluator<Boolean,\\? super (.*?)> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifNullOrNotMatching(final IEvaluator<Boolean, ? super $3> eval) {\n        return new $1Impl<$2>(%%ARRAYOF%%getTarget().selectNullOrNotMatching(eval));\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifKeyEquals\\(final (.*?)\\.\\.\\. keys\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifKeyEquals(final $3\\.\\.\\. keys) {\n        return new $1Impl<$2>(%%ARRAYOF%%getTarget().selectMapKeys(keys));\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifKeyNotEquals\\(final (.*?)\\.\\.\\. keys\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifKeyNotEquals(final $3\\.\\.\\. keys) {\n        return new $1Impl<$2>(%%ARRAYOF%%getTarget().selectMapKeysNot(keys));\n    }");
        
        methods.put(
                "public (.*?)<(.*?)> exec\\(final IFunction<\\? extends (.*?),\\? super (.*?)> function\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> exec(final IFunction<? extends $3,? super $4> function) {\n        return new $1Impl<$2>(%%ARRAYOF%%getTarget().execute(function));\n    }");
        methods.put(
                "public (.*?)<(.*?)> eval\\(final IEvaluator<\\? extends (.*?),\\? super (.*?)> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> eval(final IEvaluator<? extends $3,? super $4> eval) {\n        return new $1Impl<$2>(%%ARRAYOF%%getTarget().execute(eval));\n    }");
        methods.put(
                "public (.*?)<(.*?)> convert\\(final IConverter<\\? extends (.*?),\\? super (.*?)> converter\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> convert(final IConverter<? extends $3,? super $4> converter) {\n        return new $1Impl<$2>(%%ARRAYOF%%getTarget().execute(converter));\n    }");
        
        methods.put(
                "public (.*?)<(.*?)> endIf\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> endIf() {\n        return new $1Impl<$2>(%%ARRAYOF%%getTarget().endSelect());\n    }");
        
        methods.put(
                "public (.*?)<(.*?)> sort\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "@SuppressWarnings(\"unchecked\")\n    public $1<$2> sort() {\n        return new $1Impl<$2>(%%ARRAYOF%%getTarget().execute(new %%STRUCTUREFUNCS%%.%%SORTMETHOD%%()));\n    }");
        methods.put(
                "public (.*?)<(.*?)> sort\\(final Comparator<\\? super (Entry<(.*?)>|(.*?))> comparator\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> sort(final Comparator<? super $3> comparator) {\n        return new $1Impl<$2>(%%ARRAYOF%%getTarget().execute(new %%STRUCTUREFUNCS%%.%%SORTCOMPARATORMETHOD%%(comparator)));\n    }");
        
        methods.put(
                "public (.*?)<(.*?)> distinct\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> distinct() {\n        return new $1Impl<$2>(%%ARRAYOF%%getTarget().execute(new %%STRUCTUREFUNCS%%.Distinct<%%CURRENTLEVELELEMENT%%>()));\n    }");
        
        methods.put(
                "public (.*?)<(.*?)> forEach\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> forEach() {\n        return new $1Impl<$2>(%%ARRAYOF%%getTarget().iterate());\n    }");
        
        methods.put(
                "public (.*?)<(.*?)> endOn\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> endOn() {\n        return new $1Impl<$2>(%%ARRAYOF%%getTarget().endIterate(Structure.MAP_ENTRY, null));\n    }");

        
    }
    
    
    private static String getStructureFuncs(final LevelStructure currentLevelStructure) {
        switch(currentLevelStructure) {
            case ARRAY : return "ArrayFuncs";
            case LIST : return "ListFuncs";
            case MAP : return "MapFuncs";
            case SET : return "SetFuncs";
            default : return "%%STRUCTUREFUNCS_SHOULD_NOT_BE_HERE%%";
        }
    }
    
    
    private static String getSortMethod(final LevelStructure currentLevelStructure) {
        switch(currentLevelStructure) {
            case MAP : return "SortByKey";
            default : return "Sort";
        }
    }
    
    
    private static String getSortComparatorMethod(final LevelStructure currentLevelStructure) {
        switch(currentLevelStructure) {
            case MAP : return "SortEntries<$4>";
            default : return "SortByComparator<$3>";
        }
    }
    
    private static String getEndForDeclaration() {
        return "public (.*?)<(.*?)> endFor\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}";
    }
    
    private static String getEndForImpl(final LevelStructure previousLevelStructure, final LevelStructure currentLevelStructure) {
        String secondArgument = "null";
        if (previousLevelStructure.equals(LevelStructure.ARRAY)) {
            switch (currentLevelStructure) {
                case ELEMENTS : secondArgument = " this.arrayOf.getRawClass()";break;
                case LIST : secondArgument = " List.class";break;
                case SET : secondArgument = " Set.class";break;
                case MAP : secondArgument = " Map.class";break;
                case ARRAY : secondArgument = " Array.newInstance(this.arrayOf.getRawClass(),0).getClass()";break;
                default: throw new RuntimeException("Should not have endFor() method with previousLevel ARRAY and currentLevel: " + currentLevelStructure);
            }
        }
        return "public $1<$2> endFor() {\n        return new $1Impl<$2>(%%ARRAYOF%%getTarget().endIterate(Structure." + previousLevelStructure + ", " + secondArgument + "));\n    }"; 
    }

    
    private static String getGetDeclaration() {
        return "public (.*?) get\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}";
    }
    
    private static String getGetImpl(final int currentLevel) {
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < currentLevel; i++) {
            strBuilder.append(".endIterate()");
        }
        return "public $1 get() {\n        return ($1) getTarget()" + strBuilder.toString() + ".get();\n    }"; 
    }
    
    
    
    public static String processNonArray(final String fileContents, final int currentLevel, final LevelStructure currentLevelStructure, final LevelStructure previousLevelStructure, final String currentLevelType, final String currentLevelElement) {
        String newFileContents = fileContents; 
        for (final Map.Entry<String,String> implementation : methods.entrySet()) {
            String impl = implementation.getValue().replaceAll("%%ARRAYOF%%", "");
            impl = impl.replace("%%STRUCTUREFUNCS%%", getStructureFuncs(currentLevelStructure));
            impl = impl.replace("%%SORTMETHOD%%", getSortMethod(currentLevelStructure));
            impl = impl.replace("%%SORTCOMPARATORMETHOD%%", getSortComparatorMethod(currentLevelStructure));
            impl = impl.replace("%%CURRENTLEVELTYPE%%", currentLevelType);
            impl = impl.replace("%%CURRENTLEVELELEMENT%%", currentLevelElement);
            newFileContents = newFileContents.replaceAll(implementation.getKey(), impl);
        }
//        newFileContents = newFileContents.replaceAll(getGetDeclaration(), getGetImpl(currentLevel));
        newFileContents = newFileContents.replaceAll(getEndForDeclaration(), getEndForImpl(previousLevelStructure, currentLevelStructure).replaceAll("%%ARRAYOF%%", ""));
        return newFileContents;
    }
    
    
    public static String processArray(final String fileContents, final int currentLevel, final LevelStructure currentLevelStructure, final LevelStructure previousLevelStructure, final String currentLevelType, final String currentLevelElement) {
        String newFileContents = fileContents; 
        for (final Map.Entry<String,String> implementation : methods.entrySet()) {
            String impl = implementation.getValue().replaceAll("%%ARRAYOF%%", "this.arrayOf, ");
            impl = impl.replace("%%STRUCTUREFUNCS%%", getStructureFuncs(currentLevelStructure));
            impl = impl.replace("%%SORTMETHOD%%", getSortMethod(currentLevelStructure));
            impl = impl.replace("%%SORTCOMPARATORMETHOD%%", getSortComparatorMethod(currentLevelStructure));
            impl = impl.replace("%%CURRENTLEVELTYPE%%", currentLevelType);
            impl = impl.replace("%%CURRENTLEVELELEMENT%%", currentLevelElement);
            newFileContents = newFileContents.replaceAll(implementation.getKey(), impl);
        }
//        newFileContents = newFileContents.replaceAll(getGetDeclaration(), getGetImpl(currentLevel));
        newFileContents = newFileContents.replaceAll(getEndForDeclaration(), getEndForImpl(previousLevelStructure, currentLevelStructure).replaceAll("%%ARRAYOF%%", "this.arrayOf, "));
        return newFileContents;
    }
    
}
