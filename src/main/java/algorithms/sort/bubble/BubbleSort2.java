package algorithms.sort.bubble;

import algorithms.SortHelper;
import algorithms.Sortable;

/**
 * @author XW
 * @since 2019-02-22 18:16
 */
public class BubbleSort2 implements Sortable {
    @Override
    public void sort(int[] arr) {
        int n = arr.length;
        int newn;
        while (n > 0) {
            newn = 0;
            for (int j = 1; j < n; j ++) {
                if (arr[j] < arr[j - 1]) {
                    SortHelper.swap(arr, j-1, j);
                    newn = j;
                }
            }
            n = newn;
        }
    }

}
