package io.github.guqiyao.test;

import io.github.guqiyao.util.MessageRouterUtils;
import org.apache.commons.lang3.time.StopWatch;

/**
 * @Author: qiyao.gu
 * @Eamil: qiyao.gu@nalaa.com
 * @Date: 2019/7/11 10:37
 */
public class KeyGenerateTest {

	public static void main(String[] args) {
		String topic = "fafuli_company_business";
		String tag = "allocation";

		int count = 10000;
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		for (int i = 0 ; i < count ; i ++) {
			MessageRouterUtils.generateReceiverKey(topic + i, tag);
		}

		stopWatch.stop();

		System.out.println(stopWatch.getTime());
	}
}