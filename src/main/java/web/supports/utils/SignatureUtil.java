package web.supports.utils;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.*;
import javax.crypto.Cipher;

/**
 * @author FS
 */
public class SignatureUtil {
    private static final int MAX_ENCRYPT_BLOCK = 117;
    private static final int MAX_DECRYPT_BLOCK = 128;

    public static String convertNameValuePairsString(Map<String, Object> sortedParams) {
        StringBuilder content = new StringBuilder();
        List<String> keys = new ArrayList<>(sortedParams.keySet());
        Collections.sort(keys);
        int index = 0;

        for (String key : keys) {
            Object value = sortedParams.get(key);
            if (StringUtils.isNotBlank(key) && value != null) {
                content.append(index == 0 ? "" : "&").append(key).append("=").append(value);
                ++index;
            }
        }

        return content.toString();
    }

    public static Map<String, Object> splitNameValuePairsString(String nameValuePairsStr) {
        if (StringUtils.isBlank(nameValuePairsStr)) {
            return null;
        }
        String[] nameValuePairs = nameValuePairsStr.split("&");

        if (nameValuePairs.length == 0) {
            return null;
        }

        LinkedHashMap<String, Object> newPairs = new LinkedHashMap<>();
        for (String nameValuePair : nameValuePairs) {
            Object[] elements = nameValuePair.split("=");
            if (elements.length != 2) {
                continue;
            }

            newPairs.put(elements[0].toString(), elements[1]);
        }

        return newPairs;
    }

    public static String getSignCheckContent(Map<String, Object> params) {
        if (params == null) {
            return null;
        } else {
            StringBuilder content = new StringBuilder();
            List<String> keys = new ArrayList<>(params.keySet());
            Collections.sort(keys);

            for(int i = 0; i < keys.size(); ++i) {
                String key = keys.get(i);
                Object value = params.get(key);
                content.append(i == 0 ? "" : "&").append(key).append("=").append(value);
            }

            return content.toString();
        }
    }

    public static String rsaSign(String content, String privateKey, String charset, String signType) throws RuntimeException {
        if ("RSA".equals(signType)) {
            return rsaSign(content, privateKey, charset);
        } else if ("RSA2".equals(signType)) {
            return rsa256Sign(content, privateKey, charset);
        } else {
            throw new RuntimeException("Sign Type is Not Support : signType=" + signType);
        }
    }

    public static String rsaSign(Map<String, Object> params, String privateKey, String charset) throws RuntimeException {
        String signContent = convertNameValuePairsString(params);
        return rsaSign(signContent, privateKey, charset);
    }

    public static String rsaSign(String content, String privateKey, String charset) throws RuntimeException {
        try {
            PrivateKey priKey = getPrivateKeyFromPKCS8("RSA", new ByteArrayInputStream(privateKey.getBytes()));
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initSign(priKey);
            if (StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            byte[] signed = signature.sign();
            return new String(Base64.encodeBase64(signed));
        } catch (InvalidKeySpecException var6) {
            throw new RuntimeException("RSA私钥格式不正确，请检查是否正确配置了PKCS8格式的私钥", var6);
        } catch (Exception var7) {
            throw new RuntimeException("RSAcontent = " + content + "; charset = " + charset, var7);
        }
    }

    public static String rsa256Sign(String content, String privateKey, String charset) throws RuntimeException {
        try {
            PrivateKey priKey = getPrivateKeyFromPKCS8("RSA", new ByteArrayInputStream(privateKey.getBytes()));
            Signature signature = Signature.getInstance("SHA256WithRSA");
            signature.initSign(priKey);
            if (StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            byte[] signed = signature.sign();
            return new String(Base64.encodeBase64(signed));
        } catch (Exception var6) {
            throw new RuntimeException("RSAcontent = " + content + "; charset = " + charset, var6);
        }
    }

    public static boolean rsaCheck(Map<String, Object> params, String sign, String publicKey, String charset) throws RuntimeException {
        String content = getSignCheckContent(params);
        return rsaCheck(content, sign, publicKey, charset);
    }

    public static boolean rsa256Check(Map<String, Object> params, String sign, String publicKey, String charset) throws RuntimeException {
        String content = getSignCheckContent(params);
        return rsa256Check(content, sign, publicKey, charset);
    }


    public static boolean rsaCheck(String content, String sign, String publicKey, String charset, String signType) throws RuntimeException {
        if ("RSA".equals(signType)) {
            return rsaCheck(content, sign, publicKey, charset);
        } else if ("RSA2".equals(signType)) {
            return rsa256Check(content, sign, publicKey, charset);
        } else {
            throw new RuntimeException("Sign Type is Not Support : signType=" + signType);
        }
    }

    public static boolean rsaCheck(String content, String sign, String publicKey, String charset) throws RuntimeException {
        try {
            PublicKey pubKey = getPublicKeyFromX509("RSA", new ByteArrayInputStream(publicKey.getBytes()));
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initVerify(pubKey);
            if (StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            return signature.verify(Base64.decodeBase64(sign.getBytes()));
        } catch (Exception var6) {
            throw new RuntimeException("RSAcontent = " + content + ",sign=" + sign + ",charset = " + charset, var6);
        }
    }

    public static boolean rsa256Check(String content, String sign, String publicKey, String charset) throws RuntimeException {
        try {
            PublicKey pubKey = getPublicKeyFromX509("RSA", new ByteArrayInputStream(publicKey.getBytes()));
            Signature signature = Signature.getInstance("SHA256WithRSA");
            signature.initVerify(pubKey);
            if (StringUtils.isEmpty(charset)) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset));
            }

            return signature.verify(Base64.decodeBase64(sign.getBytes()));
        } catch (Exception var6) {
            throw new RuntimeException("RSAcontent = " + content + ",sign=" + sign + ",charset = " + charset, var6);
        }
    }

    public static String rsaEncrypt(String content, String publicKey, String charset) throws RuntimeException {
        try {
            PublicKey pubKey = getPublicKeyFromX509("RSA", new ByteArrayInputStream(publicKey.getBytes()));
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(1, pubKey);
            byte[] data = StringUtils.isEmpty(charset) ? content.getBytes() : content.getBytes(charset);
            int inputLen = data.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;

            for(int i = 0; inputLen - offSet > 0; offSet = i * 117) {
                byte[] cache;
                if (inputLen - offSet > 117) {
                    cache = cipher.doFinal(data, offSet, 117);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }

                out.write(cache, 0, cache.length);
                ++i;
            }

            byte[] encryptedData = Base64.encodeBase64(out.toByteArray());
            out.close();
            return StringUtils.isEmpty(charset) ? new String(encryptedData) : new String(encryptedData, charset);
        } catch (Exception var12) {
            throw new RuntimeException("EncryptContent = " + content + ",charset = " + charset, var12);
        }
    }

    public static String rsaDecrypt(String content, String privateKey, String charset) throws RuntimeException {
        try {
            PrivateKey priKey = getPrivateKeyFromPKCS8("RSA", new ByteArrayInputStream(privateKey.getBytes()));
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(2, priKey);
            byte[] encryptedData = StringUtils.isEmpty(charset) ? Base64.decodeBase64(content.getBytes()) : Base64.decodeBase64(content.getBytes(charset));
            int inputLen = encryptedData.length;
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int offSet = 0;

            for(int i = 0; inputLen - offSet > 0; offSet = i * 128) {
                byte[] cache;
                if (inputLen - offSet > 128) {
                    cache = cipher.doFinal(encryptedData, offSet, 128);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }

                out.write(cache, 0, cache.length);
                ++i;
            }

            byte[] decryptedData = out.toByteArray();
            out.close();
            return StringUtils.isEmpty(charset) ? new String(decryptedData) : new String(decryptedData, charset);
        } catch (Exception var12) {
            throw new RuntimeException("EncodeContent = " + content + ",charset = " + charset, var12);
        }
    }

    public static PrivateKey getPrivateKeyFromPKCS8(String algorithm, InputStream ins) throws Exception {
        if (ins != null && !StringUtils.isEmpty(algorithm)) {
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            byte[] encodedKey = StreamUtil.readText(ins).getBytes();
            encodedKey = Base64.decodeBase64(encodedKey);
            return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
        } else {
            return null;
        }
    }

    public static PrivateKey getPrivateKeyFromPKCS1(String algorithm, InputStream ins) throws Exception {
        if (ins != null && !StringUtils.isEmpty(algorithm)) {
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            byte[] encodedKey = StreamUtil.readText(ins).getBytes();
            encodedKey = Base64.decodeBase64(encodedKey);
            return keyFactory.generatePrivate(new PKCS8EncodedKeySpec(encodedKey));
        } else {
            return null;
        }
    }

    public static PublicKey getPublicKeyFromX509(String algorithm, InputStream ins) throws Exception {
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        StringWriter writer = new StringWriter();
        StreamUtil.io(new InputStreamReader(ins), writer);
        byte[] encodedKey = writer.toString().getBytes();
        encodedKey = Base64.decodeBase64(encodedKey);
        return keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
    }
}
