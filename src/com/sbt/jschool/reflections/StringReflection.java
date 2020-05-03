package com.sbt.jschool.reflections;

import java.lang.reflect.Modifier;

public class StringReflection {
    /* Task 1 */
    private static void getPackageName(){
        Class<String> strClass = String.class;
        System.out.println(strClass.getPackageName());
    }
    private static void getClassModifiers(){
        Class<String> strClass = String.class;
        int modifiers = strClass.getModifiers();
        System.out.println(Modifier.toString(modifiers));
    }
    private static void getClassHierarchy(){
        Class<?> strClass = String.class;
        while(strClass != null){
            System.out.println(strClass.getName());
            strClass = strClass.getSuperclass();
        }
    }
    private static void getInterfacesTree(){
        Class<?> strClass = String.class;
        while(strClass != null){
            Class<?>[] interfaces =  strClass.getInterfaces();
            for(Class<?> infs : interfaces){
                System.out.println(infs);
            }
            strClass = strClass.getSuperclass();
        }

    }


    public static void main(String[] args) {
        getPackageName();
        getClassModifiers();
        getClassHierarchy();
        getInterfacesTree();
    }
}
