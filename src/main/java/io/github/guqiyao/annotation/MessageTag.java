package io.github.guqiyao.annotation;

import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.*;

/**
 * @author qiyao.gu@qq.com.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageTag {

    /**
     * tag
     * @return  tag
     */
    String value() default StringUtils.EMPTY;
}