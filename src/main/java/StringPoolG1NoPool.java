import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.lang3.RandomStringUtils;

class StringPoolG1NoPool {
    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                byte[] bytes = new byte[1024];
            }
        }).start();

        List<String> strings = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        System.out.println("");
        String prefix = RandomStringUtils.random(102);
        prefix.intern();
        System.out.println("Press enter to run");
        scan.nextLine();
        for (int i = 0; i < 1000000; i++) {
            System.out.println(i + 1);
            String noIntern = (prefix + i);
            strings.add(noIntern);
        }
        System.out.println("Press enter to run GC");
        scan.nextLine();
        System.gc();
        System.out.println("Press enter to clear list and run GC");
        scan.nextLine();
        strings = null;
        System.gc();
        System.out.println("Check now");
        scan.nextLine();
    }
}
