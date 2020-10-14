import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class OOMKiller {
    private static final List<Object> OBJECTS = new ArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Waiting");
        Scanner scan = new Scanner(System.in);
        scan.nextLine();
        while (true) {
            System.out.print(".");
            OBJECTS.add(new byte[102400]);
            Thread.sleep(1);
        }
    }
}
