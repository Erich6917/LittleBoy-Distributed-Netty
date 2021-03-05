package com.little.cloud.handler;

import com.little.cloud.vo.req.Apple;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @Comment
 * @Author LiYuan
 * @Date 2021-1-21
 */
@Slf4j
@ChannelHandler.Sharable
public class LevelAppleHandler extends LevelHandler<Apple> {

  @Override
  protected void channelRead0(ChannelHandlerContext channelHandlerContext, Apple apple)
      throws Exception {
    log.info("Apple Handler");
    write(channelHandlerContext, apple);
  }

}
