package com.mo9.message.router.util;

/**
 * @author qiyao.gu@qq.com.
 */
public final class MessageRouterUtils {

    private MessageRouterUtils() {
    }

    public static String generateReceiverKey(String topic, String tag) {
        return String.format("%s:%s", topic, tag);
    }
}