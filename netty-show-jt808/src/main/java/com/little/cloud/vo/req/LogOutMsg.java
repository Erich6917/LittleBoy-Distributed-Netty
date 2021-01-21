package com.little.cloud.vo.req;

import com.little.cloud.vo.DataPacket;
import io.netty.buffer.ByteBuf;
import lombok.Data;

/**
 * @Author: Zpsw
 * @Date: 2019-05-15
 * @Description:注销包 没有包体 空包
 * @Version: 1.0
 */
@Data
public class LogOutMsg extends DataPacket {
    public LogOutMsg(ByteBuf byteBuf) {
        super(byteBuf);
    }
}
