package com.little.cloud.api;

import com.little.cloud.dto.AppleDto;
import com.little.cloud.dto.FruitDto;
import com.little.cloud.task.PrintSyncTask;

/**
 * @Comment
 * @Author LiYuan
 * @Date 2021-3-4
 */
public class ThreadSleep {

  private FruitDto fruit = new AppleDto();

  public static void main(String[] args) {
    new ThreadSleep().start();
  }

  /**
   * sleep方法不会释放锁，也就是说如果当前线程持有对某个对象的锁，则即使调用sleep方法，其他线程也无法访问这个对象
   */
  public void start() {
    PrintSyncTask task1 = new PrintSyncTask(fruit);
    PrintSyncTask task2 = new PrintSyncTask(fruit);

    Thread t1 = new Thread(task1,"线程1");
    Thread t2 = new Thread(task2,"线程2");

    t1.start();
    t2.start();
  }

}
