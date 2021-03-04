package com.little.cloud.executor;

import com.little.cloud.task.PrintTask;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @Comment
 * @Author LiYuan
 * @Date 2020-12-29
 */
public class TaskExecutorWebServer {

  private static final int NTHREADS = 10;

  /**
   * 定长线程池
   */
  private static final Executor executor = Executors.newFixedThreadPool(NTHREADS);

  /**
   * 可缓存
   */
  private static final Executor cachedThreadPool = Executors.newCachedThreadPool();

  /**
   * 单一线程，FIFO
   */
  private static final Executor singleThreadExecutor = Executors.newSingleThreadExecutor();

  /**
   * 定长，支持定时，周期性执行，类似于Timer
   */
  private static final Executor scheduledThreadPool = Executors.newScheduledThreadPool(10);

  public static void main(String[] args) throws IOException {
    new TaskExecutorWebServer().task();
//    AbstractExecutorService
  }

  public void task() throws IOException {
//    ServerSocket serverSocket = new ServerSocket();
    for (int i = 0; i < 20; i++) {
      PrintTask task = new PrintTask(randomMsg());
      executor.execute(task);
    }

  }

  private String randomMsg() {
    return "MESSAGE " + new Random().nextInt(10000);
  }

}

