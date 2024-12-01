package com.samzhou.controller;

import com.samzhou.pojo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        //保证文件的名字唯一
        String uuidFilename = UUID.randomUUID().toString() + filename.substring(filename.lastIndexOf("."));
        file.transferTo(new File("D:\\data\\files\\" + uuidFilename));
        return Result.success("图片url：" + uuidFilename);


    }


}
