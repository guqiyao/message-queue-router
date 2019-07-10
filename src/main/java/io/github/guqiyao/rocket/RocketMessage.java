package io.github.guqiyao.rocket;

import io.github.guqiyao.message.Message;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: qiyao.gu
 * @Eamil: qiyao.gu@nalaa.com
 * @Date: 2019/7/10 16:27
 */
class RocketMessage implements Message {

	@Getter
	@Setter(value = AccessLevel.PACKAGE)
	private String topic;

	@Getter
	@Setter(value = AccessLevel.PACKAGE)
	private String body;

	@Getter
	@Setter(value = AccessLevel.PACKAGE)
	private String messageId;

	@Getter
	@Setter(value = AccessLevel.PACKAGE)
	private String tag;

	@Getter
	@Setter(value = AccessLevel.PACKAGE)
	private String key;
}