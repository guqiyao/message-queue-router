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

	private boolean enabled = false;

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}