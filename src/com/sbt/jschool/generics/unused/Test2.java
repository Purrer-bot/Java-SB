package com.sbt.jschool.generics.unused;

import java.util.ArrayList;
import java.util.List;

public class Test2 {

    class Box<T>{
        T value;

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }



    public void test(){
        Box<String> sb = new Box<>();
        Box<Integer> ib = new Box<>();

        //Безопасный объект
        //Знак вопроса позволяет лишь читать данные, но не изменять их
        Box<?> b = sb;
        Box<?> c = ib;

    }

    public static void main(String[] args) {
        Test2 t = new Test2();
        t.test();

        //Добавлять нельзя
        ArrayList<Integer> an = new ArrayList<>();
        an.add(1);
        ArrayList<? extends Number> ar = an;
        System.out.println(an.get(0));

        ArrayList<Float> af = new ArrayList<>();
        af.add((float)0.4);
        ar = af;
        System.out.println(ar.get(0));

        //Чтение от objcet, запись от number
        System.out.println("=======");
        ArrayList<? super Number> a = new ArrayList<>();
        a.add(10);
        System.out.println(a.get(0));

        List<? extends Number> src = new ArrayList<>();
        List<? super Number> dest = new ArrayList<>();

        for(Number n : src){
            dest.add(n);
        }

    }
}
