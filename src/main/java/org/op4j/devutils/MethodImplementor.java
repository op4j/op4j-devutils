package org.op4j.devutils;

import java.util.LinkedHashMap;
import java.util.Map;

import org.op4j.devutils.ImplFile.LevelStructure;

public class MethodImplementor {

    
    
    
    private static Map<String,String> methods;
    
    
    
    static {
        
        methods = new LinkedHashMap<String, String>();


        
        methods.put(
                "public (.*?)<(.*?)> ifIndex\\(final int\\.\\.\\. indexes\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifIndex(final int... indexes) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().selectIndex(indexes));\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifIndexNot\\(final int\\.\\.\\. indexes\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifIndexNot(final int... indexes) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().selectIndexNot(indexes));\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifTrue\\(final IFunction<\\? super (.*?),Boolean> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifTrue(final IFunction<? super $3,Boolean> eval) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().selectMatching(eval));\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifFalse\\(final IFunction<\\? super (.*?),Boolean> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifFalse(final IFunction<? super $3,Boolean> eval) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().selectNotMatching(eval));\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifNull\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifNull() {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().selectNull());\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifNotNull\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifNotNull() {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().selectNotNull());\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifNotNullAndTrue\\(final IFunction<\\? super (.*?),Boolean> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifNotNullAndTrue(final IFunction<? super $3,Boolean> eval) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().selectNotNullAndMatching(eval));\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifNotNullAndFalse\\(final IFunction<\\? super (.*?),Boolean> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifNotNullAndFalse(final IFunction<? super $3,Boolean> eval) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().selectNotNullAndNotMatching(eval));\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifNullOrTrue\\(final IFunction<\\? super (.*?),Boolean> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifNullOrTrue(final IFunction<? super $3,Boolean> eval) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().selectNullOrMatching(eval));\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifNullOrFalse\\(final IFunction<\\? super (.*?),Boolean> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifNullOrFalse(final IFunction<? super $3,Boolean> eval) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().selectNullOrNotMatching(eval));\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifKeyEquals\\(final (.*?)\\.\\.\\. keys\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifKeyEquals(final $3\\.\\.\\. keys) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().selectMapKeys(keys));\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifKeyNotEquals\\(final (.*?)\\.\\.\\. keys\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifKeyNotEquals(final $3\\.\\.\\. keys) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().selectMapKeysNot(keys));\n    }");

        methods.put(
                "public Level0GenericMulti(.*?)<(.*?)> exec\\(final IFunction<\\? super (.*?),\\? extends (.*?)> function\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public Level0GenericMulti$1<$2> exec(final IFunction<? super $3,? extends $4> function) {\n        return new Level0GenericMulti$1<$2>(%%ELEMENTTYPE%%getTarget().execute(function, Normalisation.NONE));\n    }");
        methods.put(
                "public Level0GenericMulti(.*?)<(.*?)> execIfNotNull\\(final IFunction<\\? super (.*?),\\? extends (.*?)> function\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public Level0GenericMulti$1<$2> execIfNotNull(final IFunction<? super $3,? extends $4> function) {\n        return new Level0GenericMulti$1<$2>(%%ELEMENTTYPE%%getTarget().executeIfNotNull(function, Normalisation.NONE));\n    }");
        
        methods.put(
                "public (.*?)<(.*?)> execIfNotNull(.*?)\\(final IFunction<\\? super (.*?),\\? extends (.*?)> function\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> execIfNotNull$3(final IFunction<? super $4,? extends $5> function) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().executeIfNotNull(function, %%NORMALIZATION%%));\n    }");
        methods.put(
                "public (.*?)<(.*?)> exec(.*?)\\(final IFunction<\\? super (.*?),\\? extends (.*?)> function\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> exec$3(final IFunction<? super $4,? extends $5> function) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(function, %%NORMALIZATION%%));\n    }");
        
        methods.put(
                "public (.*?)<(.*?)> endIf\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> endIf() {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().endSelect());\n    }");
        
        methods.put(
                "public (.*?)<(.*?)> sort\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "@SuppressWarnings(\"unchecked\")\n    public $1<$2> sort() {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.%%SORTMETHOD%%()));\n    }");
        methods.put(
                "public (.*?)<(.*?)> sort\\(final Comparator<\\? super (Entry<(.*?)>|(.*?))> comparator\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> sort(final Comparator<? super $3> comparator) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.%%SORTCOMPARATORMETHOD%%(comparator)));\n    }");
        
        methods.put(
                "public (.*?)<(.*?)> distinct\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> distinct() {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Distinct<%%CURRENTLEVELELEMENT%%>()));\n    }");

        methods.put(
                "public (.*?)<(.*?)> forEach\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> forEach() {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().iterate(Structure.%%CURRENTLEVELSTRUCTURE%%));\n    }");
        methods.put(
                "public (.*?)<(.*?)> forEachEntry\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> forEachEntry() {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().iterate(Structure.%%CURRENTLEVELSTRUCTURE%%));\n    }");
        methods.put(
                "public (.*?)<(.*?)> onKey\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> onKey() {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().onKey());\n    }");
        methods.put(
                "public (.*?)<(.*?)> onValue\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> onValue() {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().onValue());\n    }");
        
        methods.put(
                "public (.*?)<(.*?)> endOn\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> endOn() {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().endOn());\n    }");

        methods.put(
                "public (.*?)<(.*?)> add\\(final T\\[\\] newElement\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "@SuppressWarnings(\"unchecked\")\n    public $1<$2> add(final T[] newElement) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Add<%%CURRENTLEVELELEMENT%%>(NormalisationUtils.normaliseArray(newElement, this.type.getRawClass()))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> add\\(final List<T> newElement\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "@SuppressWarnings(\"unchecked\")\n    public $1<$2> add(final List<T> newElement) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Add<%%CURRENTLEVELELEMENT%%>(NormalisationUtils.normaliseList(newElement))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> add\\(final Map<K,V> newElement\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "@SuppressWarnings(\"unchecked\")\n    public $1<$2> add(final Map<K,V> newElement) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Add<%%CURRENTLEVELELEMENT%%>(NormalisationUtils.normaliseMap(newElement))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> add\\(final Set<T> newElement\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "@SuppressWarnings(\"unchecked\")\n    public $1<$2> add(final Set<T> newElement) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Add<%%CURRENTLEVELELEMENT%%>(NormalisationUtils.normaliseSet(newElement))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> add\\(final (.*?) newElement\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "@SuppressWarnings(\"unchecked\")\n    public $1<$2> add(final $3 newElement) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Add<%%CURRENTLEVELELEMENT%%>(newElement)));\n    }");
        
        methods.put(
                "public (.*?)<(.*?)> addAll\\(final T\\[\\]\\.\\.\\. newElements\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> addAll(final T[]... newElements) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Add<%%CURRENTLEVELELEMENT%%>(NormalisationUtils.normaliseArrays(newElements, this.type.getRawClass()))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> addAll\\(final List<T>\\.\\.\\. newElements\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> addAll(final List<T>... newElements) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Add<%%CURRENTLEVELELEMENT%%>(NormalisationUtils.normaliseLists(newElements))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> addAll\\(final Map<K,V>\\.\\.\\. newElements\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> addAll(final Map<K,V>... newElements) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Add<%%CURRENTLEVELELEMENT%%>(NormalisationUtils.normaliseMaps(newElements))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> addAll\\(final Set<T>\\.\\.\\. newElements\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> addAll(final Set<T>... newElements) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Add<%%CURRENTLEVELELEMENT%%>(NormalisationUtils.normaliseSets(newElements))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> addAll\\(final (.*?)\\.\\.\\. newElements\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> addAll(final $3... newElements) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Add<%%CURRENTLEVELELEMENT%%>(newElements)));\n    }");
        
        methods.put(
                "public (.*?)<(.*?)> addAll\\(final Collection<T\\[\\]> collection\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> addAll(final Collection<T[]> collection) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.AddAll<%%CURRENTLEVELELEMENT%%>(NormalisationUtils.normaliseArrays(collection, this.type.getRawClass()))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> addAll\\(final Collection<List<T>> collection\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> addAll(final Collection<List<T>> collection) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.AddAll<%%CURRENTLEVELELEMENT%%>(NormalisationUtils.normaliseLists(collection))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> addAll\\(final Collection<Map<K,V>> collection\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> addAll(final Collection<Map<K,V>> collection) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.AddAll<%%CURRENTLEVELELEMENT%%>(NormalisationUtils.normaliseMaps(collection))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> addAll\\(final Collection<Set<T>> collection\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> addAll(final Collection<Set<T>> collection) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.AddAll<%%CURRENTLEVELELEMENT%%>(NormalisationUtils.normaliseSets(collection))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> addAll\\(final Collection<(.*?)> collection\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> addAll(final Collection<$3> collection) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.AddAll<%%CURRENTLEVELELEMENT%%>(collection)));\n    }");
        
        
        methods.put(
                "public (.*?)<(.*?)> insert\\(final int position, final T\\[\\] newElement\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "@SuppressWarnings(\"unchecked\")\n    public $1<$2> insert(final int position, final T[] newElement) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Insert<%%CURRENTLEVELELEMENT%%>(position, NormalisationUtils.normaliseArray(newElement, this.type.getRawClass()))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> insert\\(final int position, final List<T> newElement\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "@SuppressWarnings(\"unchecked\")\n    public $1<$2> insert(final int position, final List<T> newElement) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Insert<%%CURRENTLEVELELEMENT%%>(position, NormalisationUtils.normaliseList(newElement))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> insert\\(final int position, final Map<K,V> newElement\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "@SuppressWarnings(\"unchecked\")\n    public $1<$2> insert(final int position, final Map<K,V> newElement) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Insert<%%CURRENTLEVELELEMENT%%>(position, NormalisationUtils.normaliseMap(newElement))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> insert\\(final int position, final Set<T> newElement\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "@SuppressWarnings(\"unchecked\")\n    public $1<$2> insert(final int position, final Set<T> newElement) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Insert<%%CURRENTLEVELELEMENT%%>(position, NormalisationUtils.normaliseSet(newElement))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> insert\\(final int position, final (.*?) newElement\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "@SuppressWarnings(\"unchecked\")\n    public $1<$2> insert(final int position, final $3 newElement) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Insert<%%CURRENTLEVELELEMENT%%>(position, newElement)));\n    }");

        
        methods.put(
                "public (.*?)<(.*?)> insertAll\\(final int position, final T\\[\\]\\.\\.\\. newElements\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> insertAll(final int position, final T[]... newElements) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Insert<%%CURRENTLEVELELEMENT%%>(position, NormalisationUtils.normaliseArrays(newElements, this.type.getRawClass()))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> insertAll\\(final int position, final List<T>\\.\\.\\. newElements\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> insertAll(final int position, final List<T>... newElements) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Insert<%%CURRENTLEVELELEMENT%%>(position, NormalisationUtils.normaliseLists(newElements))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> insertAll\\(final int position, final Map<K,V>\\.\\.\\. newElements\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> insertAll(final int position, final Map<K,V>... newElements) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Insert<%%CURRENTLEVELELEMENT%%>(position, NormalisationUtils.normaliseMaps(newElements))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> insertAll\\(final int position, final Set<T>\\.\\.\\. newElements\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> insertAll(final int position, final Set<T>... newElements) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Insert<%%CURRENTLEVELELEMENT%%>(position, NormalisationUtils.normaliseSets(newElements))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> insertAll\\(final int position, final (.*?)\\.\\.\\. newElements\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> insertAll(final int position, final $3... newElements) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Insert<%%CURRENTLEVELELEMENT%%>(position, newElements)));\n    }");

        
        methods.put(
                "public (.*?)<(.*?)> insertAll\\(final int position, final Map<K,V\\[\\]> map\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> insertAll(final int position, final Map<K,V[]> map) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.InsertAll<K,V[]>(position, NormalisationUtils.normaliseMapOfArray(map, this.type.getRawClass()))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> insertAll\\(final int position, final Map<K,List<V>> map\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> insertAll(final int position, final Map<K,List<V>> map) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.InsertAll<K,List<V>>(position, NormalisationUtils.normaliseMapOfList(map))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> insertAll\\(final int position, final Map<K1,Map<K2,V>> map\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> insertAll(final int position, final Map<K1,Map<K2,V>> map) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.InsertAll<K1,Map<K2,V>>(position, NormalisationUtils.normaliseMapOfMap(map))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> insertAll\\(final int position, final Map<K,Set<V>> map\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> insertAll(final int position, final Map<K,Set<V>> map) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.InsertAll<K,Set<V>>(position, NormalisationUtils.normaliseMapOfSet(map))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> insertAll\\(final int position, final Map<(.*?)> map\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> insertAll(final int position, final Map<$3> map) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.InsertAll<$3>(position, map)));\n    }");
        
        
        methods.put(
                "public (.*?)<(.*?)> insert\\(final int position, final K newKey, final V\\[\\] newValue\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> insert(final int position, final K newKey, final V[] newValue) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Insert<K,V[]>(position, newKey, NormalisationUtils.normaliseArray(newValue, this.type.getRawClass()))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> insert\\(final int position, final K newKey, final List<V> newValue\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> insert(final int position, final K newKey, final List<V> newValue) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Insert<K,List<V>>(position, newKey, NormalisationUtils.normaliseList(newValue))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> insert\\(final int position, final K1 newKey, final Map<K2,V> newValue\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> insert(final int position, final K1 newKey, final Map<K2,V> newValue) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Insert<K1,Map<K2,V>>(position, newKey, NormalisationUtils.normaliseMap(newValue))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> insert\\(final int position, final K newKey, final Set<V> newValue\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> insert(final int position, final K newKey, final Set<V> newValue) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Insert<K,Set<V>>(position, newKey, NormalisationUtils.normaliseSet(newValue))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> insert\\(final int position, final (.*?) newKey, final (.*?) newValue\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> insert(final int position, final $3 newKey, final $4 newValue) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Insert<$3,$4>(position, newKey, newValue)));\n    }");
        
        methods.put(
                "public (.*?)<(.*?)> put\\(final K newKey, final V\\[\\] newValue\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> put(final K newKey, final V[] newValue) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Put<K,V[]>(newKey, NormalisationUtils.normaliseArray(newValue, this.type.getRawClass()))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> put\\(final K newKey, final List<V> newValue\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> put(final K newKey, final List<V> newValue) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Put<K,List<V>>(newKey, NormalisationUtils.normaliseList(newValue))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> put\\(final K1 newKey, final Map<K2,V> newValue\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> put(final K1 newKey, final Map<K2,V> newValue) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Put<K1,Map<K2,V>>(newKey, NormalisationUtils.normaliseMap(newValue))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> put\\(final K newKey, final Set<V> newValue\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> put(final K newKey, final Set<V> newValue) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Put<K,Set<V>>(newKey, NormalisationUtils.normaliseSet(newValue))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> put\\(final (.*?) newKey, final (.*?) newValue\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> put(final $3 newKey, final $4 newValue) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Put<$3,$4>(newKey, newValue)));\n    }");
        
        methods.put(
                "public (.*?)<(.*?)> putAll\\(final Map<K,V\\[\\]> map\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> putAll(final Map<K,V[]> map) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.PutAll<K,V[]>(NormalisationUtils.normaliseMapOfArray(map, this.type.getRawClass()))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> putAll\\(final Map<K,List<V>> map\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> putAll(final Map<K,List<V>> map) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.PutAll<K,List<V>>(NormalisationUtils.normaliseMapOfList(map))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> putAll\\(final Map<K1,Map<K2,V>> map\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> putAll(final Map<K1,Map<K2,V>> map) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.PutAll<K1,Map<K2,V>>(NormalisationUtils.normaliseMapOfMap(map))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> putAll\\(final Map<K,Set<V>> map\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> putAll(final Map<K,Set<V>> map) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.PutAll<K,Set<V>>(NormalisationUtils.normaliseMapOfSet(map))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> putAll\\(final Map<(.*?)> map\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> putAll(final Map<$3> map) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.PutAll<$3>(map)));\n    }");

        methods.put(
                "public (.*?)<(.*?)> removeAllIndexes\\(final int\\.\\.\\. indexes\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeAllIndexes(final int... indexes) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveAllIndexes<%%CURRENTLEVELELEMENT%%>(indexes)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeAllIndexesNot\\(final int\\.\\.\\. indexes\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeAllIndexesNot(final int... indexes) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveAllIndexesNot<%%CURRENTLEVELELEMENT%%>(indexes)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeAllEqual\\(final (.*?)\\.\\.\\. values\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeAllEqual(final $3... values) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveAllEqual<%%CURRENTLEVELELEMENT%%>(values)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeAllNull\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeAllNull() {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveAllNull<%%CURRENTLEVELELEMENT%%>()));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeAllTrue\\(final IFunction<\\? super Entry<(.*?)>,Boolean> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeAllTrue(final IFunction<? super Entry<$3>,Boolean> eval) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveAllTrue<$3>(eval)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeAllFalse\\(final IFunction<\\? super Entry<(.*?)>,Boolean> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeAllFalse(final IFunction<? super Entry<$3>,Boolean> eval) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveAllFalse<$3>(eval)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeAllTrue\\(final IFunction<\\? super (.*?),Boolean> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeAllTrue(final IFunction<? super $3,Boolean> eval) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveAllTrue<$3>(eval)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeAllFalse\\(final IFunction<\\? super (.*?),Boolean> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeAllFalse(final IFunction<? super $3,Boolean> eval) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveAllFalse<$3>(eval)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeAllNullOrTrue\\(final IFunction<\\? super (.*?),Boolean> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeAllNullOrTrue(final IFunction<? super $3,Boolean> eval) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveAllNullOrTrue<$3>(eval)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeAllNullOrFalse\\(final IFunction<\\? super (.*?),Boolean> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeAllNullOrFalse(final IFunction<? super $3,Boolean> eval) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveAllNullOrFalse<$3>(eval)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeAllNotNullAndTrue\\(final IFunction<\\? super (.*?),Boolean> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeAllNotNullAndTrue(final IFunction<? super $3,Boolean> eval) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveAllNotNullAndTrue<$3>(eval)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeAllNotNullAndFalse\\(final IFunction<\\? super (.*?),Boolean> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeAllNotNullAndFalse(final IFunction<? super $3,Boolean> eval) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveAllNotNullAndFalse<$3>(eval)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeAllKeys\\(final (.*?)\\.\\.\\. keys\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeAllKeys(final $3... keys) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveAllKeys<%%CURRENTLEVELENTRYELEMENT%%>(keys)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeAllKeysNot\\(final (.*?)\\.\\.\\. keys\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeAllKeysNot(final $3... keys) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveAllKeysNot<%%CURRENTLEVELENTRYELEMENT%%>(keys)));\n    }");

        methods.put(
                "public (.*?) getAsArrayOf\\(final Type<(.*?)> type\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1 getAsArrayOf(final Type<$2> type) {\n        return endIf().buildArrayOf(type).get();\n    }");
        methods.put(
                "public (.*?) getAsList\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1 getAsList() {\n        return endIf().buildList().get();\n    }");
        
        methods.put(
                "public (.*?)<(.*?)> replaceWith\\(final (.*?) replacement\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> replaceWith(final $3 replacement) {\n        return new $1<$2>(%%ELEMENTTYPE%%getTarget().replaceWith(replacement, %%NORMALIZATION%%));\n    }");
        
        methods.put(
                "public (.*?)<(.*?)> mapMap\\(final IFunction<\\? super (.*?),\\? extends (.*?)> function\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> mapMap(final IFunction<? super $3,? extends $4> function) {\n        return forEach().map(function).endFor();\n    }");

    }
    
    
    private static String getStructureFuncs(final LevelStructure currentLevelStructure) {
        switch(currentLevelStructure) {
            case ARRAY : return "FArray";
            case LIST : return "FList";
            case MAP : return "FMap";
            case SET : return "FSet";
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
        String previousLevelType = "%%ELEMENTTYPE%%";
        String secondArgument = "null";
        if (previousLevelStructure.equals(LevelStructure.ARRAY)) {
            switch (currentLevelStructure) {
                case ELEMENTS : secondArgument = "this.type.getRawClass()"; break;
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
                "        return new $1<$2>(" + previousLevelType + "getTarget().endIterate(" + secondArgument + "));\n    }"; 
    }
    
    private static String getMapDeclaration() {
        return "public (.*?)<(.*?)> map\\(final IFunction<\\? super (.*?),\\? extends (.*?)> function\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}";
    }
    
    private static String getMapImpl(final LevelStructure previousLevelStructure, final LevelStructure currentLevelStructure, final int currentLevel, final String fileContents) {
        String arrayComponentClass = "null";
        if (currentLevelStructure.equals(LevelStructure.ARRAY)) {
            arrayComponentClass = "this.type.getRawClass()";
        }
        return "public $1<$2> map(final IFunction<? super $3,? extends $4> function) {\n" + 
                "        return new $1<$2>(%%ELEMENTTYPE%%getTarget().map(Structure.%%CURRENTLEVELSTRUCTURE%%, function, " + arrayComponentClass + "));\n    }"; 
    }
    
    private static String getMapIfNotNullDeclaration() {
        return "public (.*?)<(.*?)> mapIfNotNull\\(final IFunction<\\? super (.*?),\\? extends (.*?)> function\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}";
    }
    
    private static String getMapIfNotNullImpl(final LevelStructure previousLevelStructure, final LevelStructure currentLevelStructure, final int currentLevel, final String fileContents) {
        String arrayComponentClass = "null";
        if (currentLevelStructure.equals(LevelStructure.ARRAY)) {
            arrayComponentClass = "this.type.getRawClass()";
        }
        return "public $1<$2> mapIfNotNull(final IFunction<? super $3,? extends $4> function) {\n" + 
                "        return new $1<$2>(%%ELEMENTTYPE%%getTarget().mapIfNotNull(Structure.%%CURRENTLEVELSTRUCTURE%%, function, " + arrayComponentClass + "));\n    }"; 
    }

    
    private static String getGetDeclaration() {
        return "public (.*?) get\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}";
    }
    
    private static String getGetImpl(final boolean hasEndIf, final boolean hasEndOn) {
        final String delegator = (hasEndIf? "endIf()" : (hasEndOn? "endOn()" : "endFor()"));
        return "public $1 get() {\n        return " + delegator + ".get();\n    }"; 
    }
    

    
    private static String getNormalisation(final String currentLevelType) {
        if (currentLevelType.equals("T") || currentLevelType.equals("K") || currentLevelType.equals("V") || currentLevelType.equals("K1") || currentLevelType.equals("K2")) {
            return "Normalisation.NONE";
        } else if (currentLevelType.equals("T[]") || currentLevelType.equals("V[]")) {
            return "Normalisation.ARRAY(this.type.getRawClass())";
        } else if (currentLevelType.equals("List<T>") || currentLevelType.equals("List<V>")) {
            return "Normalisation.LIST";
        } else if (currentLevelType.equals("Map<K,V>") || currentLevelType.equals("Map<K2,V>")) {
            return "Normalisation.MAP";
        } else if (currentLevelType.equals("Entry<K,V>") | currentLevelType.equals("Entry<K2,V>")) {
            return "Normalisation.MAP_ENTRY";
        } else if (currentLevelType.equals("Set<T>") || currentLevelType.equals("Set<V>")) {
            return "Normalisation.SET";
        }
        System.out.println(currentLevelType);
        return "Normalisation.NONE";
    }
    
    
    
    public static String processNonArray(final ImplType implType, final String fileContents, final int currentLevel, final LevelStructure currentLevelStructure, final LevelStructure previousLevelStructure, final String currentLevelType, final String currentLevelElement, final boolean hasEndIf, final boolean hasEndOn) {
        String newFileContents = fileContents; 
        for (final Map.Entry<String,String> implementation : methods.entrySet()) {
            String impl = implementation.getValue().replaceAll("%%ELEMENTTYPE%%", "");
            impl = impl.replace("%%STRUCTUREFUNCS%%", getStructureFuncs(currentLevelStructure));
            impl = impl.replace("%%SORTMETHOD%%", getSortMethod(currentLevelStructure));
            impl = impl.replace("%%SORTCOMPARATORMETHOD%%", getSortComparatorMethod(currentLevelStructure));
            impl = impl.replace("%%CURRENTLEVELTYPE%%", currentLevelType);
            impl = impl.replace("%%CURRENTLEVELELEMENT%%", currentLevelElement);
            impl = impl.replace("%%CURRENTLEVELENTRYELEMENT%%", getMapEntryElementTypes(currentLevelStructure, currentLevelElement));
            impl = impl.replace("%%NORMALIZATION%%", getNormalisation(currentLevelType));
            impl = impl.replace("%%CURRENTLEVELSTRUCTURE%%", currentLevelStructure.name());
            newFileContents = newFileContents.replaceAll(implementation.getKey(), impl);
        }
        newFileContents = newFileContents.replaceAll(getGetDeclaration(), getGetImpl(hasEndIf, hasEndOn));
        newFileContents = newFileContents.replaceAll(getEndForDeclaration(), getEndForImpl(previousLevelStructure, currentLevelStructure, currentLevel, fileContents).replaceAll("%%ELEMENTTYPE%%", ""));
        newFileContents = newFileContents.replaceAll(getMapDeclaration(), getMapImpl(previousLevelStructure, currentLevelStructure, currentLevel, fileContents).replaceAll("%%ELEMENTTYPE%%", "")).replace("%%CURRENTLEVELSTRUCTURE%%", currentLevelStructure.name());
        newFileContents = newFileContents.replaceAll(getMapIfNotNullDeclaration(), getMapIfNotNullImpl(previousLevelStructure, currentLevelStructure, currentLevel, fileContents).replaceAll("%%ELEMENTTYPE%%", "")).replace("%%CURRENTLEVELSTRUCTURE%%", currentLevelStructure.name());
        return newFileContents;
    }
    
    
    public static String processArray(final ImplType implType, final String fileContents, final int currentLevel, final LevelStructure currentLevelStructure, final LevelStructure previousLevelStructure, final String currentLevelType, final String currentLevelElement, final boolean hasEndIf, final boolean hasEndOn) {
        String newFileContents = fileContents; 
        for (final Map.Entry<String,String> implementation : methods.entrySet()) {
            String impl = implementation.getValue().replaceAll("%%ELEMENTTYPE%%", "this.type, ");
            impl = impl.replace("%%STRUCTUREFUNCS%%", getStructureFuncs(currentLevelStructure));
            impl = impl.replace("%%SORTMETHOD%%", getSortMethod(currentLevelStructure));
            impl = impl.replace("%%SORTCOMPARATORMETHOD%%", getSortComparatorMethod(currentLevelStructure));
            impl = impl.replace("%%CURRENTLEVELTYPE%%", currentLevelType);
            impl = impl.replace("%%CURRENTLEVELELEMENT%%", currentLevelElement);
            impl = impl.replace("%%CURRENTLEVELENTRYELEMENT%%", getMapEntryElementTypes(currentLevelStructure, currentLevelElement));
            impl = impl.replace("%%NORMALIZATION%%", getNormalisation(currentLevelType));
            impl = impl.replace("%%CURRENTLEVELSTRUCTURE%%", currentLevelStructure.name());
            newFileContents = newFileContents.replaceAll(implementation.getKey(), impl);
        }
        newFileContents = newFileContents.replaceAll(getGetDeclaration(), getGetImpl(hasEndIf, hasEndOn));
        newFileContents = newFileContents.replaceAll(getEndForDeclaration(), getEndForImpl(previousLevelStructure, currentLevelStructure, currentLevel, fileContents).replaceAll("%%ELEMENTTYPE%%", "this.type, "));
        newFileContents = newFileContents.replaceAll(getMapDeclaration(), getMapImpl(previousLevelStructure, currentLevelStructure, currentLevel, fileContents).replaceAll("%%ELEMENTTYPE%%", "this.type, ")).replace("%%CURRENTLEVELSTRUCTURE%%", currentLevelStructure.name());
        newFileContents = newFileContents.replaceAll(getMapIfNotNullDeclaration(), getMapIfNotNullImpl(previousLevelStructure, currentLevelStructure, currentLevel, fileContents).replaceAll("%%ELEMENTTYPE%%", "this.type, ")).replace("%%CURRENTLEVELSTRUCTURE%%", currentLevelStructure.name());
        return newFileContents;
    }
    
}
