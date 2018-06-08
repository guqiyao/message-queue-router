package com.mo9.message.router;

import com.mo9.message.router.resolver.placeholder.AbstractPlaceholderResolver;

/**
 * @author qiyao.gu@qq.com.
 */
public class StringPlaceholderResolver extends AbstractPlaceholderResolver {

    @Override
    protected String delegateGet(String parsedPlaceholder) {
        return parsedPlaceholder;
    }
}