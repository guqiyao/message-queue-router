package io.github.guqiyao.exception;

/**
 * @author qiyao.gu@qq.com.
 */
public class ParameterInjectException extends MessageRouterException {

    public ParameterInjectException(String message) {
        super(message);
    }

    public ParameterInjectException(String message, Throwable cause) {
        super(message, cause);
    }
}