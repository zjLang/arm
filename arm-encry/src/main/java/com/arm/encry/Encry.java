package com.arm.encry;

import com.arm.encry.sm2.SM2Utils;
import com.arm.encry.base.ByteUtil;
import com.arm.encry.sm4.SM4Utils;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

import java.io.File;
import java.util.List;

/**
 * @author zhaolangjing
 * @since 2021-3-24 16:10
 */
public class Encry {


    /**
     * sm4解密文件
     */
    public static void sm4(String key, String sourceFile, String targetFile) {
        SM4Utils sm4 = new SM4Utils();
        sm4.setSecretKey( key );
        sm4.setHexString( false );
        byte[] decryptFileByte = sm4.decryptData_ECB( ByteUtil.getBytes( sourceFile ) );
        ByteUtil.getFile( decryptFileByte, targetFile );
    }

    /**
     * sm2解密
     *
     * @param sm2Key
     * @param sourceFile
     * @param targetFile
     */
    public static void sm2(String sm2Key, String sourceFile, String targetFile) {
        byte[] file2byte = ByteUtil.getBytes( sourceFile );
        SM2Utils sm2 = new SM2Utils();
        byte[] sm4KeyByte = sm2.decrypt( ByteUtil.hexToByte( sm2Key ), file2byte );
        ByteUtil.getFile( sm4KeyByte, targetFile );
    }

    /**
     * 返回文件 package 路径
     *
     * @param fileName
     * @return
     * @throws ZipException
     */
    public static File unZip(String fileName) throws ZipException {
        File file = new File( fileName );
        ZipFile zipFile = new ZipFile( file );
        String targetFile = file.getParentFile() + File.separator + file.getName().replace( ".zip", "" );
        zipFile.extractAll( targetFile );
        return new File( targetFile );
    }


    class FileEntity {

        private File sourceFile;

        private String sourceFilePath;

        private List<File> childFiles;

        public FileEntity(String sourceFilePath) {
            this.sourceFilePath = sourceFilePath;
            sourceFile = new File( sourceFilePath );
            if (!(sourceFile.exists() && sourceFile.isFile())) {
                sourceFile = null;
            }
        }
    }

}
