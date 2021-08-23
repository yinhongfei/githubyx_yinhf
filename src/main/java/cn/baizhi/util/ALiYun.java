package cn.baizhi.util;

import cn.baizhi.config.ALiYunConfig;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;

public class ALiYun {
    private static String endpoint = ALiYunConfig.ENDPOINT;
    private static String accessKeyId = ALiYunConfig.ACCESS_KEY_ID;
    private static String accessKeySecret = ALiYunConfig.ACCESS_KEY_SECRET;
    public static void uploadByBytes(MultipartFile file,String fileName) {
        //上传 （字节数组上传）
//        String endpoint = ALiYunConfig.ENDPOINT;
//        String accessKeyId = ALiYunConfig.ACCESS_KEY_ID;
//        String accessKeySecret = ALiYunConfig.ACCESS_KEY_SECRET;

        //创建OSSClient实例
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        byte[] content = null;
        try {
            content = file.getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        PutObjectRequest putObjectRequest = new PutObjectRequest("yinhf", fileName, new ByteArrayInputStream(content));
        ossClient.putObject(putObjectRequest);
        ossClient.shutdown();
    }
    //删除文件
    public static void deleteFile(String fileName){

        String bucketName = "yinhf";  //存储空间名
        String objectName = fileName;  //文件名

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 删除文件。如需删除文件夹，请将ObjectName设置为对应的文件夹名称。如果文件夹非空，则需要将文件夹下的所有object删除后才能删除该文件夹。
        ossClient.deleteObject(bucketName, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
    //文件的下载
    public static void download(String fileName){
        String bucketName = "yinhf";  //存储空间名
        String objectName = fileName;  //文件名
        String localFile="D:\\bb\\"+objectName;  //下载本地地址  地址加保存名字

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File(localFile));

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}