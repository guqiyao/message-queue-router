package io.github.guqiyao.exception;

/**
 * @author qiyao.gu@qq.com.
 */
public class NotFoundReceiverException extends MessageRouterException {

    public NotFoundReceiverException(String message) {
        super(message);
    }
}