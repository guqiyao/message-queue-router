# message-queue-router
基于Spring boot 与 Aliyun Rocket MQ SDK的消息消费路由项目

## 使用

### 配置
```yaml
message:
    router:
        enable: true
```

[代码Demo](https://github.com/guqiyao/message-queue-router/tree/master/src/test/java/io/github/guqiyao/demo)

## 工作原理

### 初始化

![Markdown](http://hbimg.huabanimg.com/a4c0aed6a4505821d52aea9cc9bb371ef19feb907886-6keYyf_fw658)


### 消息消费

![Markdown](http://hbimg.huabanimg.com/d0408472f19d644b94c4735c74b32189eb5bb27322103-IzKfeD_fw658)

## 注解

### @MessageRouter

此注解用于具体作为`Consumer`的类上, 在路由功能中, 作为命名空间定位的存在, 其参数`topic`用于配置具体的topic在配置文件中的key, 
在应用程序启动时, 会将此key替换成对应的值, 若key不存在在于配置文件中时, 那么key本身将会作为value的存在

### @MessageTag

此注解用于具体的方法上, 被注解的方法将被表示为tag所对应的执行方法, 其参数`value`用于配置具体的tag在配置文件中的key, 
在应用程序启动时, 会将此key替换成对应的值, 若key不存在在于配置文件中时, 那么key本身将会作为value的存在

### @MessageBody

此注解应用于方法的参数上, 用于将请求而来的消息体序列化成对应的Bean, 目前只支持消息体为json的情况;
