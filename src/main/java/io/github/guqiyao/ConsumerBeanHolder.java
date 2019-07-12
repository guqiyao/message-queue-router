package io.github.guqiyao;

import io.github.guqiyao.annotation.MessageRouter;
import io.github.guqiyao.util.ObjectProxyUtils;

import java.lang.reflect.Method;

/**
 * @Author: qiyao.gu
 * @Eamil: qiyao.gu@nalaa.com
 * @Date: 2019/7/11 18:27
 */
public class ConsumerBeanHolder {

	private String beanName;
	private Method[] methods;
	private Object consumerBean;
	private MessageRouter router;

	public ConsumerBeanHolder(Object consumerBean) {
		this.consumerBean = consumerBean;
		init();
	}

	MessageRouter getRouter() {
		return router;
	}

	Object getConsumerBean() {
		return consumerBean;
	}

	Method[] getMethods() {
		return methods;
	}

	String getBeanName() {
		return beanName;
	}

	private void init() {
		Class originClass = getOriginClass(consumerBean);

		this.methods = originClass.getMethods();
		this.beanName = originClass.getSimpleName();
		this.router = (MessageRouter) originClass.getAnnotation(MessageRouter.class);
	}

	private Class getOriginClass(Object consumer) {
		return ObjectProxyUtils.getTarget(consumer).getClass();
	}
}