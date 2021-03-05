package com.little.cloud.channel;

import com.little.cloud.handler.LevelAppleHandler;
import com.little.cloud.handler.LevelBananaHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @Comment
 * @Author LiYuan
 * @Date 2021-1-21
 */
public class ChildChannelHandler extends ChannelInitializer<SocketChannel> {

  protected void initChannel(SocketChannel socketChannel) throws Exception {

    ByteBuf delimiter = Unpooled.copiedBuffer("$_$".getBytes());
    ByteBuf delimiter2 = Unpooled.copiedBuffer("$_$".getBytes(), "$_$".getBytes());
    ByteBuf delimiter3 = Unpooled.copiedBuffer("$_$".getBytes(), "$_$$$".getBytes());

    socketChannel.pipeline()
        .addLast(new DelimiterBasedFrameDecoder(2048, delimiter3, delimiter2, delimiter));
    socketChannel.pipeline().addLast(new StringDecoder());

    socketChannel.pipeline().addLast(new LevelAppleHandler());

    socketChannel.pipeline().addLast(new LevelBananaHandler());



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
