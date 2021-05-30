package com.gxun.testing.concurrent;

import com.gxun.services.ScriptServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;



@Component
public class PostRequestTest {

    private static ScriptServices scriptServices;

    @Autowired
    public PostRequestTest(ScriptServices scriptServices) {
        PostRequestTest.scriptServices = scriptServices;
    }
    public static HashMap<String,Object> postRequestTestStart(HashMap<String,Object> hashMap)  {

        HashMap<String, Object> msg = new HashMap<>();
        //删除所有脚本
        scriptServices.deleteAllScript();
        String url=hashMap.get("url").toString();
        String urlFormat=hashMap.get("urlFormat").toString();
        String vuser=hashMap.get("vuser").toString();
        String vBody=hashMap.get("vBody").toString();
        String vSuccess=hashMap.get("vSuccess").toString();
        String time=hashMap.get("impTime").toString();
        //执行时间
        long ts = 1000;
        System.out.println(time);
        String[] times=time.split(":");
        ts=Long.parseLong(times[0])*(3600*1000)+Long.parseLong(times[1])*(60*1000)+Long.parseLong(times[2])*1000;
        System.out.println(ts);
        long begaintime = System.currentTimeMillis();//开始系统时间

        //线程池
        ExecutorService pool = Executors.newCachedThreadPool();
        //设置集合点为93
        final int count = Integer.parseInt(vuser);
        CountDownLatch countDownLatch = new CountDownLatch(count);//与countDownLatch.await();实现运行完所有线程之后才执行后面的操作
        //final CyclicBarrier barrier = new CyclicBarrier(count);  //与barrier.await() 实现并发;
        CyclicBarrier barrier = new CyclicBarrier(count,new Runnable() {

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " 并发执行");
            }
        });  //与barrier.await() 实现并发;
        //创建100个线程
        for(int i = 0; i < count; i++){

            MyThread target = new MyThread(url, urlFormat, ts, vBody,vSuccess, countDownLatch,barrier);

            pool.execute(target);
        }

        pool.shutdown();
        try {
            countDownLatch.await();  //这一步是为了将全部线程任务执行完以后，开始执行后面的任务（计算时间，数量）
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long endTime = System.currentTimeMillis(); //结束时间
        System.out.println("启动时间 "+begaintime);
        HashMap<String, Object> map = new HashMap<>();
        ArrayList<Map> maps = new ArrayList<>();
        HashMap<String, Object> msgdata=null;
        long ll;
        long l = System.currentTimeMillis();
        ll = l;
        long countTime=begaintime;
        while (true) {
            l = System.currentTimeMillis();
            long d = l - ll;
//            if (d % 1000 == 0) {
                map.put("time",countTime);
                msgdata= (HashMap<String, Object>) scriptServices.selectScriptCount(map);
                msgdata.put("time_d",countTime);
                maps.add(msgdata);
                countTime+=1000;
                System.out.println("cout  "+msgdata.toString());
           /* }*/
            if (countTime > begaintime+ts+3000) {
                System.out.println("倒计时结束！");
                break;
            }
        }
        msg.put("responseTime",scriptServices.selecResponseTime());
        msg.put("countTime",endTime-begaintime);
        msg.put("successResponse",postRequest.success);
        msg.put("failResponse",postRequest.fail);
        System.out.println(count + " 个  接口请求总耗时 ： "+(endTime-begaintime)+"-----平均耗时为"+ ((endTime-begaintime)/count));
        //System.out.println("请求成功数："+postRequest.success+"   请求失败数："+postRequest.fail);
        System.out.println("success："+postRequest.success+"   fail："+postRequest.fail);
        //msg.put("msg","执行完成");
        msg.put("successPer",maps);
        return msg;
    }
}
