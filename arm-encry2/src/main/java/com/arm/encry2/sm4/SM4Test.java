package com.arm.encry2.sm4;

import cn.hutool.core.io.FileUtil;
import sun.misc.BASE64Decoder;

/**
 * http://server.datapipeline.scgzjg.cn/datapipeline/keyDownload?USER=lzgzw
 * <p>
 * +jmhP8O1137tj/7y8K/E9NwD8TzDbU3nchQwkwFBzBpz7ydDEamt3o07KuoeHqfG
 */
public class SM4Test {
    static String sm4Key = "+jmhP8O1137tj/7y8K/E9NwD8TzDbU3nchQwkwFBzBpz7ydDEamt3o07KuoeHqfG";
    static String sm2Key = "H1i2Zjv/hDHT2W0WxLJ2AP6v9+s2Z3fZk0FXakt7Czx8eI5VhG+sbJkXe0lJseZuKB73+sScjlPHHXLC9ocm7nV/uCmShUiMDXM5mGBJ49x4Cx2S1Xh6nrNLEZ+8uzzVlZ4JkFBo/5F8ITGdmGJMDW+1bq8cbxy37A+SB/NMIrNz7ydDEamt3o07KuoeHqfG";

    static String gzwSocialCode = "11510400771686813T";

    public static void main(String[] args) throws Exception {

        //getSm43();
        encryLzgzw();
    }

    private static String getSm43() {
        try {
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = 0;
            byte[] keyBytes;
            /*if (this.hexString) {
                keyBytes = ByteUtil.hexStringToBytes( this.secretKey );
            } else {
                keyBytes = this.secretKey.getBytes();
            }*/

            keyBytes = gzwSocialCode.getBytes();

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_dec(ctx, keyBytes);
            //byte[] decrypted = sm4.sm4_crypt_ecb( ctx, (new BASE64Decoder()).decodeBuffer( sm4Key ) );
            byte[] decrypted = sm4.sm4_crypt_ecb(ctx, sm4Key.getBytes());

            byte[] decrypted2 = sm4.sm4_crypt_ecb(ctx, (new BASE64Decoder()).decodeBuffer(sm2Key));

            System.out.println(new String(decrypted, "GBK")); // 去掉编码也是一样的。
            System.out.println(new String(decrypted2, "GBK")); // 去掉编码也是一样的。
            return null;
        } catch (Exception var6) {
            var6.printStackTrace();
            return null;
        }
    }


    /**
     * 泸州国资委包测试
     *
     * @return
     */
    public static void encryLzgzw() throws Exception {
        String sm4Key = "c44874698a179fae0425eb014b371750";
        String file = "/Users/zhaolangjing/Downloads/11510400771686813T_TASK_0037_1009_1628061018358_06a14655a61d47898cada61eb37a9adc/11510400771686813T_0037_1009_1628061018348_791332c3c0c442328fdee51e894ef138.zip";
        SM4_Context ctx = new SM4_Context();
        ctx.isPadding = true;
        ctx.mode = 0;
        SM4 sm4 = new SM4();
        sm4.sm4_setkey_dec(ctx, sm4Key.getBytes());
        byte[] bytes = FileUtil.readBytes(file);
        byte[] decrypted = sm4.sm4_crypt_ecb(ctx, bytes);
        FileUtil.writeBytes(decrypted , "/Users/zhaolangjing/Downloads/test.zip");
    }
}
