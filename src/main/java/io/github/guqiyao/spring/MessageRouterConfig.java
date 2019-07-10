package io.github.guqiyao.spring;

import io.github.guqiyao.ConsumerContainer;
import io.github.guqiyao.MessageInvoker;
import io.github.guqiyao.annotation.MessageRouter;
import io.github.guqiyao.component.target.DefaultTargetDecoratorFactory;
import io.github.guqiyao.resolver.placeholder.PlaceholderResolver;
import io.github.guqiyao.resolver.placeholder.SpringPropertyResolver;
import io.github.guqiyao.rocket.AbstractMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author qiyao.gu@qq.com.
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = MessageRouterConfigBean.PREFIX, name = "enabled", havingValue = "true")
public class MessageRouterConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ConfigurableEnvironment configurableEnvironment;

    @PostConstruct
    public void after() {
        final MessageInvoker messageInvoker = messageInvoker();

        Map<String, AbstractMessageListener> messageListeners = applicationContext.getBeansOfType(AbstractMessageListener.class);

        messageListeners.values().forEach(messageListener -> messageListener.setMessageInvoker(messageInvoker));
    }

    private MessageInvoker messageInvoker() {
        return new MessageInvoker(createConsumerContainer());
    }

    /**
     * 创建容器
     * @return      consumer container
     */
    private ConsumerContainer createConsumerContainer() {
        ConsumerContainer consumerContainer = new ConsumerContainer();

        consumerContainer.setTargetDecoratorFactory(new DefaultTargetDecoratorFactory());
        consumerContainer.setPlaceholderResolver(getPlaceholderResolver());
        consumerContainer.register(getCommands());

        return consumerContainer;
    }

    /**
     * 获取commands
     * @return  commands
     */
    private List<Object> getCommands() {
        Map<String, Object> map = applicationContext.getBeansWithAnnotation(MessageRouter.class);
        return new ArrayList<>(map.values());
    }

    /**
     * 获取placeholder resolver
     * @return  placeholder resolver
     */
    private PlaceholderResolver getPlaceholderResolver() {
        PlaceholderResolver placeholderResolver;

        try {
            placeholderResolver = applicationContext.getBean(PlaceholderResolver.class);
        } catch (BeansException e) {
            log.warn("获取用户配置的 PlaceholderResolver 失败, 即将返回默认的 PlaceholderResolver");
            placeholderResolver = createDefaultPlaceholderResolver();
        }

        return placeholderResolver;
    }

    /**
     * 创建默认的 placeholder resolver
     * @return  default placeholder resolver
     */
    private PlaceholderResolver createDefaultPlaceholderResolver() {
        return new SpringPropertyResolver(configurableEnvironment);
    }
}