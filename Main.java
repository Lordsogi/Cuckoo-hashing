package com.mycompany.app;

public class Main {
    public static void main(String[] args) {
        CuckooMap<Integer, String> test3 = new CuckooMap<>(34);

        test3.put(26, "test test");
        System.out.print(test3.get(26));
    }
}
