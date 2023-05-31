package com.mycompany.app;

import java.util.*;

/**
 * CukooMap
 */
public class CuckooMap<K, V> implements Map<K, V> {

    private int numberOfBuckets;
    private int size;
    private ArrayList<MapEntry> h1Map = new ArrayList<>();
    private ArrayList<MapEntry> h2Map = new ArrayList<>();
    public CuckooMap(int numberOfBuckets) {
        this.numberOfBuckets = numberOfBuckets;
        for (int i = 0; i < numberOfBuckets; i++) {
            MapEntry tempNull = new MapEntry(null, null);
            h2Map.add(i, tempNull);
            h1Map.add(i, tempNull);
        }
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        return ((h1Map.get(h1((K) key)).getKey() == key) || (h2Map.get(h2((K) key)).getKey() == key));
    }

    @Override
    public boolean containsValue(Object value) {
        for (int i = 0; i < numberOfBuckets; i++) {
            if (h2Map.get(i).getValue() == value) {
                return true;
            }
        }
        for (int i = 0; i < numberOfBuckets; i++) {
            if (h1Map.get(i).getValue() == value) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        if (h1Map.get(h1((K) key)).getKey() == key) {
            return h1Map.get(h1((K) key)).getValue();
        } else {
            return h2Map.get(h2((K) key)).getValue();
        }
    }

    @Override
    public V put(K key, V value) {
        MapEntry tempEntry = new MapEntry(key, value);
        if (h1Map.get(h1((K) key)).getKey() == null) {
            h1Map.add(h1((K) key), tempEntry);
        } else if (h2Map.get(h2((K) key)).getKey() == null) {
            h2Map.add(h2((K) key), tempEntry);
        } else if (h2Map.get(h2(h1Map.get(h1((K) key)).getKey())) == null) {
            h2Map.add(h2(h1Map.get(h1((K) key)).getKey()), h1Map.get(h1((K) key)));
            h1Map.add(h1((K) key), tempEntry);
        } else {
            tableResize(h1Map, h2Map);
            put(key, value);
        }
        size++;
        return null;
    }

    @Override
    public V remove(Object key) {
        if (h1Map.get(h1((K) key)).getKey() == key) {
            h1Map.remove(h1((K) key));
        } else {
            h2Map.remove(h2((K) key));
        }
        size--;
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        ArrayList<MapEntry> tempMap = getEntry(m);
        for (int i = 0; i < tempMap.size(); i++) {
            put(tempMap.get(i).getKey(), tempMap.get(i).getValue());
        }
    }

    @Override
    public void clear() {
        h1Map.clear();
        h2Map.clear();
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        Set returnSet = new HashSet();
        for (int i = 0; i < numberOfBuckets; i++) {
            returnSet.add(h1Map.get(i).getKey());
            returnSet.add(h2Map.get(i).getKey());
        }
        return returnSet;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set returnSet = new HashSet();
        for (int i = 0; i < numberOfBuckets; i++) {
            returnSet.add(h1Map.get(i));
            returnSet.add(h2Map.get(i));
        }
        return returnSet;
    }

    public int h1 (K key) {
        if (key == null) {
            return 0;
        }
        return key.hashCode() % numberOfBuckets;
    }

    public int h2 (K key) {
        if (key == null) {
            return 0;
        }
        return (h1(key) +3) % numberOfBuckets;
    }

//    private CuckooMap<K, V> tableResize (CuckooMap<K, V> tempMap) {
//        numberOfBuckets = numberOfBuckets * 2;
//        CuckooMap<K, V> newMap = new CuckooMap(numberOfBuckets);
//        for (int i = 0; i < size; i++) {
//            newMap.put(h1Map.get(i).getKey(), h1Map.get(i).getValue());
//        }
//        return newMap;
//    }

//    private void tableResize (ArrayList<MapEntry> temp1, ArrayList<MapEntry> temp2) {
//        numberOfBuckets = numberOfBuckets * 2;
//        ArrayList<MapEntry> tempArrayList = new ArrayList<>(numberOfBuckets);
//        for (int i = 0; i < size; i++) {
//            tempArrayList.put(h1Map.get(i).getKey(), h1Map.get(i).getValue());
//            h1Map = tempArrayList;
//        }
//        tempArrayList.clear();
//        for (int i = 0; i < size; i++) {
//            tempArrayList.put(h2Map.get(i).getKey(), h2Map.get(i).getValue());
//            h2Map = tempArrayList;
//        }
//    }

    private void tableResize (ArrayList<MapEntry> temp1, ArrayList<MapEntry> temp2) {
        numberOfBuckets = numberOfBuckets * 2;
        for (int i = 0; i < size; i++) {
            put(h1Map.get(i).getKey(), h1Map.get(i).getValue());
            put(h2Map.get(i).getKey(), h2Map.get(i).getValue());
        }
    }

    private ArrayList<MapEntry> getEntry (Map tempmap) {
        ArrayList<MapEntry> returnMap = new ArrayList<>(numberOfBuckets * 2);
        for (int i = 0; i < numberOfBuckets; i++) {
            returnMap.add(h1Map.get(i));
        }
        for (int i = 0; i < numberOfBuckets; i++) {
            returnMap.add(h2Map.get(i));
        }
        return returnMap;
    }

    public class MapEntry implements Entry<K, V> {
        private K key;
        private V value;

        public MapEntry(K someKey, V someValue) {
            this.key = someKey;
            this.value = someValue;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V newValue) {
            V oldValue = value;
            value = newValue;
            return oldValue;
        }
    }
}
