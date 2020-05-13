package com.sbt.jschool.classloaders;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class ClassLoaderWrapper {
    private ArrayList<String> listOfDirs;

    public ClassLoaderWrapper(ArrayList<String> listOfDirs){
        this.listOfDirs = listOfDirs;
    }

    public Class<?> getClassWithMethod(String className, String methodName){
        Class<?> cls = null;
        try {
            ArrayList<URL> urlClassList = this.getClassPaths();
            ClassLoader clsLoader = this.getClassLoader(urlClassList);
            cls = clsLoader.loadClass(className);
            if(this.checkMethod(cls, methodName)){
                return cls;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("URL exception");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Cannot load class");
        }
        return null;
    }
    private boolean checkMethod(Class<?> cls, String methodName){
        try {
            Method method = cls.getMethod(methodName);
        } catch (NoSuchMethodException e) {
            System.out.println("Method not found");
            return false;
        }
        return true;
    }
    private URLClassLoader getClassLoader(ArrayList<URL> urlList){
        URL[] urls = new URL[urlList.size()];
        for(int i = 0; i < urls.length; i++){
            urls[i] = urlList.get(i);
        }
        return new URLClassLoader(urls);
    }
    private ArrayList<URL> getClassPaths() throws MalformedURLException {
        ArrayList<URL> classURLs = new ArrayList<>();
        for(String dir : this.listOfDirs){
            File dirFile = new File(dir);
            if(dirFile.exists()){
                classURLs.add(dirFile.toURI().toURL());
            }
            else{
                System.out.println("Directory not found: " + dir);
            }
        }
        return classURLs;
    }

    public static void main(String[] args) {
        ArrayList<String> dirs = new ArrayList<>();
        dirs.add("C:\\Users\\Letova\\IdeaProjects\\JavaSB\\out\\production\\JavaSB\\com\\sbt\\jschool\\classloaders\\one_path\\");
        dirs.add("C:\\Users\\Letova\\IdeaProjects\\JavaSB\\out\\production\\JavaSB\\com\\sbt\\jschool\\classloaders\\two_path\\");

        ClassLoaderWrapper clsWrapper = new ClassLoaderWrapper(dirs);
        Class<?> cls =  clsWrapper.getClassWithMethod("com.sbt.jschool.classloaders.one_path.Test1", "nope");
        System.out.println(cls);

    }
}
