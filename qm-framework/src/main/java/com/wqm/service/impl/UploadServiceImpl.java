package com.wqm.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectResult;
import com.wqm.result.ResponseResult;
import com.wqm.service.UploadService;
import com.wqm.utils.AliOssPropertiesUtils;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Service
public class UploadServiceImpl implements UploadService {
    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        String endpoint = AliOssPropertiesUtils.END_POIND;
        String accessKeyId = AliOssPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = AliOssPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = AliOssPropertiesUtils.BUCKET_NAME;
        OSS oss = new OSSClientBuilder().build(endpoint,accessKeyId,accessKeySecret);
        try {
            InputStream inputStream = img.getInputStream();
            String name = img.getOriginalFilename();
            String uuid  = UUID.randomUUID().toString().replaceAll("-","");
            name =  uuid + name;
            String dataPath = new DateTime().toString();
            name = dataPath + "/" + name;
            oss.putObject(bucketName, name, inputStream);
            oss.shutdown();
            String filename = URLEncoder.encode(name, StandardCharsets.UTF_8.toString());
            filename = filename.replace("+", "%20");
            String url ="https://"+bucketName+"."+endpoint+"/"+filename;
            return ResponseResult.okResult(url);
        } catch (IOException e) {
            e.printStackTrace();
            return  null;
        }
    }
}
