package algorithms.sort.bubble;

import algorithms.SortHelper;
import algorithms.Sortable;

/**
 * @author XW
 * @since 2019-02-22 18:16
 */
public class BubbleSort implements Sortable {
    @Override
    public void sort(int[] arr) {
        int n = arr.length;

        while (n > 0) {
            boolean swapped = false;

            for (int j = 1; j < n; j ++) {
                if (arr[j] < arr[j - 1]) {
                    SortHelper.swap(arr, j-1, j);
                    swapped = true;
                }
            }

            if (!swapped) {
                break;
            }
            n --;
        }
    }
}
