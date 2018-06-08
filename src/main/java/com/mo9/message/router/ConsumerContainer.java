package com.mo9.message.router;

import com.mo9.message.router.annotation.MessageRouter;
import com.mo9.message.router.annotation.MessageTag;
import com.mo9.message.router.component.target.TargetDecoratorFactory;
import com.mo9.message.router.message.Message;
import com.mo9.message.router.resolver.placeholder.PlaceholderResolver;
import com.mo9.message.router.util.MessageRouterUtils;
import com.mo9.message.router.util.ObjectProxyUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author qiyao.gu@qq.com.
 */
@Slf4j
public class ConsumerContainer {

    private PlaceholderResolver placeholderResolver;

    private TargetDecoratorFactory targetDecoratorFactory;

    private ConcurrentHashMap<String, Receiver> receiverMaps = new ConcurrentHashMap<>();

    public void register(List<Object> commands) {
        commands.forEach(this::register);
    }

    private void register(Object command) {
        Object target = ObjectProxyUtils.getTarget(command);

        Class commandClass = target.getClass();

        MessageRouter router = (MessageRouter) commandClass.getAnnotation(MessageRouter.class);

        String topicPlaceholder = router.topic();

        String topic = placeholderResolver.get(topicPlaceholder);

        Method[] methods = commandClass.getDeclaredMethods();

        for (Method method : methods) {
            String methodName = method.getName();
            boolean isInstance = isInstance(method);
            if (!isInstance) {
                log.warn("当前方法非实例方法, method name : [{}]", methodName);
                continue;
            }

            String receiverName = commandClass.getSimpleName();

            MessageTag tag = method.getAnnotation(MessageTag.class);
            if (Objects.isNull(tag)) {
                log.warn("当前method未配置MessageTag注解, receiver name : [{}], method name : [{}]",
                        receiverName, methodName);
                continue;
            }

            String tagValue = placeholderResolver.get(tag.value());

            Receiver receiver = new Receiver(targetDecoratorFactory.create(command), method);
            String key = MessageRouterUtils.generateReceiverKey(topic, tagValue);

            log.info("新增receiver, receiver name : [{}], method name : [{}], topic : [{}], tag : [{}]",
                    receiverName, methodName, topic, tagValue);

            receiverMaps.put(key, receiver);
        }
    }

    public void setPlaceholderResolver(PlaceholderResolver placeholderResolver) {
        this.placeholderResolver = placeholderResolver;
    }

    public void setTargetDecoratorFactory(TargetDecoratorFactory targetDecoratorFactory) {
        this.targetDecoratorFactory = targetDecoratorFactory;
    }

    Receiver getReceiver(Message message) {
        String key = MessageRouterUtils.generateReceiverKey(message.getTopic(), message.getTag());

        return receiverMaps.get(key);
    }

    private boolean isInstance(Method method) {
        int methodModifiers = Modifier.methodModifiers();

        return (methodModifiers | method.getModifiers()) <= methodModifiers;
    }

}