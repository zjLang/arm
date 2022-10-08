package com.arm.minio;

import io.minio.*;

/**
 * @author z-ewa
 */
public class Test {


    public static void main(String[] args) {
        try {
            /*MinioClient minioClient = MinioClient.builder()
                    .endpoint("http://172.16.9.126:9000").credentials("admin", "w1ses0ft").build();*/
            MinioClient minioClient = MinioClient.builder()
                    .endpoint("http://172.16.9.101:9000").credentials("wisesoft", "wisesoft").build();
            String bucketName = "minio.zlj";
            boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
            if (!found) {
                // 新建一个桶
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }

            // 1.本地磁盘的路径,绝对路径
            UploadObjectArgs build = UploadObjectArgs.builder().bucket(bucketName).object("moto.jpg").filename("/Users/zhaolangjing" +
                            "/Pictures/mac/《原神》不动鸣神 泡影断灭4k壁纸_彼岸图网.jpg")
                    //.filename("/Users/zhaolangjing/Pictures/其他图片/B3.png")
                    .build();
            ObjectWriteResponse objectWriteResponse = minioClient.uploadObject(build);
            System.out.println("上传成功");
            boolean test = minioClient.bucketExists(BucketExistsArgs.builder().bucket("test").build());
            System.out.println("bucket test exist:" + test);

            /*ObjectStat statObjectResponse = minioClient.statObject(StatObjectArgs.builder().bucket(bucketName).object("moto.jpg").build
            ());
            System.out.println(statObjectResponse);*/

            /*ObjectWriteResponse objectWriteResponse1 = minioClient.copyObject(CopyObjectArgs.builder().bucket("minio.zlj")
                    .object("moto1.jpg").source(CopySource.builder().bucket(
                            "minio.zlj").object("moto.jpg").build()).build());
            System.out.println(objectWriteResponse1);


            ObjectStat objectStat = minioClient.statObject(StatObjectArgs.builder().bucket("minio.zlj").object("moto1.jpg").build());
            System.out.println(objectStat);

            String objectUrl = minioClient.getObjectUrl("minio.zlj", "moto1.jpg");
            System.out.println(objectUrl);*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
