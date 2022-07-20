package letcode;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FizzBuss {

    public static void main(String[] args) {
        FizzBussOut();
    }

    private static void FizzBussOut() {
        List<Integer> list = IntStream.range(0, 100).boxed().collect(Collectors.toList());

        list.forEach(integer -> {
            if (integer % 3 == 0 && integer % 5 == 0) {
                System.out.println("FizzBuzz");
            } else if (integer % 3 == 0) {
                System.out.println("Fizz");
            } else if (integer % 5 == 0) {
                System.out.println("Buzz");
            } else {
                System.out.println(integer);
            }
        });
    }
}
