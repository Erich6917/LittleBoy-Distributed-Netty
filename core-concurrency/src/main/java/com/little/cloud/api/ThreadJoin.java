package com.little.cloud.api;

import com.little.cloud.task.PrintThread;
import lombok.extern.slf4j.Slf4j;

/**
 * @Comment
 * @Author LiYuan
 * @Date 2021-3-4
 */
@Slf4j
public class ThreadJoin {

  public static void main(String[] args) {
    new ThreadJoin().test_demo1();
  }

  /**
   * 主线程控制顺序
   */
  public void test_demo1() {
    PrintThread t1 = new PrintThread("t1");
    PrintThread t2 = new PrintThread("t2");
    PrintThread t3 = new PrintThread("t3");

    log.info("按照顺序执行 t3 > t2 > t1");

    try {
      t3.start();
      t3.join();

      t2.start();
      t2.join();

      t1.start();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }


  }

  /**
   * 线程之间控制顺序
   */
  public void test_demo2() {

    final Thread thread1 = new Thread(new Runnable() {
      @Override
      public void run() {
        System.out.println("产品经理规划新需求");
      }
    });

    final Thread thread2 = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          thread1.join();
          System.out.println("开发人员开发新需求功能");
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    Thread thread3 = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          thread2.join();
          System.out.println("测试人员测试新功能");
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });

    System.out.println("早上：");
    System.out.println("测试人员来上班了...");
    thread3.start();
    System.out.println("产品经理来上班了...");
    thread1.start();
    System.out.println("开发人员来上班了...");
    thread2.start();

  }


  public void test_demo3() {

  }
}
