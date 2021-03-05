package com.little.cloud.task;

import lombok.extern.slf4j.Slf4j;

/**
 * @Comment
 * @Author LiYuan
 * @Date 2021-3-4
 */
@Slf4j
public class SleepThread extends Thread {


  @Override
  public void run() {
    log.info("START Thread {}", Thread.currentThread().getName());

    try {
      Thread.sleep(2000);
    } catch (InterruptedException e) {
      log.error("异常结束 interrupt");
      return;
    }
    log.info("END Thread {}", Thread.currentThread().getName());
  }

}
