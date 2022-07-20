package collections.hashset;

import lombok.Getter;
import lombok.Setter;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Pavel Putrenkov
 */
public class ReferenceBook<K, V> implements Book<K, V> {


    private Node<K, V>[] hashTable;
    private int size = 0;
    private float threshold;

    public ReferenceBook() {
        hashTable = new Node[16];
        threshold = hashTable.length * 0.75f;
    }


    @Override
    public boolean inset(K key, V value) {
//        if(size +1>= threshold){
//            threshold*=2;
//            arrayDoubling();
//        }
//
//        Node<K,V> newNode = new Node<>(key,value);
//        int index = newNode.hash();
//        if(hashTable[index]==null)

        return false;
    }

    private void arrayDoubling() {
    }

    @Override
    public boolean delete(K key) {
        return false;
    }

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Iterator<V> iterator() {
        return new Iterator<V>() {
            int counterArray = 0;
            int valueCounter = 0;
            Iterator<Node<K, V>> subIterator = null;

            @Override
            public boolean hasNext() {
                if (valueCounter == size) {
                    return false;
                }
                if (subIterator == null || !subIterator.hasNext()) {
                    if (moveToNextCell()) {
                        subIterator = hashTable[counterArray].getNodes().iterator();
                    } else {
                        return false;
                    }
                }
                return subIterator.hasNext();
            }

            private boolean moveToNextCell() {
                counterArray++;
                while (hashTable[counterArray] == null) {
                    counterArray++;
                    while (hashTable[counterArray] == null)
                        counterArray++;
                }
                return hashTable[counterArray] != null;
            }


            @Override
            public V next() {
                valueCounter++;
                return subIterator.next().getValue();
            }


        };
    }


    @Setter
    @Getter
    private class Node<K, V> {
        private List<Node<K, V>> nodes;
        private int hash;
        private K key;
        private V value;

        private Node(K key, V value) {
            this.key = key;
            this.value = value;
            nodes = new LinkedList<Node<K, V>>();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Node<K, V> node = (Node<K, V>) o;

            if (hash != node.hash) return false;
            if (nodes != null ? !nodes.equals(node.nodes) : node.nodes != null) return false;
            if (key != null ? !key.equals(node.key) : node.key != null) return false;
            return value != null ? value.equals(node.value) : node.value == null;
        }

        @Override
        public int hashCode() {
            hash = 31;
            hash = hash * 17 + key.hashCode();
            return hash;

        }

        private int hash() {
            return hashCode()%hashTable.length;
        }
    }

    private boolean simpleAdd(int index ,Node<K,V> newNode){
        hashTable[index] = new Node<>(null,null);
        hashTable[index].getNodes().add(newNode);
        size++;
        return true;
    }

}
