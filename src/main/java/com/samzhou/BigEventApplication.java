package com.samzhou;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@MapperScan("com.samzhou.mapper")
public class BigEventApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(BigEventApplication.class, args);
    }


}
