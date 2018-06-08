package com.mo9.message.router;

import com.mo9.message.router.exception.MessageRouterException;
import com.mo9.message.router.message.Message;

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
            throw new MessageRouterException(String.format("未获取到对应的消息处理器, 当前topic : %s, tag : %s",
                    message.getTopic(), message.getTag()));
        }

        return receiver.execute(message);
    }
}