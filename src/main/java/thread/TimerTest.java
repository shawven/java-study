package thread;

import java.util.Calendar;
import java.util.Timer;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 *
 * @description:
 * @author: FS
 * @date: 2018-08-13 17:21
 */
public class TimerTest {

    public static void main(String[] args) {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(5,
                r -> {
                    Thread thread = new Thread(r,  Calendar.getInstance().getTime().toString());
                    thread.setDaemon(true);
                    return thread;
                });

        for (int i = 0; i <= 5; i++) {
            executorService.scheduleAtFixedRate(() -> {
                System.out.println(Thread.currentThread().getName());
            }, 1, 2, TimeUnit.SECONDS);
        }



        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
