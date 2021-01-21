netty-show-jt808 

​	参考大神代码,做了一些修改，学习使用<a href="https://github.com/zpsw/jt808-netty">GIT地址</a>

​	<a href="https://juejin.im/post/5cde451f51882525fd1f6e68#heading-10">Netty实战之使用Netty解析交通部JT808协议</a>

这是一个Netty入门教学项目，解析JT808协议，整合Spring boot，并提供了一些Netty中的最佳实践。

下载代码到本地后，改动application.yml中的数据库连接即可运行，搭载Spring Data JPA会自动建表，
然后通过 NetAssist网络调试工具.exe 以及 测试报文.txt 中的报文即可调试。

项目概述

​	JT808协议技术规范，可以理解为终端和平台之间的协议。

​	协议本身不是重点，但可以通过这个例子，理解终端和平台之间的交互。

​	报文构建、加密、解密、不同消息的收发等。完整的流程如何通过Netty实现。

项目理解

​	1、项目组成Spring Boot +Netty+JPA+协议+调试工具+报文

​	2、spring项目启动，实际是启动 NettyTcpServer
​	      这里定义了EventLoop、ChannelHandler

​	     类EventLoopGroupConfig定义了 LoopGroup

​             报文的解析处理在 JT808ChannelInitializer，重点关注

​        3、JT808ChannelInitializer理解

​              ChannelPipeline加入了各种类型的ChannelHandler
​              注意加入的顺序，即是处理的顺序。

​	     以下依次讨论各个ChannelHandler

```
pipeline.addLast(
    new IdleStateHandler(readTimeOut, 0, 0, TimeUnit.SECONDS));
    
    根据设置时间，读取传入的报文，不再是发一次处理异常，测试时，为方便使用，可以注释掉。
```

​	

```
pipeline.addLast(
    new DelimiterBasedFrameDecoder(1000,
        Unpooled.copiedBuffer(new byte[]{JT808Const.PKG_DELIMITER}),
        Unpooled.copiedBuffer(new byte[]{JT808Const.PKG_DELIMITER, JT808Const.PKG_DELIMITER})));
       DelimiterBasedFrameDecoder 是黏包处理
       黏包主要处理写入内容多长，支持多次写入 或者 数据分段
	   API ：DelimiterBasedFrameDecoder(int maxFrameLength, ByteBuf... delimiters) 
       new byte[]{JT808Const.PKG_DELIMITER}，就是以0x7e切分字符，
       支持拼接就是以0x7e7e切分
       当然处理顺序，也是按添加处理的，如果前者是后者的字节，就像现在这种设置，只会触发0x7e切分
       maxFrameLength 1000是最大支持长度
       ByteBuf 支持多个，会自动拼接
       
```

```
pipeline.addLast(new JT808Decoder());
pipeline.addLast(new JT808Encoder());

这两个是加密解密方法
```

```
pipeline.addLast(heartBeatMsgHandler);
pipeline.addLast(businessGroup,locationMsgHandler);
pipeline.addLast(authMsgHandler);
pipeline.addLast(registerMsgHandler);
pipeline.addLast(logOutMsgHandler);
再次强调下顺序
```

4、关于报文

​	以心跳为例

​	测试报文为7e0002000001983002708401520e7e

​		

| 消息头组成-心跳 |            |            |            |                |
| --------------- | ---------- | ---------- | ---------- | -------------- |
| 消息ID          | 消息体属性 | 终端手机号 | 消息流水号 | 消息包封装项目 |
| 0002            | 0000       | 0198300270 | 840152     | 空             |



| 消息组成 |                          |        |        |
| -------- | ------------------------ | ------ | ------ |
| 标志位   | 消息头-消息体            | 检验码 | 标志位 |
| 7e       | 000200000198300270840152 | 0e     | 7e     |



验证码0e是计算来的，可以通过JT808Util.XorSumBytes

5、关于编码格式

​     所有消息是通过16进制发送的，报文编写，留意打印和调用的转换。

6、关于调试工具

​	因为我们代码没有写，Client的部分，可以借助调试工具。

​	协议类型选择TCP Client

​	有一个点特别注意，编码设置为HEX 16进制！！！	

​	详情见截图

​	![1611218495270](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\1611218495270.png)

​      