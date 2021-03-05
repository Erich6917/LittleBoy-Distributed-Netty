package com.little.cloud.task;

import lombok.extern.slf4j.Slf4j;

/**
 * @Comment
 * @Author LiYuan
 * @Date 2021-3-4
 */
@Slf4j
public class PrintThread extends Thread {

  public PrintThread() {
    super();
  }

  public PrintThread(String name) {
    super(name);
  }

  @Override
  public void run() {
    log.info("START Thread {}", Thread.currentThread().getName());

    /**
     * 耗时操作 没任务意义
     */

    String msg = "aaaaaaaaabbbbbeeeeefffffffffffffffffffff";
    String result = "";
    for (int i = 0; i < 5000; i++) {
      result += msg;
    }

    log.info("END Thread {}", Thread.currentThread().getName());
  }

}
