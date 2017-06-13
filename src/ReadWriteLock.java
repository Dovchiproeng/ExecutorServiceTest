import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static java.lang.Thread.sleep;

/**
 * Created by cdov on 5/19/17.
 */
public class ReadWriteLock {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        Map<String, String> map = new HashMap<>();
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        executor.submit(() -> {
            lock.writeLock().lock(); // write lock on, you can write only
            try {
                try {
                    map.put("foo", "bar");
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } finally {
                lock.writeLock().unlock();
            }
        });

        System.out.println("Read 2 times without waiting");
        Runnable readTask = () -> {
            lock.readLock().lock(); // read lock on, you can read only
            try {
                System.out.println(map.get("foo"));
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.readLock().unlock();
            }
        };

        executor.submit(readTask);
        executor.submit(readTask);

        executor.shutdown();
    }
}
