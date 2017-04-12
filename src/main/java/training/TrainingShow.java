package training;

import java.util.Arrays;

public class TrainingShow {

    public static void main(String[] args) {
        int arr[] = {4, 3, 2, 1};
        System.out.println(Arrays.toString(sort(arr)));
        System.out.println(Arrays.toString(sort1(arr)));

    }

    private static int[] sort(int[] arr) {
        return Arrays.stream(arr)
                .sorted()
                .toArray();
    }

    private static int[] sort1(int[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                }
            }
        }
        return arr;
    }
}
