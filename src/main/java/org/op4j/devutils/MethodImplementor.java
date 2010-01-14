package org.op4j.devutils;

import java.util.LinkedHashMap;
import java.util.Map;

import org.op4j.devutils.ImplFile.LevelStructure;

public class MethodImplementor {

    
    
    
    private static Map<String,String> methods;
    
    
    
    static {
        
        methods = new LinkedHashMap<String, String>();


        
        methods.put(
                "public (.*?)<(.*?)> ifIndex\\(final int\\.\\.\\. indices\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifIndex(final int... indices) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().selectIndex(indices));\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifIndexNot\\(final int\\.\\.\\. indices\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifIndexNot(final int... indices) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().selectIndexNot(indices));\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifMatching\\(final IEvaluator<Boolean,\\? super (.*?)> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifMatching(final IEvaluator<Boolean, ? super $3> eval) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().selectMatching(eval));\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifNotMatching\\(final IEvaluator<Boolean,\\? super (.*?)> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifNotMatching(final IEvaluator<Boolean, ? super $3> eval) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().selectNotMatching(eval));\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifNull\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifNull() {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().selectNull());\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifNotNull\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifNotNull() {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().selectNotNull());\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifNotNullMatching\\(final IEvaluator<Boolean,\\? super (.*?)> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifNotNullMatching(final IEvaluator<Boolean, ? super $3> eval) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().selectNotNullAndMatching(eval));\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifNotNullNotMatching\\(final IEvaluator<Boolean,\\? super (.*?)> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifNotNullNotMatching(final IEvaluator<Boolean, ? super $3> eval) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().selectNotNullAndNotMatching(eval));\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifNullOrMatching\\(final IEvaluator<Boolean,\\? super (.*?)> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifNullOrMatching(final IEvaluator<Boolean, ? super $3> eval) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().selectNullOrMatching(eval));\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifNullOrNotMatching\\(final IEvaluator<Boolean,\\? super (.*?)> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifNullOrNotMatching(final IEvaluator<Boolean, ? super $3> eval) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().selectNullOrNotMatching(eval));\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifKeyEquals\\(final (.*?)\\.\\.\\. keys\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifKeyEquals(final $3\\.\\.\\. keys) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().selectMapKeys(keys));\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifKeyNotEquals\\(final (.*?)\\.\\.\\. keys\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifKeyNotEquals(final $3\\.\\.\\. keys) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().selectMapKeysNot(keys));\n    }");
        
        methods.put(
                "public (.*?)<(.*?)> exec\\(final IFunction<\\? extends (.*?),\\? super (.*?)> function\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> exec(final IFunction<? extends $3,? super $4> function) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(function));\n    }");
        methods.put(
                "public (.*?)<(.*?)> eval\\(final IEvaluator<\\? extends (.*?),\\? super (.*?)> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> eval(final IEvaluator<? extends $3,? super $4> eval) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(eval));\n    }");
        methods.put(
                "public (.*?)<(.*?)> convert\\(final IConverter<\\? extends (.*?),\\? super (.*?)> converter\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> convert(final IConverter<? extends $3,? super $4> converter) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(converter));\n    }");
        
        methods.put(
                "public (.*?)<(.*?)> endIf\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> endIf() {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().endSelect());\n    }");
        
        methods.put(
                "public (.*?)<(.*?)> sort\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "@SuppressWarnings(\"unchecked\")\n    public $1<$2> sort() {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.%%SORTMETHOD%%()));\n    }");
        methods.put(
                "public (.*?)<(.*?)> sort\\(final Comparator<\\? super (Entry<(.*?)>|(.*?))> comparator\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> sort(final Comparator<? super $3> comparator) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.%%SORTCOMPARATORMETHOD%%(comparator)));\n    }");
        
        methods.put(
                "public (.*?)<(.*?)> distinct\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> distinct() {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Distinct<%%CURRENTLEVELELEMENT%%>()));\n    }");
        
        methods.put(
                "public (.*?)<(.*?)> forEach\\(final Type<(.*?)> elementType\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> forEach(final Type<$3> elementType) {\n        return new $1Impl<$2>(elementType, getTarget().iterate());\n    }");
        methods.put(
                "public (.*?)<(.*?)> forEach\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> forEach() {\n        return new $1Impl<$2>(getTarget().iterate());\n    }");
        methods.put(
                "public (.*?)<(.*?)> forEachEntry\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> forEachEntry() {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().iterate());\n    }");
        methods.put(
                "public (.*?)<(.*?)> onKey\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> onKey() {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().iterateIndex(0));\n    }");
        methods.put(
                "public (.*?)<(.*?)> onValue\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> onValue() {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().iterateIndex(1));\n    }");
        
        methods.put(
                "public (.*?)<(.*?)> endOn\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> endOn() {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().endIterate(Structure.MAP_ENTRY, null));\n    }");
        
        methods.put(
                "public (.*?)<(.*?)> add\\(final (.*?)\\.\\.\\. newElements\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> add(final $3... newElements) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Add<%%CURRENTLEVELELEMENT%%>(newElements)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> addAll\\(final Collection<(.*?)> collection\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> addAll(final Collection<$3> collection) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.AddAll<%%CURRENTLEVELELEMENT%%>(collection)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> insert\\(final int position, final (.*?)\\.\\.\\. newElements\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> insert(final int position, final $3... newElements) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Insert<%%CURRENTLEVELELEMENT%%>(position, newElements)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> insertAll\\(final int position, final Map<(.*?)> map\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> insertAll(final int position, final Map<$3> map) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.InsertAll<$3>(position, map)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> insert\\(final int position, final (.*?) newKey, final (.*?) newValue\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> insert(final int position, final $3 newKey, final $4 newValue) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Insert<$3,$4>(position, newKey, newValue)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> put\\(final (.*?) newKey, final (.*?) newValue\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> put(final $3 newKey, final $4 newValue) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Put<$3,$4>(newKey, newValue)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> putAll\\(final Map<(.*?)> map\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> putAll(final Map<$3> map) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.PutAll<$3>(map)));\n    }");

        methods.put(
                "public (.*?)<(.*?)> removeIndexes\\(final int\\.\\.\\. indices\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeIndexes(final int... indices) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveIndexes<%%CURRENTLEVELELEMENT%%>(indices)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeIndexesNot\\(final int\\.\\.\\. indices\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeIndexesNot(final int... indices) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveIndexesNot<%%CURRENTLEVELELEMENT%%>(indices)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeEquals\\(final (.*?)\\.\\.\\. values\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeEquals(final $3... values) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveEquals<%%CURRENTLEVELELEMENT%%>(values)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeNulls\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeNulls() {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveNulls<%%CURRENTLEVELELEMENT%%>()));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeMatching\\(final IEvaluator<Boolean,\\? super Entry<(.*?)>> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeMatching(final IEvaluator<Boolean,? super Entry<$3>> eval) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveMatching<$3>(eval)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeNotMatching\\(final IEvaluator<Boolean,\\? super Entry<(.*?)>> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeNotMatching(final IEvaluator<Boolean,? super Entry<$3>> eval) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveNotMatching<$3>(eval)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeMatching\\(final IEvaluator<Boolean,\\? super (.*?)> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeMatching(final IEvaluator<Boolean,? super $3> eval) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveMatching<$3>(eval)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeNotMatching\\(final IEvaluator<Boolean,\\? super (.*?)> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeNotMatching(final IEvaluator<Boolean,? super $3> eval) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveNotMatching<$3>(eval)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeNullOrMatching\\(final IEvaluator<Boolean,\\? super (.*?)> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeNullOrMatching(final IEvaluator<Boolean,? super $3> eval) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveNullOrMatching<$3>(eval)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeNullOrNotMatching\\(final IEvaluator<Boolean,\\? super (.*?)> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeNullOrNotMatching(final IEvaluator<Boolean,? super $3> eval) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveNullOrNotMatching<$3>(eval)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeNotNullMatching\\(final IEvaluator<Boolean,\\? super (.*?)> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeNotNullMatching(final IEvaluator<Boolean,? super $3> eval) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveNotNullMatching<$3>(eval)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeNotNullNotMatching\\(final IEvaluator<Boolean,\\? super (.*?)> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeNotNullNotMatching(final IEvaluator<Boolean,? super $3> eval) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveNotNullNotMatching<$3>(eval)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeKeys\\(final (.*?)\\.\\.\\. keys\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeKeys(final $3... keys) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveKeys<%%CURRENTLEVELENTRYELEMENT%%>(keys)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeKeysNot\\(final (.*?)\\.\\.\\. keys\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeKeysNot(final $3... keys) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveKeysNot<%%CURRENTLEVELENTRYELEMENT%%>(keys)));\n    }");

        methods.put(
                "public (.*?) getAsArray\\(final Type<(.*?)> type\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1 getAsArray(final Type<$2> type) {\n        return endIf().buildArray(type).get();\n    }");
        methods.put(
                "public (.*?) getAsList\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1 getAsList() {\n        return endIf().buildList().get();\n    }");
        
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
    
    private static String getMapEntryElementTypes(final LevelStructure currentLevelStructure, final String currentLevelElement) {
        switch(currentLevelStructure) {
            case MAP : return currentLevelElement.substring(10, currentLevelElement.length() - 1);
            default : return "%%MAPENTRYELEMENTTYPESHOULDNOTBEHERE%%";
        }
    }
    
    private static String getEndForDeclaration() {
        return "public (.*?)<(.*?)> endFor\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}";
    }
    
    private static String getEndForImpl(final LevelStructure previousLevelStructure, final LevelStructure currentLevelStructure, final int currentLevel, final String fileContents) {
        String previousLevelType = "";
        String secondArgument = "null";
        if (previousLevelStructure.equals(LevelStructure.ARRAY)) {
            switch (currentLevelStructure) {
                case ELEMENTS :
                    if (fileContents.contains("public class Level2ArrayOfArray")) {
                        previousLevelType = "org.javaruntype.type.Types.arrayOf(this.type), ";
                    }
                    secondArgument = "this.type.getRawClass()";
                    break;
                case LIST : secondArgument = "List.class";break;
                case SET : secondArgument = "Set.class";break;
                case MAP : secondArgument = "Map.class";break;
                case ARRAY :
                    secondArgument = "this.type.getRawClass()";
                    break;
                default: throw new RuntimeException("Should not have endFor() method with previousLevel ARRAY and currentLevel: " + currentLevelStructure);
            }
        }
        return "public $1<$2> endFor() {\n" + 
                "        return new $1Impl<$2>(" + previousLevelType + "getTarget().endIterate(Structure." + previousLevelStructure + ", " + secondArgument + "));\n    }"; 
    }

    
    private static String getGetDeclaration() {
        return "public (.*?) get\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}";
    }
    
    private static String getGetImpl(final boolean hasEndIf, final boolean hasEndOn) {
        final String delegator = (hasEndIf? "endIf()" : (hasEndOn? "endOn()" : "endFor()"));
        return "public $1 get() {\n        return " + delegator + ".get();\n    }"; 
    }
    
    
    
    public static String processNonArray(final String fileContents, final int currentLevel, final LevelStructure currentLevelStructure, final LevelStructure previousLevelStructure, final String currentLevelType, final String currentLevelElement, final boolean hasEndIf, final boolean hasEndOn) {
        String newFileContents = fileContents; 
        for (final Map.Entry<String,String> implementation : methods.entrySet()) {
            String impl = implementation.getValue().replaceAll("%%ELEMENTTYPE%%", "");
            impl = impl.replace("%%STRUCTUREFUNCS%%", getStructureFuncs(currentLevelStructure));
            impl = impl.replace("%%SORTMETHOD%%", getSortMethod(currentLevelStructure));
            impl = impl.replace("%%SORTCOMPARATORMETHOD%%", getSortComparatorMethod(currentLevelStructure));
            impl = impl.replace("%%CURRENTLEVELTYPE%%", currentLevelType);
            impl = impl.replace("%%CURRENTLEVELELEMENT%%", currentLevelElement);
            impl = impl.replace("%%CURRENTLEVELENTRYELEMENT%%", getMapEntryElementTypes(currentLevelStructure, currentLevelElement));
            newFileContents = newFileContents.replaceAll(implementation.getKey(), impl);
        }
        newFileContents = newFileContents.replaceAll(getGetDeclaration(), getGetImpl(hasEndIf, hasEndOn));
        newFileContents = newFileContents.replaceAll(getEndForDeclaration(), getEndForImpl(previousLevelStructure, currentLevelStructure, currentLevel, fileContents).replaceAll("%%ELEMENTTYPE%%", ""));
        return newFileContents;
    }
    
    
    public static String processArray(final String fileContents, final int currentLevel, final LevelStructure currentLevelStructure, final LevelStructure previousLevelStructure, final String currentLevelType, final String currentLevelElement, final boolean hasEndIf, final boolean hasEndOn) {
        String newFileContents = fileContents; 
        for (final Map.Entry<String,String> implementation : methods.entrySet()) {
            String impl = implementation.getValue().replaceAll("%%ELEMENTTYPE%%", "this.type, ");
            impl = impl.replace("%%STRUCTUREFUNCS%%", getStructureFuncs(currentLevelStructure));
            impl = impl.replace("%%SORTMETHOD%%", getSortMethod(currentLevelStructure));
            impl = impl.replace("%%SORTCOMPARATORMETHOD%%", getSortComparatorMethod(currentLevelStructure));
            impl = impl.replace("%%CURRENTLEVELTYPE%%", currentLevelType);
            impl = impl.replace("%%CURRENTLEVELELEMENT%%", currentLevelElement);
            impl = impl.replace("%%CURRENTLEVELENTRYELEMENT%%", getMapEntryElementTypes(currentLevelStructure, currentLevelElement));
            newFileContents = newFileContents.replaceAll(implementation.getKey(), impl);
        }
        newFileContents = newFileContents.replaceAll(getGetDeclaration(), getGetImpl(hasEndIf, hasEndOn));
        newFileContents = newFileContents.replaceAll(getEndForDeclaration(), getEndForImpl(previousLevelStructure, currentLevelStructure, currentLevel, fileContents).replaceAll("%%ELEMENTTYPE%%", "this.type, "));
        return newFileContents;
    }
    
}
