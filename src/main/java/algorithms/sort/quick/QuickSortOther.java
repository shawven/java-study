package algorithms.quick;

import algorithms.SortHelper;
import algorithms.Sortable;

import java.util.Random;

/**
 * @author XW
 * @since 2019-02-23 11:41
 */
public class QuickSortOther implements Sortable {

    @Override
    public void sort(int[] arr) {
        sort(arr, 0, arr.length - 1);
    }

    private void sort(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }

        // 优化1：取随机值交换第一个， 避免有序时退化到O(n平方）
        SortHelper.swap(arr, l, new Random().nextInt(r - l + 1) + l);

        // 选取索引l的值k为基数，接着考察元素i，从l + 1 开始
        // 使得arr[l+1 ... p] < k ; [p+1 ...i) > k （i为考察元素索引，故为开区间）
        int k = arr[l], p = l;
        for (int i = l + 1; i <= r; i ++) {
            // 当第i个元素大于k时，则继续考察下一个元素i
            // 当第i个元素小于k时，把大于k区间的第一个元素和i对调，同时指针先后移动一位
            if (k > arr[i]) {
                SortHelper.swap(arr, i, p + 1);
                p ++;
            }
        }

        // 把基数索引l和p对调，使得p的值为k，满足左边小于k 右边大于k
        SortHelper.swap(arr, l, p);

        sort(arr, l, p -1);
        sort(arr, p + 1, r);
    }
}
