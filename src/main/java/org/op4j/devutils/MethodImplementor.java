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
                "public (.*?)<(.*?)> ifTrue\\(final IEvaluator<Boolean,\\? super (.*?)> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifTrue(final IEvaluator<Boolean, ? super $3> eval) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().selectMatching(eval));\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifFalse\\(final IEvaluator<Boolean,\\? super (.*?)> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifFalse(final IEvaluator<Boolean, ? super $3> eval) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().selectNotMatching(eval));\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifNull\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifNull() {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().selectNull());\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifNotNull\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifNotNull() {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().selectNotNull());\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifNotNullAndTrue\\(final IEvaluator<Boolean,\\? super (.*?)> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifNotNullAndTrue(final IEvaluator<Boolean, ? super $3> eval) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().selectNotNullAndMatching(eval));\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifNotNullAndFalse\\(final IEvaluator<Boolean,\\? super (.*?)> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifNotNullAndFalse(final IEvaluator<Boolean, ? super $3> eval) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().selectNotNullAndNotMatching(eval));\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifNullOrTrue\\(final IEvaluator<Boolean,\\? super (.*?)> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifNullOrTrue(final IEvaluator<Boolean, ? super $3> eval) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().selectNullOrMatching(eval));\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifNullOrFalse\\(final IEvaluator<Boolean,\\? super (.*?)> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifNullOrFalse(final IEvaluator<Boolean, ? super $3> eval) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().selectNullOrNotMatching(eval));\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifKeyEquals\\(final (.*?)\\.\\.\\. keys\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifKeyEquals(final $3\\.\\.\\. keys) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().selectMapKeys(keys));\n    }");
        methods.put(
                "public (.*?)<(.*?)> ifKeyNotEquals\\(final (.*?)\\.\\.\\. keys\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> ifKeyNotEquals(final $3\\.\\.\\. keys) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().selectMapKeysNot(keys));\n    }");

        methods.put(
                "public Level0GenericMulti(.*?)<(.*?)> exec\\(final IFunction<\\? extends (.*?),\\? super (.*?)> function\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public Level0GenericMulti$1<$2> exec(final IFunction<? extends $3,? super $4> function) {\n        return new Level0GenericMulti$1Impl<$2>(%%ELEMENTTYPE%%getTarget().iterate().execute(function, Normalization.NONE).endIterate(org.op4j.target.Target.Structure.LIST, null));\n    }");
        methods.put(
                "public Level0GenericMulti(.*?)<(.*?)> eval\\(final IEvaluator<\\? extends (.*?),\\? super (.*?)> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public Level0GenericMulti$1<$2> eval(final IEvaluator<? extends $3,? super $4> eval) {\n        return new Level0GenericMulti$1Impl<$2>(%%ELEMENTTYPE%%getTarget().iterate().execute(eval, Normalization.NONE).endIterate(org.op4j.target.Target.Structure.LIST, null));\n    }");
        methods.put(
                "public Level0GenericMulti(.*?)<(.*?)> convert\\(final IConverter<\\? extends (.*?),\\? super (.*?)> converter\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public Level0GenericMulti$1<$2> convert(final IConverter<? extends $3,? super $4> converter) {\n        return new Level0GenericMulti$1Impl<$2>(%%ELEMENTTYPE%%getTarget().iterate().execute(converter, Normalization.NONE).endIterate(org.op4j.target.Target.Structure.LIST, null));\n    }");
        
        methods.put(
                "public (.*?)<(.*?)> exec\\(final IFunction<\\? extends (.*?),\\? super (.*?)> function\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> exec(final IFunction<? extends $3,? super $4> function) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(function, %%NORMALIZATION%%));\n    }");
        methods.put(
                "public (.*?)<(.*?)> eval\\(final IEvaluator<\\? extends (.*?),\\? super (.*?)> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> eval(final IEvaluator<? extends $3,? super $4> eval) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(eval, %%NORMALIZATION%%));\n    }");
        methods.put(
                "public (.*?)<(.*?)> convert\\(final IConverter<\\? extends (.*?),\\? super (.*?)> converter\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> convert(final IConverter<? extends $3,? super $4> converter) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(converter, %%NORMALIZATION%%));\n    }");
        
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
                "public $1<$2> onKey() {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().onKey());\n    }");
        methods.put(
                "public (.*?)<(.*?)> onValue\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> onValue() {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().onValue());\n    }");
        
        methods.put(
                "public (.*?)<(.*?)> endOn\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> endOn() {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().endOn());\n    }");

        methods.put(
                "public (.*?)<(.*?)> add\\(final T\\[\\] newElement\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "@SuppressWarnings(\"unchecked\")\n    public $1<$2> add(final T[] newElement) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Add<%%CURRENTLEVELELEMENT%%>(NormalizationUtils.normalizeArray(newElement))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> add\\(final List<T> newElement\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "@SuppressWarnings(\"unchecked\")\n    public $1<$2> add(final List<T> newElement) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Add<%%CURRENTLEVELELEMENT%%>(NormalizationUtils.normalizeList(newElement))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> add\\(final Map<K,V> newElement\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "@SuppressWarnings(\"unchecked\")\n    public $1<$2> add(final Map<K,V> newElement) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Add<%%CURRENTLEVELELEMENT%%>(NormalizationUtils.normalizeMap(newElement))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> add\\(final Set<T> newElement\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "@SuppressWarnings(\"unchecked\")\n    public $1<$2> add(final Set<T> newElement) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Add<%%CURRENTLEVELELEMENT%%>(NormalizationUtils.normalizeSet(newElement))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> add\\(final (.*?) newElement\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "@SuppressWarnings(\"unchecked\")\n    public $1<$2> add(final $3 newElement) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Add<%%CURRENTLEVELELEMENT%%>(newElement)));\n    }");
        
        methods.put(
                "public (.*?)<(.*?)> addAll\\(final T\\[\\]\\.\\.\\. newElements\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> addAll(final T[]... newElements) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Add<%%CURRENTLEVELELEMENT%%>(NormalizationUtils.normalizeArrays(newElements))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> addAll\\(final List<T>\\.\\.\\. newElements\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> addAll(final List<T>... newElements) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Add<%%CURRENTLEVELELEMENT%%>(NormalizationUtils.normalizeLists(newElements))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> addAll\\(final Map<K,V>\\.\\.\\. newElements\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> addAll(final Map<K,V>... newElements) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Add<%%CURRENTLEVELELEMENT%%>(NormalizationUtils.normalizeMaps(newElements))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> addAll\\(final Set<T>\\.\\.\\. newElements\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> addAll(final Set<T>... newElements) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Add<%%CURRENTLEVELELEMENT%%>(NormalizationUtils.normalizeSets(newElements))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> addAll\\(final (.*?)\\.\\.\\. newElements\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> addAll(final $3... newElements) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Add<%%CURRENTLEVELELEMENT%%>(newElements)));\n    }");
        
        methods.put(
                "public (.*?)<(.*?)> addAll\\(final Collection<T\\[\\]> collection\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> addAll(final Collection<T[]> collection) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.AddAll<%%CURRENTLEVELELEMENT%%>(NormalizationUtils.normalizeArrays(collection))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> addAll\\(final Collection<List<T>> collection\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> addAll(final Collection<List<T>> collection) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.AddAll<%%CURRENTLEVELELEMENT%%>(NormalizationUtils.normalizeLists(collection))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> addAll\\(final Collection<Map<K,V>> collection\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> addAll(final Collection<Map<K,V>> collection) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.AddAll<%%CURRENTLEVELELEMENT%%>(NormalizationUtils.normalizeMaps(collection))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> addAll\\(final Collection<Set<T>> collection\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> addAll(final Collection<Set<T>> collection) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.AddAll<%%CURRENTLEVELELEMENT%%>(NormalizationUtils.normalizeSets(collection))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> addAll\\(final Collection<(.*?)> collection\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> addAll(final Collection<$3> collection) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.AddAll<%%CURRENTLEVELELEMENT%%>(collection)));\n    }");
        
        
        methods.put(
                "public (.*?)<(.*?)> insert\\(final int position, final T\\[\\] newElement\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "@SuppressWarnings(\"unchecked\")\n    public $1<$2> insert(final int position, final T[] newElement) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Insert<%%CURRENTLEVELELEMENT%%>(position, NormalizationUtils.normalizeArray(newElement))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> insert\\(final int position, final List<T> newElement\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "@SuppressWarnings(\"unchecked\")\n    public $1<$2> insert(final int position, final List<T> newElement) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Insert<%%CURRENTLEVELELEMENT%%>(position, NormalizationUtils.normalizeList(newElement))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> insert\\(final int position, final Map<K,V> newElement\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "@SuppressWarnings(\"unchecked\")\n    public $1<$2> insert(final int position, final Map<K,V> newElement) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Insert<%%CURRENTLEVELELEMENT%%>(position, NormalizationUtils.normalizeMap(newElement))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> insert\\(final int position, final Set<T> newElement\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "@SuppressWarnings(\"unchecked\")\n    public $1<$2> insert(final int position, final Set<T> newElement) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Insert<%%CURRENTLEVELELEMENT%%>(position, NormalizationUtils.normalizeSet(newElement))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> insert\\(final int position, final (.*?) newElement\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "@SuppressWarnings(\"unchecked\")\n    public $1<$2> insert(final int position, final $3 newElement) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Insert<%%CURRENTLEVELELEMENT%%>(position, newElement)));\n    }");

        
        methods.put(
                "public (.*?)<(.*?)> insertAll\\(final int position, final T\\[\\]\\.\\.\\. newElements\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> insertAll(final int position, final T[]... newElements) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Insert<%%CURRENTLEVELELEMENT%%>(position, NormalizationUtils.normalizeArrays(newElements))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> insertAll\\(final int position, final List<T>\\.\\.\\. newElements\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> insertAll(final int position, final List<T>... newElements) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Insert<%%CURRENTLEVELELEMENT%%>(position, NormalizationUtils.normalizeLists(newElements))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> insertAll\\(final int position, final Map<K,V>\\.\\.\\. newElements\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> insertAll(final int position, final Map<K,V>... newElements) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Insert<%%CURRENTLEVELELEMENT%%>(position, NormalizationUtils.normalizeMaps(newElements))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> insertAll\\(final int position, final Set<T>\\.\\.\\. newElements\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> insertAll(final int position, final Set<T>... newElements) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Insert<%%CURRENTLEVELELEMENT%%>(position, NormalizationUtils.normalizeSets(newElements))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> insertAll\\(final int position, final (.*?)\\.\\.\\. newElements\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> insertAll(final int position, final $3... newElements) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Insert<%%CURRENTLEVELELEMENT%%>(position, newElements)));\n    }");

        
        methods.put(
                "public (.*?)<(.*?)> insertAll\\(final int position, final Map<K,V\\[\\]> map\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> insertAll(final int position, final Map<K,V[]> map) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.InsertAll<K,V[]>(position, NormalizationUtils.normalizeMapOfArray(map))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> insertAll\\(final int position, final Map<K,List<V>> map\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> insertAll(final int position, final Map<K,List<V>> map) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.InsertAll<K,List<V>>(position, NormalizationUtils.normalizeMapOfList(map))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> insertAll\\(final int position, final Map<K1,Map<K2,V>> map\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> insertAll(final int position, final Map<K1,Map<K2,V>> map) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.InsertAll<K1,Map<K2,V>>(position, NormalizationUtils.normalizeMapOfMap(map))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> insertAll\\(final int position, final Map<K,Set<V>> map\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> insertAll(final int position, final Map<K,Set<V>> map) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.InsertAll<K,Set<V>>(position, NormalizationUtils.normalizeMapOfSet(map))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> insertAll\\(final int position, final Map<(.*?)> map\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> insertAll(final int position, final Map<$3> map) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.InsertAll<$3>(position, map)));\n    }");
        
        
        methods.put(
                "public (.*?)<(.*?)> insert\\(final int position, final K newKey, final V\\[\\] newValue\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> insert(final int position, final K newKey, final V[] newValue) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Insert<K,V[]>(position, newKey, NormalizationUtils.normalizeArray(newValue))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> insert\\(final int position, final K newKey, final List<V> newValue\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> insert(final int position, final K newKey, final List<V> newValue) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Insert<K,List<V>>(position, newKey, NormalizationUtils.normalizeList(newValue))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> insert\\(final int position, final K1 newKey, final Map<K2,V> newValue\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> insert(final int position, final K1 newKey, final Map<K2,V> newValue) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Insert<K1,Map<K2,V>>(position, newKey, NormalizationUtils.normalizeMap(newValue))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> insert\\(final int position, final K newKey, final Set<V> newValue\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> insert(final int position, final K newKey, final Set<V> newValue) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Insert<K,Set<V>>(position, newKey, NormalizationUtils.normalizeSet(newValue))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> insert\\(final int position, final (.*?) newKey, final (.*?) newValue\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> insert(final int position, final $3 newKey, final $4 newValue) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Insert<$3,$4>(position, newKey, newValue)));\n    }");
        
        methods.put(
                "public (.*?)<(.*?)> put\\(final K newKey, final V\\[\\] newValue\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> put(final K newKey, final V[] newValue) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Put<K,V[]>(newKey, NormalizationUtils.normalizeArray(newValue))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> put\\(final K newKey, final List<V> newValue\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> put(final K newKey, final List<V> newValue) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Put<K,List<V>>(newKey, NormalizationUtils.normalizeList(newValue))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> put\\(final K1 newKey, final Map<K2,V> newValue\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> put(final K1 newKey, final Map<K2,V> newValue) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Put<K1,Map<K2,V>>(newKey, NormalizationUtils.normalizeMap(newValue))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> put\\(final K newKey, final Set<V> newValue\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> put(final K newKey, final Set<V> newValue) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Put<K,Set<V>>(newKey, NormalizationUtils.normalizeSet(newValue))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> put\\(final (.*?) newKey, final (.*?) newValue\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> put(final $3 newKey, final $4 newValue) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.Put<$3,$4>(newKey, newValue)));\n    }");
        
        methods.put(
                "public (.*?)<(.*?)> putAll\\(final Map<K,V\\[\\]> map\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> putAll(final Map<K,V[]> map) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.PutAll<K,V[]>(NormalizationUtils.normalizeMapOfArray(map))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> putAll\\(final Map<K,List<V>> map\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> putAll(final Map<K,List<V>> map) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.PutAll<K,List<V>>(NormalizationUtils.normalizeMapOfList(map))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> putAll\\(final Map<K1,Map<K2,V>> map\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> putAll(final Map<K1,Map<K2,V>> map) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.PutAll<K1,Map<K2,V>>(NormalizationUtils.normalizeMapOfMap(map))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> putAll\\(final Map<K,Set<V>> map\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> putAll(final Map<K,Set<V>> map) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.PutAll<K,Set<V>>(NormalizationUtils.normalizeMapOfSet(map))));\n    }");
        methods.put(
                "public (.*?)<(.*?)> putAll\\(final Map<(.*?)> map\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> putAll(final Map<$3> map) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.PutAll<$3>(map)));\n    }");

        methods.put(
                "public (.*?)<(.*?)> removeAllIndexes\\(final int\\.\\.\\. indices\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeAllIndexes(final int... indices) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveAllIndexes<%%CURRENTLEVELELEMENT%%>(indices)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeAllIndexesNot\\(final int\\.\\.\\. indices\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeAllIndexesNot(final int... indices) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveAllIndexesNot<%%CURRENTLEVELELEMENT%%>(indices)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeAllEqual\\(final (.*?)\\.\\.\\. values\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeAllEqual(final $3... values) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveAllEqual<%%CURRENTLEVELELEMENT%%>(values)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeAllNull\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeAllNull() {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveAllNull<%%CURRENTLEVELELEMENT%%>()));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeAllTrue\\(final IEvaluator<Boolean,\\? super Entry<(.*?)>> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeAllTrue(final IEvaluator<Boolean,? super Entry<$3>> eval) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveAllTrue<$3>(eval)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeAllFalse\\(final IEvaluator<Boolean,\\? super Entry<(.*?)>> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeAllFalse(final IEvaluator<Boolean,? super Entry<$3>> eval) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveAllFalse<$3>(eval)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeAllTrue\\(final IEvaluator<Boolean,\\? super (.*?)> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeAllTrue(final IEvaluator<Boolean,? super $3> eval) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveAllTrue<$3>(eval)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeAllFalse\\(final IEvaluator<Boolean,\\? super (.*?)> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeAllFalse(final IEvaluator<Boolean,? super $3> eval) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveAllFalse<$3>(eval)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeAllNullOrTrue\\(final IEvaluator<Boolean,\\? super (.*?)> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeAllNullOrTrue(final IEvaluator<Boolean,? super $3> eval) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveAllNullOrTrue<$3>(eval)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeAllNullOrFalse\\(final IEvaluator<Boolean,\\? super (.*?)> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeAllNullOrFalse(final IEvaluator<Boolean,? super $3> eval) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveAllNullOrFalse<$3>(eval)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeAllNotNullAndTrue\\(final IEvaluator<Boolean,\\? super (.*?)> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeAllNotNullAndTrue(final IEvaluator<Boolean,? super $3> eval) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveAllNotNullAndTrue<$3>(eval)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeAllNotNullAndFalse\\(final IEvaluator<Boolean,\\? super (.*?)> eval\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeAllNotNullAndFalse(final IEvaluator<Boolean,? super $3> eval) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveAllNotNullAndFalse<$3>(eval)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeAllKeys\\(final (.*?)\\.\\.\\. keys\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeAllKeys(final $3... keys) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveAllKeys<%%CURRENTLEVELENTRYELEMENT%%>(keys)));\n    }");
        methods.put(
                "public (.*?)<(.*?)> removeAllKeysNot\\(final (.*?)\\.\\.\\. keys\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> removeAllKeysNot(final $3... keys) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().execute(new %%STRUCTUREFUNCS%%.RemoveAllKeysNot<%%CURRENTLEVELENTRYELEMENT%%>(keys)));\n    }");

        methods.put(
                "public (.*?) getAsArray\\(final Type<(.*?)> type\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1 getAsArray(final Type<$2> type) {\n        return endIf().buildArray(type).get();\n    }");
        methods.put(
                "public (.*?) getAsList\\(\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1 getAsList() {\n        return endIf().buildList().get();\n    }");
        
        methods.put(
                "public (.*?)<(.*?)> replaceWith\\(final (.*?) replacement\\) \\{\\s*\\n\\s*return null;\\s*\\n\\s*\\}", 
                "public $1<$2> replaceWith(final $3 replacement) {\n        return new $1Impl<$2>(%%ELEMENTTYPE%%getTarget().replaceWith(replacement));\n    }");

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
    

    
    private static String getNormalization(final String currentLevelType) {
        if (currentLevelType.equals("T") || currentLevelType.equals("K") || currentLevelType.equals("V") || currentLevelType.equals("K1") || currentLevelType.equals("K2")) {
            return "Normalization.NONE";
        } else if (currentLevelType.equals("T[]") || currentLevelType.equals("V[]")) {
            return "Normalization.ARRAY";
        } else if (currentLevelType.equals("List<T>") || currentLevelType.equals("List<V>")) {
            return "Normalization.LIST";
        } else if (currentLevelType.equals("Map<K,V>") || currentLevelType.equals("Map<K2,V>")) {
            return "Normalization.MAP";
        } else if (currentLevelType.equals("Entry<K,V>") | currentLevelType.equals("Entry<K2,V>")) {
            return "Normalization.MAPENTRY";
        } else if (currentLevelType.equals("Set<T>") || currentLevelType.equals("Set<V>")) {
            return "Normalization.SET";
        } else if (currentLevelType.equals("T[][]")) {
            return "Normalization.ARRAY_OF_ARRAY";
        } else if (currentLevelType.equals("List<T>[]")) {
            return "Normalization.ARRAY_OF_LIST";
        } else if (currentLevelType.equals("Map<K,V>[]")) {
            return "Normalization.ARRAY_OF_MAP";
        } else if (currentLevelType.equals("Set<T>[]")) {
            return "Normalization.ARRAY_OF_SET";
        } else if (currentLevelType.equals("List<T[]>")) {
            return "Normalization.LIST_OF_ARRAY";
        } else if (currentLevelType.equals("List<List<T>>")) {
            return "Normalization.LIST_OF_LIST";
        } else if (currentLevelType.equals("List<Map<K,V>>")) {
            return "Normalization.LIST_OF_MAP";
        } else if (currentLevelType.equals("List<Set<T>>")) {
            return "Normalization.LIST_OF_SET";
        } else if (currentLevelType.equals("Set<T[]>")) {
            return "Normalization.SET_OF_ARRAY";
        } else if (currentLevelType.equals("Set<List<T>>")) {
            return "Normalization.SET_OF_LIST";
        } else if (currentLevelType.equals("Set<Map<K,V>>")) {
            return "Normalization.SET_OF_MAP";
        } else if (currentLevelType.equals("Set<Set<T>>")) {
            return "Normalization.SET_OF_SET";
        } else if (currentLevelType.equals("Map<K,V[]>")) {
            return "Normalization.MAP_OF_ARRAY";
        } else if (currentLevelType.equals("Map<K,List<V>>")) {
            return "Normalization.MAP_OF_LIST";
        } else if (currentLevelType.equals("Map<K1,Map<K2,V>>")) {
            return "Normalization.MAP_OF_MAP";
        } else if (currentLevelType.equals("Map<K,Set<V>>")) {
            return "Normalization.MAP_OF_SET";
        } else if (currentLevelType.equals("Entry<K,V[]>")) {
            return "Normalization.MAPENTRY_OF_ARRAY";
        } else if (currentLevelType.equals("Entry<K,List<V>>")) {
            return "Normalization.MAPENTRY_OF_LIST";
        } else if (currentLevelType.equals("Entry<K1,Map<K2,V>>")) {
            return "Normalization.MAPENTRY_OF_MAP";
        } else if (currentLevelType.equals("Entry<K,Set<V>>")) {
            return "Normalization.MAPENTRY_OF_SET";
        }
        System.out.println(currentLevelType);
        return "Normalization.NONE";
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
            impl = impl.replace("%%NORMALIZATION%%", getNormalization(currentLevelType));
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
            impl = impl.replace("%%NORMALIZATION%%", getNormalization(currentLevelType));
            newFileContents = newFileContents.replaceAll(implementation.getKey(), impl);
        }
        newFileContents = newFileContents.replaceAll(getGetDeclaration(), getGetImpl(hasEndIf, hasEndOn));
        newFileContents = newFileContents.replaceAll(getEndForDeclaration(), getEndForImpl(previousLevelStructure, currentLevelStructure, currentLevel, fileContents).replaceAll("%%ELEMENTTYPE%%", "this.type, "));
        return newFileContents;
    }
    
}
