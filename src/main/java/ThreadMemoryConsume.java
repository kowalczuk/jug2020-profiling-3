import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

class ThreadMemoryConsume {
    private static final Object MUTEX = new Object();
    private static final Object MUTEX_2 = new Object();
    private static final Object MUTEX_3 = new Object();
    private static final int TOTAL_THREADS = 3000;
    private static volatile AtomicInteger atomicInteger = new AtomicInteger(0);

    private static int doIt(int i) throws InterruptedException {
        if (i == 0) {
            synchronized (MUTEX) {
                System.out.print(".");
                int current = atomicInteger.incrementAndGet();
                if (current % 100 == 0) {
                    System.out.println("");
                }
                if (current == TOTAL_THREADS) {
                    System.out.println("");
                    System.out.println("All threads are waiting with no stack, press ENTER");
                    atomicInteger = new AtomicInteger(0);
                }
                MUTEX.wait();
            }
        }

        if (i == 100) {
            synchronized (MUTEX_2) {
                System.out.print(".");
                int current = atomicInteger.incrementAndGet();
                if (current % 100 == 0) {
                    System.out.println("");
                }
                if (current == TOTAL_THREADS) {
                    System.out.println("");
                    System.out.println("All threads are waiting with low stack, press ENTER");
                    atomicInteger = new AtomicInteger(0);
                }
                MUTEX_2.wait();
            }
        }

        if (i == 2000) {
            synchronized (MUTEX_3) {
                System.out.print(".");
                int current = atomicInteger.incrementAndGet();
                if (current % 100 == 0) {
                    System.out.println("");
                }
                if (current == TOTAL_THREADS) {
                    System.out.println("");
                    System.out.println("All threads are waiting with large stack, press ENTER");
                    atomicInteger = new AtomicInteger(0);
                }
                MUTEX_3.wait();
            }
            return i;
        }
        int rec = doIt(i + 1);
        if (i == 0) {
            synchronized (MUTEX) {
                System.out.print(".");
                int current = atomicInteger.incrementAndGet();
                if (current % 100 == 0) {
                    System.out.println("");
                }
                if (current == TOTAL_THREADS) {
                    System.out.println("");
                    System.out.println("All threads are waiting with no stack, press ENTER");
                    atomicInteger = new AtomicInteger(0);
                }
                MUTEX.wait();
            }
        }
        return i + rec;
    }

    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < TOTAL_THREADS; i++) {
            threads.add(new Thread(() -> {
                try {
                    doIt(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }));
        }
        for (Thread thread : threads) {
            thread.start();
        }

        scan.nextLine();

        synchronized (MUTEX) {
            MUTEX.notifyAll();
        }

        scan.nextLine();

        synchronized (MUTEX_2) {
            MUTEX_2.notifyAll();
        }
        scan.nextLine();

        synchronized (MUTEX_3) {
            MUTEX_3.notifyAll();
        }

        scan.nextLine();
        System.gc();
        System.out.println("GC done, press ENTER");
        scan.nextLine();

        synchronized (MUTEX) {
            MUTEX.notifyAll();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("All threads are dead, press ENTER");

        scan.nextLine();
        threads.clear();
        System.gc();
        System.out.println("Clear & GC done, press ENTER");
        scan.nextLine();
    }
}
