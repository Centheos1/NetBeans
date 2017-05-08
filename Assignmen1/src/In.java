import java.util.Scanner;

public class In {
    private static final Scanner scanner;

    private In() {
    }

    public static String nextLine() {
        return scanner.nextLine();
    }

    public static int nextInt() {
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }

    public static double nextDouble() {
        double value = scanner.nextDouble();
        scanner.nextLine();
        return value;
    }

    public static char nextChar() {
        return scanner.nextLine().charAt(0);
    }

    static {
        scanner = new Scanner(System.in);
    }
}
