package com.al_tair.demo.oss.constant;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Al_tair
 * @date 2022/7/28-9:40
 * 初始化 Bean对象的时候获取 OSS 相关配置文件
 */
@Component
public class OssConstant implements InitializingBean {
    @Value("${aliyun.oss.file.endPoint}")
    private String oss_file_endpoint;
    @Value("${aliyun.oss.file.keyid}")
    private String oss_file_keyid;

    @Value("${aliyun.oss.file.keysecret}")
    private String oss_file_keysecret;

    @Value("${aliyun.oss.file.bucketname}")
    private String oss_file_bucketname;

    public static String OSS_FILE_ENDPOINT;
    public static String OSS_FILE_KEY_ID;
    public static String OSS_FILE_KEY_SECRCT;
    public static String OSS_FILE_BUCKET_NAME;

    /**
     * 初始化方法
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        OSS_FILE_ENDPOINT = oss_file_endpoint;
        OSS_FILE_KEY_ID = oss_file_keyid;
        OSS_FILE_KEY_SECRCT = oss_file_keysecret;
        OSS_FILE_BUCKET_NAME = oss_file_bucketname;
    }
}

