package com.little.cloud.server;

import com.little.cloud.channel.ChildChannelHandler;
import com.little.cloud.channel.LevelChannel;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Comment
 * @Author LiYuan
 * @Date 2021-1-21
 */
public class LevelServer {


  public void bind(int port) {
    EventLoopGroup bossGroup = new NioEventLoopGroup();
    EventLoopGroup workGroup = new NioEventLoopGroup();
    try {

      ServerBootstrap bootstrap = new ServerBootstrap();

      bootstrap.group(bossGroup, workGroup)
          .channel(NioServerSocketChannel.class)
          .option(ChannelOption.SO_BACKLOG, 1024)
          .childHandler(new LevelChannel())
          ;

      //绑定端口，等待同步成功
      ChannelFuture f = bootstrap.bind(port).sync();
      f.channel().closeFuture().sync();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      bossGroup.shutdownGracefully();
      workGroup.shutdownGracefully();
    }

  }

  public static void main(String[] args) {
    int port = 9000;
    try {
      new LevelServer().bind(port);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
