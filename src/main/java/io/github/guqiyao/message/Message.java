package io.github.guqiyao.message;

/**
 * @author qiyao.gu@qq.com.
 */
public interface Message {

    /**
     * topic
     * @return  topic name
     */
    String getTopic();

    /**
     * 消息体中的数据
     * @return  data
     */
    String getBody();

    /**
     * 消息id
     * @return  当前消息的message id
     */
    String getMessageId();

    /**
     * 消息的tag
     * @return  tag
     */
    String getTag();

    /**
     * 消息的key
     * @return  key
     */
    String getKey();
}