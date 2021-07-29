package com.arm.war.sm2;

import cn.xjfme.encrypt.utils.sm2.SM2EncDecUtilsSeq;
import com.arm.encry.base.ByteUtil;
import com.arm.encry.sm2.SM2Utils;
import org.bouncycastle.math.ec.ECPoint;
import org.pzone.crypto.SM2;
import org.pzone.crypto.SM2KeyPair;

import java.io.IOException;
import java.math.BigInteger;

/**
 * 对包 arm-encry 和 SM2_SM3_SM4Encrypt 包中自己写的 加解密的包进行SM2EncDecUtilsSeq进行佳姐呀测试。
 * 测试通过
 */
public class SM2Test3 {
    static String publicKey = "04BB34D657EE7E8490E66EF577E6B3CEA28B739511E787FB4F71B7F38F241D87F18A5A93DF74E90FF94F4EB907F271A36B295B851F971DA5418F4915E2C1A23D6E";
    static String privatekey = "0B1CE43098BC21B8E82B5C065EDB534CB86532B1900A49D49F3C53762D2997FA";

    public static void main(String[] args) throws IOException {
        test1();
        test2();
    }

    private static void test1() throws IOException {
        System.out.println("------------------------");
        System.out.println("使用国务院的标准加密，使用开源加自己实现的解码：");
        String text = "Hello World";
        byte[] encrypt = SM2Utils.encrypt(ByteUtil.hexToByte(publicKey), text.getBytes());
        System.out.println("加密：" + ByteUtil.byteToHex(encrypt));

        // 解密
        byte[] decrypt = SM2EncDecUtilsSeq.decrypt(ByteUtil.hexToByte(privatekey), encrypt);
        System.out.println("解密：" + new String(decrypt));
    }


    private static void test2() throws IOException {
        System.out.println("------------------------");
        System.out.println("使用开源加自己实现的代码加密，使用国务院的标准解码：");
        String text = "Hello 中国";
        byte[] encrypt = SM2EncDecUtilsSeq.encrypt(ByteUtil.hexToByte(publicKey), text.getBytes());
        System.out.println("加密：" + ByteUtil.byteToHex(encrypt));

        // 解密
        byte[] decrypt = SM2Utils.decrypt(ByteUtil.hexToByte(privatekey), encrypt);
        System.out.println("解密：" + new String(decrypt));
    }


}
