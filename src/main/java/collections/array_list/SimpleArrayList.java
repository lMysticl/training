package collections.array_list;

import java.util.Iterator;

/**
 * @author Pavel Putrenkov
 */
public class SimpleArrayList<E> implements Simple<E> {

    public static void main(String[] args) {
        Simple<String> strings = new SimpleArrayList<>();
        strings.add("first");
        strings.add("second");
        strings.add("three");
        System.out.println(strings.get(0));
        strings.update(0, "first-updated");
        System.out.println(strings.get(0));
        strings.delete(0);
        System.out.println(strings.get(0));
        System.out.println(strings.size());
    }

    private E[] values;
    private E[] temp;


    public SimpleArrayList() {

        values = (E[]) new Object[0];
    }

    @Override

    public boolean add(E e) {
        try {
            temp = upInSizeArray();
            System.arraycopy(temp, 0, values, 0, temp.length);
            values[values.length - 1] = e;
            return true;
        } catch (ClassCastException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private E[] upInSizeArray() {
        E[] temp = values;
        values = (E[]) new Object[temp.length + 1];
        return temp;
    }

    @Override
    public void delete(int index) {
        try{
        temp = upInSizeArray();
        System.arraycopy(temp, 0, values, 0, index);
        int amountElementAfterIndex = temp.length - index - 1;
        System.arraycopy(temp, index + 1, values, index, amountElementAfterIndex);}
        catch (ClassCastException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public int size() {
        return values.length;
    }

    @Override
    public E get(int index) {
        return values[index];
    }

    @Override
    public void update(int index, E e) {
        values[index] = e;
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayIterator<>(values);
    }

}
