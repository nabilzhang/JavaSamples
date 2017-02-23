package me.nabil.io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

/**
 * @author zhangbi
 */
public class BufferredStreamDemo {

    private static final String ROOT_PATH = System.getProperty("user.dir");

    public static void main(String[] args) throws FileNotFoundException {

        scanner1();

        scanner2();

        consoleIo();
    }

    private static void consoleIo() {
        Console c = System.console();
        if (c == null) {
            System.err.println("No console.");
            System.exit(1);
        }

        String login = c.readLine("Enter your login: ");
        char[] oldPassword = c.readPassword("Enter your old password: ");

        if (verify(login, oldPassword)) {
            boolean noMatch;
            do {
                char[] newPassword1 = c.readPassword("Enter your new password: ");
                char[] newPassword2 = c.readPassword("Enter new password again: ");
                noMatch = !Arrays.equals(newPassword1, newPassword2);
                if (noMatch) {
                    c.format("Passwords don't match. Try again.%n");
                } else {
                    change(login, newPassword1);
                    c.format("Password for %s changed.%n", login);
                }
                Arrays.fill(newPassword1, ' ');
                Arrays.fill(newPassword2, ' ');
            } while (noMatch);
        }

        Arrays.fill(oldPassword, ' ');
    }

    // Dummy change method.
    static boolean verify(String login, char[] password) {
        // This method always returns
        // true in this example.
        // Modify this method to verify
        // password according to your rules.
        return true;
    }

    // Dummy change method.
    static void change(String login, char[] password) {
        // Modify this method to change
        // password according to your rules.
    }

    private static void scanner2() throws FileNotFoundException {
        Scanner s = null;
        double sum = 0;

        try {
            s = new Scanner(new BufferedReader(
                    new FileReader(ROOT_PATH + File.separator + "resources/testScaner.txt")));
            s.useLocale(Locale.US);

            while (s.hasNext()) {
                if (s.hasNextDouble()) {
                    sum += s.nextDouble();
                } else {
                    s.next();
                }
            }
        } finally {
            s.close();
        }

        System.out.println(sum);
    }

    private static void scanner1() throws FileNotFoundException {
        BufferedInputStream bufferedInputStream =
                new BufferedInputStream(new FileInputStream(
                        new File(ROOT_PATH + File.separator + "resources/testScaner.txt")));
        Scanner s = null;

        try {
            s = new Scanner(bufferedInputStream);
            s.useDelimiter(",\\s*");

            while (s.hasNext()) {
                System.out.println(s.next());
            }
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }
}
