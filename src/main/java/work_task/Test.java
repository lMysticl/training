package work_task;

import java.util.Random;

/**
 * @author Pavel Putrenkov
 */

public class Test {

        public static int solution(int[] A) {
            int leftPart=0;
            int rightPart=1;

            int minDifference=A[0];
            for(int i=1;i<A.length;i++) {
                if (minDifference - A[rightPart] > minDifference) {
                    minDifference = A[leftPart] - A[rightPart];
                }
                leftPart++;
                rightPart++;
            }
            return minDifference; }





    public static void main(String[] args) {

       System.out.println(solution(new int[]{4,3, 2,5,1,1}));
        System.out.println(solution(new int[]{1,3,-3}));
        int size = 1000000000;
        int max = 1000000000;
        int[] array = new int[size];
        int loop = 0;

        Random generator = new Random();


        generator.nextInt(max);


        for (int i = 0; i<1000; i++)
        {
            array [i] = generator.nextInt(max);
        }

        System.out.println(solution(array));
    }
}
