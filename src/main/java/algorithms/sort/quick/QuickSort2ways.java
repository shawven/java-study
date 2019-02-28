package algorithms.sort.quick;

import algorithms.SortHelper;
import algorithms.Sortable;
import algorithms.sort.insert.InsertSortAdvance;

import java.util.Random;

/**
 * @author XW
 * @since 2019-02-23 11:41
 */
public class QuickSort2ways implements Sortable {

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

        //选取索引l的值k为基数，接着考察元素i，从l + 1 开始
        //arr[l+1...i) <= v; arr(j...r] >= v
        int i = l + 1, j = r, k = arr[l];

        while (true) {
            //  比如数组 1,0,0, ..., 0, 0
            //  a. 对于arr[i] < k和arr[j] > k的方式，第一次partition得到的分点是数组中间；
            //  b. 对于arr[i] <= k和arr[j] >= k的方式，第一次partition得到的分点是数组的倒数第二个。
            while (j >= l + 1 && arr[j] > k) {
                j --;
            }
            while (i <= r && arr[i] < k) {
                i ++;
            }
            if (i > j) {
                break;
            }

            SortHelper.swap(arr, i, j);

            // 使等于k的均匀分布到两端
            j --;
            i ++;
        }

        // 基数位置与最后一次交换的小于k的位置j进行交换 满足左边小于k 右边大于k
        // SortHelper.swap(arr, i, j)交换之前经过两次while循环i的值小于k，j的值大于k
        // 交换后相反j的值小于k，故基数l与此交换
        SortHelper.swap(arr, l, j);

        sort(arr, l, j - 1);
        sort(arr, j + 1, r);
    }
}
