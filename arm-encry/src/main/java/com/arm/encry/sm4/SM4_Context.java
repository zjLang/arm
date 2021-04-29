package com.arm.encry.sm4;

/**
 * @author zhaolangjing
 * @since 2021-3-24 10:28
 */
public class SM4_Context {
    public int mode = 1;
    public long[] sk = new long[32];
    public boolean isPadding = true;

    public SM4_Context() {
    }
}
