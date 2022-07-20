package codewars;

import java.util.Arrays;
import java.util.PrimitiveIterator;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

/**
 * @author Pavel Putrenkov
 * @version 1.0
 * @since
 */
/* You have an array of numbers.
 * Your task is to sort ascending odd numbers but even numbers must be on their places.
 * Zero isn't an odd number and you don't need to move it. If you have an empty array, you need to return it.
 * Example
 * sortArray([5, 3, 2, 8, 1, 4]) == [1, 3, 2, 8, 5, 4]
 */

public class SortTheOdd {

    public static int[] sortArray(int[] array, IntPredicate predicate) {
        int[] filtered = IntStream.of(array)
                .filter(predicate)
                .sorted()
                .toArray();
        int[] dst = new int[array.length];
        for (int i = 0, j = 0; i < array.length; i++) {
            dst[i] = predicate.test(array[i]) ? filtered[j++] : array[i];
        }
        return dst;
    }


    public static int[] sortArray(int[] array) {
        PrimitiveIterator.OfInt sortedOdds = IntStream
                .of(array)
                .filter(i -> i % 2 == 1)
                .sorted()
                .iterator();

        return IntStream
                .of(array)
                .map(i -> i % 2 == 0 ? i : sortedOdds.nextInt())
                .toArray();
    }

    public static int[] sortArray2(final int[] array) {

        // Sort the odd numbers only
        final int[] sortedOdd = Arrays.stream(array)
                .filter(e -> e % 2 == 1)
                .sorted()
                .toArray();

        // Then merge them back into original array
        for (int j = 0, s = 0; j < array.length; j++) {
            if (array[j] % 2 == 1) array[j] = sortedOdd[s++];
        }

        return array;
    }

    public static void main(String[] args) {
        final int[] ints = {5, 3, 2, 8, 1, 4};
        System.out.println(Arrays.toString(sortArray(ints, (n) -> n % 2 != 0)));
        System.out.println(Arrays.toString(sortArray(ints)));
        System.out.println(Arrays.toString(sortArray2(ints)));
    }

}
