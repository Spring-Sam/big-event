package com.samzhou.validation;

import jakarta.validation.ConstraintValidator;
import com.samzhou.anno.State;
import jakarta.validation.ConstraintValidatorContext;

public class StateValidation implements ConstraintValidator<State, String> {


    /**
     *
     * @param s 要校验的数据
     * @param constraintValidatorContext
     * @return true-通过 false-不通过
     */
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s==null){
            return false;
        }
        if(s.equals("已发布") || s.equals("草稿")){
            return true;
        }

        return false;
    }
}
