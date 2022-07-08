package two_pointers;

import java.util.Scanner;

import static java.lang.Math.sqrt;

public class Fibonacci {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNext()) {
            int i = scanner.nextInt();
//            System.out.println(fibonacci(i));
            System.out.println(fibonacci2(i));
            System.out.println(fib(i));

        }
    }
/**
 * Iterative implementation for nth fibonacci number
 * Time complexity - O(n)
 * Space complexity - O(1)
 */
 private static int fibonacci(int i) {
        if (i == 0) {
            return 0;
        } else if (i == 1 | i == 2) {
            return 1;
        }
        System.out.println(i);
        return fibonacci(i - 1) + fibonacci(i - 2);
    }

    /**
     * Recursive implementation for nth fibonacci number
     * Time complexity - O(1)
     * Space complexity - O(1)
     *
     * @param n
     * @return
     */
    public static int fib(int n){

        double x = sqrt(5)/5;

        double termOne = x * Math.pow((1+sqrt(5))/2, n);

        double termTwo = -x * Math.pow((1-sqrt(5))/2, n);

        return (int)(termOne + termTwo);
    }

    /**
     * Recursive implementation for nth fibonacci number
     * Time complexity - O(n)
     * Space complexity - O(n)
     *
     * @param
     * @return
     */

    public static int fibonacci2(int number) {
        if (number == 1 || number == 2) {
            return 1;
        }
        int fibo1 = 1, fibo2 = 1, fibonacci = 1;
        for (int i = 3; i <= number; i++) {
            fibonacci = fibo1 + fibo2;
            fibo1 = fibo2;
            fibo2 = fibonacci;
        }
        return fibonacci;
    }
}
