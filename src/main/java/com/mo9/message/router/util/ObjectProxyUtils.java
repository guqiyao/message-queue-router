package com.mo9.message.router.util;

import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.Field;

/**
 * @author qiyao.gu@qq.com.
 */
public final class ObjectProxyUtils {

    private static final String CGLIB_ADVISED_FIELD_NAME = "advised";
    private static final String CGLIB_DECLARED_FIELD_NAME = "CGLIB$CALLBACK_0";

    private ObjectProxyUtils() {
    }

    public static Object getTarget(Object object) {
        if (!AopUtils.isAopProxy(object)) {
            return object;
        }

        if (AopUtils.isCglibProxy(object)) {
            try {
                Field field = object.getClass().getDeclaredField(CGLIB_DECLARED_FIELD_NAME);
                field.setAccessible(true);

                Object dynamicAdvisedInterceptor = field.get(object);

                Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField(CGLIB_ADVISED_FIELD_NAME);
                advised.setAccessible(true);

                return ((AdvisedSupport) advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();
            } catch (Exception e) {
                throw new NoSuchTargetException(String.format("未找到对应的原型对象, class name : %s", object.getClass().getName()), e);
            }
        }

        throw new NoSuchTargetException(String.format("未找到对应的原型对象, class name : %s", object.getClass().getName()));
    }

    private static class NoSuchTargetException extends RuntimeException {

        NoSuchTargetException(String message) {
            super(message);
        }

        NoSuchTargetException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}