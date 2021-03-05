package com.little.cloud.server;

import com.little.cloud.handler.TimeServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @Comment 测试粘包 DelimiterBasedFrameDecoder
 * @Author LiYuan
 * @Date 2021-1-19
 */
public class TimeServer {

  public void bind(int port) throws Exception {

    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workGroup = new NioEventLoopGroup();
    try {
      ServerBootstrap b = new ServerBootstrap();
      //最大连接数量设定 BACKLOG用于构造服务端套接字ServerSocket对象，标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度。如果未设置或所设置的值小于1，Java将使用默认值50。
      b.group(bossGroup, workGroup)
          .channel(NioServerSocketChannel.class)
          .option(ChannelOption.SO_BACKLOG, 1024)
          .childHandler(new ChildChannelHandler());
      //绑定端口，等待同步成功
      ChannelFuture f = b.bind(port).sync();
      f.channel().closeFuture().sync();
    } finally {
      bossGroup.shutdownGracefully();
      workGroup.shutdownGracefully();
    }
  }

  private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

    protected void initChannel(SocketChannel socketChannel) throws Exception {

      ByteBuf delimiter = Unpooled.copiedBuffer("$_$".getBytes());
      ByteBuf delimiter2 = Unpooled.copiedBuffer("$_$".getBytes(), "$_$".getBytes());
      ByteBuf delimiter3 = Unpooled.copiedBuffer("$_$".getBytes(), "$_$$$".getBytes());

      socketChannel.pipeline()
          .addLast(new DelimiterBasedFrameDecoder(2048, delimiter3, delimiter2, delimiter));
      socketChannel.pipeline().addLast(new StringDecoder());
      socketChannel.pipeline().addLast(new TimeServerHandler());

      /**
       * ChannelHandler 是有顺序的 ，依次处理
       *
       * DelimiterBasedFrameDecoder 是做黏包处理的
       * tcp产生粘包问题的原因有
       * 1、应用程序write写入的字节大小大于套接字发送缓冲区的大小
       * 2.进行MSS（TCP的数据部分）大小的TCP分段
       * 3.以太网帧的payload大于MTU进行IP分片
       *
       * netty粘包处理
       * netty提供了多种编码器用于处理半包，这些编码器包含
       * LineBasedFrameDecoder 时间解码器
       * DelimiterBasedFrameDecoder 分隔符解码器
       * FixedLengthFrameDecoder 定长解码器
       *
       *
       * 以如下为例
       * ByteBuf delimiter = Unpooled.copiedBuffer("$_$".getBytes());
       * 表示传输内容 以"$_$"结尾作为一个请求
       * 若传输内容为111$_$222$_$，则会截取两段内容111和222
       *
       * ByteBuf delimiter = Unpooled.copiedBuffer("$_$".getBytes(),"$_$".getBytes());
       * 其实是两个分隔符拼接，可以是多个
       * 实际是以"$_$$_$"作为分隔符
       *
       * DelimiterBasedFrameDecoder 添加是有顺序的，
       * 如果是长的分隔符，包含短的分隔符，注意添加顺序
       */

      /**
       * 测试文本
       * 111$_$222$_$
       * 111$_$$_$$$222$_$$_$333$_$$_$
       *
       */
    }
  }

  public static void main(String[] args) {
    int port = 9000;
    try {
      new TimeServer().bind(port);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
