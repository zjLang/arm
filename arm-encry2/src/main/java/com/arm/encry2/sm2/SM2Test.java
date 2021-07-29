package com.arm.encry2.sm2;

import java.util.Arrays;

public class SM2Test {


    public static void main(String[] args) {
        String text = "11111111";
        SM2Util sm2 = new SM2Util();
        SM2KeyPair keyPair = sm2.generateKeyPair();
        byte[] data = sm2.encrypt(text, keyPair.getPublicKey());
        System.out.println("data is:" + Arrays.toString(data));
        sm2.decrypt(data, keyPair.getPrivateKey());//71017045908707391874054405929626258767106914144911649587813342322113806533034
    }
}
