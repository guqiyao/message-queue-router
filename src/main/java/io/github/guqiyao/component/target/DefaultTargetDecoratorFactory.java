package io.github.guqiyao.component.target;

/**
 * @author qiyao.gu@qq.com.
 */
public class DefaultTargetDecoratorFactory implements TargetDecoratorFactory {

    @Override
    public TargetDecorator create(Object target) {
        return new DefaultTargetDecorator(target);
    }
}