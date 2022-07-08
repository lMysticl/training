package map;

/**
 * @author Pavel Putrenkov
 * @version 1.0
 * @since
 */
public class HashMap {



    /**
     * Связывает определенное значение с определенным ключом в этой карте(map).
     * Если карта перед этим содержала значение для данного ключа, это значение
     * заменится на новое.
     *
     * @param key
     *            ключ с которым указанное значение должно быть связано.
     * @param value
     *            значение которое должно быть связано с ключом.
     * @return вернет предыдущее значение связанное с key, или null
     *         если не было значений связанных с key. (Вернет null
     *         так же, если перед этим key был связан со значением null)
     */
//    public V put(K key, V value) {
//        if (key == null)
//            return putForNullKey(value);
//        int hash = hash(key.hashCode());
//        int i = indexFor(hash, table.length);
//        for (Entry<k , V> e = table[i]; e != null; e = e.next) {
//            Object k;
//            if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
//                V oldValue = e.value;
//                e.value = value;
//                e.recordAccess(this);
//                return oldValue;
//            }
//        }
//
//        modCount++;
//        addEntry(hash, key, value, i);
//        return null;
//    }
//
//
//    static class Entry implements Map.Entry
//    {
//        final K key;
//        V value;
//        Entry next;
//        final int hash;
//        ...//остальной код тут…
//    }
}
