
public class QuickSort {
    public <U extends Comparable> void sort(U[] list) {
        sort(list, 0, list.length - 1);
    }

    private <U extends Comparable> void sort(U[] list, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        int j = partition(list, lo, hi);
        sort(list, lo, j - 1);
        sort(list, j + 1, hi);
    }

    private <U extends Comparable> int partition(U[] list, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        U v = list[lo];
        while (true) {

            while (list[++i].compareTo(v) < 0) {
                if (i == hi) {
                    break;
                }
            }

            while (v.compareTo(list[--j]) < 0) {
                if (j == lo) {
                    break;
                }
            }

            if (i >= j) {
                break;
            }

            Util.swap(list, i, j);
        }

        Util.swap(list, lo, j);

        return j;
    }


}
