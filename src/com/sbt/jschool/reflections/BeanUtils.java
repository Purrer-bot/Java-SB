package com.sbt.jschool.reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class BeanUtils {
    /**
      * Scans object "from" for all getters. If object "to"
      * contains correspondent setter, it will invoke it
      * to set property value for "to" which equals to the property
      * of "from".
      * <p/>
      * The type in setter should be compatible to the value returned
      * by getter (if not, no invocation performed).
      * Compatible means that parameter type in setter should
      * be the same or be superclass of the return type of the getter.
      * <p/>
      * The method takes care only about public methods.
      *
      * @param to   Object which properties will be set.
      * @param from Object which properties will be used to get values.
      */

    private static ArrayList<Method> getGetters(Class<?> cls){
        ArrayList<Method> getterMethods = new ArrayList<>();
        ArrayList<String> croppedMethodNames = new ArrayList<>();
        Method[] methods = cls.getMethods();
        for(Method m : methods){
            if(m.getName().startsWith("get")
                    && m.getParameterTypes().length == 0
                    && !m.getReturnType().getName().equals("void")
            ){
                getterMethods.add(m);
            }
        }
        return getterMethods;
    }
    private static ArrayList<Method> getSetters(Class<?> cls){
        ArrayList<Method> setterMethods = new ArrayList<>();
        Method[] methods = cls.getMethods();
        for(Method m : methods){
            if(m.getName().startsWith("set")
                    && m.getParameterTypes().length == 1
            ){
                setterMethods.add(m);
            }
        }
        return setterMethods;
    }

    private static ArrayList<Method> getSettersAndGetters(Class<?> clsTo, Class<?> clsFrom){
        ArrayList<Method> getSetList = new ArrayList<>();
        Method[] methodsTo = clsTo.getDeclaredMethods();
        Method[] methodsFrom = clsFrom.getDeclaredMethods();

        for(Method m : methodsFrom){
            if(m.getName().startsWith("get")
                    && m.getParameterTypes().length == 0
                    && !m.getReturnType().getName().equals("void")
                    && Modifier.isPublic(m.getModifiers())
            ){
                getSetList.add(m);
            }
        }
        for(Method m : methodsTo){
            if(m.getName().startsWith("set")
                    && m.getParameterTypes().length == 1
                    && Modifier.isPublic(m.getModifiers())
            ){
                getSetList.add(m);
            }
        }
        return  getSetList;

    }
    public static void assign(Object to, Object from) {
        Class<?> clsTo = to.getClass();
        Class<?> clsFrom = from.getClass();
        ArrayList<Method> getSetList = BeanUtils.getSettersAndGetters(clsTo, clsFrom);
        for(Method m: getSetList){
            if(m.getName().startsWith("get")){
                String croppedName = m.getName().substring(3);
                for(Method n: getSetList){
                    if(n.getName().startsWith("set") && n.getName().substring(3).equals(croppedName)){
                        Class<?>[] setType = n.getParameterTypes();
                        for(Class<?> s : setType){
                            System.out.println(s.getSuperclass());
                            if(s.equals(m.getReturnType()) || s.equals(m.getReturnType().getSuperclass())){
                                try {
                                    n.invoke(to, m.invoke(from));
                                } catch (IllegalAccessException | InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        From f = new From();
        To t = new To();

        assign(t, f);
        System.out.println("=======");
        System.out.println(t.getD());
        System.out.println(t.getI());
        System.out.println(t.getItr());
    }


}
class From{
    private int i = 1;
    private String d = "Test";
    private Integer itr = 23;

    public From(){}

    public int getI() {
        return i;
    }

    public String getD() {
        return d;
    }

    public Integer getItr() {
        return itr;
    }
}
class To{
    private int i = 4;
    private String d = "Хы";
    private Number itr = 254;


    public To(){}

    public void setI(int i) {
        this.i = i;
    }

    public void setD(String d) {
        this.d = d;
    }

    public int getI() {
        return i;
    }

    public String getD() {
        return d;
    }
    public Number getItr() {
        return itr;
    }

    public void setItr(Number itr) {
        this.itr = itr;
    }

}
