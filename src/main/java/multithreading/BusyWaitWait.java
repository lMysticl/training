package multithreading;


public class BusyWaitWait {

    private volatile static boolean in=false;

    public static void main(String[] args) throws InterruptedException {
        final Object monitor = new Object();


        new Thread(new Runnable() {
            @Override
            public void run() {
                //поток будет заблокирован на попытке захватить монитор
                synchronized (monitor) {
                  in =true;
                    try {
                        System.out.println("X");
                        monitor.wait();//поток перестает выполнятся. Другой поток может зайти с synchronized и ввыполнится. Монитор отпущен.
                        System.out.println("Y");
//                        monitor.notify();
                    } catch (InterruptedException ignore) {/*NOP*/}
                }
            }
        }).start();
        System.out.println("A");
        while (!in);//spin lock /busy waiting
        System.out.println("B");
        synchronized (monitor){
            System.out.println("C");
            monitor.notify();//даже разбудив поток все равно выполнится D
//            monitor.wait();
            System.out.println("D");
        }
        System.out.println("E");
    }
}
/*
A
B
X
C
D
E
Y
*/