package com.sbt.jschool.generics.unused;

import java.util.ArrayList;
import java.util.List;

public class AnotherTest {


    class GenClass<T> implements GenInterface<T>{
        T value;

        public GenClass(){

        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        @Override
        public void print(T value) {
            System.out.println(value.toString());
        }
    }
    interface GenInterface<T>{
        void print(T value);
    }

    public void test(){
        GenInterface<Integer> gi = new GenClass<>();

        gi.print(10);

        GenInterface<String> gs = new GenClass<>();

        gs.print("test");
    }
}
