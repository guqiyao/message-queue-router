package io.github.guqiyao.demo;

import io.github.guqiyao.annotation.MessageBody;
import io.github.guqiyao.annotation.MessageRouter;
import io.github.guqiyao.annotation.MessageTag;
import io.github.guqiyao.message.Message;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @Author: qiyao.gu
 * @Eamil: qiyao.gu@nalaa.com
 * @Date: 2019/7/10 18:26
 */
@Component
@MessageRouter(topic = "demo.topic")
public class ConsumerDemo {

	//@MessageBody注解将json格式的数据体转化为目标对象
	//Message为内置对象, 包含此次消息的所有信息
	@MessageTag("demo.tag")
	public void consume(@MessageBody DemoEntity demoEntity, Message message) {
		//进行业务操作
	}

	//不需要Message内置对象的方法
	@MessageTag("demo.tag1")
	public void consume1(@MessageBody DemoEntity demoEntity) {
		//进行业务操作
	}

	//直接使用内置对象进行业务逻辑处理
	@MessageTag("demo.tag2")
	public void consume2(Message message) {
		//进行业务操作
	}

	@Data
	public static class DemoEntity {
		private String name;
		private Integer age;
		private BigDecimal height;
	}
}