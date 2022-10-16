package os.lab_1.cancellation;

import java.util.Scanner;

public class Main {
    private static int MenuInputValue() {
        Scanner sc = new Scanner(System.in);
        int inputValue = 0;
        boolean correctResponse = false;
        while (!correctResponse) {
            System.out.println("Type x parameter value:");
            correctResponse = true;
            if (sc.hasNextInt()) {
                inputValue = sc.nextInt();
            } else {
                correctResponse = false;
            }
            sc.nextLine();
        }
        System.out.println("Value is read");
        return inputValue;
    }
    public static void main(String[] args) {
        int v = MenuInputValue();
        new Manager(v);
    }
}
