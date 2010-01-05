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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.lang.StringUtils;


/**
 * 
 * @since 1.0
 * 
 * @author Daniel Fern&aacute;ndez
 *
 */
public class SelectedImplFileCreator {
    
    
    private static int filesCreated = 0;
    
    
    private static Class<?>[] getClasses(String packageName)
            throws ClassNotFoundException, IOException {
        
        ClassLoader classLoader = Thread.currentThread()
                .getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile().replaceAll("%20", " ")));
        }
        ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
        }
        return classes.toArray(new Class[classes.size()]);
    }

    
    
    private static List<Class<?>> findClasses(File directory, String packageName)
            throws ClassNotFoundException {

        List<Class<?>> classes = new ArrayList<Class<?>>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "."
                        + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName
                        + '.'
                        + file.getName().substring(0,
                                file.getName().length() - 6)));
            }
        }
        return classes;
    }    
    
    
    
    
    
    
    
    private static void createFile(final String structureName, final String className, final ImplFile implFile) 
            throws Exception {
        
        final URL templateFileURL = Thread.currentThread().getContextClassLoader().getResource("OneLevelMapStructureLevel0SelectedOperator.txt");
        
        final String operatorsFolderName =
            (StringUtils.substringBeforeLast(StringUtils.substringBeforeLast(templateFileURL.getPath(), "/"),"/") + "/operators").replaceAll("%20", " ");
        
        final File operatorsFolder = new File(operatorsFolderName);
        operatorsFolder.mkdir();
        
        
        final File implFolder = new File(operatorsFolderName + "/impl");
        implFolder.mkdir();
        
        
        final File structureFolder = new File(operatorsFolderName + "/impl/" + structureName.toLowerCase());
        structureFolder.mkdir();
        
        final File resultFile = new File(operatorsFolderName + "/impl/" + structureName.toLowerCase() + "/" + className);
        final FileWriter writer = new FileWriter(resultFile);
        

        writer.write(implFile.computeFileContents());

        writer.close();
        
        System.out.println("Created: " + resultFile.getAbsolutePath());
        filesCreated++;
        
    }
    
    
    
	
	public static void main(String[] args) {
	

	    Class<?> currentClass = null;
		try {

			final Class<?>[] classesInPackage = getClasses("org.op4j.operators.intf");
			for (Class<?> clazz : classesInPackage) {
			    
			    currentClass = clazz;
			    
			    if (clazz.getSimpleName().contains("Selected")) {
			        
	                final ImplFile implFile = new ImplFile(clazz);
	                createFile(StringUtils.substringAfterLast(implFile.getPackageName(),"."),StringUtils.substringBefore(implFile.getClassName(),"<") + ".java", implFile);
	                
			    }
			    
			}

            System.out.println("\n " + filesCreated + " files created.");
			
		} catch (final Exception e) {
		    System.err.println("ERROR PROCESSING : " + currentClass.getName());
			e.printStackTrace();
		}
		
		
	}
	
	
}
