package two_pointers;

public class factorial {


    public static void main(String[] args) {
        System.out.println(factorial(3));
    }

    private static int factorial(int x){

        if (x == 1){

            return 1;
        }
        else{

            return x * factorial(x - 1);
        }
    }

}


