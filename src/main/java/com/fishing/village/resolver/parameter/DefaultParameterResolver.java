package com.fishing.village.resolver.parameter;

import com.fishing.village.annotation.MessageBody;
import com.fishing.village.exception.MessageRouterException;
import com.fishing.village.message.Message;
import com.fishing.village.util.JSONUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Objects;

/**
 * @author qiyao.gu@qq.com.
 */
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
        if (parameterTypes.length == 0) {
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
                }

                parameters[i] = parameter;

            }
            return parameters;
        } catch (Exception e) {
            throw new MessageRouterException("参数注入失败", e);
        }

    }
}