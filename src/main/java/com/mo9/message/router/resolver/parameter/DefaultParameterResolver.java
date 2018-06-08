package com.mo9.message.router.resolver.parameter;

import com.mo9.message.router.annotation.MessageBody;
import com.mo9.message.router.exception.MessageRouterException;
import com.mo9.message.router.message.Message;
import com.mo9.message.router.util.JSONUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Objects;

/**
 * @author qiyao.gu@qq.com.
 */
@Slf4j
public class DefaultParameterResolver implements ParameterResolver {

    private static final Object[] EMPTY_PARAMETER = new Object[0];

    private static final DefaultParameterResolver DEFAULT_PARAMETER_RESOLVER = new DefaultParameterResolver();

    private DefaultParameterResolver() {
    }

    public static DefaultParameterResolver getInstance() {
        return DEFAULT_PARAMETER_RESOLVER;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object[] resolve(Method handler, Message message) {
        Parameter[] parameterTypes = handler.getParameters();
        if (isEmpty(parameterTypes)) {
            return EMPTY_PARAMETER;
        }

        Object[] parameters = new Object[parameterTypes.length];

        try {
            for (int i = 0; i < parameterTypes.length; i ++) {
                Parameter parameterType = parameterTypes[i];
                Class parameterClazz = parameterType.getType();
                Object parameter = null;

                MessageBody messageBody = parameterType.getDeclaredAnnotation(MessageBody.class);
                if (Objects.nonNull(messageBody)) {
                    parameter = JSONUtils.toBean(message.getBody(), parameterClazz);
                } else if (parameterClazz.isAssignableFrom(Message.class)) {
                    parameter = message;
                } else {
                    log.warn("当前参数未匹配到对应的消息数据, parameter name : [{}]", parameterClazz.getSimpleName());
                }

                parameters[i] = parameter;

            }
            return parameters;
        } catch (Exception e) {
            throw new MessageRouterException("参数注入失败", e);
        }

    }

    private boolean isEmpty(Parameter[] parameters) {
        return parameters.length == 0;
    }
}