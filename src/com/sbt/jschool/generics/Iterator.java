package com.sbt.jschool.generics;

import java.util.NoSuchElementException;

public class Iterator<E> implements java.util.Iterator {
    private E value;
    private int length;
    private int counter = 0;
    private LinkedList<E> list;

    public Iterator(LinkedList<E> list){
        this.list = list;
        this.length = list.getLength();
    }

    @Override
    public boolean hasNext() {
        return this.counter < length;
    }

    @Override
    public E next() {
        if(counter >= length){
            throw new NoSuchElementException();
        }
        this.value = this.list.get(counter);
        counter++;
        return value;
    }
}
