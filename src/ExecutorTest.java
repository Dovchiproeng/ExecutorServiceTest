import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by cdov on 5/19/17.
 */
class MyThread implements Runnable {
    public int counter = 0;

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            counter++;
        }
    }
}

class MySynchronizedThread implements Runnable {
    public int counter = 0;

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            synchronized (this) {
                counter++;
            }
        }
    }
}

public class ExecutorTest {

    public static void main(String[] args) throws InterruptedException {

//            MyThread runnableObject = new MyThread();
//            ExecutorService ex = Executors.newFixedThreadPool(5);
//            for (int i = 0; i < 5; i++) {
//                ex.execute(runnableObject);
//            }
//
//
//            ex.shutdown();
//            ex.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
//
//            System.out
//                    .println("Without synchronization: " + runnableObject.counter);

        MySynchronizedThread runnableSynchronizedObject = new MySynchronizedThread();
        ExecutorService ex2 = Executors.newFixedThreadPool(5);
        int executeRunnableTimes = 5;
        long startTime = System.nanoTime();
        for (int i = 0; i < executeRunnableTimes; i++) {
            ex2.execute(runnableSynchronizedObject);
        }

        ex2.shutdown();
        ex2.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
        long entTime = System.nanoTime();
        long totalTime = (entTime - startTime) / 1000000L;
        System.out.println("With synchronization: "
                + runnableSynchronizedObject.counter);
        System.out.println("total run time "+ totalTime);

    }
}
