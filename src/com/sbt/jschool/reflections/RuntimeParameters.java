package com.sbt.jschool.reflections;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;

public class RuntimeParameters {
    /* Task 2 */
    private static void getAllParameters() {
        Field privateField;
        Class<Runtime> runtimeClass = Runtime.class;
        Method[] methods = runtimeClass.getDeclaredMethods();

        ArrayList<Type> allParameters = new ArrayList<>(Arrays.asList(runtimeClass.getTypeParameters()));
        addBounds(runtimeClass.getTypeParameters(), allParameters);

        try {
            privateField = runtimeClass.getDeclaredField("integers");
            privateField.setAccessible(true);
            allParameters.add(privateField.getGenericType());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        //Получаем параметр интерфейса
        Type[] intfs = runtimeClass.getGenericInterfaces();
        for(Type i: intfs){
            if(i instanceof ParameterizedType){
                allParameters.addAll(Arrays.asList(((ParameterizedType) i).getActualTypeArguments()));
            }
        }

        for (Method m : methods) {
            Type g = m.getGenericReturnType();
            //Получить только параметризованные типы. Убрать для получения всех типов
            if(g instanceof ParameterizedType){
                allParameters.add(m.getGenericReturnType());
            }
        }

        for (Type t : allParameters) {
            System.out.println(t.getTypeName());
        }

    }

    private static void addBounds(TypeVariable<?>[] array, ArrayList<Type> allParameters) {
        for (TypeVariable<?> var : array) {
            Type[] bounds = var.getBounds();
            //add bound parameters from class to array
            allParameters.addAll(Arrays.asList(bounds));
        }
    }


    public static void main(String[] args) {
        RuntimeParameters.getAllParameters();
    }
}
