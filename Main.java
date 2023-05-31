package com.mycompany.app;

public class Main {
    public static void main(String[] args) {
        CuckooMap<Integer, String> cuckooHashMap = new CuckooMap<>(4);

        // Inserting key-value pairs
        cuckooHashMap.put(1, "One");
        cuckooHashMap.put(2, "Two");
        cuckooHashMap.put(3, "Three");
        cuckooHashMap.put(4, "Four");

        // Getting values by keys
        System.out.println(cuckooHashMap.get(1)); // Output: One
        System.out.println(cuckooHashMap.get(2)); // Output: Two
        System.out.println(cuckooHashMap.get(3)); // Output: Three

        // Checking if keys exist
        System.out.println(cuckooHashMap.containsKey(1)); // Output: true
        System.out.println(cuckooHashMap.containsKey(5)); // Output: false

        // Checking if values exist
        System.out.println(cuckooHashMap.containsValue("One")); // Output: true
        System.out.println(cuckooHashMap.containsValue("Five")); // Output: false

        // Removing key-value pair
        cuckooHashMap.remove(2);
        System.out.println(cuckooHashMap.containsKey(2)); // Output: false
        System.out.println(cuckooHashMap.get(2)); // Output: null

        // Size of the map
        System.out.println(cuckooHashMap.size()); // Output: 3

        // Clearing the map
        cuckooHashMap.clear();
        System.out.println(cuckooHashMap.size()); // Output: 0
    }
}
