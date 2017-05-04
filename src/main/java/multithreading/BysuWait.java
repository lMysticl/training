package multithreading;

public class BysuWait {


    private volatile static boolean in=false;

    public static void main(String[] args) {
        final Object monitor = new Object();
     

        new Thread(new Runnable() {
            @Override
            public void run() {
                //поток будет заблокирован на попытке захватить монитор
                synchronized (monitor) {
                  in =true;
                    try {
                        Thread.sleep(Long.MAX_VALUE);
                    } catch (InterruptedException ignore) {/*NOP*/}
                }
            }
        }).start();
        System.out.println("A");
        while (!in);//spin lock /busy waiting
        System.out.println("B");
        synchronized (monitor){
            System.out.println("C");
        }
    }
}
