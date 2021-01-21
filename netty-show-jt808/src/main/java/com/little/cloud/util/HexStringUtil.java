package com.little.cloud.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * @Comment
 * @Author LiYuan
 * @Date 2021-1-21
 */
@Slf4j
public class HexStringUtil {

  public static byte[] toByteArray(String hexString) {
    if (StringUtils.isEmpty(hexString)) {
      return null;
    }
    hexString = hexString.toLowerCase();
    final byte[] byteArray = new byte[hexString.length() >> 1];
    int index = 0;
    for (int i = 0; i < hexString.length(); i++) {
      if (index > hexString.length() - 1) {
        return byteArray;
      }
      byte highDit = (byte) (Character.digit(hexString.charAt(index), 16) & 0xFF);
      byte lowDit = (byte) (Character.digit(hexString.charAt(index + 1), 16) & 0xFF);
      byteArray[i] = (byte) (highDit << 4 | lowDit);
      index += 2;
    }
    return byteArray;
  }

  public static void printByte(byte[] bytes) {
    for (int i = 0; i < bytes.length; i++) {
      byte bb = bytes[i];
      log.info("{} 十六进制 {} 十进制 {}", i, Integer.toHexString(bb), bb);
    }
  }
}
