package task;

import java.util.ArrayList;
import java.util.Collection;

public class Main {
    public static void main(String[] args) {
        Btn btn1 =new Btn(1,null,null);
        Btn btn2 =new Btn(2,btn1,btn1);
        Btn btn3 =new Btn(1,null,null);
        System.out.println(btn1.equals(btn3));


        Element element =new Element(5,"sdasd",19);
        Element element2 =new  Element(5,"sda123123sd",22);
        Element element3 =new  Element(21,"sda123123sd",21);
        ArrayList<Element> elements=new ArrayList<>();
        elements.add(0,element);
        elements.add(1,element2);
        elements.add(2,element3);
        Collection<Element> elements1 = Element.filterElements(elements);
        System.out.println(elements1.toString());
    }
}
