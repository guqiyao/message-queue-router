package io.github.guqiyao;

/**
 * @Author: qiyao.gu
 * @Eamil: qiyao.gu@nalaa.com
 * @Date: 2019/7/10 16:40
 */
public enum  Action {
	SUCCESS,            //正常执行
	REPEATED_MESSAGE    //重复消息, 当返回此枚举值时, 将会自动把消息标记为重复消息并通知rocket mq消息服务器
	;
}