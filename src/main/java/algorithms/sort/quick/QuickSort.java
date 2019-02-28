package algorithms.sort.quick;

import algorithms.SortHelper;
import algorithms.Sortable;

/**
 * @author XW
 * @since 2019-02-23 11:41
 */
public class QuickSort implements Sortable {

    @Override
    public void sort(int[] arr) {
        sort(arr, 0, arr.length - 1);
    }

    private void sort(int[] arr, int left, int right) {
        int l = left, r = right, k = arr[l];

        while (l < r) {
            while (l < r && k <= arr[r]) {
                r --;
            }
            if (l < r) {
                SortHelper.swap(arr, l, r);
                l ++;
            }
            while (l < r && k >= arr[l]) {
                l ++;
            }
            if (l < r) {
                SortHelper.swap(arr, l, r);
                r --;
            }
        }

        if (l > left) {
            sort(arr, left, l - 1);
        }
        if (r < right) {
            sort(arr, l + 1, right);
        }
    }
}

