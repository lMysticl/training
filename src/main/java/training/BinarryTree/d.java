package training.BinarryTree;

/**
 * @author Pavel Putrenkov
 */
public class d<Sting,Integer,Double> {
//    public static void main(String[] args) {
//
//        int[] a = new int[10];
//
//        a[20] = 10;
//
//        a[5] = a[2] / 0;
//
//    }
public void main(String[] args) {

    Thread t1 = new Thread(new Runnable() {

        @Override

        public void run() {

            System.out.println(1);

        }

    });

    Thread t2 = new Thread(new Runnable() {

        @Override

        public void run() {

            try {

                t1.join();

            } catch (InterruptedException e) {

                e.printStackTrace();

            }

            System.out.println(2);

        }

    });

    t1.start();

    t2.start();
t1.interrupt();




}

}

