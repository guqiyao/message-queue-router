package io.github.guqiyao;

import io.github.guqiyao.resolver.placeholder.AbstractPlaceholderResolver;

/**
 * @author qiyao.gu@qq.com.
 */
public class StringPlaceholderResolver extends AbstractPlaceholderResolver {

    @Override
    protected String delegateGet(String parsedPlaceholder) {
        return parsedPlaceholder;
    }
}