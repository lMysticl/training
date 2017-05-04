package multithreading;

/*
wait() освобождает монитор объекта, поток остается в wait list-е, а sleep() просто замораживает поток на определенное параметром время без отпускания монитора.

собственно, все есть в доке:

public static void sleep(long millis,
 int nanos)
 throws InterruptedException
Causes the currently executing thread to sleep (temporarily cease execution) for the specified number of milliseconds plus the specified number of nanoseconds, subject to the precision and accuracy of system timers and schedulers. The thread does not lose ownership of any monitors.

public final void wait()
 throws InterruptedException
Causes the current thread to wait until another thread invokes the notify() method or the notifyAll() method for this object. In other words, this method behaves exactly as if it simply performs the call wait(0).
The current thread must own this object's monitor. The thread releases ownership of this monitor and waits until another thread notifies threads waiting on this object's monitor to wake up either through a call to the notify method or the notifyAll method. The thread then waits until it can re-obtain ownership of the monitor and resumes execution.*/
public class Monitor {


    public static void main(String[] args) {
        //fff();

        mutualExclusion();
        //Фокус вызова метода у null
        Monitor monitor = null;
        monitor.f();
    }


    //Взаимное исключение.Или А будет бесконечно выводится на экран или B
    private static void mutualExclusion() {
        final Object monitor = new Object();
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (monitor) {
                    while (true) {
                        System.out.println("A");
                    }
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //поток будет заблокирован на попытке захватить монитор
                synchronized (monitor) {
                    while (true) {
                        System.out.println("B");
                    }
                }
            }
        }).start();
    }

    //Захват монитора
    private synchronized static void fff() {
        System.out.println(Thread.holdsLock(Monitor.class));
    }

    static void f() {
        System.out.println("f");
    }

}
