package test2;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author Pavel Putrenkov
 * @version 1.0
 * @since
 */
public class ItereteToLast {

    public Object getLastElement(final Collection c) {
        final Iterator itr = c.iterator();
        Object lastElement = itr.next();
        while(itr.hasNext()) {
            lastElement = itr.next();
        }
        return lastElement;
    }


}

