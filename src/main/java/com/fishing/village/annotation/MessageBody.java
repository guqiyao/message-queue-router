package com.fishing.village.annotation;

import java.lang.annotation.*;

/**
 * @author qiyao.gu@qq.com.
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageBody {
}