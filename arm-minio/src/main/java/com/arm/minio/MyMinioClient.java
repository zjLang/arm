package com.arm.minio;

import cn.hutool.core.io.FileUtil;
import io.minio.*;

import java.io.File;
import java.io.InputStream;

/**
 * @author z-ewa
 */
public class MyMinioClient {
    /**
     * 减少链接的创建
     */
    private final MinioClient minioClient;

    public MyMinioClient(String url, String accessKey, String secretKey) {
        minioClient = MinioClient.builder().endpoint(url).credentials(accessKey, secretKey).build();
    }

    public boolean saveOrUpdateFile(String bucketName, String key, File filePath) throws MyMinioException {
        try {
            createBucket(bucketName);
            minioClient.uploadObject(UploadObjectArgs.builder().bucket(bucketName).object(key).build());
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyMinioException("保存对象错误：bucketName" + bucketName + "，key：" + key + "fileName:" + filePath.getName());
        }
        return true;
    }

    public void deleteFile(String bucketName, String key) throws MyMinioException {
        try {
            createBucket(bucketName);
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(key).build());
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyMinioException("保存对象错误：bucketName" + bucketName + "，key：" + key);
        }
    }

    /**
     * 请注意关闭流
     *
     * @param bucketName
     * @param key
     * @return
     * @throws MyMinioException
     */
    public InputStream downloadFile(String bucketName, String key) throws MyMinioException {
        try {
            InputStream object = minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(key).build());

            FileUtil.writeFromStream(object, "/Users/zhaolangjing/Downloads/test/test.png");



            /*byte[] buff = new byte[1024];
            FastByteArrayOutputStream fbaos = new FastByteArrayOutputStream();
            int len = 0;
            while ((len = object.read(buff)) != -1) {
                fbaos.write(buff, 0, len);
            }
            fbaos.flush();
            // Create the file
            File file = new File("/Users/zhaolangjing/Downloads/test.png");
            if (!file.exists()) {
                file.createNewFile();
            }

            // Write the file
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(fbaos.toByteArray());
            outputStream.flush();
            fbaos.close();*/
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyMinioException("获取对象错误：bucketName" + bucketName + "，key：" + key);
        }
    }


    public boolean objectExist(String bucketName, String key) {
        try {
            minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object(key).build());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void createBucket(String bucket) throws MyMinioException {
        try {
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
            if (!found) {
                // 新建一个桶
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyMinioException("创建桶异常：" + bucket);
        }
    }

    public static class MyMinioException extends Exception {
        public MyMinioException(String message) {
            super(message);
        }
    }

    public static void main(String[] args) throws MyMinioException {
        //MyMinioClient myMinioClient = new MyMinioClient("http://43.154.8.2:9000", "admin", "w1ses0ft");
        MyMinioClient myMinioClient = new MyMinioClient("http://172.16.9.126:9000", "admin", "w1ses0ft");
        myMinioClient.downloadFile("minio.zlj", "moto.jpg");
    }
}
