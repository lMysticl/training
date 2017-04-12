package training2;


import java.util.concurrent.*;

//extends Thread or implements Runnable
public class ThreadCreating extends Thread implements Runnable,Callable {


                                                                                    // (1)
    private static void threadCreating(){
         Thread thread = new Thread();
            thread.isAlive();
    }

    @Override
    public void run() {
        System.out.println("Hello from a thread!");                                 // (2)
    }

    public static void main(String[] args) {
        (new Thread(new ThreadCreating())).start();                                 //(2)

        threadCreating();                                                           //(1)
        ThreadCreating threadCreating = new ThreadCreating();                       //(3)
        threadCreating.run();                                                       //(3)
        ThreadCreating threadCreating2 = new ThreadCreating();                      //(4)
        threadCreating2.start();

        ThreadCreating threadCreating1 = new ThreadCreating();                      //(5)
        threadCreating1.run();

        ExecutorService executorService = Executors.newFixedThreadPool(5); //(6)

        //создать ExecutorService на базе пула из пяти потоков
           ExecutorService es1 = Executors.newFixedThreadPool(5);
           //поместить задачу в очередь на выполнение
//           Future<String> f1 = es1.submit(new CallableSample());
//           while(!f1.isDone()) {
//                   //подождать пока задача не выполнится
//               }
//           try {
//                   //получить результат выполнения задачи
//                   System.out.println("task has been completed : " + f1.get());
//               } catch (InterruptedException | ExecutionException ie) {
//                   ie.printStackTrace(System.err);
//               }
//        es1.shutdown();

    }


    @Override
    public Object call() throws Exception {
        return null;
    }
}
