### I.MultipartFile.transferTo(File) 引起的"not a regular file"异常及文件无法删除

#### 1.问题描述：

在集成minio时，上传文件时，minio在调用Files.isRegularFile()方法时报出"not a regular file"错误。</br>
调用链如下：首先调用MultipartFile.transferTo(File)保存到本地文件,然后调用MinioClient.uploadObject()方法出错。</br>
在这MinioClient.uploadObject()中报出异常：not a regular file异常。

#### 2.问题分析：