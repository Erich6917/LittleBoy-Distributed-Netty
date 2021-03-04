package com.little.cloud.task;

import com.little.cloud.dto.FruitDto;
import java.util.Random;

/**
 * @Comment
 * @Author LiYuan
 * @Date 2021-3-3
 */
public class PrintSyncTask implements Runnable {

  private FruitDto object;

  public PrintSyncTask(FruitDto object) {
    this.object = object;
  }


  public void run() {
    synchronized (object) {
      System.out.println("START " + Thread.currentThread().getName());
      try {
        Thread.sleep(new Random().nextInt(2000) + 1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      System.out.println("END " + Thread.currentThread().getName());

    }
  }
}
