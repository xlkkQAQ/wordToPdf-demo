package com.xlkk.wordtopdf.utils;

import com.alibaba.fastjson.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Component
public class QiniuUtils {
    public static final String url = "http://img.xlkk.xyz/";

    //@Value("${qiniu.accessKey}")
    private static String accessKey = "C2ahYnFR0y8W2HQtvmj1iJCTSEgA5nGDMzaT2NGG";
    //@Value("${qiniu.accessSecretKey}")
    private static String accessSecretKey = "EJ8Gn0MunHGF8-LWQjkRfYVY3vaoGNbkQxAcs5gk";

    public  static String upload(MultipartFile file, String targetAddr){

        //生成随机文件名
        String fileName = String.valueOf(UUID.randomUUID());
        // 获取文件的扩展名如png,jpg等
        String extension = getFileExtension(Objects.requireNonNull(file.getOriginalFilename()));
        // 获取文件存储的相对路径(带文件名)
        String relativeAddr = targetAddr + fileName + extension;
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huanan());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String bucket = "xlkk";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        try {
            byte[] uploadBytes = file.getBytes();
            Auth auth = Auth.create(accessKey, accessSecretKey);
            String upToken = auth.uploadToken(bucket);
            Response response = uploadManager.put(uploadBytes, relativeAddr, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url + relativeAddr;
}

    public  static String upload(String targetAddr){
        //生成随机文件名
        String fileName = String.valueOf(UUID.randomUUID());
        // 获取文件的扩展名如png,jpg等
        String extension = ".pdf";
        // 获取文件存储的相对路径(带文件名)
        String relativeAddr = targetAddr + fileName + extension;
        Configuration cfg = new Configuration(Region.region0());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        String accessKey = "your access key";
        String secretKey = "your secret key";
        String bucket = "your bucket name";
        //如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "E:\\desktop\\1.docx";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return url + relativeAddr;
    }
    /**
     * 获取输入文件流的扩展名
     *
     * @param fileName
     * @return
     */
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    public static void delete(String key)  {
        Auth auth = Auth.create(accessKey, accessSecretKey);
        Configuration cfg = new Configuration(Region.huanan());
        String bucket = "xlkk";
        BucketManager bucketManager = new BucketManager(auth, cfg);

        try {
            bucketManager.delete(bucket, key);
        } catch (QiniuException e) {
            throw new RuntimeException(e);
        }
    }
}

