package me.nabil.decrypt;

import com.sun.org.apache.xpath.internal.operations.String;
import me.nabil.mixed.Md5Util;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author nabilzhang
 * @date 2017-08-03
 */
public class Digest2Test {
    public static void main(String[] args) {
        System.out.println(DigestUtils.md5Hex("aaaa"));
        System.out.println(Md5Util.getMd5ByByte("aaaa".getBytes()));

        try {
            System.out.println(Hex.decodeHex("2DFCD8".toCharArray()));
        } catch (DecoderException e) {
            e.printStackTrace();
        }
    }
}
