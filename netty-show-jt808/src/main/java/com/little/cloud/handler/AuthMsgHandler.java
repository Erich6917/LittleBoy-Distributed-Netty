package com.little.cloud.handler;

import com.little.cloud.config.ChannelManager;
import com.little.cloud.vo.req.AuthMsg;
import com.little.cloud.vo.resp.CommonResp;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: Zpsw
 * @Date: 2019-05-15
 * @Description: 鉴权消息->CommonResp
 * @Version: 1.0
 */

@Slf4j
@Component
@ChannelHandler.Sharable
public class AuthMsgHandler extends BaseHandler<AuthMsg> {

    @Autowired
    private ChannelManager channelManager;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, AuthMsg msg) throws Exception {
        log.debug(msg.toString());
        channelManager.add(msg.getHeader().getTerminalPhone(), ctx.channel());
        CommonResp resp = CommonResp.success(msg, getSerialNumber(ctx.channel()));
        write(ctx,resp);
    }
}
