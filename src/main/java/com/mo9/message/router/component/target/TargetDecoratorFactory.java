package com.mo9.message.router.component.target;

/**
 * @author qiyao.gu@qq.com.
 */
public interface TargetDecoratorFactory {

    /**
     * 创建
     * @param target    target
     * @return          target decorator
     */
    TargetDecorator create(Object target);
}