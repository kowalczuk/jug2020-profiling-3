import java.io.File;
import java.io.RandomAccessFile;
import java.util.concurrent.ThreadLocalRandom;

class Database {
    public static final int MB = 1024 * 1042;

    public static void main(String[] args) throws Exception {
        String filePath = "/home/centos/database";
        RandomAccessFile randomAccessFile = new RandomAccessFile(new File(filePath), "r");
        byte[] data = new byte[MB];
        for (int j = 0; j < 10000; j++) {
            long started = System.currentTimeMillis();
            for (int i = 0; i < 10000; i++) {
                int random = Math.abs(ThreadLocalRandom.current().nextInt()) % 1023;
                randomAccessFile.seek(random * MB);
                randomAccessFile.read(data, 0, MB);
            }
            System.out.println(System.currentTimeMillis() - started + "ms");
        }
        randomAccessFile.close();
    }
}
