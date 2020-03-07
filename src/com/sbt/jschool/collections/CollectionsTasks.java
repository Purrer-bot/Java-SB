package com.sbt.jschool.collections;

import java.io.*;
import java.util.*;

public class CollectionsTasks {
    private static void getDifs(ArrayList<String> text) {
        //Here we need ALL words not only strings
        HashSet<String> difWords = new HashSet<>(text);

        System.out.println(difWords.size());

        ArrayList<String> words = new ArrayList<>(difWords);
        words.sort(new AlphComparator());
        for (String word : words) {
            System.out.println(word);
        }
    }

    private static void countWords(ArrayList<String> text) {
        //Here we need ALL words not only strings
        HashMap<String, Integer> counter = new HashMap<>();
        for (String word : text) {
            if (!counter.containsKey(word)) {
                counter.put(word, 1);
            } else {
                int value = counter.get(word);
                value++;
                counter.replace(word, counter.get(word), value);
            }
        }
        for (Map.Entry<String, Integer> entry : counter.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private static void reverse(ArrayList<String> text) {
        //Here we need only strings not words
        ArrayDeque<String> deque = new ArrayDeque<>(text);
        while (!deque.isEmpty()) {
            System.out.println(deque.pollLast());
        }
    }

    private static void getElem(ArrayList<String> text, int elem) {
        //Here we need only strings not words
        try {
            String word = text.get(elem);
            System.out.println(word);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("No such elem");
        }
    }

    //Two different reader cause we need lines or words
    private static ArrayList<String> readLines() {
        try {
            ArrayList<String> lines = new ArrayList<>();
            FileInputStream fl = new FileInputStream("C:\\Users\\Letova\\IdeaProjects\\JavaSB\\src\\com\\sbt\\jschool\\collections\\read.txt");
            BufferedReader buffer = new BufferedReader(new InputStreamReader(fl));

            String line = buffer.readLine();
            while (line != null) {
                lines.add(line);
                line = buffer.readLine();
            }
            return lines;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ArrayList<String> readWords() {
        try {
            ArrayList<String> words = new ArrayList<>();
            FileInputStream fl = new FileInputStream("C:\\Users\\Letova\\IdeaProjects\\JavaSB\\src\\com\\sbt\\jschool\\collections\\read.txt");
            BufferedReader buffer = new BufferedReader(new InputStreamReader(fl));

            String line = buffer.readLine();
            while (line != null) {
                String[] wordsLine = line.split("\\s+");
                Collections.addAll(words, wordsLine);

                line = buffer.readLine();
            }
            return words;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {
        ArrayList<String> strings = readLines();
        ArrayList<String> words = readWords();

        getDifs(words);
        System.out.println("========");
        countWords(words);
        System.out.println("========");
        reverse(strings);
        System.out.println("========");

        System.out.println("Now enter the number of string");
        Scanner scanner = new Scanner(System.in);
        int elem = scanner.nextInt();
        scanner.close();
        getElem(strings, elem);

    }

    public static class AlphComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            if(o1.length() == o2.length()){
                return o1.compareToIgnoreCase(o2);
            }
            else{
                return Integer.compare(o1.length(), o2.length());
            }
        }
    }
}
