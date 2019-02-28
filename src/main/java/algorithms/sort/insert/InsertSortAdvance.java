package algorithms.sort.insert;

import algorithms.Sortable;

/**
 * @author XW
 * @since 2019-02-22 15:12
 */
public class InsertSortAdvance implements Sortable {
    @Override
    public void sort(int[] arr) {
        int n = arr.length;

        for (int i = 0; i < n; i ++) {
            int e = arr[i];
            int j = i;

            for (; j > 0 && arr[j - 1] > e; j--) {
                arr[j] = arr[j - 1];
            }

            arr[j] = e;
        }
    }

    /**
     * 对arr[l...r]的区间使用InsertionSort排序
     *
     * @param arr
     * @param l
     * @param r
     */
    public void sort(int[] arr, int l, int r) {

        for( int i = l + 1; i <= r ; i ++ ){
            int e = arr[i];
            int j = i;

            for(; j > l && arr[j-1] > e; j--) {
                arr[j] = arr[j-1];
            }

            arr[j] = e;
        }
    }
}
