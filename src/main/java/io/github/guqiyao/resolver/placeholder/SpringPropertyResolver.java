package io.github.guqiyao.resolver.placeholder;

import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author qiyao.gu@qq.com.
 */
public class SpringPropertyResolver extends AbstractPlaceholderResolver {

    private ConfigurableEnvironment configurableEnvironment;

    public SpringPropertyResolver(ConfigurableEnvironment configurableEnvironment) {
        this.configurableEnvironment = configurableEnvironment;
    }

    @Override
    protected String delegateGet(String placeholder) {
        return configurableEnvironment.getProperty(placeholder);
    }
}