package string_array;

import java.util.Arrays;

/**
 * Rotate an array of n elements to the right by k steps.
 * For example, with n = 7 and k = 3, the array [1,2,3,4,5,6,7] is rotated to [5,6,7,1,2,3,4]. How many different ways do you know to solve this problem?
 */
public class Rotate_Array {

    public static void main(String[] args) {

        int[] arr={1,2,3,4,5,6,7};
        int order =3;
        rotate(arr,order);

    }

    private static void rotate(int[] arr, int order) {
        if (arr == null || order < 0) {
            throw new IllegalArgumentException("Illegal argument!");
        }
            //3 times roll
        for (int i = 0; i < order; i++) {
                //6 times roll till first then 5 ,4...1 of element
            for (int j = arr.length - 1; j > 0; j--) {
                System.out.println( arr[j]);

                int temp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = temp;
            }
            System.out.println(Arrays.toString(arr));
        }
        System.out.println(Arrays.toString(arr));
    }
}
