package algorithms.sort.selection;

import algorithms.SortHelper;
import algorithms.Sortable;

/**
 * @author XW
 * @since 2019-02-22 15:52
 */
public class SelectionSort2 implements Sortable {

    /**
     * 每一轮同时找最大值和最小值
     *
     * @param arr
     */
    @Override
    public void sort(int[] arr) {
        // 定义左右边界
        int left = 0, right = arr.length - 1;

        while(left < right) {
            int minIndex = left, maxIndex = right;

            // 在每一轮查找时, 要保证arr[minIndex] <= arr[maxIndex]
            if (arr[minIndex] > arr[maxIndex]) {
                SortHelper.swap(arr, left, right);
            }

            // 选择剩余的最大活最小值
            for (int i = left + 1; i < right; i++) {
                if (arr[i] < arr[minIndex]) {
                    minIndex = i;
                } else if (arr[i] > arr[maxIndex]) {
                    maxIndex = i;
                }
            }

            SortHelper.swap(arr, left, minIndex);
            SortHelper.swap(arr, right, maxIndex);

            left ++;
            right --;
        }
    }
}
