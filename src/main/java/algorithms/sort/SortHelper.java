package algorithms;

public class SortHelper {

    // SortTestHelper不允许产生任何实例
    private SortHelper(){}

    // 生成有n个元素的随机数组,每个元素的随机范围为[rangeL, rangeR]
    public static int[] generateRandomArray(int n, int rangeL, int rangeR) {

        assert rangeL <= rangeR;

        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = (int) (Math.random() * (rangeR - rangeL + 1) + rangeL);
        }
        return arr;
    }

    // 生成一个近乎有序的数组
    // 首先生成一个含有[0...n-1]的完全有序数组, 之后随机交换swapTimes对数据
    // swapTimes定义了数组的无序程度:
    // swapTimes == 0 时, 数组完全有序
    // swapTimes 越大, 数组越趋向于无序
    public static int[] generateNearlyOrderedArray(int n, int swapTimes){

        int[] arr = new int[n];
        for( int i = 0 ; i < n ; i ++ ) {
            arr[i] = i;
        }

        for( int i = 0 ; i < swapTimes ; i ++ ){
            int a = (int)(Math.random() * n);
            int b = (int)(Math.random() * n);
            int t = arr[a];
            arr[a] = arr[b];
            arr[b] = t;
        }

        return arr;
    }

    // 打印arr数组的所有内容
    public static void printArray(Object[] arr) {

        for (Object o : arr) {
            System.out.print(o);
            System.out.print(' ');
        }
        System.out.println();
    }

    // 判断arr数组是否有序
    public static boolean isSorted(int[] arr){

        for( int i = 0 ; i < arr.length - 1 ; i ++ ) {
            if(arr[i] > arr[i+1]) {
                return false;
            }
        }
        return true;
    }

    public static void swap(int[] arr, int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    // 测试sortClassName所对应的排序算法排序arr数组所得到结果的正确性和算法运行时间
    public static void testSort(Class cls, int[] arr){
        // 通过Java的反射机制，通过排序的类名，运行排序函数
        try{
            // 通过sortClassName获得排序函数的Class对象
            Class sortClass = Class.forName(cls.getName());

            Sortable.class.isAssignableFrom(sortClass);

            Sortable sortable = (Sortable) sortClass.newInstance();

            long startTime = System.currentTimeMillis();
            // 调用排序函数
            sortable.sort(arr);
            long endTime = System.currentTimeMillis();

            if (!isSorted(arr)) {
                throw new RuntimeException(sortClass.getSimpleName() + "未排好序");
            }

            System.out.println( sortClass.getSimpleName()+ " : " + (double)(endTime-startTime) / 1000 + "s" );
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
