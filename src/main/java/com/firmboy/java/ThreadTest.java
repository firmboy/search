package com.firmboy.java;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author playboy
 * @create 2019-11-12 7:11 下午
 * @description 线程测试
 * @serviceLogic
 **/
public class ThreadTest {

    static ExecutorService executorService = Executors.newCachedThreadPool();


    public static void main(String[] args) {

        List<String> list = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            list.add("" + i);
        }
        List<List<String>> listGroup = new ArrayList<>(2);
        for (int i = 0; i < 2; i++) {
            listGroup.add(list);
        }

        CountDownLatch latchGoup = new CountDownLatch(listGroup.size());
        listGroup.forEach(list1 -> {
            Runnable runnableG = new Runnable() {
                @Override
                public void run() {
                    CountDownLatch countDownLatch = new CountDownLatch(list1.size());
                    list1.forEach(s -> {
                        //多线程生成vouHead
                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                countDownLatch.countDown();
                            }
                        };
                        executorService.submit(runnable);
                    });
                    //主线程等待子线程全部结束
                    try {
                        countDownLatch.await();
//                        System.out.println("concurrency counts = " + (50 - countDownLatch.getCount()));
                    } catch (InterruptedException e) {
                    } finally {
                        latchGoup.countDown();
                    }

                }
            };
            executorService.submit(runnableG);
        });


        try {
            latchGoup.await();
        } catch (InterruptedException e) {
        }
        executorService.shutdown();
    }
}
