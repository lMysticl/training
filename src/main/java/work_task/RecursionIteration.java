package work_task;

/**
 * @author Pavel Putrenkov
 * @version 1.0
 * @since
 */
public class RecursionIteration {

    public static void main(String[] args) {
        recursionDisplay(0);
    }


    private static int recursionDisplay(int n) {
        if (n == 101) return n;
        System.out.println(n);
        return  recursionDisplay(n+1);
    }

}
