package multithreading;


public class BusyWaitVolotile {


    private  static int counter;
    private volatile static boolean finish1;
    private volatile static boolean finish2;


    public static void main(String[] args) {


        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10_000_000; i++) {
                    inc();

                    //counter++;
                }
                finish1=true;
            }


        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10_000_000; i++) {
                    inc();
                  //  counter++;
                }
                finish2=true;
            }
        }).start();
        while(!finish1||!finish2);
        System.out.println(counter);
    }

    //Должен обладать свойствами атомарности.Что бы передним никто не влез.(synchronized)
    private synchronized static void inc() {
        int tmp= counter;
        tmp =tmp +1;
        counter=tmp;
    }

    //Lost update Встречается в базах-данных.
    private /*synchronized*/ static void incSHOW() {
        int tmp= counter;
        tmp =tmp +1;
        counter=tmp;
    }
}
