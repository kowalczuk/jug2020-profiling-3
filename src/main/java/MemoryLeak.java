import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class MemoryLeak {
    private static final List<ShortLived> leak = new ArrayList<>();
    private static final Queue<LongLived> longLived = new LinkedList<>();
    private static final List<Eternal> eternal = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        int i = 0;
        while (true) {
            i++;

            new ShortLived();

            if (i % 2_000 == 0) {
                doLeak();
                eternal.add(new Eternal());
            }

            longLived.add(new LongLived());
            if (longLived.size() > 100_000) {
                longLived.poll();
            }

            Thread.sleep(1);
        }
    }

    private static void doLeak() {
        leak.add(new ShortLived());
    }


    static class ShortLived {
        byte[] array = new byte[1024];
    }

    static class LongLived {
        byte[] array = new byte[1024];
    }

    static class Eternal {
        byte[] array = new byte[1024];
    }
}
