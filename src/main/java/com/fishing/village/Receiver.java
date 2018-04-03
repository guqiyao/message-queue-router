package com.fishing.village;

import com.fishing.village.exception.MessageRouterException;
import com.fishing.village.message.Message;
import com.fishing.village.resolver.parameter.DefaultParameterResolver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author qiyao.gu@qq.com.
 */
class Receiver {

    private Object target;

    private Method handler;

    Receiver(Object target, Method handler) {
        this.target = target;
        this.handler = handler;
    }

    Object execute(Message message) {
        Object[] parameters = DefaultParameterResolver.getInstance().resolve(handler, message);

        try {
            return handler.invoke(target, parameters);
        } catch (IllegalAccessException | InvocationTargetException e) {
            String errorMessage = String.format("路由方法执行失败, message router : %s, method name : %s",
                    target.getClass().getSimpleName(), handler.getName());
            throw new MessageRouterException(errorMessage, e);
        }
    }
}