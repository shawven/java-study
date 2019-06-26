package thread;

//import web.supports.utils.KeyWorker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingLong;

/**
 * @author FS
 */
public class ThreadTest {

    public static void main(String[] args) {

        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 15, 200, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(10),
                r -> new Thread(r, Calendar.getInstance().getTime().toString()));

        ThreadTest test = new ThreadTest();

        for (int i = 0; i <= 30; i ++) {
            try {
                executor.execute(test::runTask);
            } catch (RejectedExecutionException e) {
                System.out.println("队列已满");
            }
            System.out.println("线程池中线程数目：" + executor.getPoolSize()
                    + "，队列中任务数目：" + executor.getQueue().size());
        }

        executor.shutdown();

//        while(!executor.isTerminated()) {
//            System.out.println("已完成任务数目：" + executor.getCompletedTaskCount());
//        }

        System.out.println("hhh");
    }

    public void runTask() {
        List<String> collect = generator().stream()
                .filter(item -> item > 1)
                .sorted(comparingLong(item -> -item))
                .map(item -> "No:" + item)
                .collect(Collectors.toList());

        System.out.println(collect.toString());
    }

    public List<Long> generator() {
        HashSet<Long> integers = new HashSet<>();

//        while (integers.size() <= 20) {
//            integers.add(KeyWorker.nextId());
//        }
        return new ArrayList<>(integers);
    }

}
