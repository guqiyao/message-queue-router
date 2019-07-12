package io.github.guqiyao.demo;

import io.github.guqiyao.Action;
import io.github.guqiyao.message.Message;
import io.github.guqiyao.AbstractMessageListener;

/**
 * @Author: qiyao.gu
 * @Eamil: qiyao.gu@nalaa.com
 * @Date: 2019/7/10 18:25
 */
public class DemoMessageListener extends AbstractMessageListener {

	@Override
	protected Action pre(Message message) {
		//消息处理前进行前置处理, 可以进行重复消息过滤, 日志记录等

		return Action.SUCCESS;
	}

	@Override
	protected void post(Message message) {
		//消息处理成功后的操作
	}

	@Override
	protected void exception(Message message, Exception e) {
		//消息处理中出现异常后的回调
		//当执行pre, 具体的consume方法, post出现异常时则进入此方法
	}
}