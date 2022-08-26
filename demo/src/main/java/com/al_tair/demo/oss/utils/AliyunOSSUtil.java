package com.al_tair.demo.oss.utils;

import com.al_tair.demo.oss.constant.OssConstant;
import com.aliyun.oss.*;
import com.aliyun.oss.model.ObjectMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Al_tair
 * @date 2022/7/28-9:49
 */
public class AliyunOSSUtil {
    private static final String accessKeyId= OssConstant.OSS_FILE_KEY_ID;
    private static final String accessKeySecret=OssConstant.OSS_FILE_KEY_SECRCT;
    private static final String endpoint =  OssConstant.OSS_FILE_ENDPOINT;
    private static final String bucketName = OssConstant.OSS_FILE_BUCKET_NAME;

    private static final Logger logger = LoggerFactory.getLogger(AliyunOSSUtil.class);

    public static String uploadAliyun(MultipartFile file, String fileName) throws IOException {
        //外面获取文件输入流，最后方便关闭
        InputStream in = file.getInputStream();
        try {
            //2 创建OssClient对象
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
            //3 获取文件信息，为了上传
            // meta设置请求头
            ObjectMetadata meta = new ObjectMetadata();
            meta.setContentType("image/jpg");
            //4 设置知道文件夹
            ossClient.putObject(bucketName, fileName, in, meta);
            //5 关闭ossClient
            ossClient.shutdown();
            //6 返回上传之后地址，拼接地址
            String uploadUrl = "https://" + bucketName + "." + endpoint + "/" + fileName;
            return uploadUrl;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            in.close();
        }
    }
}

