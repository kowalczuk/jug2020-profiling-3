import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.zip.ZipInputStream;

class Zip {
    public static void main(String[] args) throws Exception {
        Scanner scan = new Scanner(System.in);
        List<InputStream> leak = new ArrayList<>();
        while (true) {
            System.out.println("Press enter");
            scan.nextLine();
            for (int i = 0; i < 100_000; i++) {
                leak.add(new ZipInputStream(new FileInputStream("/home/pasq/JDK/jdk1.7.0_80/src.zip")));
                System.out.println(i);
            }
        }
    }
}
