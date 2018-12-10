import java.io.*;
import java.util.Scanner;

public class MostCommonWords {

    private static Map words;
    private static String[] sortedArray;

    private static final int BIG_PRIME = 786433;

    public static void main(String[] args) {
        if (args.length >= 1) {
            String fileName = args[0];
            File file = new File(fileName);
            words = new Map(BIG_PRIME);
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                Scanner scanner = new Scanner(file);
                scanner.useDelimiter(" +");
                String line;
                String[] lineWords;
                while ((line = reader.readLine()) != null) {
                    lineWords = line.split(" ");
                    for (String word : lineWords) {
                        words.add(word);
                    }
                }

                sortedArray = new String[words.size()];
                int arraySize = 0;

                for (String key : words.keys()) {
                    sortedArray[arraySize++] = key;
                }

                sort(sortedArray);

                if (args.length == 2) {
                    int args1 = new Integer(args[1]);
                    printMostCommonWord(args1);
                } else if (args.length == 3) {
                    int args1 = new Integer(args[1]),
                            args2 = new Integer(args[2]);
                    printMostCommonWords(args1, args2);
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void sort(String[] list) {
        sort(list, 0, list.length - 1);
    }

    private static void sort(String[] list, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        int j = partition(list, lo, hi);
        sort(list, lo, j - 1);
        sort(list, j + 1, hi);
    }

    private static int partition(String[] list, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        String v = list[lo];
        while (true) {

            while (words.get(list[++i]) > words.get(v)) {
                if (i == hi) {
                    break;
                }
            }

            while (words.get(v) > words.get(list[--j])) {
                if (j == lo) {
                    break;
                }
            }

            if (i >= j) {
                break;
            }

            swap(list, i, j);
        }

        swap(list, lo, j);

        return j;
    }

    private static void swap(String[] list, int i, int j) {
        String x = list[i];
        list[i] = list[j];
        list[j] = x;
    }

    public static void printMostCommonWord(int rank) {
        System.out.println(rank + ": " + sortedArray[rank - 1] + " - " + words.get(sortedArray[rank - 1]) + " occurrences");
    }

    public static void printMostCommonWords(int startRank, int endRank) {
        System.out.println("Most Common Words from " + startRank + " to " + endRank + ":");
        for (int i = startRank - 1; i <= endRank - 1; i++) {
            System.out.println(i + 1 + " - " + sortedArray[i] + " - " + words.get(sortedArray[i]) + " occurrences");
        }
    }


}
