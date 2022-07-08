package multithreading;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.Thread.currentThread;

/*В Java с каждым объектом ассоциирован монитор.
Состояние монитора - это
1) логический флажок - занято или нет
2) wait-set - множество "пассивных" потоков ждущих пробуждения (CPU не загружают)
2) blocked-set - множество "активных" потоков ждущих только возможности захватить монитор (CPU не загружают)

Метод notify() перемещает один из потоков из wait-set в blocked-set.
Метод notifyAll() перемещает все потоки из wait-set в blocked-set.

Поток помещается в wait-set объекта когда:
 - сам вызывает метод wait у объекта
Поток помещается в blocked-set объекта когда
 - вызывает synchronized метод/секцию, а монитор занят
 - он счастливчик и os/jvm перемещает его из wait-set в blocked-set по notify(), а монитор занят
 - по notifyAll() его среди всех остальных перемещают в blocked-set, а монитор занят
 - он вошел в wait-set через использования одной из версий wait(...) с таймаутом и таймаут истек
 - сработал spirious wakeup и поток выбросило из wait-set в blocked-set

Спецификация не гарантирует никакого порядка
 - ни при выборе по notify() одного из wait-set в blocked-set
 - ни при захвате монитора потоками из blocked-set

Но в реальности os/jvm реализует какой-то порядок.

Демонстрация нахождения в wait-set:*/
public class MonitorExample {

    //Демонстрация нахождения в wait-set:
    public static class ExampleStateWaiting {
        public static void main(String[] args) throws InterruptedException {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    Object lock = new Object();
                    synchronized (lock) {
                        try {
                            lock.wait();
                        } catch (InterruptedException ignore) {/*NOP*/}
                    }
                }
            });
            thread.start();
            while (thread.getState() != Thread.State.WAITING) ;
            System.out.println("Ok!");
            thread.interrupt();
        }
    }

    //Демонстрация нахождения в blocked-set:
    public static class ExampleStateBlocking {
        private static Object lock = new Object();

        public static void main(String[] args) throws InterruptedException {
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    synchronized (lock) {
                        try {
                            lock.wait();
                        } catch (InterruptedException ignore) {/*NOP*/}
                    }
                }
            });
            thread.start();
            while (thread.getState() != Thread.State.WAITING) ;
            synchronized (lock) {
                lock.notify();
                while (thread.getState() != Thread.State.BLOCKED) ;
                System.out.println("Ok!");
            }
            thread.interrupt();
        }
    }

    //Технический класс:
    public static class MyRunnable implements Runnable {
        private final Object lock;
        private final BlockingQueue<String> queue;

        public MyRunnable(Object lock, BlockingQueue<String> queue) {
            this.queue = queue;
            this.lock = lock;
        }

        public void run() {
            synchronized (lock) {
                try {
                    lock.wait();
                    queue.put(currentThread().getName());
                } catch (InterruptedException ignore) {/*NOP*/}
            }
        }
    }

    //Демонстрация порядка выбора по notify (у меня вывело " 0 1 2 3 4 5 6 7 8 9")
    public static class ExampleNotifyOrder {
        private static Object lock = new Object();
        private static BlockingQueue<String> queue = new LinkedBlockingQueue<String>();

        public static void main(String[] args) throws InterruptedException {
            for (int k = 0; k < 10; k++) {
                new Thread(new MyRunnable(lock, queue), "" + k).start();
                Thread.sleep(100);
            }

            Thread.sleep(100);

            for (int k = 0; k < 10; k++) {
                synchronized (lock) {
                    lock.notify();
                }
                System.out.print(" " + queue.take());
            }
        }
    }

//Демонстрация порядка захвата монитора (у меня вывело " 9 8 7 6 5 4 3 2 1 0")

    public static class ExampleNotifyAllOrder {
        private static Object lock = new Object();
        private static BlockingQueue<String> queue = new LinkedBlockingQueue<String>();

        public static void main(String[] args) throws InterruptedException {
            for (int k = 0; k < 10; k++) {
                new Thread(new MyRunnable(lock, queue), "" + k).start();
                Thread.sleep(100);
            }

            Thread.sleep(100);

            synchronized (lock) {
                lock.notifyAll();
            }
            for (int k = 0; k < 10; k++) {
                System.out.print(" " + queue.take());
            }
        }
    }
}
