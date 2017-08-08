package work_task;

/**
 * @author Pavel Putrenkov
 */
public class Fib {

    private static final int MOD =(int) (1e9+7);//10^9+7

    private static int fib(int num){
        int a=0;
        int b=1;
        for(int i = 0; i < num; i++){
           int c=(a+b)%MOD;
           a=b;
           b=c;
        }
        return a;
    }



    private static final int  MOD1 = (int) (1e9+7);

    private static int fib1(int num){

        int a=0;
        int b=1;
        for (int i = 0; i < num; i++) {
            int c = (a+b)%MOD1;
            a=b;
            b=c;
        }
        return a;
    }






    public static void main(String[] args) {
        System.out.println(fib1(10000));
        System.out.println(fib(10000));
    }

}
