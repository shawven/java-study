package algorithms.sort.insert;

import algorithms.SortHelper;
import algorithms.Sortable;

/**
 * @author XW
 * @since 2019-02-22 15:12
 */
public class InsertSort implements Sortable {
    @Override
    public void sort(int[] arr) {
        int n = arr.length;

        for (int i = 0; i < n; i ++) {

            for (int j = i; j > 0; j --) {
                if (arr[j - 1] > arr[j]) {
                    SortHelper.swap(arr, j, j - 1);
                } else {
                    break;
                }
            }
        }
    }
}
