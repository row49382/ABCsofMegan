package com.row49382.models;

/**
 * Interface for holding all the dictionary words that can only
 * be read, not altered
 * @param <K> The key of the dictionary
 * @param <V> The value of the dictionary
 */
public interface ReadonlyDictionary<K, V> {

    /**
     * Gets the value for the dictionary using the specified key
     * @param key The key to use for getting values in the dictionary
     * @return The value of the Dictionary for that key
     */
    public V get(K key);
}
