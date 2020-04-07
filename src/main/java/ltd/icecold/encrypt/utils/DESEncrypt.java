package ltd.icecold.encrypt.utils;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Random;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.SecretKey;
import javax.crypto.Cipher;

/**
 * DES加密工具类
 * @author ice_cold
 */
public class DESEncrypt {
    /**
     * 加密
     * @param data String
     * @param password String
     * @return String
     */
    public static String encrypt(String data, String password) {
        try{
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(password.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secureKey = keyFactory.generateSecret(desKey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, secureKey, random);
            return new Base64().encodeToString(cipher.doFinal(data.getBytes(StandardCharsets.UTF_8)));
        }catch(Throwable e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 解密
     * @param data String
     * @param password String
     * @return String
     */
    public static String decrypt(String data, String password) throws Exception {
        SecureRandom random = new SecureRandom();
        DESKeySpec desKey = new DESKeySpec(password.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secureKey = keyFactory.generateSecret(desKey);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, secureKey, random);
        return new String(cipher.doFinal(new Base64().decode(data)), StandardCharsets.UTF_8);
    }

    /**
     * 生成随机密码
     * @param length int
     * @return String
     */
    public static String generatePassword(int length){
        String str="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random=new Random();
        StringBuilder stringBuilder=new StringBuilder();
        for(int i=0;i<length;i++){
            stringBuilder.append(str.charAt(random.nextInt(62)));
        }
        return stringBuilder.toString();
    }
}
