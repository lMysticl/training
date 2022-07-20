package collections.hashset;

/**
 * @author Pavel Putrenkov
 */
public interface Book<K, V> extends Iterable<V> {
    boolean inset(K key, V value);

    boolean delete(K key);

    V get(K key);

    int size();
}
