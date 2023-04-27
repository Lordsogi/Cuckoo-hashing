package com.mycompany.app;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * CukooMap
 */
public class CuckooMap<K, V> implements Map<K, V> {

    public CuckooMap(int numberOfBuckets) {
    }

    @Override
    public int size() {
        return this.size();
    }

    @Override
    public boolean isEmpty() {
        if (this.size() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean containsKey(Object key) { ///// hash key then check for value
        int tempKey = hashKey(key);
        if (this.containsKey(tempKey)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        if (this.containsValue(value)) {
            return true;
        }
        return false;
    }

    @Override
    public V get(Object key) { ////////////////////////////// return the value at the input key?
        if (this.isEmpty()) {
            return null;
        }
        return this.get(values());
    }

    public MapEntry getEntry(int index) {
        return null;
    }

    @Override
    public V put(K key, V value) {
        return null;
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
    }

    @Override
    public void clear() {
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
