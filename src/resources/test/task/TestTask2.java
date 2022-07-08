package task;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;

public class TestTask2 {
    private Element element1;
    private Element element2;
    private Element element3;
    private ArrayList<Element> elements = new ArrayList<>();
    private Collection<Element> elements1;

    @Before
    public void setUp() {
        element1 = new Element(5, "sdasd", 19);
        element2 = new Element(5, "sda123123sd", 22);
        element3 = new Element(21, "sda123123sd", 21);

        elements.add(0, element1);
        elements.add(1, element2);
        elements.add(2, element3);
    }


    @Test
    public void element() {

        elements1 = Element.filterElements(elements);
        System.out.println(elements1.toString());
    }

}
