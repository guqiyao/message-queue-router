package com.mo9.message.router.component.target;

/**
 * @author qiyao.gu@qq.com.
 */
public class DefaultTargetDecorator implements TargetDecorator {

    private Object target;

    public DefaultTargetDecorator(Object target) {
        this.target = target;
    }

    @Override
    public Object getTarget() {
        return target;
    }
}