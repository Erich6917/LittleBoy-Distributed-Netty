package com.little.cloud.api;

import com.little.cloud.task.SleepThread;

/**
 * @Comment
 * @Author LiYuan
 * @Date 2021-3-4
 */
public class ThreadInterrupt {

  public static void main(String[] args) {

//    new ThreadInterrupt().test_interrupt();
//    new ThreadInterrupt().test_nonblock_interrupt();
    new ThreadInterrupt().test_nonblock_interrupt2();
  }

  /**
   * 单独调用interrupt方法可以使得处于阻塞状态的线程抛出一个异常，也就说，它可以用来中断一个正处于阻塞状态的线程
   */

  public void test_interrupt() {
    SleepThread sleepThread = new SleepThread();

    /**
     * sleep 2秒，中途呗打断
     */
    sleepThread.start();

    sleepThread.interrupt();
  }


  /**
   * interrupt 不能打断正在运行的线程
   */
  public void test_nonblock_interrupt() {
    MyThread myThread = new MyThread();
    myThread.start();

    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    if (myThread.isAlive()) {
      myThread.interrupt();
    }
  }

  /**
   * 可以增加标志位
   */
  public void test_nonblock_interrupt2() {
    FlagThread flagThread = new FlagThread();
    flagThread.start();
    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    if (flagThread.isAlive()) {
      flagThread.interrupt();
    }
  }

  class MyThread extends Thread {

    @Override
    public void run() {
      int i = 0;
      while (i < Integer.MAX_VALUE) {
        System.out.println(i + " while循环");
        i++;
      }
    }
  }


  /**
   * 增加打断标志符号
   */
  class FlagThread extends Thread {

    @Override
    public void run() {
      int i = 0;
      while (!isInterrupted() && i < Integer.MAX_VALUE) {
        System.out.println(i + " while循环");
        i++;
      }
    }
  }


}
