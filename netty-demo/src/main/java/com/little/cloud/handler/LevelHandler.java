package com.little.cloud.handler;

import com.little.cloud.vo.Fruit;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @Comment
 * @Author LiYuan
 * @Date 2021-1-21
 */
@Slf4j
public abstract class LevelHandler<T> extends SimpleChannelInboundHandler<T> {


  public void write(ChannelHandlerContext ctx, Fruit msg) {
    System.out.println(msg);
    ctx.writeAndFlush(msg).addListener(future -> {
      if (!future.isSuccess()) {
        log.error("发送失败", future.cause());
      }
    });
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    log.error("exceptionCaught",cause);
    ctx.close();
  }

  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    if (evt instanceof IdleStateEvent) {
      //此实例项目只设置了读取超时时间,可以通过state分别做处理,一般服务端在这里关闭连接节省资源，客户端发送心跳维持连接
      IdleState state = ((IdleStateEvent) evt).state();
      if (state == IdleState.READER_IDLE) {
        log.warn("客户端{}读取超时,关闭连接", ctx.channel().remoteAddress());
        ctx.close();
      } else if (state == IdleState.WRITER_IDLE) {
        log.warn("客户端{}写入超时", ctx.channel().remoteAddress());
      }else if(state == IdleState.ALL_IDLE){
        log.warn("客户端{}读取写入超时", ctx.channel().remoteAddress());
      }
    } else {
      super.userEventTriggered(ctx, evt);
    }
  }

}
