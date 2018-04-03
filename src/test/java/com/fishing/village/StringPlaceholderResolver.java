package com.fishing.village;

import com.fishing.village.resolver.placeholder.AbstractPlaceholderResolver;

/**
 * @author qiyao.gu@qq.com.
 */
public class StringPlaceholderResolver extends AbstractPlaceholderResolver {

    @Override
    protected String delegateGet(String parsedPlaceholder) {
        return parsedPlaceholder;
    }
}