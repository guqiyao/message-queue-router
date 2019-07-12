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
//@MessageRouter将会读取配置文件中所配置的key : 'demo.topic' 对应的value
//下列@MssageTag注解也是相同
//当配置文件中的key不存在时, 则使用当前所填写的key作为value, 以@MessageRouter为例, 当'demo.topic'未配置, 那么demo.topic将会作为value
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