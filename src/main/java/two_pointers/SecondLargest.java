package two_pointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SecondLargest {
    public static void main(String[] args) {

        List<Integer> integers = new ArrayList<>(Arrays.asList(1, 2, 3));
//        integers.stream()
//                .map()
//                .filter(e ->);


        int[] nums = {1,2,3};
        System.out.println(secondHighest(nums));
    }


    private static int secondHighest(int... nums) {
        int high1 = Integer.MIN_VALUE;
        int high2 = Integer.MIN_VALUE;
        for (int num : nums) {
            if (num > high1) {
                high2 = high1;
                high1 = num;
            } else if (num > high2) {
                high2 = num;
            }
        }
        return high2;
    }

    private static int se(int... nums){

        int high1 =Integer.MIN_VALUE;
        int high2 =Integer.MIN_VALUE;
        for (int num :nums) {
            if (num > high1) {
                high2 = high1;
                high1 = num;
            } else if (num > high2) {
                high2 = num;
            }
        }
        return high2;
    }
}
