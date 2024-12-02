package com.samzhou.config;

import com.samzhou.utils.AliOssUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AliyunConfig {

    @Value("${aliyun.ACCESS_KEY_ID}")
    private String accessKeyId;

    @Value("${aliyun.SECRET_ACCESS_KEY}")
    private String accessKeySecret;

    @Value("${aliyun.endPoint}")
    private String endPoint;

    @PostConstruct
    public void setValue() {
        AliOssUtil.ACCESS_KEY_ID = accessKeyId;
        AliOssUtil.SECRET_ACCESS_KEY = accessKeySecret;
        AliOssUtil.ENDPOINT = endPoint;
    }

}
