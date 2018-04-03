package com.fishing.village.spring;

import com.fishing.village.ConsumerContainer;
import com.fishing.village.MessageInvoker;
import com.fishing.village.annotation.MessageRouter;
import com.fishing.village.resolver.placeholder.PlaceholderResolver;
import com.fishing.village.resolver.placeholder.SpringPropertyResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author qiyao.gu@qq.com.
 */
@Configuration
public class MessageComponentConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageComponentConfig.class);

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public MessageInvoker messageInvoker() {
        return new MessageInvoker(createConsumerContainer());
    }

    /**
     * 创建容器
     * @return      consumer container
     */
    private ConsumerContainer createConsumerContainer() {
        ConsumerContainer consumerContainer = new ConsumerContainer();

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
        return map.entrySet().stream().map(Map.Entry::getValue).collect(Collectors.toList());
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
            LOGGER.warn("获取用户配置的 PlaceholderResolver 失败, 即将返回默认的 PlaceholderResolver");
            placeholderResolver = createDefaultPlaceholderResolver();
        }

        return placeholderResolver;
    }

    /**
     * 创建默认的 placeholder resolver
     * @return  default placeholder resolver
     */
    private PlaceholderResolver createDefaultPlaceholderResolver() {
        return new SpringPropertyResolver(applicationContext.getBean(ConfigurableEnvironment.class));
    }
}