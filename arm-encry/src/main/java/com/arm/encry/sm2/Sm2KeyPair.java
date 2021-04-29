package com.arm.encry.sm2;

/**
 * @author zhaolangjing
 * @since 2021-3-24 15:22
 */
public class Sm2KeyPair {
    private byte[] priKey;
    private byte[] pubKey;

    public Sm2KeyPair(byte[] priKey, byte[] pubKey) {
        this.priKey = priKey;
        this.pubKey = pubKey;
    }

    public byte[] getPriKey() {
        return this.priKey;
    }

    public void setPriKey(byte[] priKey) {
        this.priKey = priKey;
    }

    public byte[] getPubKey() {
        return this.pubKey;
    }

    public void setPubKey(byte[] pubKey) {
        this.pubKey = pubKey;
    }
}
