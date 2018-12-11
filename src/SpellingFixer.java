import java.io.*;
import java.util.Optional;
import java.util.Scanner;

public class SpellingFixer {

    private static final String FILE_NAME_APPENDER = "_fixed.txt";
    private static final String MISSPELLINGS_FILE_NAME = "misspellings.txt";
    private static final int MISSPELLINGS_COUNT = 4278;
    private static String[] misspellings = new String[MISSPELLINGS_COUNT];
    private static int misspellingsCount = 0;

    public static void main(String[] args) {
        if (args.length >= 1) {
            File misspellingsFile = new File(MISSPELLINGS_FILE_NAME);
            String fileName = args[0];
            File file = new File(args[0]);
            try {
                Util.readWordsFromFile(misspellingsFile, (word) -> {
                    if (isGoodWord(word)) {
                        misspellings[misspellingsCount++] = word;
                    }
                });
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String line;
                String[] lineWords;
                File fixedFile = new File(makeFixedFileName(fileName));
                fixedFile.createNewFile();
                BufferedWriter writer = new BufferedWriter(new FileWriter(fixedFile));
                StringBuilder builder = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    String[] characters = line.split("");
                    for (int i = 0; i < characters.length; i++) {
                        if (isLetterOrDigit(characters[i])) {
                            builder.append(characters[i]);
                            try {
                                if (!isLetterOrDigit(characters[i + 1])) {
                                    writeCorrection(writer, builder);
                                }
                            } catch (IndexOutOfBoundsException ignored) {
                                writeCorrection(writer, builder);
                            }
                        } else {
                            writer.write(characters[i]);
                        }
                    }
                    writer.write(System.lineSeparator());
                }
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void writeCorrection(BufferedWriter writer, StringBuilder builder) throws IOException {
        writer.write(getCorrection(builder.toString()));
        builder.setLength(0);
    }

    private static String getCorrection(String s) {
        return getOptionalCorrection(s).orElse(s);

    }

    private static boolean isGoodWord(String word) {
        return word.contains("-");
    }

    private static Optional<String> getOptionalCorrection(String word) {
        String maybeCorrection = null, misspelling;
        int low = 0, high = misspellings.length;
        while (low <= high) {
            int mid = (low + high) / 2;
            misspelling = trimMisspelling(misspellings[mid]);
            if (less(misspelling, word)) {
                low = mid + 1;
            } else if (less(word, misspelling)) {
                high = mid - 1;
            } else if (word.compareToIgnoreCase(misspelling) == 0) {
                maybeCorrection = getCorrectionFromIndex(mid);
                break;
            }
        }
        return Optional.ofNullable(maybeCorrection);
    }

    private static String trimMisspelling(String misspelling) {
        int arrowIndex = misspelling.indexOf("-");
        return misspelling.substring(0, arrowIndex);
    }

    private static String getCorrectionFromIndex(int index) {
        int arrowIndex = misspellings[index].indexOf("-");
        return misspellings[index].substring(arrowIndex + 2);
    }

    private static boolean less(String misspellingWithArrow, String word) {
        return misspellingWithArrow.compareToIgnoreCase(word) < 0;
    }

    private static String makeFixedFileName(String fileName) {
        String originalPrefix = fileName.replaceAll("\\.txt", "");
        return originalPrefix + FILE_NAME_APPENDER;
    }

    private static boolean isLetterOrDigit(char character) {
        return Character.isLetter(character) || Character.isDigit(character);
    }

    private static boolean isLetterOrDigit(int character) {
        return isLetterOrDigit((char) character);
    }

    private static boolean isLetterOrDigit(String string) {
        return string.length() == 1 && isLetterOrDigit(string.charAt(0));
    }
}
