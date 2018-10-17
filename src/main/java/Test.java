import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.AbstractResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author FS
 * @date 2018/8/14 15:05
 */
public class Test {

    private static Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) throws Exception {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet httpGet = new HttpGet("https://www.baidu.com/");

//        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");

//        CloseableHttpResponse response = httpClient.execute(httpGet, new AbstractResponseHandler<CloseableHttpResponse>() {
//            @Override
//            public CloseableHttpResponse handleEntity(HttpEntity httpEntity) throws IOException {
//                return httpEntity;
//            }
//        });

//        HttpEntity entity = response.getEntity();
//
//        logger.info(EntityUtils.toString(entity, "utf-8"));
//
//        response.close();
        httpClient.close();
    }
}
