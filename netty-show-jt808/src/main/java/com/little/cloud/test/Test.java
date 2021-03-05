package com.little.cloud.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * @Comment
 * @Author LiYuan
 * @Date 2021-1-19
 */
@Slf4j
public class Test {

  public static void main(String[] args) {

    test2();
  }

  public static void test1() {
    /**
     * 16进制表示 如下
     *
     * 直接打印，自动转化为十进制，
     * 可调用Integer.toHexString 生成对应的16进制字符串
     */
    byte a = 0x40;
    byte b = 0x5a;
    String msg1 = Integer.toHexString(a);
    String msg2 = Integer.toHexString(b);

    System.out.println(msg1);
    System.out.println(msg2);
  }

  public static void test2() {
    /**
     * 演示16进制字符串，16进制和10进制的切换
     */
    String msg = "0002000001983002708401520e";
    byte[] bytes = toByteArray(msg);
    System.out.println(bytes.length);
    for (int i = 0; i < bytes.length; i++) {
      byte bb = bytes[i];
      log.info("{} 十六进制 {} 十进制 {}", i, Integer.toHexString(bb), bb);
    }
  }


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
}
