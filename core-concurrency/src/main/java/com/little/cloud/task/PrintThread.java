package com.little.cloud.task;

import lombok.extern.slf4j.Slf4j;

/**
 * @Comment
 * @Author LiYuan
 * @Date 2021-3-4
 */
@Slf4j
public class PrintThread extends Thread {

  @Override
  public void run() {
    log.info("Thread {}", Thread.currentThread().getName());
  }

}
