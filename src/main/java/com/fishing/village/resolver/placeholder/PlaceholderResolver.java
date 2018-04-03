package com.fishing.village.resolver.placeholder;

/**
 * @author qiyao.gu@qq.com.
 */
public interface PlaceholderResolver {

    /**
     * 根据 place holder 获取值
     * @param placeholder   place holder
     * @return              值
     */
    String get(String placeholder);
}