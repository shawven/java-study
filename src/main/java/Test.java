import com.google.common.base.CaseFormat;
import org.apache.commons.lang3.RandomStringUtils;
import util.HttpClientUtils;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author FS
 * @since 2018-12-26
 */
public class Test {

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {

        CompletableFuture<String> future = HttpClientUtils.executor()
                .get("http://www.baidu.com")
                .setConnectTimeOut(20)
                .setReadTimeOut(20)
                .asyncExecute();



        future.thenApply((s) -> {
            System.out.println("consume produce: " + s);
            return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_HYPHEN, s);
        }).thenApply(s -> {
            return s + "  say: " + RandomStringUtils.randomAlphabetic(5);
        }).whenComplete((s, throwable) -> {
            System.out.println("whenComplete");
        }).join();

    }
}

