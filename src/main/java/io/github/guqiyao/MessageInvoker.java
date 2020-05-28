package io.github.guqiyao;

import io.github.guqiyao.exception.NotFoundReceiverException;
import io.github.guqiyao.message.Message;

import java.util.Objects;

/**
 * @author qiyao.gu@qq.com.
 */
public class MessageInvoker {

    private boolean ignoreMismatch;
    private ConsumerContainer consumerContainer;

    public MessageInvoker(boolean ignoreMismatch, ConsumerContainer consumerContainer) {
        this.ignoreMismatch = ignoreMismatch;
        this.consumerContainer = consumerContainer;
    }

    InvokeResult invoke(Message message) {
        Receiver receiver = consumerContainer.getReceiver(message);
        if (Objects.isNull(receiver)) {

            if (ignoreMismatch) {
                return InvokeResult.IGNORE;
            }

            throw new NotFoundReceiverException(String.format("未获取到对应的消息处理器, 当前topic : %s, tag : %s",
                    message.getTopic(), message.getTag()));
        }

        receiver.execute(message);

        return InvokeResult.SUCCESS;
    }
}