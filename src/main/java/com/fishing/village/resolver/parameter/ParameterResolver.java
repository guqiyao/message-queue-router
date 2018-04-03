package com.fishing.village.resolver.parameter;

import com.fishing.village.message.Message;

import java.lang.reflect.Method;

/**
 * @author qiyao.gu@qq.com.
 */
public interface ParameterResolver {

    /**
     * 请求参数解析器
     * @param handler   handler
     * @param message   消息体
     * @return          参数列表
     */
    Object[] resolve(Method handler, Message message);
}