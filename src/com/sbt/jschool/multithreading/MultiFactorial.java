package com.sbt.jschool.multithreading;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiFactorial{

    private ArrayList<Integer> fileReader(String path) throws IOException {
        ArrayList<Integer> numbers = new ArrayList<>();
        try(Scanner scanner = new Scanner(new FileInputStream(path))){
            while(scanner.hasNext()){
                numbers.add(scanner.nextInt());
            }
        }
        return numbers;
    }

    private void factorial(int number){
        int res = 1;
        for(int i = 1; i <= number; i++){
            res = res*i;
        }
        System.out.println(Thread.currentThread().getName());
        System.out.println(res);
    }

    public static void main(String[] args) {
        MultiFactorial factorial = new MultiFactorial();
        ArrayList<Integer> numsFromFile = new ArrayList<>();
        try {
            numsFromFile = factorial.fileReader("C:\\Users\\Letova\\IdeaProjects\\JavaSB\\src\\com.sbt.jschool.multithreading\\numbers.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        ExecutorService service = Executors.newFixedThreadPool(numsFromFile.size());
        numsFromFile.forEach(elem->service.execute(() -> factorial.factorial(elem)));
        service.shutdown();

    }


}
