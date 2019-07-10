package io.github.guqiyao.rocket;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import io.github.guqiyao.MessageInvoker;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author: qiyao.gu
 * @Eamil: qiyao.gu@nalaa.com
 * @Date: 2019/7/10 16:19
 */
@Slf4j
public abstract class AbstractMessageListener implements MessageListener {

	private MessageConverter messageConverter = new MessageConverter();

	@Setter
	private MessageInvoker messageInvoker;

	@Override
	public Action consume(Message message, ConsumeContext consumeContext) {
		io.github.guqiyao.message.Message data = messageConverter.convert(message);

		if (log.isInfoEnabled()) {
			log.info(String.format("接收消息, topic : %s, message id : %s, tag : %s, key : %s, body : %s",
					data.getTopic(), data.getMessageId(), data.getTag(), data.getKey(), data.getBody()));
		}

		try {
			io.github.guqiyao.Action action = pre(data);
			if (action == io.github.guqiyao.Action.REPEATED_MESSAGE) {
				if (log.isWarnEnabled()) {
					log.warn(String.format("当前消息作为重复消息进行过滤, topic : %s, message id : %s, tag : %s, key : %s, body : %s",
							data.getTopic(), data.getMessageId(), data.getTag(), data.getKey(), data.getBody()));
				}

				return Action.CommitMessage;
			}

			messageInvoker.invoke(data);

			post(data);

			return Action.CommitMessage;
		} catch (Exception e) {

			exception(data, e);

			return Action.ReconsumeLater;
		}

	}

	/**
	 * 消息处理前
	 * @param message   消息体
	 */
	protected abstract io.github.guqiyao.Action pre(io.github.guqiyao.message.Message message);

	/**
	 * 消息正常处理后
	 * @param message   消息体
	 */
	protected abstract void post(io.github.guqiyao.message.Message message);

	/**
	 * 处理发生异常
	 * @param message    消息体
	 * @param e          异常
	 */
	protected abstract void exception(io.github.guqiyao.message.Message message, Exception e);
}