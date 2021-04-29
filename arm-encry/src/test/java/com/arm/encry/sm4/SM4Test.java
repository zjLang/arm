package com.arm.encry.sm4;

import com.arm.encry.Encry;
import com.arm.encry.base.ByteUtil;
import com.arm.encry.base.FileUtil;
import com.arm.encry.sm2.SM2Utils;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.ZipFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author zhaolangjing
 * @since 2021-3-24 10:32
 */
@Slf4j
public class SM4Test {
    static final String file = "E:\\2.资料\\2.公司资料\\1.在建项目\\15.国资委数据上报\\test文件\\file_e843c30783e64e0a8972e354a6d06b0f.zip";
    static final String sm4SrcKey = "80ecf4b6fc704248aa3581872178e394";
    static final String toFile = "E:\\2.资料\\2.公司资料\\1.在建项目\\15.国资委数据上报\\test文件\\toFile.zip";

    public static void main(String[] args) throws IOException {
        //test1();
        Encry.sm4( sm4SrcKey, file, toFile );
        File packageFile = Encry.unZip( toFile );

        // 获取sm2密钥
        String sm2Key = FileUtil.readFile( packageFile + File.separator + "A0001.key" );

        // 再次解压 115100007650616494_0037_1002_20210226154242.zip
        List<File> zip = FileUtil.getFile( packageFile, "zip" );
        packageFile = Encry.unZip( zip.get( 0 ).getPath() );
        String keyPath = packageFile + File.separator + packageFile.getName() + ".key";
        Encry.sm2( sm2Key, keyPath, keyPath );

        // 拿到用sm2解压得到的值再去解压里面的zip并转成db文件
        String sm4Key = FileUtil.readFile( keyPath );
        String zipPath = packageFile + File.separator + packageFile.getName() + ".zip";
        Encry.sm4( sm4Key, zipPath, zipPath.replace( "zip", "db" ) );

    }

    private static void test1() throws IOException {
        // 1.sm4解压第一层，得到三个文件：
        // 15100007650616494_0037_1002_20210226154242.zip
        // A0001.key
        // A0002.key
        SM4Utils sm4 = new SM4Utils();
        sm4.setSecretKey( sm4SrcKey );//上次下发的sm4秘钥
        sm4.setHexString( false );
        byte[] fileByte = ByteUtil.getBytes( file );
        byte[] decryptFileByte = sm4.decryptData_ECB( fileByte );
        // 解密文件得到解密后的zip文件
        ByteUtil.getFile( decryptFileByte, toFile );
        File file = new File( toFile );

        // 2.解压文件
        ZipFile zipFile = new ZipFile( toFile );
        String targetFile = file.getParentFile() + File.separator + file.getName().replace( ".zip", "" );
        zipFile.extractAll( targetFile );

        String sm2_prikey = FileUtil.readFile( targetFile + File.separator + "A0001.key" );

        // 3.解压toFile中的 115100007650616494_0037_1002_20210226154242.zip 文件
        List<File> zip = FileUtil.getFile( targetFile, "zip" );
        zipFile = new ZipFile( zip.get( 0 ) );
        targetFile = zip.get( 0 ).getPath().replace( ".zip", "" );
        zipFile.extractAll( targetFile );

        // 3.获取 A0001Key 私钥
        byte[] file2byte = ByteUtil.getBytes( targetFile + File.separator + zip.get( 0 ).getName().replace( "zip", "key" ) );
        SM2Utils sm2 = new SM2Utils();
        byte[] sm4KeyByte = sm2.decrypt( ByteUtil.hexToByte( sm2_prikey ), file2byte );

        // 4.对 A0001.key 解密出来的key为sm4密码，再用该密码解密db文件
        sm4.setSecretKey( new String( sm4KeyByte ) );
        sm4.setHexString( false );
        fileByte = ByteUtil.getBytes( targetFile + File.separator + zip.get( 0 ).getName() );
        byte[] finalSm4 = sm4.decryptData_ECB( fileByte );

        ByteUtil.getFile( finalSm4, targetFile + File.separator + zip.get( 0 ).getName().replace( "zip", "db" ) );
    }


}
