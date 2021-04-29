package com.arm.encry.sm4;

import com.arm.encry.base.ByteUtil;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhaolangjing
 * @since 2021-3-24 10:42
 */
public class SM4Utils {
    private String secretKey = "";
    private boolean hexString = false;
    private String iv = "";

    public String getSecretKey() {
        return this.secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getIv() {
        return this.iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }

    public boolean isHexString() {
        return this.hexString;
    }

    public void setHexString(boolean hexString) {
        this.hexString = hexString;
    }

    public SM4Utils() {
    }

    public String encryptData_ECB(String plainText) {
        try {
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = 1;
            byte[] keyBytes;
            if (this.hexString) {
                keyBytes = ByteUtil.hexStringToBytes(this.secretKey);
            } else {
                keyBytes = this.secretKey.getBytes();
            }

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_enc(ctx, keyBytes);
            byte[] encrypted = sm4.sm4_crypt_ecb(ctx, plainText.getBytes("GBK"));
            String cipherText = (new BASE64Encoder()).encode(encrypted);
            if (cipherText != null && cipherText.trim().length() > 0) {
                Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                Matcher m = p.matcher(cipherText);
                cipherText = m.replaceAll("");
            }

            return cipherText;
        } catch (Exception var9) {
            var9.printStackTrace();
            return null;
        }
    }

    public byte[] encryptData_ECB(byte[] plainText) {
        try {
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = 1;
            byte[] keyBytes;
            if (this.hexString) {
                keyBytes = ByteUtil.hexStringToBytes(this.secretKey);
            } else {
                keyBytes = this.secretKey.getBytes();
            }

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_enc(ctx, keyBytes);
            return sm4.sm4_crypt_ecb(ctx, plainText);
        } catch (Exception var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public String decryptData_ECB(String cipherText) {
        try {
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = 0;
            byte[] keyBytes;
            if (this.hexString) {
                keyBytes = ByteUtil.hexStringToBytes(this.secretKey);
            } else {
                keyBytes = this.secretKey.getBytes();
            }

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_dec(ctx, keyBytes);
            byte[] decrypted = sm4.sm4_crypt_ecb(ctx, (new BASE64Decoder()).decodeBuffer(cipherText));
            return new String(decrypted, "GBK");
        } catch (Exception var6) {
            var6.printStackTrace();
            return null;
        }
    }

    public byte[] decryptData_ECB(byte[] cipherText) {
        try {
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = 0;
            byte[] keyBytes;
            if (this.hexString) {
                keyBytes = ByteUtil.hexStringToBytes(this.secretKey);
            } else {
                keyBytes = this.secretKey.getBytes();
            }

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_dec(ctx, keyBytes);
            return sm4.sm4_crypt_ecb(ctx, cipherText);
        } catch (Exception var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public String encryptData_CBC(String plainText) {
        try {
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = 1;
            byte[] keyBytes;
            byte[] ivBytes;
            if (this.hexString) {
                keyBytes = ByteUtil.hexStringToBytes(this.secretKey);
                ivBytes = ByteUtil.hexStringToBytes(this.iv);
            } else {
                keyBytes = this.secretKey.getBytes();
                ivBytes = this.iv.getBytes();
            }

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_enc(ctx, keyBytes);
            byte[] encrypted = sm4.sm4_crypt_cbc(ctx, ivBytes, plainText.getBytes("GBK"));
            String cipherText = (new BASE64Encoder()).encode(encrypted);
            if (cipherText != null && cipherText.trim().length() > 0) {
                Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                Matcher m = p.matcher(cipherText);
                cipherText = m.replaceAll("");
            }

            return cipherText;
        } catch (Exception var10) {
            var10.printStackTrace();
            return null;
        }
    }

    public byte[] encryptData_CBC(byte[] plainText) {
        try {
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = 1;
            byte[] keyBytes;
            byte[] ivBytes;
            if (this.hexString) {
                keyBytes = ByteUtil.hexStringToBytes(this.secretKey);
                ivBytes = ByteUtil.hexStringToBytes(this.iv);
            } else {
                keyBytes = this.secretKey.getBytes();
                ivBytes = this.iv.getBytes();
            }

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_enc(ctx, keyBytes);
            return sm4.sm4_crypt_cbc(ctx, ivBytes, plainText);
        } catch (Exception var6) {
            var6.printStackTrace();
            return null;
        }
    }

    public String decryptData_CBC(String cipherText) {
        try {
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = 0;
            byte[] keyBytes;
            byte[] ivBytes;
            if (this.hexString) {
                keyBytes = ByteUtil.hexStringToBytes(this.secretKey);
                ivBytes = ByteUtil.hexStringToBytes(this.iv);
            } else {
                keyBytes = this.secretKey.getBytes();
                ivBytes = this.iv.getBytes();
            }

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_dec(ctx, keyBytes);
            byte[] decrypted = sm4.sm4_crypt_cbc(ctx, ivBytes, (new BASE64Decoder()).decodeBuffer(cipherText));
            return new String(decrypted, "GBK");
        } catch (Exception var7) {
            var7.printStackTrace();
            return null;
        }
    }

    public byte[] decryptData_CBC(byte[] cipherText) {
        try {
            SM4_Context ctx = new SM4_Context();
            ctx.isPadding = true;
            ctx.mode = 0;
            byte[] keyBytes;
            byte[] ivBytes;
            if (this.hexString) {
                keyBytes = ByteUtil.hexStringToBytes(this.secretKey);
                ivBytes = ByteUtil.hexStringToBytes(this.iv);
            } else {
                keyBytes = this.secretKey.getBytes();
                ivBytes = this.iv.getBytes();
            }

            SM4 sm4 = new SM4();
            sm4.sm4_setkey_dec(ctx, keyBytes);
            return sm4.sm4_crypt_cbc(ctx, ivBytes, cipherText);
        } catch (Exception var6) {
            var6.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws IOException {
        SM4Utils sm4 = new SM4Utils();
        sm4.secretKey = "E8veikqrzUAPzYz0gJ7TJkHbNXhbQNCF";
        sm4.hexString = false;
        String encryptPath = "F:/encryptFile.zip";
        byte[] fileByte2 = ByteUtil.getBytes(encryptPath);
        byte[] decryptFileByte = sm4.decryptData_ECB(fileByte2);
        ByteUtil.getFile(decryptFileByte, "F:/", "decryptFile2.zip");
    }
}
