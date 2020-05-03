package com.sbt.jschool.generics.unused;

public class GenericsTest<T> {

    public static void main(String[] args) {
        GenericsTest<String> s = new GenericsTest<>();
        s.test();
    }

    public void test(){
        Test<Integer> t = new Test<>(10);
        System.out.println(t.multiple());

        //Наследника передавать можно
        TestExtendsInter<ExtExtender> ext = new TestExtendsInter<>();


    }


    //Ограничение дженериков
    class Test<T extends Integer>{
        T value;
        Test(T value){
            this.value = value;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
        public <T> Integer multiple(){
            return Integer.valueOf(value)*10;
        }
        //Как работать с ограничениями?
    }

    //Класс идет вперед, потом интерфейс
    //Ваще силное ограничение жесть
    class TestExtendsInter<T extends Extender & A & B>{
        private T value;

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }

    interface A{};
    interface B{};
    class Extender implements A, B{}
    class ExtExtender extends Extender implements A,B{}

    class Box<T>{
        T value;

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }
    class IntBox extends Box<Integer>{

    }
    //Стирание типа это плохо
}
