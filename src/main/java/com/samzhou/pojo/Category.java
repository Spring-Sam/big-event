package com.samzhou.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.groups.Default;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Category {

    @NotNull(groups =Update.class)
    private Integer id;//主键ID

    //@NotEmpty(groups = {Add.class,Update.class})
    @NotEmpty
    private String categoryName;//分类名称

    //@NotEmpty(groups = {Add.class,Update.class}) 默认是default，下面集成default
    @NotEmpty
    private String categoryAlias;//分类别名

    private Integer createUser;//创建人ID

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;//更新时间


    //区分validate校验分组
    public interface Add extends Default {

    }

    public interface Update extends Default {}





}
