
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Base64Test {

    public static void main(String[] args) throws InterruptedException {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("当前毫秒" + System.currentTimeMillis());
                System.out.println("当前时间" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
            }
        }, 1_000, 2_000);
        Thread.sleep(5000);
        timer.cancel();
    }
}
