import java.util.Objects;
import java.util.Scanner;
public class Duke {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String LOGO = "   /\\_/\\  \n" +
                "  ( o.o ) \n" +
                "   > ^ <\n";
        String GREETING = "Hello(@.@), I'm NiHao \nWhat can I do for you?";
        String EXIT = "Bye(T.T), Hope to see you again soon!";
        String HORILINE = "_____________________________________________________________";
        System.out.println(LOGO + GREETING);
        while (true) {
            String input = sc.nextLine();
            if (Objects.equals(input, "exit") || Objects.equals(input, "Exit") || Objects.equals(input, "EXIT")) {
                break;
            } else {
                System.out.println("\t" +input);
            }
        }
        System.out.println(EXIT);
    }
}
