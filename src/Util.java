import java.io.*;
import java.util.Scanner;
import java.util.function.Consumer;

public class Util {
    public static <U extends Comparable> void swap(U[] list, int i, int j) {
        U x = list[i];
        list[i] = list[j];
        list[j] = x;
    }

    public static <U extends Consumer<String>> void readLinesFromFile(File file, U consumer) throws IOException {
        readFromFile(file, consumer::accept);
    }

    public static <U extends Consumer<String>> void readWordsFromFile(File file, U consumer) throws IOException {
        readFromFile(file, (line) -> {
            String[] words = line.split(" ");
            for (String word : words) {
                consumer.accept(word);
            }
        });
    }

    private static void readFromFile(File file, Consumer<String> doSomethingWithLine) {
        String line;
        String[] lineWords;
        try {
            Scanner scanner = new Scanner(file);
            scanner.useDelimiter(" +");
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            while ((line = reader.readLine()) != null) {
                doSomethingWithLine.accept(line );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}