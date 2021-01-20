package com.little.cloud.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Comment 打印输入内容
 * @Author LiYuan
 * @Date 2021-1-19
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {

  private int counter;

  //有连接可以读取
  @Override
  public void channelRead(ChannelHandlerContext ctx,Object msg){

    String body = (String) msg;
    try {
      System.out.println("The time server receive order :" +(++counter)+":"+ body);
      String currentTime = System.currentTimeMillis()+"";
      ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
      ctx.write(resp);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx){
    ctx.flush();
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx,Throwable cause){
    System.out.println(cause.toString());
    ctx.close();
  }

}
