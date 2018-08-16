package thread;

import java.text.ParseException;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;

/**
 * @author FS
 */
public class ThreadTest {

    public static void main(String[] args) throws ParseException {

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
                    + "，队列中任务数目：" + executor.getQueue().size()
                    + "，已执行玩别的任务数目：" + executor.getCompletedTaskCount());
        }

        executor.shutdown();

    }

    public void runTask() {
        List<String> collect = generator().stream()
                .filter(item -> item > 1)
                .sorted(comparingInt(item -> -item))
                .map(item -> "a" + item)
                .collect(Collectors.toList());

        System.out.println(collect.toString());
    }

    public List<Integer> generator() {
        Random random = new Random();

        HashSet<Integer> integers = new HashSet<>();

        while (integers.size() <= 10) {
            integers.add(random.nextInt(10000));
        }

        return new ArrayList<>(integers);
    }
}
