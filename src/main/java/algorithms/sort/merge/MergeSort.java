package algorithms.sort.merge;

import algorithms.Sortable;
import algorithms.sort.insert.InsertSortAdvance;

import java.util.Arrays;

/**
 * @author XW
 * @since 2019-02-22 16:10
 */
public class MergeSort implements Sortable {

    private InsertSortAdvance insertSort = new InsertSortAdvance();

    @Override
    public void sort(int[] arr) {
        sort(arr, 0, arr.length - 1);
    }


    /**
     * 对【left, right】进行归并排序
     *
     * @param arr
     * @param left
     * @param right
     */
    private void sort(int[] arr, int left, int right) {
        // 优化1 n很小时使用插入排序
        if (right - left <= 15) {
            insertSort.sort(arr, left, right);
            return;
        }

        int mid = (left + right) / 2;

        sort(arr, left, mid);
        sort(arr, mid + 1, right);

        //优化2 左边最大值小于右边最小值时，不用排序
        if (arr[mid] > arr[mid + 1]) {
            merge(arr, left, mid, right);
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
