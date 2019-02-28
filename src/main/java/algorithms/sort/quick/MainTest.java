package algorithms.sort.quick;

import algorithms.SortHelper;
import algorithms.sort.merge.MergeSortUp;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author XW
 * @since 2019-02-22 14:42
 */
public class MainTest {

    public static void main(String[] args) {
        int n = 30000000;
        int[] arr1 = SortHelper.generateRandomArray(n, 0, n);
        int[] arr2 = Arrays.copyOf(arr1, arr1.length);
        int[] arr3 = Arrays.copyOf(arr1, arr1.length);

//        System.out.println("一般测试");
//        SortHelper.testSort(MergeSortUp.class, arr1);
//        SortHelper.testSort(QuickSort.class, arr2);
//        SortHelper.testSort(QuickSortOther.class, arr3);
//
//
        arr1 = SortHelper.generateRandomArray(n, 0, 10);
        arr2 = Arrays.copyOf(arr1, arr1.length);
        arr3 = Arrays.copyOf(arr1, arr1.length);

        System.out.println("大量重复元素的测试");
        SortHelper.testSort(MergeSortUp.class, arr1);
        SortHelper.testSort(QuickSort2ways.class, arr2);
        SortHelper.testSort(QuickSort3ways.class, arr3);
//
//        arr1 = SortHelper.generateNearlyOrderedArray(n, 100);
//        arr2 = Arrays.copyOf(arr1, arr1.length);
//        arr3 = Arrays.copyOf(arr1, arr1.length);
//
//        System.out.println("测试近乎有序的数组");
//        SortHelper.testSort(MergeSortUp.class, arr1);
//        SortHelper.testSort(QuickSort.class, arr2);
//        SortHelper.testSort(QuickSortOther.class, arr3);

    }
}
