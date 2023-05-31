package com.mycompany.app;

import java.util.*;

/**
 * CukooMap
 */
public class CuckooMap<K, V> implements Map<K, V> {

    private int buckets;
    private Entry<K, V>[] table;
    private int size;

    public CuckooMap(int numberOfBuckets) {
        buckets = numberOfBuckets;
        table = new Entry[buckets];
        size = 0;
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
        if ((table[h1((K) key)] != null) &&
                (table[h1((K) key)].getKey().equals(key))) {
            return true;
        } else if ((table[h2((K) key)] != null) &&
                (table[h2((K) key)].getKey().equals(key))) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean containsValue(Object value) {
        for (int i = 0; i < buckets; i++) {
            if (table[i].getValue().equals(value)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public V get(Object key) {
        int index1 = h1((K) key);
        int index2 = h2((K) key);

        if ((table[index1] != null) && (table[index1].getKey().equals(key))) {
            return table[index1].getValue();
        } else if ((table[index2] != null) && (table[index2].getKey().equals(key))) {
            return table[index2].getValue();
        } else {
            return null;
        }
    }

    public MapEntry getEntry(int index) {
        return (MapEntry) table[index];
    }

    @Override
    public V put(K key, V value) {
        if (size == buckets) {
            tableResize();
        }
        Entry<K,V> tempEntry = new MapEntry(key, value);
        int index1 = h1(key);
        int index2 = h2(key);

        if (table[index1] == null) {
            table[index1] = tempEntry;
            size++;
        } else if (table[index2] == null) {
            table[index2] = tempEntry;
            size++;
        } else if (table[h2(table[index1].getKey())] == null) {
            table[h2(table[index1].getKey())] = table[index1];
            table[index1] = tempEntry;
            size++;
        } else if (table[h2(table[index2].getKey())] == null) {
            table[h2(table[index2].getKey())] = table[index2];
            table[index2] = tempEntry;
            size++;
        } else {
            tableResize();
            put(key, value);
            size++;
        }
        return null;
    }

    @Override
    public V remove(Object key) {
        int index1 = h1((K) key);
        int index2 = h2((K) key);

        if ((table[index1] != null) &&
                (table[index1].getKey().equals(key))) {
            table[index1] = null;
            size--;
        } else if ((table[index2] != null) &&
                (table[index2].getKey().equals(key))) {
            table[index2] = null;
            size--;
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (int i = 0; i < buckets; i++) {
            put(table[i].getKey(), table[i].getValue());
        }
    }

    @Override
    public void clear() {
        for (int i = 0; i < buckets; i++) {
            table[i] = null;
        }
        size=0;
    }

    @Override
    public Set<K> keySet() {
        Set<K> uniqueSet = new HashSet<>();
        for (Entry<K, V> entry : table) {
            if (entry != null) {
                uniqueSet.add(entry.getKey());
            }
        }
        return uniqueSet;
    }

    @Override
    public Collection<V> values() {
        List<V> valueList = new ArrayList<>();
        for (Entry<K, V> entry : table) {
            if (entry != null) {
                valueList.add(entry.getValue());
            }
        }
        return valueList;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> uniqueSet = new HashSet<>();
        for (Entry<K, V> entry : table) {
            if (entry != null) {
                uniqueSet.add(entry);
            }
        }
        return uniqueSet;
    }

    private void tableResize() {
        int newBuckets = buckets * 2;
        Entry<K, V>[] newTable = new Entry[newBuckets];
        for (Entry<K, V> entry : table) {
            int index1 = h1(entry.getKey());
            int index2 = h2(entry.getKey());

            if (newTable[index1] == null) {
                newTable[index1] = entry;
            } else if (newTable[index2] == null) {
                newTable[index2] = entry;
            } else {
                put(entry.getKey(), entry.getValue());
            }
        }
        table = newTable;
    }

    public int h1 (K key) {
        if (key == null) {
            return 0;
        }
        return key.hashCode() % buckets;
    }

    public int h2 (K key) {
        if (key == null) {
            return 0;
        }
        return (h1(key) +3) % buckets;
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
