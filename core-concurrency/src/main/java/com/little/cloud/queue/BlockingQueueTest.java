package com.little.cloud.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @Comment
 * @Author LiYuan
 * @Date 2021-1-15
 */
public class BlockingQueueTest {


  class Basket {

    BlockingQueue<String> basket = new ArrayBlockingQueue<String>(10);

    public void produce() {
    }

    public void resume() {

    }

  }


}
