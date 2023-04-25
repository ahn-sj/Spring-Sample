package springbox.exceptionhandle.recover;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// https://www.baeldung.com/java-exceptions
public class PlayerScore {
    public static void main(String[] args) {
        System.out.println(getPlayerScore("test"));
    }

    public static int getPlayerScore(String playerFile) {
        Scanner contents = null;
        try {
            contents = new Scanner(new File(playerFile));
            return Integer.parseInt(contents.nextLine());
        } catch (FileNotFoundException noFile ) {
            System.out.println("File not found, resetting score.");
            return 0;
        } finally {
            if (contents != null) {
                contents.close();
            }
        }
    }
}
