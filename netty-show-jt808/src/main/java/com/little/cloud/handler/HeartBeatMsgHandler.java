package com.little.cloud.handler;

import com.little.cloud.handler.BaseHandler;
import com.little.cloud.vo.req.HeartBeatMsg;
import com.little.cloud.vo.resp.CommonResp;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Author: Zpsw
 * @Date: 2019-05-15
 * @Description: 心跳消息->CommonResp
 * @Version: 1.0
 */

@Slf4j
@Component
@ChannelHandler.Sharable
public class HeartBeatMsgHandler extends BaseHandler<HeartBeatMsg> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeartBeatMsg msg) throws Exception {
        log.debug(msg.toString());
        CommonResp resp = CommonResp.success(msg, getSerialNumber(ctx.channel()));
        write(ctx,resp);
    }
}
