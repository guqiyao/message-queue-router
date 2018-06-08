package com.mo9.message.router;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mo9.message.router.annotation.MessageBody;
import com.mo9.message.router.annotation.MessageRouter;
import com.mo9.message.router.annotation.MessageTag;
import com.mo9.message.router.message.Message;
import com.mo9.message.router.util.JSONUtils;

/**
 * @author qiyao.gu@qq.com.
 */
@MessageRouter(topic = "test.topic")
public class Consumer {

    @MessageTag("test.tag")
    public void testConsumer(Message message, @MessageBody People people) throws JsonProcessingException {

        System.out.println("---------");
        System.out.println(JSONUtils.toJson(message));
        System.out.println(JSONUtils.toJson(people));
    }

    private static class People {
        private String name;

        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}