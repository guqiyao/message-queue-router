package com.fishing.village;

import com.fishing.village.annotation.MessageRouter;
import com.fishing.village.annotation.MessageTag;
import com.fishing.village.message.Message;
import com.fishing.village.resolver.placeholder.PlaceholderResolver;
import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author qiyao.gu@qq.com.
 */
public final class ConsumerContainer {

    private PlaceholderResolver placeholderResolver;

    private ConcurrentHashMap<String, Receiver> receiverMaps = new ConcurrentHashMap<>();

    public void register(List<Object> commands) {
        for (Object command : commands) {
            Object target = getTarget(command);

            Class commandClass = target.getClass();

            MessageRouter router = (MessageRouter) commandClass.getAnnotation(MessageRouter.class);

            String topicPlaceholder = router.topic();

            String topic = placeholderResolver.get(topicPlaceholder);

            Method[] methods = commandClass.getDeclaredMethods();

            for (Method method : methods) {
                boolean isInstance = isInstance(method);
                if (!isInstance) {
                    continue;
                }

                MessageTag tag = method.getAnnotation(MessageTag.class);
                if (Objects.isNull(tag)) {
                    continue;
                }

                String tagValue = placeholderResolver.get(tag.value());

                Receiver receiver = new Receiver(command, method);
                String key = topic + "_" + tagValue;
                receiverMaps.put(key, receiver);
            }
        }

    }

    public void setPlaceholderResolver(PlaceholderResolver placeholderResolver) {
        this.placeholderResolver = placeholderResolver;
    }

    Receiver getReceiver(Message message) {
        String key = message.getTopic() + "_" + message.getTag();

        return receiverMaps.get(key);
    }

    private Object getTarget(Object object) {
        if (!AopUtils.isAopProxy(object)) {
            return object;
        }

        if (AopUtils.isCglibProxy(object)) {
            try {
                Field field = object.getClass().getDeclaredField("CGLIB$CALLBACK_0");
                field.setAccessible(true);

                Object dynamicAdvisedInterceptor = field.get(object);

                Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
                advised.setAccessible(true);

                return ((AdvisedSupport) advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        throw new RuntimeException();
    }

    private boolean isInstance(Method method) {
        int methodModifiers = Modifier.methodModifiers();

        return (methodModifiers | method.getModifiers()) <= methodModifiers;
    }

}