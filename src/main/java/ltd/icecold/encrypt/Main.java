package ltd.icecold.encrypt;

import ltd.icecold.encrypt.utils.DESEncrypt;
import ltd.icecold.encrypt.utils.RSAEncrypt;
import org.apache.commons.codec.binary.Base64;

import java.security.KeyPair;


/**
 * 测试类
 *
 * 也可将DES换成其他类型加密
 *
 * 加密流程(服务器->客户端)：
 * 1、服务器客户端生成密钥对
 * 2、服务器客户端相互交换公钥
 * 3、生成随机DES密码
 * 4、使用DES加密数据
 * 5、使用客户端公钥RSA加密DES密码
 * 6、组合加密密码与加密数据
 *
 * 解密方法相反
 *
 * @author ice_cold
 */
public class Main {
    private static String serverPublicKey;
    private static String clientPublicKey;
    public static void main(String[] args) throws Exception {
        Process server = new Process();
        Process client = new Process();
        //模拟双方公钥交换
        serverPublicKey = server.init();
        clientPublicKey = client.init();
        System.out.println("服务器公钥："+serverPublicKey);
        System.out.println("客户端公钥："+clientPublicKey);
        System.out.println("-------------服务器发送数据-------------");
        //服务器发送数据，客户端接收并解密
        String data = "Welcome to connect to the server";
        System.out.println("服务器发送数据："+data);
        Message sendMessage = server.sendMessage(data,clientPublicKey);
        System.out.println("加密后数据："+sendMessage.toString());
        System.out.println("-------------客户端接收数据-------------");
        System.out.println("解密后数据："+client.receiveMessage(sendMessage));
    }
}


/**
 * 模拟服务器与客户端
 */
class Process{
    private static KeyPair rsaKey;
    public Process() throws Exception {
        rsaKey = RSAEncrypt.genKeyPair(2048);
    }
    public String init(){
        return Base64.encodeBase64String(rsaKey.getPublic().getEncoded());
    }
    public Message sendMessage(String data,String key) throws Exception {
        //长度需为8的倍数
        String randomPassword = DESEncrypt.generatePassword(96);
        Message message = new Message();
        //加密数据
        String enMessage = DESEncrypt.encrypt(data,randomPassword);
        message.setEnData(enMessage);

        String enPassword = Base64.encodeBase64String(RSAEncrypt.encrypt(randomPassword.getBytes(), RSAEncrypt.getPublicKey(key)));
        message.setEnDesPassword(enPassword);
        return message;
    }
    public String receiveMessage(Message message) throws Exception {
        //获取des密码
        String password = new String(RSAEncrypt.decrypt(Base64.decodeBase64(message.getEnDesPassword()),rsaKey.getPrivate()));
        return DESEncrypt.decrypt(message.getEnData(),password);
    }
}
