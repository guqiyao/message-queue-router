package com.mo9.message.router;

import com.mo9.message.router.component.target.TargetDecorator;
import com.mo9.message.router.exception.MessageRouterException;
import com.mo9.message.router.message.Message;
import com.mo9.message.router.resolver.parameter.DefaultParameterResolver;

import java.lang.reflect.Method;

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
            throw new MessageRouterException(errorMessage, e);
        }
    }
}