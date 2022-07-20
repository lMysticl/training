package collections.the_list;


import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

/**
 * @author Pavel Putrenkov
 */

public class LinkedContainer<E> implements Linked<E> {
    private Node<E> fstNode;
    private Node<E> lstNode;
    private int size = 0;

    //  null<-pevElement[fstNode(e==null)] nextElement-> <-prevElement[fstNode(e = val)] nextElement-> <-prevElement[fstNode(e = null)] nextElement-> null


    public LinkedContainer() {
        lstNode = new Node<E>(null, fstNode, null);
        fstNode = new Node<E>(null, null, lstNode);
    }

    public static void main(String[] args) {
        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        Linked<String> stringLinked = new LinkedContainer<>();
        stringLinked.addLast("abc");
        stringLinked.addFirst("ывыв");
        System.out.println(stringLinked.size());
        System.out.println(stringLinked.getElementByIndex(0));
    }

    @Override
    public void addLast(E e) {
        Node<E> prev = lstNode;
        prev.setCurrentElement(e);
        // Node<E>(e,null, lstNode)
        lstNode = new Node<E>(null, prev, null);
        prev.setNextElement(lstNode);
        // Node<E>(e,lstNode, lstNode)
        size++;
    }

    @Override
    public void addFirst(E e) {
        Node<E> next = fstNode;
        next.setCurrentElement(e);
        fstNode = new Node<>(null, null, next);
        next.setPrevElement(fstNode);
        size++;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E getElementByIndex(int counter) {
        Node<E> target = fstNode.getNextElement();
        for (int i = 0; i < counter; i++) {
            System.out.println(i);
            target = getNextElementFromCurrent(target);
        }

        return target.getCurrentElement();
    }

    private Node<E> getNextElementFromCurrent(Node<E> current) {
        return current.getNextElement();
    }

    @Getter
    @Setter
    private class Node<E> {

        private E currentElement;
        private Node<E> nextElement;
        private Node<E> prevElement;

        public Node(E currentElement, Node<E> prevElement, Node<E> nextElement) {
            this.currentElement = currentElement;
            this.nextElement = nextElement;
            this.prevElement = prevElement;
        }

    }

}
