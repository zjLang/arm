package com.arm.encry.sm2;

import com.arm.encry.base.ByteUtil;
import org.bouncycastle.asn1.*;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.encoders.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Enumeration;


/**
 * @author zhaolangjing
 * @since 2021-3-24 15:08
 */
public class SM2Utils {
    public SM2Utils() {
    }

    public static byte[] encrypt(byte[] publicKey, byte[] data) {
        if (publicKey != null && publicKey.length != 0) {
            if (data != null && data.length != 0) {
                byte[] source = new byte[data.length];
                System.arraycopy( data, 0, source, 0, data.length );
                byte[] formatedPubKey;
                if (publicKey.length == 64) {
                    formatedPubKey = new byte[65];
                    formatedPubKey[0] = 4;
                    System.arraycopy( publicKey, 0, formatedPubKey, 1, publicKey.length );
                } else {
                    formatedPubKey = publicKey;
                }

                Cipher cipher = new Cipher();
                SM2 sm2 = SM2.Instance();
                ECPoint userKey = sm2.ecc_curve.decodePoint( formatedPubKey );
                ECPoint c1 = cipher.Init_enc( sm2, userKey );
                cipher.Encrypt( source );
                byte[] c3 = new byte[32];
                cipher.Dofinal( c3 );
                DERInteger x = new DERInteger( c1.getX().toBigInteger() );
                DERInteger y = new DERInteger( c1.getY().toBigInteger() );
                DEROctetString derDig = new DEROctetString( c3 );
                DEROctetString derEnc = new DEROctetString( source );
                ASN1EncodableVector v = new ASN1EncodableVector();
                v.add( x );
                v.add( y );
                v.add( derDig );
                v.add( derEnc );
                DERSequence seq = new DERSequence( v );
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                DEROutputStream dos = new DEROutputStream( bos );

                try {
                    dos.writeObject( seq );
                    byte[] var19 = bos.toByteArray();
                    return var19;
                } catch (IOException var27) {
                    var27.printStackTrace();
                } finally {
                    try {
                        dos.close();
                    } catch (IOException var26) {
                        var26.printStackTrace();
                    }

                }

                return null;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static byte[] decrypt(byte[] privateKey, byte[] encryptedData) {
        if (privateKey != null && privateKey.length != 0) {
            if (encryptedData != null && encryptedData.length != 0) {
                byte[] enc = new byte[encryptedData.length];
                System.arraycopy( encryptedData, 0, enc, 0, encryptedData.length );
                SM2 sm2 = SM2.Instance();
                BigInteger userD = new BigInteger( 1, privateKey );
                ByteArrayInputStream bis = new ByteArrayInputStream( enc );
                ASN1InputStream dis = new ASN1InputStream( bis );

                try {
                    ASN1Sequence asn1 = (ASN1Sequence) dis.readObject();
                    BigInteger x = new BigInteger( asn1.getObjectAt( 0 ).toString() );
                    BigInteger y = new BigInteger( asn1.getObjectAt( 1 ).toString() );
                    ECPoint c1 = sm2.ecc_curve.createPoint( x, y, true );
                    Cipher cipher = new Cipher();
                    cipher.Init_dec( userD, c1 );
                    DEROctetString data = (DEROctetString) asn1.getObjectAt( 3 );
                    enc = data.getOctets();
                    cipher.Decrypt( enc );
                    byte[] c3 = new byte[32];
                    cipher.Dofinal( c3 );
                    byte[] var16 = enc;
                    return var16;
                } catch (IOException var24) {
                    var24.printStackTrace();
                } finally {
                    try {
                        dis.close();
                    } catch (IOException var23) {
                        var23.printStackTrace();
                    }

                }

                return null;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static byte[] sign(byte[] privateKey, byte[] sourceData) {
        String userId = "1234567812345678";
        return sign( userId.getBytes(), privateKey, sourceData );
    }

    public static byte[] sign(byte[] userId, byte[] privateKey, byte[] sourceData) {
        if (privateKey != null && privateKey.length != 0) {
            if (sourceData != null && sourceData.length != 0) {
                SM2 sm2 = SM2.Instance();
                BigInteger userD = new BigInteger( privateKey );
                System.out.println( "userD: " + userD.toString( 16 ) );
                System.out.println( "" );
                ECPoint userKey = sm2.ecc_point_g.multiply( userD );
                System.out.println( "椭圆曲线点X: " + userKey.getX().toBigInteger().toString( 16 ) );
                System.out.println( "椭圆曲线点Y: " + userKey.getY().toBigInteger().toString( 16 ) );
                System.out.println( "" );
                SM3Digest sm3 = new SM3Digest();
                byte[] z = sm2.sm2GetZ( userId, userKey );
                System.out.println( "SM3摘要Z: " + ByteUtil.getHexString( z ) );
                System.out.println( "" );
                System.out.println( "M: " + ByteUtil.getHexString( sourceData ) );
                System.out.println( "" );
                sm3.update( z, 0, z.length );
                sm3.update( sourceData, 0, sourceData.length );
                byte[] md = new byte[32];
                sm3.doFinal( md, 0 );
                System.out.println( "SM3摘要值: " + ByteUtil.getHexString( md ) );
                System.out.println( "" );
                SM2Result sm2Result = new SM2Result();
                sm2.sm2Sign( md, userD, userKey, sm2Result );
                System.out.println( "r: " + sm2Result.r.toString( 16 ) );
                System.out.println( "s: " + sm2Result.s.toString( 16 ) );
                System.out.println( "" );
                DERInteger d_r = new DERInteger( sm2Result.r );
                DERInteger d_s = new DERInteger( sm2Result.s );
                ASN1EncodableVector v2 = new ASN1EncodableVector();
                v2.add( d_r );
                v2.add( d_s );
                DERSequence sign = new DERSequence( v2 );
                //return sign.getDEREncoded();
                try {
                    return sign.getEncoded();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                return null;
            }
        }
        return null;
    }

    public static boolean verifySign(byte[] publicKey, byte[] sourceData, byte[] signData) {
        String userId = "1234567812345678";
        return verifySign( userId.getBytes(), publicKey, sourceData, signData );
    }

    public static boolean verifySign(byte[] userId, byte[] publicKey, byte[] sourceData, byte[] signData) {
        if (publicKey != null && publicKey.length != 0) {
            if (sourceData != null && sourceData.length != 0) {
                byte[] formatedPubKey;
                if (publicKey.length == 64) {
                    formatedPubKey = new byte[65];
                    formatedPubKey[0] = 4;
                    System.arraycopy( publicKey, 0, formatedPubKey, 1, publicKey.length );
                } else {
                    formatedPubKey = publicKey;
                }

                SM2 sm2 = SM2.Instance();
                ECPoint userKey = sm2.ecc_curve.decodePoint( formatedPubKey );
                SM3Digest sm3 = new SM3Digest();
                byte[] z = sm2.sm2GetZ( userId, userKey );
                sm3.update( z, 0, z.length );
                sm3.update( sourceData, 0, sourceData.length );
                byte[] md = new byte[32];
                sm3.doFinal( md, 0 );
                System.out.println( "SM3摘要值: " + ByteUtil.getHexString( md ) );
                System.out.println( "" );
                ByteArrayInputStream bis = new ByteArrayInputStream( signData );
                ASN1InputStream dis = new ASN1InputStream( bis );
                SM2Result sm2Result = null;

                try {
                    ASN1Sequence derObj = (ASN1Sequence) dis.readObject();
                    Enumeration<DERInteger> e = derObj.getObjects();
                    BigInteger r = ((DERInteger) e.nextElement()).getValue();
                    BigInteger s = ((DERInteger) e.nextElement()).getValue();
                    sm2Result = new SM2Result();
                    sm2Result.r = r;
                    sm2Result.s = s;
                    System.out.println( "r: " + sm2Result.r.toString( 16 ) );
                    System.out.println( "s: " + sm2Result.s.toString( 16 ) );
                    System.out.println( "" );
                    sm2.sm2Verify( md, userKey, sm2Result.r, sm2Result.s, sm2Result );
                    boolean var18 = sm2Result.r.equals( sm2Result.R );
                    return var18;
                } catch (IOException var26) {
                    var26.printStackTrace();
                } finally {
                    try {
                        dis.close();
                    } catch (IOException var25) {
                        var25.printStackTrace();
                    }

                }

                return false;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        Sm2KeyPair sm2 = generateKeyPair();
        String pubKey = ByteUtil.getHexString( sm2.getPubKey() );
        String priKey = ByteUtil.getHexString( sm2.getPriKey() );
        String plainText = "哈哈哈哈哈你好";
        byte[] sourceData = plainText.getBytes();
        String prikS = new String( Base64.encode( ByteUtil.hexToByte( priKey ) ) );
        System.out.println( "prikS: " + prikS );
        System.out.println( priKey.length() );
        String pubkS = new String( Base64.encode( ByteUtil.hexToByte( pubKey ) ) );
        System.out.println( "pubkS: " + pubkS );
        System.out.println( "" );
        System.out.println( "加密: " );
        byte[] cipherText = encrypt( Base64.decode( pubkS.getBytes() ), sourceData );
        System.out.println( new String( Base64.encode( cipherText ) ) );
        System.out.println( "" );
        System.out.println( "解密: " );
        plainText = new String( decrypt( Base64.decode( prikS.getBytes() ), cipherText ) );
        System.out.println( plainText );
    }

    public static Sm2KeyPair generateKeyPair() {
        SM2 sm2 = SM2.Instance();
        AsymmetricCipherKeyPair keypair = sm2.ecc_key_pair_generator.generateKeyPair();
        ECPrivateKeyParameters ecpriv = (ECPrivateKeyParameters) keypair.getPrivate();
        ECPublicKeyParameters ecpub = (ECPublicKeyParameters) keypair.getPublic();
        byte[] priKey = new byte[32];
        byte[] pubKey = new byte[64];
        byte[] bigNumArray = ecpriv.getD().toByteArray();
        System.arraycopy( bigNumArray, bigNumArray[0] == 0 ? 1 : 0, priKey, 0, 32 );
        System.arraycopy( ecpub.getQ().getEncoded(), 1, pubKey, 0, 64 );
        return new Sm2KeyPair( priKey, pubKey );
    }

    public static void main() {
        String plainText = "Hello SM !";
        byte[] sourceData = plainText.getBytes();
        String prik = "444E6EA3EE0C7E0AAA5EE5C6BBC7A2D8DE3FB3FA990AD470232D07FB445F92D7";
        byte[] c = sign( ByteUtil.hexToByte( prik ), sourceData );
        System.out.println( "sign: " + ByteUtil.getHexString( c ) );
        String pubk = "2E9173C4DB1DB0B22980DD3235ABF99B787DE8E5C6D08BDBA4503D61EE2B32F0F7083CC46D92DAE72FD0223305D0B44A95D438142C45382B23B2A58122E1F3DF";
        boolean vs = verifySign( ByteUtil.hexToByte( pubk ), sourceData, c );
        System.out.println( "验签结果: " + vs );
        System.out.println( "加密: " );
        byte[] cipherText = encrypt( ByteUtil.hexToByte( pubk ), sourceData );
        System.out.println( ByteUtil.getHexString( cipherText ) );
        System.out.println( "解密: " );
        plainText = new String( decrypt( ByteUtil.hexToByte( prik ), cipherText ) );
        System.out.println( plainText );
    }

    public static void Sm2Test() {
        String plainText = "Hello SM !";
        byte[] sourceData = plainText.getBytes();
        Sm2KeyPair keyPair = generateKeyPair();
        System.out.println( "私钥: " + ByteUtil.getHexString( keyPair.getPriKey() ) );
        System.out.println( "公钥: " + ByteUtil.getHexString( keyPair.getPubKey() ) );
        byte[] c = sign( keyPair.getPriKey(), sourceData );
        System.out.println( "sign: " + ByteUtil.getHexString( c ) );
        boolean vs = verifySign( keyPair.getPubKey(), sourceData, c );
        System.out.println( "验签结果: " + vs );
        System.out.println( "加密: " );
        byte[] cipherText = encrypt( keyPair.getPubKey(), sourceData );
        System.out.println( ByteUtil.getHexString( cipherText ) );
        System.out.println( "解密: " );
        plainText = new String( decrypt( keyPair.getPriKey(), cipherText ) );
        System.out.println( plainText );
    }
}
