public class Util {
    public static <U extends Comparable> void swap(U[] list, int i, int j) {
        U x = list[i];
        list[i] = list[j];
        list[j] = x;
    }
}
