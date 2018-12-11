import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.function.Consumer;

public class QuickSortVsInsertionSort {

    private static final int DEFAULT_SEED = Instant.now().getNano();

    public static void main(String[] args) {
        if (args.length == 2) {
            int count = new Integer(args[0]),
                    max = new Integer(args[1]);

            Integer[] intArray1 = makeRandomArrayInts(count, max, DEFAULT_SEED);
            Integer[] intArray2 = intArray1.clone();
            compareSortingTimes(intArray1, intArray2, "ints");

            System.out.println();

            Float[] floatArray1 = makeRandomArrayFloats(count, max, DEFAULT_SEED);
            Float[] floatArray2 = floatArray1.clone();
            compareSortingTimes(floatArray1, floatArray2,"floats");
        }
    }

    private static <U extends Comparable> void compareSortingTimes(U[] array1, U[] array2, String itemType) {
        long quicksortTime = getTimeForSortingAlgorithm(array1, QuickSortVsInsertionSort::quicksort, "Quicksort");
        long insertionsortTime = getTimeForSortingAlgorithm(array2, QuickSortVsInsertionSort::insertionsort, "Insertionsort");
        System.out.println((quicksortTime > insertionsortTime ? "InsertionSort " : "QuickSort ") + "was faster for an array with " + array1.length + " " + itemType + " by " + (quicksortTime > insertionsortTime ? quicksortTime - insertionsortTime : insertionsortTime - quicksortTime) + " milliseconds.");
    }

    private static <U, V extends Consumer<U[]>> long getTimeForSortingAlgorithm(U[] array, V algorithm, String algoName) {
        Instant timeBefore = Instant.now();
        algorithm.accept(array);
        Instant timeAfter = Instant.now();
        long duration = Duration.between(timeBefore, timeAfter).toMillis();
        System.out.println(algoName + " took " + duration + " milliseconds");

        return duration;
    }

    private static <U extends Comparable> void quicksort(U[] array) {
        QuickSort sort = new QuickSort();
        sort.sort(array);
    }

    private static <U extends Comparable> void insertionsort(U[] array) {
        int size = array.length;
        for (int i = 1 ; i < size ; i++) {
            for (int j = i ; j > 0 && array[j].compareTo(array[j-1]) < 0 ; j--) {
                Util.swap(array, j, j-1);
            }
        }

    }

    private static Integer[] makeRandomArrayInts(int count, int max, int seed) {
        Integer[] array = new Integer[count];
        Random random = new Random(seed);
        for (int i = 0; i < count; i++) {
            array[i] = random.nextInt(max + 1);
        }
        return array;
    }

    private static Float[] makeRandomArrayFloats(int count, int max, int seed) {
        Float[] array = new Float[count];
        Random random = new Random(seed);
        for (int i = 0; i < count; i++) {
            array[i] = (random.nextFloat() * (max + 1));
        }
        return array;
    }
}
