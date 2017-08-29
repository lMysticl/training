package work_task;

import java.util.Arrays;
import java.util.Random;

/**
 * @author Pavel Putrenkov
 */
public class Test2 {
    private static boolean isTriangle(int a, int b, int c) {
        return (a + b) > c && (a + c) > b && (b + c) > a;
    }

    public static int solution(int[] A) {
        int result = -1;
        int len = A.length;

        Arrays.sort(A);

        for (int p = 0; p < len - 2; p++) {
            for (int q = p+1; q < len - 1; q++) {
                for (int r = q+1; r < len; r++) {
                    if (isTriangle(A[p], A[q], A[r])) {
                        int perimeter = A[p] + A[q] + A[r];
                        if (perimeter > result) {
                            result = perimeter;
                        }
                    }

                }
            }
        }
        return result;
    }





    public static void main(String[] args) {
        int size = 10000;
        int max = 10000;
        int[] array = new int[size];
        int loop = 0;

        Random generator = new Random();


        generator.nextInt(max);


        for (int i = 0; i<1000; i++)
        {
            array [i] = generator.nextInt(max);
        }
        solution(array);
    }
}
