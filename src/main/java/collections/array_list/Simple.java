package collections.array_list;

/**
 * @author Pavel Putrenkov
 */
public interface Simple<E> extends Iterable<E> {
    boolean add(E e);

    void delete(int index);

    int size();

    E get(int index);

    void update(int index, E e);
}
