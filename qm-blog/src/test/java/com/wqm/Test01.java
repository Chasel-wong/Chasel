package com.wqm;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import com.wqm.utils.AliOssPropertiesUtils;
import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@SpringBootTest
@ConfigurationProperties(prefix = "oss")
public class Test01 {

    private String accessKey;
    private String secretKey;
    private String bucket;
    private String endpoint;
    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }
    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
   @Test
    public void test(){
       OSS oss = new OSSClientBuilder().build(endpoint,accessKey,secretKey);

       try {
           InputStream inputStream = new FileInputStream("D:\\note\\pic\\QQ图片20230719172324.jpg");
           String name = "2023";
           String uuid  = UUID.randomUUID().toString().replaceAll("-","");
           name =  uuid + name;
           String dataPath = new DateTime().toString();
           name = dataPath + "/" + name;
           oss.putObject(bucket,name,inputStream);

       } catch (FileNotFoundException fileNotFoundException) {
           fileNotFoundException.printStackTrace();
       }



    }

}
