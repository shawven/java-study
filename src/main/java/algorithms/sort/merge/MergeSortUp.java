package algorithms.sort.merge;

import algorithms.Sortable;
import algorithms.sort.insert.InsertSortAdvance;

import java.util.Arrays;

/**
 * @author XW
 * @since 2019-02-22 16:10
 */
public class MergeSortUp implements Sortable {

    private InsertSortAdvance insertSort = new InsertSortAdvance();

    @Override
    public void sort(int[] arr) {
        int n = arr.length;

        //优化1：小数组优化
        for(int i = 0; i < n; i += 16) {
            insertSort.sort(arr, i, Math.min(i + 15, n - 1));
        }

        for (int size = 1; size <= n; size += size) {
            for (int i = 0; i  + size < n; i += 2 * size) {
                // 优化2：已经有序无需归并
                if (arr[i + size - 1] > arr[i + size]) {
                    // 对 arr[i...i+sz-1] 和 arr[i+sz...i+2*sz-1] 进行归并
                    merge(arr, i, i + size - 1, Math.min(i + 2 * size - 1, n - 1));
                }
            }
        }
    }



    private void merge(int[] arr, int left, int mid, int right) {
        // 复制部分元素，新数组取值时需要减去偏移量
        int[] ints = Arrays.copyOfRange(arr, left, right + 1);

        int l = left, r = mid + 1;

        for (int i = left; i <= right; i ++) {
            // 如果左边处理完毕
            if (l > mid) {
                arr[i] = ints[r - left];
                r ++;
            // 如果右边处理完毕
            } else if (r > right) {
                arr[i] = ints[l - left];
                l ++;
             //  左半部分所指元素 < 右半部分所指元素
            } else if (ints[l - left] < ints[r - left]) {
                arr[i] = ints[l - left];
                l ++;
            // 左半部分所指元素 >= 右半部分所指元素
            } else {
                arr[i] = ints[r - left];
                r ++;
            }
        }
    }
}
