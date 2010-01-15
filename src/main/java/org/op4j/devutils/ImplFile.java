package org.op4j.devutils;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.op4j.functions.ArrayFuncs;
import org.op4j.functions.ListFuncs;
import org.op4j.functions.MapFuncs;
import org.op4j.functions.SetFuncs;
import org.op4j.operators.impl.AbstractOperatorImpl;
import org.op4j.operators.qualities.UniqOperator;
import org.op4j.target.Target;
import org.op4j.target.Target.Structure;

public class ImplFile {
    
    public enum LevelStructure { ARRAY, LIST, SET, MAP, MAP_ENTRY, ELEMENTS, LEVEL_DOES_NOT_EXIST }
    
    private final String packageName;
    private final Set<String> imports;
    private final Set<String> methodNames;
    private final List<String> methodImplementations;
    private final String className;
    private final TypeRep interfaceTypeRep;
    
    private String currentLevelType;
    private String currentLevelElement;

    private static Map<String,String[]> paramNames;
    private static Set<String> varargsPositions;
    
    private static Set<String> arrayTypeRequired;
    
    private static Map<String,LevelStructure> currentLevelsByPrefix;
    private static Map<String,LevelStructure> previousLevelsByPrefix;
    
    
    static {
    	
        paramNames = new HashMap<String, String[]>();
        paramNames.put("ifIndex", new String[] {"indices"});
        paramNames.put("ifMatching", new String[] {"eval"});
        paramNames.put("ifNotMatching", new String[] {"eval"});
        paramNames.put("ifNullOrNotMatching", new String[] {"eval"});
        paramNames.put("ifNotNullNotMatching", new String[] {"eval"});
        paramNames.put("ifNullOrMatching", new String[] {"eval"});
        paramNames.put("ifIndexNot", new String[] {"indices"});
        paramNames.put("ifNotNullMatching", new String[] {"eval"});
        paramNames.put("ifKeyEquals", new String[] {"keys"});
        paramNames.put("ifKeyNotEquals", new String[] {"keys"});
        paramNames.put("add", new String[] {"newElements"});
        paramNames.put("insert", new String[] {"position","newElements"});
        paramNames.put("insert%3", new String[] {"position","newKey","newValue"});
        paramNames.put("insertAll", new String[] {"position","map"});
        paramNames.put("addAll", new String[] {"collection"});
        paramNames.put("removeIndexes", new String[] {"indices"});
        paramNames.put("removeEquals", new String[] {"values"});
        paramNames.put("removeMatching", new String[] {"eval"});
        paramNames.put("removeNotMatching", new String[] {"eval"});
        paramNames.put("removeNullOrNotMatching", new String[] {"eval"});
        paramNames.put("removeNotNullNotMatching", new String[] {"eval"});
        paramNames.put("removeNotNullMatching", new String[] {"eval"});
        paramNames.put("removeNullOrMatching", new String[] {"eval"});
        paramNames.put("removeIndexesNot", new String[] {"indices"});
        paramNames.put("removeKeys", new String[] {"keys"});
        paramNames.put("removeKeysNot", new String[] {"keys"});
        paramNames.put("convert", new String[] {"converter"});
        paramNames.put("eval", new String[] {"eval"});
        paramNames.put("exec", new String[] {"function"});
        paramNames.put("sort", new String[] {"comparator"});
        paramNames.put("put", new String[] {"newKey","newValue"});
        paramNames.put("putAll", new String[] {"map"});
        paramNames.put("getAsArray", new String[] {"type"});
        paramNames.put("forEach", new String[] {"elementType"});

        varargsPositions = new HashSet<String>();
        varargsPositions.add("removeIndexes$0");
        varargsPositions.add("removeIndexesNot$0");
        varargsPositions.add("removeEquals$0");
        varargsPositions.add("removeKeys$0");
        varargsPositions.add("removeKeysNot$0");
        varargsPositions.add("add$0");
        varargsPositions.add("insert$1");
        varargsPositions.add("ifIndex$0");
        varargsPositions.add("ifIndexNot$0");
        varargsPositions.add("ifKeyEquals$0");
        varargsPositions.add("ifKeyNotEquals$0");

        arrayTypeRequired = new HashSet<String>();
// Commented out when changing the use of the "elementType" variable        
//        arrayTypeRequired.add("Level0ArrayOperator");
//        arrayTypeRequired.add("Level0ArrayElements");
//        arrayTypeRequired.add("Level0ArraySelected");
        arrayTypeRequired.add("Level1ArrayOperator");
        arrayTypeRequired.add("Level1ArrayElements");
        arrayTypeRequired.add("Level1ArraySelected");
//        arrayTypeRequired.add("Level0ArrayOfArray");
        arrayTypeRequired.add("Level1ArrayOfArray");
        arrayTypeRequired.add("Level2ArrayOfArray");
//        arrayTypeRequired.add("Level0ListOfArray");
//        arrayTypeRequired.add("Level1ListOfArray");
        arrayTypeRequired.add("Level2ListOfArray");
//        arrayTypeRequired.add("Level0MapOfArray");
//        arrayTypeRequired.add("Level1MapOfArray");
//        arrayTypeRequired.add("Level2MapOfArray");
        arrayTypeRequired.add("Level3MapOfArray");
//        arrayTypeRequired.add("Level0SetOfArray");
//        arrayTypeRequired.add("Level1SetOfArray");
        arrayTypeRequired.add("Level2SetOfArray");

        currentLevelsByPrefix = new HashMap<String, LevelStructure>();
        
        currentLevelsByPrefix.put("Level0ArrayOperator", LevelStructure.ARRAY);
        currentLevelsByPrefix.put("Level0ArraySelected", LevelStructure.ARRAY);
        currentLevelsByPrefix.put("Level0ListOperator", LevelStructure.LIST);
        currentLevelsByPrefix.put("Level0ListSelected", LevelStructure.LIST);
        currentLevelsByPrefix.put("Level0MapOperator", LevelStructure.MAP);
        currentLevelsByPrefix.put("Level0MapSelected", LevelStructure.MAP);
        currentLevelsByPrefix.put("Level0SetOperator", LevelStructure.SET);
        currentLevelsByPrefix.put("Level0SetSelected", LevelStructure.SET);
        
        currentLevelsByPrefix.put("Level1ArrayElements", LevelStructure.ELEMENTS);
        currentLevelsByPrefix.put("Level1ArraySelected", LevelStructure.ELEMENTS);
        currentLevelsByPrefix.put("Level1ListElements", LevelStructure.ELEMENTS);
        currentLevelsByPrefix.put("Level1ListSelected", LevelStructure.ELEMENTS);
        currentLevelsByPrefix.put("Level1MapEntries", LevelStructure.MAP_ENTRY);
        currentLevelsByPrefix.put("Level1MapSelected", LevelStructure.MAP_ENTRY);
        currentLevelsByPrefix.put("Level1SetElements", LevelStructure.ELEMENTS);
        currentLevelsByPrefix.put("Level1SetSelected", LevelStructure.ELEMENTS);

        currentLevelsByPrefix.put("Level2MapEntries", LevelStructure.ELEMENTS);
        currentLevelsByPrefix.put("Level2MapSelected", LevelStructure.ELEMENTS);

        currentLevelsByPrefix.put("Level0ArrayOfArray", LevelStructure.ARRAY);
        currentLevelsByPrefix.put("Level1ArrayOfArray", LevelStructure.ARRAY);
        currentLevelsByPrefix.put("Level2ArrayOfArray", LevelStructure.ELEMENTS);
        currentLevelsByPrefix.put("Level0ArrayOfList", LevelStructure.ARRAY);
        currentLevelsByPrefix.put("Level1ArrayOfList", LevelStructure.LIST);
        currentLevelsByPrefix.put("Level2ArrayOfList", LevelStructure.ELEMENTS);
        currentLevelsByPrefix.put("Level0ArrayOfMap", LevelStructure.ARRAY);
        currentLevelsByPrefix.put("Level1ArrayOfMap", LevelStructure.MAP);
        currentLevelsByPrefix.put("Level2ArrayOfMap", LevelStructure.MAP_ENTRY);
        currentLevelsByPrefix.put("Level3ArrayOfMap", LevelStructure.ELEMENTS);
        currentLevelsByPrefix.put("Level0ArrayOfSet", LevelStructure.ARRAY);
        currentLevelsByPrefix.put("Level1ArrayOfSet", LevelStructure.SET);
        currentLevelsByPrefix.put("Level2ArrayOfSet", LevelStructure.ELEMENTS);

        currentLevelsByPrefix.put("Level0ListOfArray", LevelStructure.LIST);
        currentLevelsByPrefix.put("Level1ListOfArray", LevelStructure.ARRAY);
        currentLevelsByPrefix.put("Level2ListOfArray", LevelStructure.ELEMENTS);
        currentLevelsByPrefix.put("Level0ListOfList", LevelStructure.LIST);
        currentLevelsByPrefix.put("Level1ListOfList", LevelStructure.LIST);
        currentLevelsByPrefix.put("Level2ListOfList", LevelStructure.ELEMENTS);
        currentLevelsByPrefix.put("Level0ListOfMap", LevelStructure.LIST);
        currentLevelsByPrefix.put("Level1ListOfMap", LevelStructure.MAP);
        currentLevelsByPrefix.put("Level2ListOfMap", LevelStructure.MAP_ENTRY);
        currentLevelsByPrefix.put("Level3ListOfMap", LevelStructure.ELEMENTS);
        currentLevelsByPrefix.put("Level0ListOfSet", LevelStructure.LIST);
        currentLevelsByPrefix.put("Level1ListOfSet", LevelStructure.SET);
        currentLevelsByPrefix.put("Level2ListOfSet", LevelStructure.ELEMENTS);

        currentLevelsByPrefix.put("Level0MapOfArray", LevelStructure.MAP);
        currentLevelsByPrefix.put("Level1MapOfArray", LevelStructure.MAP_ENTRY);
        currentLevelsByPrefix.put("Level2MapOfArraySelectedEntriesKey", LevelStructure.ELEMENTS);
        currentLevelsByPrefix.put("Level2MapOfArrayEntriesSelectedKey", LevelStructure.ELEMENTS);
        currentLevelsByPrefix.put("Level2MapOfArrayEntriesKey", LevelStructure.ELEMENTS);
        currentLevelsByPrefix.put("Level2MapOfArraySelectedEntriesSelectedKey", LevelStructure.ELEMENTS);
        currentLevelsByPrefix.put("Level2MapOfArraySelectedEntriesValue", LevelStructure.ARRAY);
        currentLevelsByPrefix.put("Level2MapOfArrayEntriesSelectedValue", LevelStructure.ARRAY);
        currentLevelsByPrefix.put("Level2MapOfArrayEntriesValue", LevelStructure.ARRAY);
        currentLevelsByPrefix.put("Level2MapOfArraySelectedEntriesSelectedValue", LevelStructure.ARRAY);
        currentLevelsByPrefix.put("Level3MapOfArray", LevelStructure.ELEMENTS);

        currentLevelsByPrefix.put("Level0MapOfList", LevelStructure.MAP);
        currentLevelsByPrefix.put("Level1MapOfList", LevelStructure.MAP_ENTRY);
        currentLevelsByPrefix.put("Level2MapOfListSelectedEntriesKey", LevelStructure.ELEMENTS);
        currentLevelsByPrefix.put("Level2MapOfListEntriesSelectedKey", LevelStructure.ELEMENTS);
        currentLevelsByPrefix.put("Level2MapOfListEntriesKey", LevelStructure.ELEMENTS);
        currentLevelsByPrefix.put("Level2MapOfListSelectedEntriesSelectedKey", LevelStructure.ELEMENTS);
        currentLevelsByPrefix.put("Level2MapOfListSelectedEntriesValue", LevelStructure.LIST);
        currentLevelsByPrefix.put("Level2MapOfListEntriesSelectedValue", LevelStructure.LIST);
        currentLevelsByPrefix.put("Level2MapOfListEntriesValue", LevelStructure.LIST);
        currentLevelsByPrefix.put("Level2MapOfListSelectedEntriesSelectedValue", LevelStructure.LIST);
        currentLevelsByPrefix.put("Level3MapOfList", LevelStructure.ELEMENTS);

        currentLevelsByPrefix.put("Level0MapOfMap", LevelStructure.MAP);
        currentLevelsByPrefix.put("Level1MapOfMap", LevelStructure.MAP_ENTRY);
        currentLevelsByPrefix.put("Level2MapOfMapSelectedEntriesKey", LevelStructure.ELEMENTS);
        currentLevelsByPrefix.put("Level2MapOfMapEntriesSelectedKey", LevelStructure.ELEMENTS);
        currentLevelsByPrefix.put("Level2MapOfMapEntriesKey", LevelStructure.ELEMENTS);
        currentLevelsByPrefix.put("Level2MapOfMapSelectedEntriesSelectedKey", LevelStructure.ELEMENTS);
        currentLevelsByPrefix.put("Level2MapOfMapSelectedEntriesValue", LevelStructure.MAP);
        currentLevelsByPrefix.put("Level2MapOfMapEntriesSelectedValue", LevelStructure.MAP);
        currentLevelsByPrefix.put("Level2MapOfMapEntriesValue", LevelStructure.MAP);
        currentLevelsByPrefix.put("Level2MapOfMapSelectedEntriesSelectedValue", LevelStructure.MAP);
        currentLevelsByPrefix.put("Level3MapOfMap", LevelStructure.MAP_ENTRY);
        currentLevelsByPrefix.put("Level4MapOfMap", LevelStructure.ELEMENTS);

        currentLevelsByPrefix.put("Level0MapOfSet", LevelStructure.MAP);
        currentLevelsByPrefix.put("Level1MapOfSet", LevelStructure.MAP_ENTRY);
        currentLevelsByPrefix.put("Level2MapOfSetSelectedEntriesKey", LevelStructure.ELEMENTS);
        currentLevelsByPrefix.put("Level2MapOfSetEntriesSelectedKey", LevelStructure.ELEMENTS);
        currentLevelsByPrefix.put("Level2MapOfSetEntriesKey", LevelStructure.ELEMENTS);
        currentLevelsByPrefix.put("Level2MapOfSetSelectedEntriesSelectedKey", LevelStructure.ELEMENTS);
        currentLevelsByPrefix.put("Level2MapOfSetSelectedEntriesValue", LevelStructure.SET);
        currentLevelsByPrefix.put("Level2MapOfSetEntriesSelectedValue", LevelStructure.SET);
        currentLevelsByPrefix.put("Level2MapOfSetEntriesValue", LevelStructure.SET);
        currentLevelsByPrefix.put("Level2MapOfSetSelectedEntriesSelectedValue", LevelStructure.SET);
        currentLevelsByPrefix.put("Level3MapOfSet", LevelStructure.ELEMENTS);

        currentLevelsByPrefix.put("Level0SetOfArray", LevelStructure.SET);
        currentLevelsByPrefix.put("Level1SetOfArray", LevelStructure.ARRAY);
        currentLevelsByPrefix.put("Level2SetOfArray", LevelStructure.ELEMENTS);
        currentLevelsByPrefix.put("Level0SetOfList", LevelStructure.SET);
        currentLevelsByPrefix.put("Level1SetOfList", LevelStructure.LIST);
        currentLevelsByPrefix.put("Level2SetOfList", LevelStructure.ELEMENTS);
        currentLevelsByPrefix.put("Level0SetOfMap", LevelStructure.SET);
        currentLevelsByPrefix.put("Level1SetOfMap", LevelStructure.MAP);
        currentLevelsByPrefix.put("Level2SetOfMap", LevelStructure.MAP_ENTRY);
        currentLevelsByPrefix.put("Level3SetOfMap", LevelStructure.ELEMENTS);
        currentLevelsByPrefix.put("Level0SetOfSet", LevelStructure.SET);
        currentLevelsByPrefix.put("Level1SetOfSet", LevelStructure.SET);
        currentLevelsByPrefix.put("Level2SetOfSet", LevelStructure.ELEMENTS);
        
        currentLevelsByPrefix.put("Level0GenericMulti", LevelStructure.LIST);
        currentLevelsByPrefix.put("Level0GenericUniq", LevelStructure.ELEMENTS);
        


        previousLevelsByPrefix = new HashMap<String, LevelStructure>();
        
        previousLevelsByPrefix.put("Level0ArrayOperator", LevelStructure.LEVEL_DOES_NOT_EXIST);
        previousLevelsByPrefix.put("Level0ArraySelected", LevelStructure.LEVEL_DOES_NOT_EXIST);
        previousLevelsByPrefix.put("Level0ListOperator", LevelStructure.LEVEL_DOES_NOT_EXIST);
        previousLevelsByPrefix.put("Level0ListSelected", LevelStructure.LEVEL_DOES_NOT_EXIST);
        previousLevelsByPrefix.put("Level0MapOperator", LevelStructure.LEVEL_DOES_NOT_EXIST);
        previousLevelsByPrefix.put("Level0MapSelected", LevelStructure.LEVEL_DOES_NOT_EXIST);
        previousLevelsByPrefix.put("Level0SetOperator", LevelStructure.LEVEL_DOES_NOT_EXIST);
        previousLevelsByPrefix.put("Level0SetSelected", LevelStructure.LEVEL_DOES_NOT_EXIST);
        
        previousLevelsByPrefix.put("Level1ArrayElements", LevelStructure.ARRAY);
        previousLevelsByPrefix.put("Level1ArraySelected", LevelStructure.ARRAY);
        previousLevelsByPrefix.put("Level1ListElements", LevelStructure.LIST);
        previousLevelsByPrefix.put("Level1ListSelected", LevelStructure.LIST);
        previousLevelsByPrefix.put("Level1MapEntries", LevelStructure.MAP);
        previousLevelsByPrefix.put("Level1MapSelected", LevelStructure.MAP);
        previousLevelsByPrefix.put("Level1SetElements", LevelStructure.SET);
        previousLevelsByPrefix.put("Level1SetSelected", LevelStructure.SET);

        previousLevelsByPrefix.put("Level2MapEntries", LevelStructure.MAP_ENTRY);
        previousLevelsByPrefix.put("Level2MapSelected", LevelStructure.MAP_ENTRY);

        previousLevelsByPrefix.put("Level0ArrayOfArray", LevelStructure.LEVEL_DOES_NOT_EXIST);
        previousLevelsByPrefix.put("Level1ArrayOfArray", LevelStructure.ARRAY);
        previousLevelsByPrefix.put("Level2ArrayOfArray", LevelStructure.ARRAY);
        previousLevelsByPrefix.put("Level0ArrayOfList", LevelStructure.LEVEL_DOES_NOT_EXIST);
        previousLevelsByPrefix.put("Level1ArrayOfList", LevelStructure.ARRAY);
        previousLevelsByPrefix.put("Level2ArrayOfList", LevelStructure.LIST);
        previousLevelsByPrefix.put("Level0ArrayOfMap", LevelStructure.LEVEL_DOES_NOT_EXIST);
        previousLevelsByPrefix.put("Level1ArrayOfMap", LevelStructure.ARRAY);
        previousLevelsByPrefix.put("Level2ArrayOfMap", LevelStructure.MAP);
        previousLevelsByPrefix.put("Level3ArrayOfMap", LevelStructure.MAP_ENTRY);
        previousLevelsByPrefix.put("Level0ArrayOfSet", LevelStructure.LEVEL_DOES_NOT_EXIST);
        previousLevelsByPrefix.put("Level1ArrayOfSet", LevelStructure.ARRAY);
        previousLevelsByPrefix.put("Level2ArrayOfSet", LevelStructure.SET);

        previousLevelsByPrefix.put("Level0ListOfArray", LevelStructure.LEVEL_DOES_NOT_EXIST);
        previousLevelsByPrefix.put("Level1ListOfArray", LevelStructure.LIST);
        previousLevelsByPrefix.put("Level2ListOfArray", LevelStructure.ARRAY);
        previousLevelsByPrefix.put("Level0ListOfList", LevelStructure.LEVEL_DOES_NOT_EXIST);
        previousLevelsByPrefix.put("Level1ListOfList", LevelStructure.LIST);
        previousLevelsByPrefix.put("Level2ListOfList", LevelStructure.LIST);
        previousLevelsByPrefix.put("Level0ListOfMap", LevelStructure.LEVEL_DOES_NOT_EXIST);
        previousLevelsByPrefix.put("Level1ListOfMap", LevelStructure.LIST);
        previousLevelsByPrefix.put("Level2ListOfMap", LevelStructure.MAP);
        previousLevelsByPrefix.put("Level3ListOfMap", LevelStructure.MAP_ENTRY);
        previousLevelsByPrefix.put("Level0ListOfSet", LevelStructure.LEVEL_DOES_NOT_EXIST);
        previousLevelsByPrefix.put("Level1ListOfSet", LevelStructure.LIST);
        previousLevelsByPrefix.put("Level2ListOfSet", LevelStructure.SET);

        previousLevelsByPrefix.put("Level0MapOfArray", LevelStructure.LEVEL_DOES_NOT_EXIST);
        previousLevelsByPrefix.put("Level1MapOfArray", LevelStructure.MAP);
        previousLevelsByPrefix.put("Level2MapOfArray", LevelStructure.MAP_ENTRY);
        previousLevelsByPrefix.put("Level3MapOfArray", LevelStructure.ARRAY);

        previousLevelsByPrefix.put("Level0MapOfList", LevelStructure.LEVEL_DOES_NOT_EXIST);
        previousLevelsByPrefix.put("Level1MapOfList", LevelStructure.MAP);
        previousLevelsByPrefix.put("Level2MapOfList", LevelStructure.MAP_ENTRY);
        previousLevelsByPrefix.put("Level3MapOfList", LevelStructure.LIST);

        previousLevelsByPrefix.put("Level0MapOfMap", LevelStructure.LEVEL_DOES_NOT_EXIST);
        previousLevelsByPrefix.put("Level1MapOfMap", LevelStructure.MAP);
        previousLevelsByPrefix.put("Level2MapOfMap", LevelStructure.MAP_ENTRY);
        previousLevelsByPrefix.put("Level3MapOfMap", LevelStructure.MAP);
        previousLevelsByPrefix.put("Level4MapOfMap", LevelStructure.MAP_ENTRY);

        previousLevelsByPrefix.put("Level0MapOfSet", LevelStructure.LEVEL_DOES_NOT_EXIST);
        previousLevelsByPrefix.put("Level1MapOfSet", LevelStructure.MAP);
        previousLevelsByPrefix.put("Level2MapOfSet", LevelStructure.MAP_ENTRY);
        previousLevelsByPrefix.put("Level3MapOfSet", LevelStructure.SET);

        previousLevelsByPrefix.put("Level0SetOfArray", LevelStructure.LEVEL_DOES_NOT_EXIST);
        previousLevelsByPrefix.put("Level1SetOfArray", LevelStructure.SET);
        previousLevelsByPrefix.put("Level2SetOfArray", LevelStructure.ARRAY);
        previousLevelsByPrefix.put("Level0SetOfList", LevelStructure.LEVEL_DOES_NOT_EXIST);
        previousLevelsByPrefix.put("Level1SetOfList", LevelStructure.SET);
        previousLevelsByPrefix.put("Level2SetOfList", LevelStructure.LIST);
        previousLevelsByPrefix.put("Level0SetOfMap", LevelStructure.LEVEL_DOES_NOT_EXIST);
        previousLevelsByPrefix.put("Level1SetOfMap", LevelStructure.SET);
        previousLevelsByPrefix.put("Level2SetOfMap", LevelStructure.MAP);
        previousLevelsByPrefix.put("Level3SetOfMap", LevelStructure.MAP_ENTRY);
        previousLevelsByPrefix.put("Level0SetOfSet", LevelStructure.LEVEL_DOES_NOT_EXIST);
        previousLevelsByPrefix.put("Level1SetOfSet", LevelStructure.SET);
        previousLevelsByPrefix.put("Level2SetOfSet", LevelStructure.SET);
        
        previousLevelsByPrefix.put("Level0GenericMulti", LevelStructure.LEVEL_DOES_NOT_EXIST);
        previousLevelsByPrefix.put("Level0GenericUniq", LevelStructure.LEVEL_DOES_NOT_EXIST);
    
    
    }
    
    
    public ImplFile(final Class<?> interfaceClass){
        
        super();
        
        try {
            
            this.imports = new LinkedHashSet<String>();
            this.methodImplementations = new ArrayList<String>();
            this.methodNames = new LinkedHashSet<String>();
            this.interfaceTypeRep = new TypeRep(interfaceClass);
            this.packageName = interfaceClass.getPackage().getName().replace(".intf.", ".impl.");
            this.imports.add(interfaceClass.getName());
            
            this.className = 
                StringUtils.substringBefore(this.interfaceTypeRep.getStringRep(), "<") +
                "Impl<" +
                StringUtils.substringAfter(this.interfaceTypeRep.getStringRep(), "<");
            
            computeMethodImplementations(interfaceClass);
            
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
        
    }
    

    public LevelStructure getCurrentLevelStructure() {
        for (final Map.Entry<String, LevelStructure> currentLevel : currentLevelsByPrefix.entrySet()) {
            if (this.className.startsWith(currentLevel.getKey())) {
                return currentLevel.getValue();
            }
        }
        throw new RuntimeException("No current level structure for: " + this.className);
    }
    

    public LevelStructure getPreviousLevelStructure() {
        for (final Map.Entry<String, LevelStructure> currentLevel : previousLevelsByPrefix.entrySet()) {
            if (this.className.startsWith(currentLevel.getKey())) {
                return currentLevel.getValue();
            }
        }
        throw new RuntimeException("No previous level structure for: " + this.className);
    }
    
    public int getCurrentLevel() {
        return Integer.parseInt(this.className.substring(5, 6));
    }
    
    
    public boolean hasEndIf() {
        return this.methodNames.contains("endIf");
    }
    
    
    public boolean hasEndOn() {
        return this.methodNames.contains("endOn");
    }
    
    
    public String getPackageName() {
        return this.packageName;
    }



    public String getClassName() {
        return this.className;
    }

   
    public String getCurrentLevelType() {
        return this.currentLevelType;
    }

    
    public String getCurrentLevelElement() {
        return this.currentLevelElement;
    }


    public void computeMethodImplementations(final Class<?> interfaceClass) throws Exception {
        
        final List<Method> interfaceMethods = new ArrayList<Method>();
        interfaceMethods.addAll(Arrays.asList(interfaceClass.getDeclaredMethods()));
        
        final Set<Type> extendedInterfaces = new HashSet<Type>(Arrays.asList(interfaceClass.getGenericInterfaces()));
        Type getReturnType = null;
        for (final Type extendedInterface : extendedInterfaces) {
            if (extendedInterface instanceof ParameterizedType) {
                final ParameterizedType pType = (ParameterizedType) extendedInterface;
                if (((Class<?>)pType.getRawType()).equals(UniqOperator.class)) {
                    getReturnType = pType.getActualTypeArguments()[0];
                }
            }
        }
        
        try {
            interfaceMethods.add(interfaceClass.getMethod("get"));
        } catch (NoSuchMethodException e) {
            // nothing to do
        }
        
        for (final Method interfaceMethod : interfaceMethods) {
            
            final String methodName = interfaceMethod.getName();
            this.methodNames.add(methodName);
            
            final TypeRep returnType =
                (methodName.equals("get")? 
                        new TypeRep(getReturnType) :
                        new TypeRep(interfaceMethod.getGenericReturnType()));

            
            final Type[] parameterTypes = interfaceMethod.getGenericParameterTypes();

            if (methodName.equals("exec")) {
                this.currentLevelType = (new TypeRep(((WildcardType)((ParameterizedType)parameterTypes[0]).getActualTypeArguments()[1]).getLowerBounds()[0])).getStringRep();
                if (this.currentLevelType.endsWith("[]")) {
                    this.currentLevelElement = this.currentLevelType.substring(0, this.currentLevelType.length() - 2);
                } else if (this.currentLevelType.startsWith("List<") && this.currentLevelType.endsWith(">")) {
                    this.currentLevelElement = this.currentLevelType.substring(5, this.currentLevelType.length() - 1);
                } else if (this.currentLevelType.startsWith("Set<") && this.currentLevelType.endsWith(">")) {
                    this.currentLevelElement = this.currentLevelType.substring(4, this.currentLevelType.length() - 1);
                } else if (this.currentLevelType.startsWith("Map<") && this.currentLevelType.endsWith(">")) {
                    this.currentLevelElement = "Map.Entry<" + this.currentLevelType.substring(4, this.currentLevelType.length() - 1) + ">";
                } else {
                    this.currentLevelElement = "%%CURRENTELEMENTSHOULDNOTBEHERE%%";
                }
            }
            
            final StringBuilder parameterStrBuilder = new StringBuilder();
            parameterStrBuilder.append("(");
            
            String[] paramNamesForMethod = paramNames.get(methodName + "%" + parameterTypes.length);
            if (paramNamesForMethod == null) {
                paramNamesForMethod = paramNames.get(methodName);
            }
            
            for (int j = 0; j < parameterTypes.length; j++) {
                
                if (j > 0) {
                    parameterStrBuilder.append(", ");
                }
                
                final TypeRep paramTypeRep = new TypeRep(parameterTypes[j]);
                
                String paramType = paramTypeRep.getStringRep();
                if (paramType.endsWith("[]")) {
                    if (varargsPositions.contains(methodName + "$" + j)) {
                        paramType = StringUtils.substringBeforeLast(paramType,"[]") + "...";
                    }
                }
                        
                parameterStrBuilder.append("final " + paramType + " ");
                
                if (paramNamesForMethod == null) {
                    throw new RuntimeException("No name for parameter " + j + " of method " + methodName + " in interface " + this.interfaceTypeRep.getStringRep());
                }
                parameterStrBuilder.append(paramNamesForMethod[j]);
                
            }
            parameterStrBuilder.append(")");
            
            final StringBuilder strBuilder = new StringBuilder();
            strBuilder.append("    public " + returnType.getStringRep() + " " + methodName + parameterStrBuilder.toString() + " {\n");
            strBuilder.append("        return null;\n");
            strBuilder.append("    }\n");
            
            this.methodImplementations.add(strBuilder.toString());
            
        }
        
    }
    
    
    public String computeFileContents() {
        final StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("package " + this.packageName + ";\n\n");
        if (isArrayTypeRequired()) {
            this.imports.add(org.javaruntype.type.Type.class.getName());
        }
        this.imports.add(Target.class.getName());
        switch(getCurrentLevelStructure()) {
            case ARRAY : this.imports.add(ArrayFuncs.class.getName()); break; 
            case LIST : this.imports.add(ListFuncs.class.getName()); break; 
            case MAP : this.imports.add(MapFuncs.class.getName()); break; 
            case SET : this.imports.add(SetFuncs.class.getName()); break;
            default :
        }
        if (this.methodNames.contains("endFor") || this.methodNames.contains("endOn")) {
            this.imports.add(Structure.class.getName().replace("$", "."));
            if (getCurrentLevelStructure().equals(LevelStructure.ARRAY) && getPreviousLevelStructure().equals(LevelStructure.ARRAY)) {
                this.imports.add(Array.class.getName());
            }
        }
        this.imports.add(AbstractOperatorImpl.class.getName());
        final List<String> importList = new ArrayList<String>(this.imports);
        Collections.sort(importList);
        for (final String classImport : importList) {
            if (classImport.contains(".") && !(classImport.startsWith("java.lang.") && !classImport.startsWith("java.lang.reflect."))) {
                strBuilder.append("import " + classImport + ";\n");
            }
        }
        strBuilder.append("\n\n");
        strBuilder.append("public class " + this.className + " extends AbstractOperatorImpl implements " + this.interfaceTypeRep.getStringRep() + " {\n");
        if (isArrayTypeRequired()) {
            strBuilder.append("\n\n");
            final String arrayLetter = (this.className.contains("MapOfArray")? "V" : this.className.contains("Level1ArrayOfArray")? "T[]" : "T");
            strBuilder.append("    private final Type<? extends " + arrayLetter + "> type;\n");
        }
        strBuilder.append("\n\n");
        if (isArrayTypeRequired()) {
            final String arrayLetter = (this.className.contains("MapOfArray")? "V" : this.className.contains("Level1ArrayOfArray")? "T[]" : "T");
            strBuilder.append("    public " + StringUtils.substringBefore(this.className, "<") + "(final Type<? extends " + arrayLetter + "> type, final Target target) {\n");
            strBuilder.append("        super(target);\n");
            strBuilder.append("        this.type = type;\n");
        } else {
            strBuilder.append("    public " + StringUtils.substringBefore(this.className, "<") + "(final Target target) {\n");
            strBuilder.append("        super(target);\n");
        }
        strBuilder.append("    }\n");
        strBuilder.append("\n\n");
        for (final String methodImplementation : this.methodImplementations) {
            strBuilder.append(methodImplementation + "\n\n");
        }
        strBuilder.append("\n}\n");
        
        String fileContents = strBuilder.toString();
        if (isArrayTypeRequired()) {
            fileContents = MethodImplementor.processArray(fileContents, getCurrentLevel(), getCurrentLevelStructure(), getPreviousLevelStructure(), getCurrentLevelType(), getCurrentLevelElement(), hasEndIf(), hasEndOn());
        } else {
            fileContents = MethodImplementor.processNonArray(fileContents, getCurrentLevel(), getCurrentLevelStructure(), getPreviousLevelStructure(), getCurrentLevelType(), getCurrentLevelElement(), hasEndIf(), hasEndOn());
        }
        return fileContents;
    }

    
    
    private boolean isArrayTypeRequired() {
        for (final String prefix : arrayTypeRequired) {
            if (this.className.contains(prefix)) {
                return true;
            }
        }
        return false;
    }
    
    
    
    
    public class TypeRep {
        
        private final String stringRep;
        private final Class<?> componentClass;
        
        public TypeRep(final Type type) {
            
            super();

            if (type instanceof GenericArrayType) {
                final GenericArrayType gatType = (GenericArrayType) type;
                final TypeRep component = new TypeRep(gatType.getGenericComponentType());
                this.stringRep = component.getStringRep() + "[]";
                this.componentClass = component.getComponentClass();
            } else if (type instanceof ParameterizedType) {
                final ParameterizedType pType = (ParameterizedType) type;
                final TypeRep componentType = new TypeRep(pType.getRawType());
                this.componentClass = componentType.getComponentClass();
                final String[] paramStringReps = new String[pType.getActualTypeArguments().length];
                for (int i = 0; i < pType.getActualTypeArguments().length; i++) {
                    final TypeRep paramTypeRep = new TypeRep(pType.getActualTypeArguments()[i]);
                    paramStringReps[i] = paramTypeRep.getStringRep();
                }
                final StringBuilder srStrBuilder = new StringBuilder();
                srStrBuilder.append(this.componentClass.getSimpleName());
                srStrBuilder.append("<");
                srStrBuilder.append(StringUtils.join(paramStringReps, ","));
                srStrBuilder.append(">");
                this.stringRep = srStrBuilder.toString();
            } else if (type instanceof TypeVariable<?>) {
                final TypeVariable<?> tvType = (TypeVariable<?>) type;
                this.stringRep = tvType.getName();
                this.componentClass = null;
            } else if (type instanceof WildcardType){
                final WildcardType wType = (WildcardType) type;
                if (wType.getLowerBounds() != null && wType.getLowerBounds().length > 0) {
                    this.stringRep = "? super " +  (new TypeRep(wType.getLowerBounds()[0]).getStringRep());
                } else if (wType.getUpperBounds() != null && wType.getUpperBounds().length > 0) {
                    this.stringRep = "? extends " +  (new TypeRep(wType.getUpperBounds()[0]).getStringRep());
                } else {
                    this.stringRep = "?";
                }
                this.componentClass = null;
            } else {
                // type instanceof Class<?>
                final Class<?> cType = (Class<?>) type;
                this.componentClass = cType;
                ImplFile.this.imports.add(cType.getName().replaceAll("\\$","."));
                
                final StringBuilder srStrBuilder = new StringBuilder();
                srStrBuilder.append(cType.getSimpleName());
                if (cType.getTypeParameters() != null && cType.getTypeParameters().length > 0) {
                    
                    final String[] paramStringReps = new String[cType.getTypeParameters().length];
                    for (int i = 0; i < cType.getTypeParameters().length; i++) {
                        final TypeRep paramTypeRep = new TypeRep(cType.getTypeParameters()[i]);
                        paramStringReps[i] = paramTypeRep.getStringRep();
                    }
                    srStrBuilder.append("<");
                    srStrBuilder.append(StringUtils.join(paramStringReps, ","));
                    srStrBuilder.append(">");
                }
                this.stringRep = srStrBuilder.toString();
                
            }
                
        }

        public String getStringRep() {
            return this.stringRep;
        }

        public Class<?> getComponentClass() {
            return this.componentClass;
        }
        
    }
    
}