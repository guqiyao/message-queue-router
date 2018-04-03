package com.fishing.village;

import com.fishing.village.message.Message;
import com.fishing.village.resolver.placeholder.PlaceholderResolver;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qiyao.gu@qq.com.
 */
public class MessageQueueRouterTest {

    private MessageInvoker messageInvoker;

    @Before
    public void before() {
        Consumer consumer = new Consumer();
        PlaceholderResolver placeholderResolver = new StringPlaceholderResolver();
        ConsumerContainer consumerContainer = new ConsumerContainer();
        consumerContainer.setPlaceholderResolver(placeholderResolver);

        List<Object> consumers = new ArrayList<>();
        consumers.add(consumer);

        consumerContainer.register(consumers);

        messageInvoker = new MessageInvoker(consumerContainer);
    }

    @Test
    public void testInvoke() {
        Message message = new TestMessage();

        messageInvoker.invoke(message);
    }

    private class TestMessage implements Message {

        @Override
        public String getTopic() {
            return "test.topic";
        }

        @Override
        public String getBody() {
            return "{\"name\" : \"guqiyao\", \"age\" : \"25\"}";
        }

        @Override
        public String getMessageId() {
            return "messageId";
        }

        @Override
        public String getTag() {
            return "test.tag";
        }

        @Override
        public String getKey() {
            return "key";
        }
    }
}