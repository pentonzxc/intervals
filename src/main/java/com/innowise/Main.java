package com.innowise;


import static com.innowise.Intervals.intervalConstruction;
import static com.innowise.Intervals.intervalIdentification;

public class Main {
    public static void main(String[] args) {
        System.out.println(intervalConstruction(new String[]{"M2", "C", "asc"}));
        System.out.println(intervalIdentification(new String[]{"C", "D", "asc"}));
    }
}