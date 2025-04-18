package com.wqm.controller;

import com.wqm.result.ResponseResult;
import com.wqm.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
public class UploadController {
    @Autowired
    private UploadService uploadService;
    @PostMapping("/upload")
    public ResponseResult uploadImg(MultipartFile img){

        try {
            return uploadService.uploadImg(img);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("文件上传上传失败");
        }

    }
}

