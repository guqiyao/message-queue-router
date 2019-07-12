package io.github.guqiyao.rocket;

import io.github.guqiyao.message.Message;

/**
 * @Author: qiyao.gu
 * @Eamil: qiyao.gu@nalaa.com
 * @Date: 2019/7/10 16:26
 */
public class MessageConverter {

	public Message convert(com.aliyun.openservices.ons.api.Message message) {
		RocketMessage data = new RocketMessage();

		data.setBody(new String(message.getBody()));
		data.setKey(message.getKey());
		data.setMessageId(message.getMsgID());
		data.setTag(message.getTag());
		data.setTopic(message.getTopic());

		return data;
	}
}