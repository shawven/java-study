package algorithms.binarySearch;

/**
 * @author XW
 * @since 2019-02-23 23:35
 */
public class BinarySearch {

    static int find(int[] arr, int target) {
        int l = 0;
        int r = arr.length - 1;
        while(l < r) {
            int mid = l + (r - l) / 2;

            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }

        return -1;
    }

    static int floor(int[] arr, int target) {
        int l = -1;
        int r = arr.length - 1;

        while (l < r) {
            int mid = l + (r-l+1)/2;

            if (arr[mid] >= target) {
                r = mid - 1;
            } else {
                l = mid;
            }

        }

        if (l + 1 < arr.length && arr[l + 1] == target) {
            return l + 1;
        }

        return l;
    }

    static int ceil(int[] arr, int target) {
        int r = arr.length;
        int l = 0;

        while (l < r) {
            int mid = l + (r - l) / 2;
            if (arr[mid] <= target) {
                l = mid + 1;
            } else {
                r = mid;
            }
        }

        if (r - 1 > 0 && arr[r - 1] == target) {
            return r - 1;
        }

        return r;
    }

    public static void main(String[] args) {
        int[] ints = {1,3,5};
        System.out.println(find(ints, 3));
        System.out.println(floor(ints, 2));
        System.out.println(ceil(ints, 4));
    }
}
