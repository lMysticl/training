package work_task;

/**
 * @author Pavel Putrenkov
 */
public class Test3 {

    public static void main(String[] args) {
        d.hi();
    }

    private static class d {
        static void hi() {
            System.out.println("hi");
        }

    }


    private void iligalArgumentTest(int[] arr){
        arr= new int[0];

    }
}
