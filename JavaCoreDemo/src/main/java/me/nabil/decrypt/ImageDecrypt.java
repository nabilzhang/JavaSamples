package me.nabil.decrypt;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;

/**
 * 图片解密
 *
 * @author nabilzhang
 * @date 2017-08-03
 */
public class ImageDecrypt {

    private static final String PASS_PHRASE = "ioDwQcsew9R4XcnnSuJycnW77iSUqjA3";
    private static int IV_SIZE = 8;

    public static void main(String[] args) throws Exception {
        String iv = StringUtils.substring((DigestUtils.md5Hex(hexStr2Str("1B3C58").getBytes()) + PASS_PHRASE), 0, IV_SIZE);
//        SecureRandom rnd = new SecureRandom();
//        byte[] iv = new byte[16];
//        rnd.nextBytes(iv);

        String key = (DigestUtils.md5Hex((hexStr2Str("2DFCD8") + PASS_PHRASE).getBytes())
                + DigestUtils.md5Hex((hexStr2Str("2DFCD9") + PASS_PHRASE).getBytes())).substring(0, 24);

        byte[] encData = FileUtils.readFileToByteArray(new File(ImageDecrypt.class.getClassLoader()
                .getResource("1ff8099177d84824db12f53bab29aadab0768a87_900x675_secret").getFile()));
        byte[] decData = decrypt(encData, key.getBytes(), iv.getBytes());

        FileUtils.writeByteArrayToFile(new File("a.jpg"), decData);
    }

    public static byte[] decrypt(byte[] encData, byte[] key, byte[] iv) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv));
        byte[] decbbdt = cipher.doFinal(encData);
        return decbbdt;
    }

    public static String hexStr2Str(String hexStr) {
        String str = "0123456789ABCDEF";
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int n;
        for (int i = 0; i < bytes.length; i++) {
            n = str.indexOf(hexs[2 * i]) * 16;
            n += str.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (n & 0xff);
        }
        return new String(bytes);
    }
}
