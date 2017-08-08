package work_task;

/**
 * @author Pavel Putrenkov
 */
public class Fact {


    private static int fact1(int num){

        return (num == 0) ? 1 : num * fact1 (num - 1);
    }

    private static int fact2(int num){

        int fact=1;
        for (int i=2; i<=num;i++)
             fact*=i;
        return fact;
    }


    public static void main(String[] args) {
        System.out.println(fact2(2));

    }


}
