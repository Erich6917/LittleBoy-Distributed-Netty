package com.little.cloud.handler;

import com.little.cloud.handler.BaseHandler;
import com.little.cloud.vo.req.RegisterMsg;
import com.little.cloud.vo.resp.RegisterResp;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: Zpsw
 * @Date: 2019-05-15
 * @Description: 注册消息->RegisterResp
 * @Version: 1.0
 */

@Slf4j
@Component
@ChannelHandler.Sharable
public class RegisterMsgHandler extends BaseHandler<RegisterMsg> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RegisterMsg msg) throws Exception {
        log.debug(msg.toString());
        //默认鉴权成功
        RegisterResp resp = RegisterResp.success(msg, getSerialNumber(ctx.channel()));
        write(ctx,resp);
    }
}
