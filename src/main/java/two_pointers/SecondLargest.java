package two_pointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SecondLargest {
    public static void main(String[] args) {

        List<Integer> integers = new ArrayList<>(Arrays.asList(1, 2, 3, -4, -5));
        int[] nums = {1, 2, 3, -4, -5};

        long timeStart = System.nanoTime();
//        Optional<Integer> secondHighest = getSecondHighest(integers);
        long timeEnd = System.nanoTime();
        System.out.println(secondHighest(nums));
        System.out.println(timeEnd - timeStart);

//        System.out.println(secondHighest);
//        deleteLastTwoIndex(args);

    }

    private static Optional<Integer> getSecondHighest(List<Integer> integers) {
        return integers.stream()
                    .sorted()
                    .filter(f ->{
                        integers.remove(integers.size()-1);
                        return true;})
                    .reduce(Integer::max);
    }

    private static void deleteLastTwoIndex(String[] args) {
        List<String> list = Arrays.stream(args)
                .filter(f -> f.length() <= 2)
                .collect(Collectors.toList());
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

    private static int se(int... nums) {

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
}
