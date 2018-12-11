import java.io.*;
import java.util.Scanner;
import java.util.function.Consumer;

public class Util {
    public static <U extends Comparable> void swap(U[] list, int i, int j) {
        U x = list[i];
        list[i] = list[j];
        list[j] = x;
    }

    public static <U extends Consumer<String>> void readFromFile(File file, U consumer) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        Scanner scanner = new Scanner(file);
        scanner.useDelimiter(" +");
        String line;
        String[] lineWords;
        while ((line = reader.readLine()) != null) {
            lineWords = line.split(" ");
            for (String word : lineWords) {
                consumer.accept(word);
            }
        }
    }
}