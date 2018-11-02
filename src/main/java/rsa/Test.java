package rsa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.supports.utils.SignatureUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author FS
 * @date 2018/8/14 15:05
 */
public class Test {

    private static Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) throws Exception {
        String content = "abcd123456234";
        String charset = StandardCharsets.UTF_8.name();
        String privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAPNWUFluLxwhXhmffTzIdR27kxOSLmpdnqIiWUYW5ur+t/9Ve3ffXYqxuNgKYh2uEVNqul9K++yui3vYctSkbUOXUOUnc1yJoZ/a0oFaYb6+TQfa4coRVWGAqE/Ranis7plft1i7p7RrSDysHi+/H2BedeprK1c0vSU4s/HgledzAgMBAAECgYAiZaEWAy1FnLHgjVr2rJ9hhJJVweQtxO4XnSNhdPtPXJJF1Z+OYlSmtliasV0NNP3d/Mg7kC3sVwAAkagpxtRq4vN9d87znUe8I61tCk99VQNBdxzOi6BuOBR98atGboLy3+8TM0Dn2FrUzNyRJQordujqavs5Zf2rql+GufzhAQJBAP/G5FJJIz1SrowDkxtV7xD4FUOzxNHaQ3u14RxVOusErVtVGWGjyMs03Uoj0m7/e236F4js96pvv8pF/vq5T0sCQQDzjKT/RG/JHu5W8dcKhvUie61UdtFIy1ChY+V5I5upx2WGwv1ofhZB01c2Kw4gD4VoLtq8EET6aTSS0WZ54Sd5AkEA/3L3Y0sSO9OQ6dtEF1/61HrAWg53epK/JRWgDrj8j+3q6TWTlNWL248fl0UxjJPyBQNqb+Ei9QCIjo7bujJLAwJBAIuUt5t05XwmtgIuaEgoQKWarCQUVK2bmIJmdkWPGs+a75zkc8dHPApNzVXGJzpIqvSipcnSqTW6xd6/FDQVzkECQQCeROg9QYXv9NTyMwmj36GfTisDNtyEOfZtxy+94xMY1WBU3LcwzTkyJxhsQ6fozHPNfOi85aGo+s3TMlHTbQqP";
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDzVlBZbi8cIV4Zn308yHUdu5MTki5qXZ6iIllGFubq/rf/VXt3312KsbjYCmIdrhFTarpfSvvsrot72HLUpG1Dl1DlJ3NciaGf2tKBWmG+vk0H2uHKEVVhgKhP0Wp4rO6ZX7dYu6e0a0g8rB4vvx9gXnXqaytXNL0lOLPx4JXncwIDAQAB";

        String sign = SignatureUtils.rsaSign(content, privateKey, charset);
        logger.info("sign:{}", sign);

        boolean result = SignatureUtils.rsaCheck(content, "oTCO8HZfB7xoJEIh8VUMjIP7wVeBLEsrkLtcWTgAEoRY6z96BD1NWu29Q6sZVT+IALvfrCQAgKjtoBT9on/GQNqAjYwnpLEoSD3OnMtZkC4IOUewd7nhecAOo2rEtmNRvCelEbOYiXeNIjEYBLKZYfTJuisYbnRe1Mwx/4bQ0HA=", publicKey, charset);
        logger.info("result:{}", result);


        String encrypt = SignatureUtils.rsaEncrypt(content, publicKey, charset);
        logger.info("encrypt:{}", encrypt);

        String decrypt = SignatureUtils.rsaDecrypt("Gf7csbeyHiNxZjf+pe5qkofcjiCEVdge0QwMjUxnkt9TXyYtnSy2uIZhkzbmigdfdj/jasDe4WH7+klVNMbh331ClWSBst2Im5J8ytSwzV42JHJI8XEZE/DnDUCT08FJulB17dd016p8SDFwxbaYC6H6T07uV1Nt2+Zx6+EdGkA=", privateKey, charset);
        logger.info("decrypt:{}", decrypt);
    }
}
