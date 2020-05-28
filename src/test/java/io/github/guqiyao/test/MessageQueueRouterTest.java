package io.github.guqiyao.test;

import io.github.guqiyao.ConsumerBeanHolder;
import io.github.guqiyao.ConsumerContainer;
import io.github.guqiyao.MessageInvoker;
import io.github.guqiyao.component.target.DefaultTargetDecoratorFactory;
import io.github.guqiyao.message.Message;
import io.github.guqiyao.resolver.placeholder.PlaceholderResolver;
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
        consumerContainer.setTargetDecoratorFactory(new DefaultTargetDecoratorFactory());

        List<ConsumerBeanHolder> consumers = new ArrayList<>();
        consumers.add(new ConsumerBeanHolder(consumer));

        consumerContainer.register(consumers);

        messageInvoker = new MessageInvoker(true, consumerContainer);
    }

    @Test
    public void testInvoke() {
        Message message = new TestMessage();

//        messageInvoker.invoke(message);
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