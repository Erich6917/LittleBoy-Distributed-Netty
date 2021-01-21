package com.little.cloud.server;

import com.little.cloud.codec.JT808Decoder;
import com.little.cloud.codec.JT808Encoder;
import com.little.cloud.config.JT808Const;
import com.little.cloud.handler.AuthMsgHandler;
import com.little.cloud.handler.HeartBeatMsgHandler;
import com.little.cloud.handler.LocationMsgHandler;
import com.little.cloud.handler.LogOutMsgHandler;
import com.little.cloud.handler.RegisterMsgHandler;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author: Zpsw
 * @Date: 2019-05-15
 * @Description:
 * @Version: 1.0
 */
@Component
@Slf4j
public class JT808ChannelInitializer extends ChannelInitializer<SocketChannel> {

  @Value("${netty.read-timeout}")
  private int readTimeOut;

  @Autowired
  @Qualifier("businessGroup")
  private EventExecutorGroup businessGroup;

  @Autowired
  private AuthMsgHandler authMsgHandler;

  @Autowired
  private HeartBeatMsgHandler heartBeatMsgHandler;

  @Autowired
  private LocationMsgHandler locationMsgHandler;

  @Autowired
  private LogOutMsgHandler logOutMsgHandler;

  @Autowired
  private RegisterMsgHandler registerMsgHandler;

  @Override
  protected void initChannel(SocketChannel ch) throws Exception {
    log.info("initChannel");
    ChannelPipeline pipeline = ch.pipeline();
//    pipeline.addLast(
//        new IdleStateHandler(readTimeOut, 0, 0, TimeUnit.SECONDS));
    // jt808协议 包头最大长度16+ 包体最大长度1023+分隔符2+转义字符最大姑且算60 = 1100
    pipeline.addLast(
        new DelimiterBasedFrameDecoder(1000,
            Unpooled.copiedBuffer(new byte[]{JT808Const.PKG_DELIMITER}),
            Unpooled.copiedBuffer(new byte[]{JT808Const.PKG_DELIMITER, JT808Const.PKG_DELIMITER})));
    pipeline.addLast(new JT808Decoder());
    pipeline.addLast(new JT808Encoder());
//
    pipeline.addLast(heartBeatMsgHandler);
    pipeline.addLast(businessGroup,
        locationMsgHandler);//因为locationMsgHandler中涉及到数据库操作，所以放入businessGroup
    pipeline.addLast(authMsgHandler);
    pipeline.addLast(registerMsgHandler);
    pipeline.addLast(logOutMsgHandler);
//    pipeline.addLast(echoServerHandler);

  }

}
