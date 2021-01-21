package com.little.cloud.codec;

import com.little.cloud.codec.JT808Decoder;
import com.little.cloud.util.HexStringUtil;
import com.little.cloud.util.JT808Util;
import io.netty.buffer.ByteBuf;
import lombok.extern.slf4j.Slf4j;

/**
 * @Comment
 * @Author LiYuan
 * @Date 2021-1-21
 */
@Slf4j
public class JT808DecoderTest {

  JT808Decoder decoder = new JT808Decoder();

  public void test() {
    String hexString = "7e0102000301983002708400013132336e7e";

    byte[] raw = HexStringUtil.toByteArray(hexString);

    ByteBuf byteBuf = decoder.revert(raw);
    int length = byteBuf.readableBytes();
    byte[] rt = new byte[length];
    for (int i = 0; i < length; i++) {
      rt[i] = byteBuf.getByte(i);
    }
    HexStringUtil.printByte(rt);

  }

  public void test2(){
    String hexString ="000200000198300270840152";
    byte[] raw = HexStringUtil.toByteArray(hexString);
    ByteBuf byteBuf = decoder.revert(raw);
//    int length = byteBuf.readableBytes();
//    byte[] rt = new byte[length];
//    for (int i = 0; i < length; i++) {
//      rt[i] = byteBuf.getByte(i);
//    }
    byte check = JT808Util.XorSumBytes(byteBuf);
    log.info("check {}",Integer.toHexString(check));
  }
  public static void main(String[] args) {
    new JT808DecoderTest().test2();
  }
}
