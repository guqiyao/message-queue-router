package com.fishing.village;


import com.fishing.village.exception.MessageRouterException;
import com.fishing.village.message.Message;

import java.util.Objects;

/**
 * @author qiyao.gu@qq.com.
 */
public class MessageInvoker {

    private ConsumerContainer consumerContainer;

    public MessageInvoker(ConsumerContainer consumerContainer) {
        this.consumerContainer = consumerContainer;
    }

    public Object invoke(Message message) {
        Receiver receiver = consumerContainer.getReceiver(message);
        if (Objects.isNull(receiver)) {
            throw new MessageRouterException(String.format("未获取到对应的message router, 当前topic : %s, tag : %s",
                    message.getTopic(), message.getTag()));
        }

        return receiver.execute(message);
    }
}