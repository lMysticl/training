package map;

/**
 * @author Pavel Putrenkov
 * @version 1.0
 * @since
 */
public class RealizeHashMap<K,V> implements Map<K,V> {

    private MapEntry<K,V>[] array;
    private int size;

    public RealizeHashMap()
    {
        array = new MapEntry[8];
    }

    @Override
    public int size()
    {
        return size;
    }

    @Override
    public V put(K key, V value)
    {
        int index = getIndex(key);
        while(true)
        {
            if(array[index] == null)
            {
                MapEntry newEntry = new MapEntry<>(key, value);
                array[index] = newEntry;
                ++size;
                checkForExpansion();
                return null;
            }
            else if(array[index].getKey().equals(key))
            {
                V oldValue = array[index].getValue();
                array[index] = new MapEntry<>(key, value);
                return oldValue;
            }
            else
            {
                index = incIndex(index);
            }
        }
    }

    @Override
    public V get(K key)
    {
        int index = getIndex(key);
        int collisions = 0;
        while(true)
        {

            if(array[index] == null)
            {
                System.out.println("Collisions: " + collisions);
                return null;
            }
            else if(array[index].getKey().equals(key))
            {
                System.out.println("Collisions: " + collisions);
                return array[index].getValue();
            }
            else
            {
                index = incIndex(index);
                ++collisions;
            }
        }
    }

    @Override
    public V remove(K key)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private int incIndex(int index)
    {
        ++index;
        if(index >= array.length)
        {
            index = 0;
        }
        return index;
    }

    private int getIndex(K key)
    {
        return Math.abs(key.hashCode()) % array.length;
    }

    private void checkForExpansion()
    {
        if((size * 10) / array.length > 2)
        {
            MapEntry<K,V>[] oldArray = array;
            array = new MapEntry[array.length*2];
            size = 0;
            for(int i = 0; i < oldArray.length; ++i)
            {
                if(oldArray[i] != null)
                {
                    put(oldArray[i].getKey(), oldArray[i].getValue());
                }
            }
        }
    }

    private static class MapEntry<K,V>
    {
        private final K key;
        private final V value;

        public MapEntry(K key, V value)
        {
            this.key = key;
            this.value = value;
        }

        public K getKey()
        {
            return key;
        }

        public V getValue()
        {
            return value;
        }
    }
}
