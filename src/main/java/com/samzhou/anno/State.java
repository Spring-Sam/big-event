package com.samzhou.anno;


import com.samzhou.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

//自定义校验
@Documented
@Target({ ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint( validatedBy = {StateValidation.class})
public @interface State {

    //校验失败的错误信息
    String message() default "state参数状态错误";

    //分组
    Class<?>[] groups() default {};

    //负载
    Class<? extends Payload>[] payload() default {};


}
