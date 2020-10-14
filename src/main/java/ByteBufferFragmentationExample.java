import java.nio.ByteBuffer;
import java.util.Random;

class ByteBufferFragmentationExample {
    public static void main(String[] args) {
        for (int i = 0; i < 60; i++) {
            new Thread(() -> {
                Random random = new Random();
                while (true) {
                    ByteBuffer.allocateDirect(random.nextInt(1024 * 1024));
                }
            }).start();
        }
    }
}
