package base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;


class Main{
    public static void main(String[] args) {
        String s = "我abc们edfsjk让我日记反倒是";
        //System.out.println(s.substring(1,2));
        System.out.println(Algorithm.SubString.substring(s,4));
    }
}

public class Algorithm {


    public static void main(String[] args) {
        HashSet<Object> objects = new HashSet<>();
        objects.add(2);
        objects.add(3);
        objects.add(4);
        System.out.println(objects);
    }


    public  static class SubString {
        public static String substring(String s, int len) {
            int n = ( getCount(s) + len - 1) / len;
            String[] strings = new String[n];

            int start = 0;
            for (int i = 0; i < n; i++) {
                int end = getIndex(s, start, len);
                if (end > start) {
                    strings[i] = s.substring(start, end);
                }  else {
                    strings[i] = s.substring(start);
                }
                start = end;
            }

            return Arrays.toString(strings);
        }

        public static int getIndex(String s, int start, int len) {
            int strlen = s.length();
            if (start >= 0 && start < strlen && len > 1 && strlen >= len) {
                int count = 0;
                for (;start < strlen; start++) {
                    if (s.charAt(start) < 255) {
                        count += 1;
                    } else {
                        count += 2;
                    }
                    if (count >= len) {
                        return start + 1;
                    }
                }
            }
            return -1;
        }

        public static int getCount(String s) {
            int count = 0;
            for (int i = 0;i < s.length(); i++) {
                if (s.charAt(i) < 255) {
                    count += 1;
                } else {
                    count += 2;
                }
            }
            return count;
        }
    }


    public static String substring1(String s, int len) {
        int n = ( s.length() + len - 1) / len;
        String[] strings = new String[n];

        for (int i = 0; i < n; i++) {
            if (i < n - 1) {
                strings[i] = s.substring(i * len, (i + 1) * len);
            } else {
                strings[i] = s.substring(i * len);
            }

        }

        return Arrays.toString(strings);
    }


    public static void yuesefu(int totalNum, int countNum, int startNum) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 1; i <= totalNum; i++) {
            list.add(i);
        }

        int k = startNum - 1;
        while (list.size() > 0) {
            System.out.println(list);
            k = (k + countNum) % list.size() - 1;
            if (k < 0) {
                System.out.println(list.get(list.size() - 1));
                list.remove(list.size() - 1);
                k = 0;
            } else {
                System.out.println(list.get(k));
                list.remove(list.remove(k));
            }
        }
    }


    public static void insertSort(int[] arr) {
        if (arr == null || arr.length < 2) {
        }
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0; j--) {
                if (arr[j] < arr[j - 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                } else {
                    break;
                }
            }
        }
    }


    public static void selectSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int index = i;
            for (int j = arr.length - 1; j > i ; j--) {
                if (arr[index] > arr[j]) {
                    index = j;
                }
            }
            if (i != index) {
                int temp = arr[i];
                arr[i] = arr[index];
                arr[index] = temp;
            }
        }
    }

    public static void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = arr.length; j > i ; j--) {
                if (arr[j] < arr[j-1]) {
                    int temp = arr[j-1];
                    arr[j-1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }


    public static void quickSort(int[] arr, int low, int high) {
        int start = low;
        int end = high;
        int s = arr[low];

        while (start < end) {
            while (start < end && s <= arr[end])
                end--;

            if (s >= arr[end]) {
                int temp = arr[end];
                arr[end] = arr[start];
                arr[start] = temp;
            }

            while (start < end && s >= arr[start])
                start ++;

            if (s <= arr[start]) {
                int temp = arr[end];
                arr[end] = arr[start];
                arr[start] = temp;
            }
            System.out.println(Arrays.toString(arr));
            if (start > low) quickSort(arr, low, start-1);
            if (end < high) quickSort(arr, end+1, high);
        }


    }

    public static int rabbit(int init ,int n) {
        if (n ==1 || n == 2) {
            return init;
        }

        int sum = init;
        for (int i = 3 ; i <= n; i++) {
            sum += rabbit(sum,n-2);
        }
        return sum;
    }



    public static String extractTheSame(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();
        int index = len1 - 1;
        int number = 0;
        for (int i = 0; i < len1; ) {
            if (index + 3 < len1 && number >= 3) {
               return s1.substring(index,index + 3);
            }
            for (int j = 0; j < len2; ) {
                while (s1.charAt(i) == s2.charAt(j)) {
                    number ++;
                    if (number == 1) {
                        index = i;
                    }
                    i++;
                    j++;
                    continue;
                }
                j++;
            }
            i++;
        }
        return null;
    }




    public static int calc(int n) {
        if (n == 1 || n == 2) {
            return 1;
        }
        return calc(n - 1) + calc(n - 2);
    }
}
