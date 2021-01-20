package com.little.cloud.queue;

import java.util.concurrent.ArrayBlockingQueue;
import lombok.extern.slf4j.Slf4j;

/**
 * @Comment
 * @Author LiYuan
 * @Date 2021-1-15
 */
@Slf4j
public class ArrayBlockingQueueTest {

  public static void main(String[] args) {

    ArrayBlockingQueueTest test = new ArrayBlockingQueueTest();
    test.demo1();
    ;
  }

  public void demo1() {
    ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<Integer>(10);

    /**
     * API ADD && REMOVE && ELEMENT
     * 越界异常
     */

    int size = 5;


    queue.add(3);
    queue.add(5);
    queue.add(7);

    log.info("RESULT {}", queue.toString());

    queue.remove();
    log.info("REMOVE ONE RESULT {}", queue.toString());
    queue.remove();
    log.info("REMOVE ONE RESULT {}", queue.toString());
    queue.remove();
    log.info("REMOVE ONE RESULT {}", queue.toString());
    queue.remove();
    log.info("REMOVE ONE RESULT {}", queue.toString());

  }

}
