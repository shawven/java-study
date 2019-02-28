package algorithms.sort.selection;

import algorithms.SortHelper;

import java.util.Arrays;

/**
 * @author XW
 * @since 2019-02-22 14:42
 */
public class MainTest {

    public static void main(String[] args) {
        int n = 50000;
        int[] arr1 = SortHelper.generateRandomArray(n, 0, n);
        int[] arr2 = Arrays.copyOf(arr1, arr1.length);

        System.out.println("一般测试");
        SortHelper.testSort(SelectionSort.class, arr1);
        SortHelper.testSort(SelectionSort2.class, arr2);


        arr1 = SortHelper.generateRandomArray(n, 0, 3);
        arr2 = Arrays.copyOf(arr1, arr1.length);

        System.out.println("有序性更强的测试");
        SortHelper.testSort(SelectionSort.class, arr1);
        SortHelper.testSort(SelectionSort2.class, arr2);

        arr1 = SortHelper.generateNearlyOrderedArray(n, 100);
        arr2 = Arrays.copyOf(arr1, arr1.length);

        System.out.println("测试近乎有序的数组");
        SortHelper.testSort(SelectionSort.class, arr1);
        SortHelper.testSort(SelectionSort2.class, arr2);
    }
}
