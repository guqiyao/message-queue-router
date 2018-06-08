package com.mo9.message.router.resolver.placeholder;

import org.apache.commons.lang3.StringUtils;

/**
 * @author qiyao.gu@qq.com.
 */
public abstract class AbstractPlaceholderResolver implements PlaceholderResolver {

    private static final String PLACEHOLDER_PREFIX = "${";

    private static final String PLACEHOLDER_SUFFIX = "}";

    @Override
    public String get(String placeholder) {
        if (!isDelegateGet(placeholder)) {
            return placeholder;
        }

        String value = delegateGet(parse(placeholder));

        return StringUtils.isNotBlank(value) ? value : placeholder;
    }

    /**
     * 由具体的子类去去实现此获取方式
     * @param parsedPlaceholder   解析后的占位符
     * @return                    返回值
     */
    protected abstract String delegateGet(String parsedPlaceholder);

    /**
     * 解析占位符
     * @param placeholder   占位符
     * @return              解析后的占位符
     */
    private String parse(String placeholder) {
        int startIndex = placeholder.indexOf(PLACEHOLDER_PREFIX);
        if (startIndex < 0) {
            return placeholder;
        }
        int endIndex = placeholder.lastIndexOf(PLACEHOLDER_SUFFIX);
        if (endIndex < 0) {
            return placeholder;
        }

        return placeholder.substring(startIndex + PLACEHOLDER_PREFIX.length(), endIndex);
    }

    /**
     * 是否需要委托获取占位符对应的值
     * @param placeholder   占位符
     * @return              true | false
     */
    private boolean isDelegateGet(String placeholder) {
        return placeholder.startsWith(PLACEHOLDER_PREFIX);
    }
}