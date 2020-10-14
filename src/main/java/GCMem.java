import java.util.LinkedList;
import java.util.List;

class GCMem {
    private static final List<byte[]> OBJECTS = new LinkedList<>();

    public static void main(String[] args) {
        int i = 0;
        while (true) {
            new Object();
            if (i++ % 1000 == 0) {
                OBJECTS.add(new byte[1024]);
            }

            if (OBJECTS.size() > 1024 * 1040) {
                OBJECTS.clear();
            }
        }
    }
}
