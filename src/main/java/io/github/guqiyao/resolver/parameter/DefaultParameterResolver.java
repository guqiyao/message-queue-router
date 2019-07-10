package io.github.guqiyao.resolver.parameter;

import io.github.guqiyao.annotation.MessageBody;
import io.github.guqiyao.exception.ParameterInjectException;
import io.github.guqiyao.message.Message;
import io.github.guqiyao.util.JSONUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Parameter;
import java.util.Objects;

/**
 * @author qiyao.gu@qq.com.
 */
@Slf4j
public class DefaultParameterResolver extends AbstractParameterResolver {

    private static final DefaultParameterResolver DEFAULT_PARAMETER_RESOLVER = new DefaultParameterResolver();

    private DefaultParameterResolver() {
    }

    public static DefaultParameterResolver getInstance() {
        return DEFAULT_PARAMETER_RESOLVER;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Object[] resolve(Parameter[] parameterTypes, Message message) {
        int parameterLength = parameterTypes.length;

        Object[] parameters = new Object[parameterLength];

        try {
            for (int i = 0; i < parameterLength; i ++) {
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
            throw new ParameterInjectException("参数注入失败", e);
        }
    }

}