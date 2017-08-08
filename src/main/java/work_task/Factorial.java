package work_task;

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


    public static void main(String[] args) {
        System.out.println(factorial(5));
        System.out.println(factorial2(5));
    }
}
