package com.arm.encry.sm2;

import org.bouncycastle.math.ec.ECPoint;

import java.math.BigInteger;

/**
 * @author zhaolangjing
 * @since 2021-3-24 15:14
 */
public class SM2Result {
    public BigInteger r;
    public BigInteger s;
    public BigInteger R;
    public byte[] sa;
    public byte[] sb;
    public byte[] s1;
    public byte[] s2;
    public ECPoint keyra;
    public ECPoint keyrb;


}
