package com.gxun.testing.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

class MyThread implements Runnable {

    private String url;
    private String urlFormat;
    private String para3;
    private String vSuccess;
    private CountDownLatch countDownLatch;  //多线程结束后，执行后面的代码（计算时间、数量）
    private CyclicBarrier barrier;
    private Long time;

    public MyThread(String url, String urlFormat, Long time ,String para3, String vSuccess, CountDownLatch countDownLatch, CyclicBarrier barrier) {
        this.url = url;
        this.urlFormat = urlFormat;
        this.para3 = para3;
        this.countDownLatch = countDownLatch;
        this.barrier = barrier;
        this.time = time;
        this.vSuccess = vSuccess;
    }

    @Override
    public void run() {

        long start= System.currentTimeMillis();
        long end= start;
        try{
            barrier.await();
            do {
                end = System.currentTimeMillis();
                postRequest.postRequestTest(url, urlFormat, para3,vSuccess);
            } while ((end - start) <= time);

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            countDownLatch.countDown();
        }
    }


}
