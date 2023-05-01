package com.mycompany.app;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * CukooMap
 */


public class CuckooMap<K, V> implements Map<K, V> {
    private int size;
    private int numberOfBuckets;
    private ArrayList<MapEntry> hashtable = new ArrayList<>(numberOfBuckets);

    public CuckooMap(int numberOfBuckets) {
        this.numberOfBuckets = numberOfBuckets;
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
        Object tempKey = hashKey(key);
        for (int i = 0; i < hashtable.size(); i++) {
            if (hashtable.get(i).getKey() == tempKey) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        for (int i = 0; i < hashtable.size(); i++) {
            if (hashtable.get(i).getValue() == value) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        return null;
    }

    public MapEntry getEntry(int index) {
        return null;
    }

    @Override
    public V put(K key, V value) {
        MapEntry currentEntry = new MapEntry(key, value);

        size++;
        return null;
    }

    @Override
    public V remove(Object key) {
        size--;
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (int i = 0; 0 < hashtable.size(); i++) {
            hashtable.set(i, null);
        }
    }

    @Override
    public void clear() {
        size = 1;
        hashtable.clear();
    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    private int hashKey(Object key) {
        return (Math.abs(key.hashCode()) % this.size() + 3) % this.size();
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
