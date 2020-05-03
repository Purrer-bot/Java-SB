package com.sbt.jschool.reflections;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

import static java.util.Collections.emptyList;

public class Runtime<T extends Number> implements Callable<Double> {
    private final List<Integer> integers = emptyList();

    public List<T> numbers() {
        return emptyList();
    }

    public List<String> strings() {
        return emptyList();
    }

    @Override
    public Double call() {
        return 0d;
    }



}



