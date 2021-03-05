package com.little.cloud.api;

/**
 * @Comment
 * @Author LiYuan
 * @Date 2021-3-5
 */
public class ThreadSync {

  private int count = 0;

  private static int total = 0;

  public synchronized void test_sync1(int value) {
    this.count += value;
  }

  public void test_sync2(int value) {
    synchronized (this) {
      this.count += value;
    }
  }

  public static synchronized void test_sync3(int value) {
    total += value;
  }

  public static void test_sync4(int value) {
    synchronized (ThreadSync.class) {
      total += value;
    }
  }

}
