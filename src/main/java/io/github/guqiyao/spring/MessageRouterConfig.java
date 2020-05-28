package io.github.guqiyao.spring;

import io.github.guqiyao.ConsumerBeanHolder;
import io.github.guqiyao.ConsumerContainer;
import io.github.guqiyao.MessageInvoker;
import io.github.guqiyao.annotation.MessageRouter;
import io.github.guqiyao.component.target.DefaultTargetDecoratorFactory;
import io.github.guqiyao.resolver.placeholder.PlaceholderResolver;
import io.github.guqiyao.resolver.placeholder.SpringPropertyResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = MessageRouterConfigBean.PREFIX, name = "enable", havingValue = "true")
public class MessageRouterConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ConfigurableEnvironment configurableEnvironment;

    @Value("${message.router.ignore-mismatch-tag:false}")
    private boolean ignoreMismatchTag;

    @Bean
    public MessageInvoker messageInvoker() {
        return new MessageInvoker(ignoreMismatchTag, createConsumerContainer());
    }

    /**
     * 创建容器
     * @return      consumer container
     */
    private ConsumerContainer createConsumerContainer() {
        ConsumerContainer consumerContainer = new ConsumerContainer();

        consumerContainer.setTargetDecoratorFactory(new DefaultTargetDecoratorFactory());
        consumerContainer.setPlaceholderResolver(getPlaceholderResolver());
        consumerContainer.register(getConsumers());

        return consumerContainer;
    }

    /**
     * 获取commands
     * @return  commands
     */
    private List<ConsumerBeanHolder> getConsumers() {
        Map<String, Object> map = applicationContext.getBeansWithAnnotation(MessageRouter.class);

        return map.values()
           .stream()
           .map(ConsumerBeanHolder::new)
           .collect(Collectors.toList());
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