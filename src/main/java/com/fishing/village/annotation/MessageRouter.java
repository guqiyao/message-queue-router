package com.fishing.village.annotation;

import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.*;

/**
 * @author qiyao.gu@qq.com.
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageRouter {

    /**
     * topic
     * @return  topic
     */
    String topic() default StringUtils.EMPTY;
}