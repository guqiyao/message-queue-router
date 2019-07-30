package io.github.guqiyao.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.guqiyao.util.JSONUtils;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: qiyao.gu
 * @Eamil: qiyao.gu@nalaa.com
 * @Date: 2019/7/11 10:37
 */
public class KeyGenerateTest {

	public static void main(String[] args) throws JsonProcessingException {
//		String topic = "fafuli_company_business";
//		String tag = "allocation";
//
//		int count = 10000;
//		StopWatch stopWatch = new StopWatch();
//		stopWatch.start();
//
//		for (int i = 0 ; i < count ; i ++) {
//			MessageRouterUtils.generateReceiverKey(topic + i, tag);
//		}
//
//		stopWatch.stop();
//
//		System.out.println(stopWatch.getTime());

		Map<String, Person> map = new HashMap<>();
		Person person = new Person();
		person.age = 10;
		person.name = "10";

		map.put("1", person);

		person = new Person();
		person.age = 11;
		person.name = "11";

		map.put("2", person);

		System.out.println(JSONUtils.toJson(map));
	}

	@Data
	private static class Person {
		String name;
		int age;
	}
}