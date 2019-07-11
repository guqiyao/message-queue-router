package io.github.guqiyao.demo;

import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.bean.ConsumerBean;
import com.aliyun.openservices.ons.api.bean.Subscription;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: qiyao.gu
 * @Eamil: qiyao.gu@nalaa.com
 * @Date: 2019/7/10 18:26
 */
@Configuration
public class MessageConfig {

	@Bean
	public DemoMessageListener demoMessageListener() {
		return new DemoMessageListener();
	}

	@Bean
	public ConsumerBean consumerBean(MessageListener messageListener) {
		ConsumerBean consumerBean = new ConsumerBean();

		Properties properties = new Properties();

		//账户信息
		properties.put("GROUP_ID", "GROUP_ID");
		properties.put("AccessKey", "GROUP_ID");
		properties.put("SecretKey", "GROUP_ID");
		properties.put("NAMESRV_ADDR", "GROUP_ID");

		//消费者线程数
		properties.put("ConsumeThreadNums", "GROUP_ID");

		consumerBean.setProperties(properties);

		Subscription subscription = new Subscription();
		subscription.setTopic("topic");

		//即Tag,可以设置成具体的Tag,如 taga||tagb||tagc,也可设置成*, *仅代表订阅所有Tag,不支持通配
		subscription.setExpression("*");

		Map<Subscription, MessageListener> map = new HashMap<>();
		map.put(subscription, messageListener);

		consumerBean.setSubscriptionTable(map);

		return consumerBean;
	}
}