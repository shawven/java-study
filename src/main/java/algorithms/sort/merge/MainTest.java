package algorithms.sort.merge;

import algorithms.SortHelper;

import java.util.Arrays;

/**
 * @author XW
 * @since 2019-02-22 14:42
 */
public class MainTest {

    public static void main(String[] args) {
        int n = 10000000;
        int[] arr1 = SortHelper.generateRandomArray(n, 0, n);
        int[] arr2 = Arrays.copyOf(arr1, arr1.length);
        int[] arr3 = Arrays.copyOf(arr1, arr1.length);

        System.out.println("一般测试");
        SortHelper.testSort(MergeSort.class, arr1);
        SortHelper.testSort(MergeSortUp.class, arr2);


        arr1 = SortHelper.generateRandomArray(n, 0, 10);

        System.out.println("大量重复元素的测试");
        SortHelper.testSort(MergeSort.class, arr1);
        SortHelper.testSort(MergeSortUp.class, arr2);

        arr1 = SortHelper.generateNearlyOrderedArray(n, 100);

        System.out.println("测试近乎有序的数组");
        SortHelper.testSort(MergeSort.class, arr1);
        SortHelper.testSort(MergeSortUp.class, arr2);


    }
}
