package com.little.cloud.task;

import java.util.Random;

/**
 * @Comment
 * @Author LiYuan
 * @Date 2021-3-3
 */
public class PrintTask implements Runnable {

  private String msg;

  public PrintTask(String msg) {
    this.msg = msg;
  }

  public void run() {

    System.out.println("START " + Thread.currentThread().getName());
    try {
      Thread.sleep(new Random().nextInt(2000) + 1000);
      System.out.println("print task " + msg);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    System.out.println("END " + Thread.currentThread().getName());
  }
}
