package com.little.cloud.channel;

import com.little.cloud.handler.LevelAppleHandler;
import com.little.cloud.handler.LevelBananaHandler;
import com.little.cloud.handler.TimeServerHandler;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @Comment
 * @Author LiYuan
 * @Date 2021-1-21
 */
public class LevelChannel extends ChannelInitializer<SocketChannel> {

  @Override
  protected void initChannel(SocketChannel channel) throws Exception {

    ChannelPipeline pipeline = channel.pipeline();

    pipeline.addLast(new DelimiterBasedFrameDecoder(2048, Unpooled.copiedBuffer("$_$".getBytes())));
    pipeline.addLast(new StringDecoder());
    pipeline.addLast(new LevelAppleHandler());
    pipeline.addLast(new LevelBananaHandler());
    pipeline.addLast(new TimeServerHandler());

  }
}
