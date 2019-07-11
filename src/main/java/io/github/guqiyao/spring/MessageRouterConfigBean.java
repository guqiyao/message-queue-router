package io.github.guqiyao.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: qiyao.gu
 * @Eamil: qiyao.gu@nalaa.com
 * @Date: 2019/7/10 17:39
 */
@Component
@ConfigurationProperties(value = MessageRouterConfigBean.PREFIX)
public class MessageRouterConfigBean {

	static final String PREFIX = "message.router";

	private boolean enable = false;

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}
}