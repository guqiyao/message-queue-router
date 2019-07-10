package io.github.guqiyao.resolver.parameter;

import io.github.guqiyao.message.Message;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author qiyao.gu@qq.com.
 */
public abstract class AbstractParameterResolver implements ParameterResolver {

    private static final Object[] EMPTY_PARAMETER = new Object[0];

    @Override
    public Object[] resolve(Method handler, Message message) {
        Parameter[] parameterTypes = handler.getParameters();
        if (isEmpty(parameterTypes)) {
            return EMPTY_PARAMETER;
        }

        return resolve(parameterTypes, message);
    }

    protected abstract Object[] resolve(Parameter[] parameterTypes, Message message);

    private boolean isEmpty(Parameter[] parameterTypes) {
        return parameterTypes.length == 0;
    }
}
