package com.little.cloud.handler;

import com.little.cloud.vo.req.Banana;
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
public class LevelBananaHandler extends LevelHandler<Banana> {

  @Override
  protected void channelRead0(ChannelHandlerContext channelHandlerContext, Banana banana)
      throws Exception {

    log.info("Banana Handler");
    write(channelHandlerContext, banana);
  }
}
