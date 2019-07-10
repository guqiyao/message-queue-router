package io.github.guqiyao;

import io.github.guqiyao.exception.NotFoundReceiverException;
import io.github.guqiyao.message.Message;

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
            throw new NotFoundReceiverException(String.format("未获取到对应的消息处理器, 当前topic : %s, tag : %s",
                    message.getTopic(), message.getTag()));
        }

        return receiver.execute(message);
    }
}