package io.github.guqiyao;

import io.github.guqiyao.component.target.TargetDecorator;
import io.github.guqiyao.exception.MessageRouterException;
import io.github.guqiyao.message.Message;
import io.github.guqiyao.resolver.parameter.DefaultParameterResolver;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author qiyao.gu@qq.com.
 */
class Receiver {

    private TargetDecorator targetDecorator;

    private Method handler;

    Receiver(TargetDecorator targetDecorator, Method handler) {
        this.targetDecorator = targetDecorator;
        this.handler = handler;
    }

    Object execute(Message message) {
        try {
            Object[] parameters = DefaultParameterResolver.getInstance().resolve(handler, message);
            return handler.invoke(targetDecorator.getTarget(), parameters);
        } catch (Exception e) {
            String errorMessage = String.format("路由方法执行失败, message router : %s, method name : %s",
                    targetDecorator.getTarget().getClass().getSimpleName(), handler.getName());
            if (e instanceof InvocationTargetException) {
                Throwable targetException = ((InvocationTargetException) e).getTargetException();
                if (Objects.nonNull(targetException) && targetException instanceof Exception) {
                    e = (Exception) targetException;
                }
            }
            throw new MessageRouterException(errorMessage, e);
        }
    }
}