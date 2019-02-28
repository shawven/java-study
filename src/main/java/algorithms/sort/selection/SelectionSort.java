package algorithms.sort.selection;

import algorithms.SortHelper;
import algorithms.Sortable;

/**
 * @author XW
 * @since 2019-02-22 14:41
 */
public class SelectionSort implements Sortable {

    @Override
    public void sort(int[] arr) {
        int n = arr.length;

        for (int i = 0; i < n; i ++) {

            int minIndex = i;
            for (int j = i + 1; j < n; j ++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            SortHelper.swap(arr, i, minIndex);
        }
    }
}
