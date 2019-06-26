import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author FS
 * @since 2018-12-26
 */
public class Test {

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {

    }

    public static void printThead() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread());
    }
}

