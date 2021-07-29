package com.arm.war.sm2;

import cn.xjfme.encrypt.utils.Util;
import cn.xjfme.encrypt.utils.sm2.SM2EncDecUtils;
import cn.xjfme.encrypt.utils.sm2.SM2KeyVO;
import com.arm.encry.sm2.SM2Utils;
import org.bouncycastle.util.encoders.Base64;
import org.junit.Test;

import java.io.IOException;

import static cn.xjfme.encrypt.test.SecurityTestAll.SM2Enc;

/**
 * 对包 arm-encry 和 SM2_SM3_SM4Encrypt 包的sm2加解密测试
 */
@Deprecated
public class SM2Test {

    @Test
    public void test() throws IOException {
        // 使用SM2_SM3_SM4Encrypt 加密
        SM2KeyVO sm2KeyVO = SM2EncDecUtils.generateKeyPair();
        System.out.println("公钥:" + sm2KeyVO.getPubHexInSoft());
        System.out.println("私钥:" + sm2KeyVO.getPriHexInSoft());
        //数据加密
        System.out.println("--测试加密开始--");
        String src = "I Love You";
        System.out.println("原文UTF-8转hex:" + Util.byteToHex(src.getBytes()));
        String SM2Enc = SM2Enc(sm2KeyVO.getPubHexInSoft(), src);
        System.out.println("密文:" + SM2Enc);

        // 使用arm-encry 解密
        String plainText = new String(SM2Utils.decrypt(sm2KeyVO.getPriHexInSoft().getBytes(), SM2Enc.getBytes() )   );
        System.out.println( plainText );
    }
}

