package algorithms.sort.quick;

import algorithms.SortHelper;
import algorithms.Sortable;
import algorithms.sort.insert.InsertSortAdvance;

import java.util.Random;

/**
 * @author XW
 * @since 2019-02-23 11:41
 */
public class QuickSort3ways implements Sortable {

    private InsertSortAdvance insertSort = new InsertSortAdvance();

    @Override
    public void sort(int[] arr) {
        sort(arr, 0, arr.length - 1);
    }

    private void sort(int[] arr, int l, int r) {
        if (l >= r) {
            return;
        }

        // 对于小规模数组, 使用插入排序
        if( r - l <= 15 ){
            insertSort.sort(arr, l, r);
            return;
        }

        // 优化1：取随机值交换第一个， 避免有序时退化到O(n平方)
        SortHelper.swap(arr, l, new Random().nextInt(r - l + 1) + l);


        int k = arr[l];
        // [l+1 ... lt] < k
        int lt = l;
        // [lt+1 ... i) == k
        int i = l + 1;
        // [gt ... r] > k
        int gt = r + 1;

        while (i < gt) {
            if (arr[i] < k) {
                SortHelper.swap(arr, i, lt + 1);
                lt ++;
                i ++;
            } else if (arr[i] > k) {
                SortHelper.swap(arr, i, gt - 1);
                gt --;
            } else {
                i ++;
            }
        }

        SortHelper.swap(arr, l, lt);

        sort(arr, l, lt - 1);
        sort(arr, gt, r);
    }
}
