package test2;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Pavel Putrenkov
 * @version 1.0
 * @since
 */
public class Test2 {

    public static void main(String[] args) {
        show1();
        show2();
        System.out.println(compute(3));

    }

    private static void show2() {
        Set item = new TreeSet();
        item.add("b");
        item.add("A");
        item.add("C");
        item.add("c");
        item.add("B");
        item.add("a");
        System.out.println(item.size());
        System.out.println(item.toString());
    }

    private static void show1() {
        Set item = new HashSet();
        item.add("1");
        item.add("2");
        item.add("3");
        item.add(1);
        item.add(2);
        item.add(3);
        item.add(new Integer(1));
        item.add(null);
        System.out.println(item.size());
        System.out.println(item.toString());
    }

public static boolean compute(int value){
        return ((value & (value-1))==0);
}

}
