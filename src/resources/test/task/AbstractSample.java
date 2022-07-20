package task;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by indigo on 2016-11-24.
 */
public class AbstractSample {
    public String doSomethingUnimaginable(Object... data) {
        String result = "Do something unimaginable";
        System.out.println(result + printIfNotEmpty(data));
        return result;
    }

    public String doSomethingElse(Object... data) {
        String result = "Do something else";
        System.out.println(result + printIfNotEmpty(data));
        return result;
    }

    public String doSomething(Object... data) {
        String result = "Do something";
        System.out.println(result + printIfNotEmpty(data));
        return result;
    }

    public String doSomethingNew(Object... data) {
        String result = "Do something new";
        System.out.println(result + printIfNotEmpty(data));
        return result;
    }

    public boolean anyBoolean() {
        boolean result = new Random().nextBoolean();
        System.out.println("Boolean value: " + result);
        return result;
    }

    public int anyInt() {
        int result = new Random().nextInt(10);
        System.out.println("Int value: " + result);
        return result;
    }

    public String printIfNotEmpty(Object[] objects) {
        if (objects == null || objects.length == 0) {
            return "";
        }
        return " " + Arrays.asList(objects);
    }

    public void printArray(int[] array) {
        System.out.println(Arrays.toString(array));
    }

    public void printArray(boolean[] array) {
        System.out.println(Arrays.toString(array));
    }

    public void printArray(char[] array) {
        System.out.println(Arrays.toString(array));
    }

    public void printArray(Object[] array) {
        System.out.println(Arrays.deepToString(array));
    }
}
