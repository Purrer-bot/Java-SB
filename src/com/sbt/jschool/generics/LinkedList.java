package com.sbt.jschool.generics;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

public class LinkedList<E> implements Iterable{
    private class Node<E>{
        private E value;
        private Node<E> next;

        Node(E value){
            this.value = value;
            this.next = null;
        }

        Node(){
            this.value = null;
            this.next = null;
        }

        public E getValue() {
            return value;
        }

        public void setValue(E value) {
            this.value = value;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }
    }
    private Node<E> head;
    private int length;

    public LinkedList(){
        this.head = null;
        this.length = 0;
    }

    public int getLength() {
        return length;
    }

    public Iterator<E> iterator(){
        return new com.sbt.jschool.generics.Iterator<>(this);
    }

    public void add(E e){
        Node<E> node = new Node<>(e);
        if(head == null){
            this.head = node;
        }
        else{
            Node<E> temp = this.head;
            while(temp.getNext() != null){
                //Разобраться
                temp = temp.next;
            }
            temp.next = node;
        }
        length++;
    }
    public void add(E e, int index){
        Node<E> node = new Node<>(e);
        if(index > length){
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
//        1. head is null
        if(this.head == null){
            this.head = node;
        }
//        2. If index in length add node and move nodes to the right
        else{
            int counter = 0;
            Node<E> temp = this.head;
            Node<E> previous = null;
            while (counter != index){
                previous = temp;
                temp = temp.next;
                counter++;
            }
            if(previous != null){
                previous.setNext(node);
                Node<E> insert = previous.getNext();
                insert.setNext(temp);
            }
        }
    }
    public E get(int index){
        if(index > this.length){
            throw new IndexOutOfBoundsException("Index out of bounds");
        }
        else if(index == 0){
            return this.head.value;
        }
        else{
            Node<E> temp = this.head;
            for(int i = 0; i < index; i++){
                temp = temp.next;
            }
            return temp.value;
        }
    }
    public void remove(int index){
        int counter = 0;
        Node<E> temp = this.head;
        Node<E> previous = null;

        if(index > length){
            throw new IndexOutOfBoundsException("Out of bounds");
        }
        if(index == 0){
            this.head = head.next;
        }
        if(temp != null){
            while(counter != index){
                previous = temp;
                temp = temp.next;
                counter++;
            }
            if(previous != null){
                previous.next = temp.next;
            }
        }
    }

    public void out(){
        if(this.head != null){
            Node<E> temp = this.head;
            while(temp != null){
                System.out.print(temp.value + " ");
                temp = temp.next;
            }
            System.out.println();
        }
    }
    //Simplified cause of start task conditions
    public boolean copy(Collection<? extends E> c){
        if(this.length < c.size()){
            throw new IndexOutOfBoundsException();
        }
        else {
            Node<E> temp = this.head;
            for (E e : c) {
                try{
                temp.value = e;
                temp = temp.next;
                }
                catch (UnsupportedOperationException u){
                    u.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }
    //Simplified cause of start task conditions
    public boolean addAll(Collection<? extends E> c){
        for(E elem: c){
            try{
                this.add(elem);
            }
            catch (UnsupportedOperationException e){
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }



}
