package collections.the_list;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

/**
 * @author Pavel Putrenkov
 */

public interface Linked<E> {
    void addLast(E e);

    void addFirst(E e);

    int size();

    E getElementByIndex(int counter);
}
