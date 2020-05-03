package com.sbt.jschool.reflections;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class ClassInfo {
    /* Task 3
    * For simplicity using Example class
    * */
    private Class<?> cls;
    private Method[] methods;


    ClassInfo(Class<?> cls){
        this.cls = cls;
    }
    public void getClassMethods(){
        HashSet<Method> methodSet = new HashSet<>();
        methodSet.addAll(Arrays.asList(cls.getDeclaredMethods()));
        methodSet.addAll(Arrays.asList(cls.getMethods()));
        methodSet.forEach(m -> System.out.println(m));
    }
    public void getClassGetters(){
        this.methods = cls.getDeclaredMethods();
        for(Method m: methods){
            System.out.println(m.getReturnType().getName());
            if(m.getName().startsWith("get")
                    && m.getParameterTypes().length == 0
                    && !m.getReturnType().getName().equals("void")
            ){
                System.out.println(m);
            }
        }
    }
    public void checkConstantStrings(){
        Boolean allFinalFieldsEqual = true;
        Field[] fields = cls.getDeclaredFields();
        for(Field f: fields){
            f.setAccessible(true);
            int modifiers = f.getModifiers();
            if(Modifier.isFinal(modifiers)){
                try {
                    String content = f.get(cls.getConstructor().newInstance()).toString();
                    if (!content.equals(f.getName())){
                        allFinalFieldsEqual = false;
                        break;
                    }
                } catch (IllegalAccessException | NoSuchMethodException | InstantiationException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        if(allFinalFieldsEqual) System.out.println("All final field names equal to content");
        else System.out.println("Not all field names equal and thats bad");
    }

    public static void main(String[] args) {
        ClassInfo info = new ClassInfo(ExampleClass.class);
        info.getClassMethods();
        info.getClassGetters();
        info.checkConstantStrings();
    }

}

class ExampleClass extends Thread{
    private final String MONDAY = "MONDAY";
    private final String SLEEPTIME = "SLEEPTIME";

//    private final String Trap = "It's a trap!";
    public int trappy = -1;

    public ExampleClass() {
    }

    public String getMONDAY() {
        return this.MONDAY;
    }
    public String getSLEEPTIME() {
        return this.SLEEPTIME;
    }
    private void getOneFinal(){
        System.out.println(this.MONDAY);
    }

    protected void getOneFinalButProtected(){
        System.out.println(this.MONDAY);
    }

}