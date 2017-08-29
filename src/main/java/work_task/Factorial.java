package work_task;

import java.math.BigInteger;
import java.util.stream.IntStream;

/**
 * @author Pavel Putrenkov
 */
public class Factorial {
    static int factorial(int x){
        int result=1;
        for (int i = 1; i <= x; i++)
        {
            result *= i;
        }
        return  result;
    }


    public static int factorial2(int num) {
        int fact = 1;
        for (; num > 0; fact *= num--);
        return fact;
    }


        public static int factorial3(int n) {

            BigInteger factorial = BigInteger.ONE;
            if (n < 0 || n > 12){
                throw new IllegalArgumentException("only Integers between 0 and 12 are supported");
            }
            for (int i = 2; i <= n; i++) {
                factorial = factorial.multiply(BigInteger.valueOf(i));
            }

            return factorial.intValue();
        }



    public static int factorial4(int n) {
        if (n < 0 || n > 12) throw new IllegalArgumentException();
        return IntStream.rangeClosed(1, n).reduce(1, (a, b) -> a * b);
    }
    public static void main(String[] args) {
        System.out.println(factorial(5));
        System.out.println(factorial2(5));
        System.out.println(factorial3(5));
        System.out.println(factorial4(5));
    }
}
