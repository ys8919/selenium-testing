package com.gxun.testing.concurrent;

import com.gxun.entity.Script;
import com.gxun.services.ScriptServices;
import com.gxun.util.RandIdUtil;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.seleniumhq.jetty9.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

@Component
public class postRequest {

    //@Autowired
    private static ScriptServices scriptServices;

    @Autowired
    public postRequest(ScriptServices scriptServices) {
        postRequest.scriptServices = scriptServices;
    }
    public static int success=0;
    public static int fail=0;
    public static int NO200=0;
    public static int response_NO=0;
    public static int response_5=0;
    public static int response_6=0;
    public static void postRequestTest(String url, String urlFormat, String para3, String vSuccess) throws Exception {

        Script requestScript = new Script();
        requestScript.setId(RandIdUtil.randomScriptId());
        requestScript.setScriptName("测试1");
        requestScript.setProcessId(Thread.currentThread().getName());
        long begaintime = System.currentTimeMillis();//开始系统时间
        CloseableHttpClient httpclient = HttpClients.createDefault();
        //String url = "https://www.baidu.com";
        //String url = "http://134.175.222.24:9090/promotion/commodity/queryCommodityList";
        //String url = "http://127.0.0.1:9090/promotion/commodity/queryCommodityListIndex";

        //设置 params 参数-------------设置了body就不能再设置params
        //String params = "";
        //String charSet = "UTF-8";
        //StringEntity entity = new StringEntity(params, charSet);
        //httpPost.setEntity(entity);

        CloseableHttpResponse response = null;

        try {
            switch (urlFormat){
                case "GET":
                    HttpGet httpGet = new HttpGet(url);// 创建httpPost
                    httpGet.setHeader("Content-Type", "application/json;charset=UTF-8");
                    httpGet.setHeader("Date:", new Date().toString());
                    httpGet.setHeader("X-Accept-Locale", "zh_CN");
                    response = httpclient.execute(httpGet);
                    break;
                case "POST":
                    HttpPost httpPost = new HttpPost(url);// 创建httpPost
                    //httpPost.setHeader("Authorization", "Bearer  qwertyu12345678zxcvbnm");
                    httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
                    //httpPost.setHeader("time", "11234567890");
                    httpPost.setHeader("Date:", new Date().toString());
                    httpPost.setHeader("X-Accept-Locale", "zh_CN");
                    //添加 body 参数
                    //String orderToken = postRequest1(para1);  //从上一个接口的返回数据里面获取参数
                    //String body = String.format("{\"Name\":\"%s\",\"age\":\"%s\",\"address\":\"%s\"}", para2, para3, orderToken);
                    //String body = String.format("{\"limit\":\"%s\",\"page\":\"%s\"}", 5, 1);
                    String body ="";
                    if(para3!=null&&!para3.equals("")){
                        body=para3;
                    }
                    System.out.println(body);
                    httpPost.setEntity(new StringEntity(body));
                    response = httpclient.execute(httpPost);
                    break;
                case "PUT":
                    HttpPut httpPut = new HttpPut(url);// 创建httpPost
                    httpPut.setHeader("Content-Type", "application/json;charset=UTF-8");
                    httpPut.setHeader("Date", new Date().toString());
                    httpPut.setHeader("X-Accept-Locale", "zh_CN");
                    String body1 ="";
                    if(para3!=null&&!para3.equals("")){
                        body1=para3;
                    }
                    httpPut.setEntity(new StringEntity(body1));
                    response = httpclient.execute(httpPut);
                    break;
                case "DELETE":
                    HttpDelete httpDelete = new HttpDelete(url);// 创建httpPost
                    httpDelete.setHeader("Content-Type", "application/json;charset=UTF-8");
                    httpDelete.setHeader("Date:", new Date().toString());
                    httpDelete.setHeader("X-Accept-Locale", "zh_CN");
                    response = httpclient.execute(httpDelete);
                    break;
                default:
                    break;
            }

            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();

            if (state == HttpStatus.OK_200) {
                HttpEntity responseEntity = response.getEntity();
                String jsonString = EntityUtils.toString(responseEntity);
                System.out.println("TakegoOrder 接口请求成功");
                //return jsonString;
                System.out.println(jsonString);
                //if (jsonString.contains("\"success\":true") && jsonString.contains("\"time\":\"2018")) {
                if(vSuccess!=null&&!vSuccess.equals("")){
                    //String[] vsu=vSuccess.split(";");
                    if (jsonString.contains(vSuccess)) {
                        success++;
                        requestScript.setType(1);
                        System.out.println("成功查询");
                    } else {
                        fail++;
                        requestScript.setType(2);
                        System.err.println("查询失败！！----");
                        //System.err.println("查询失败！！----");
                    }
                }else {
                    success++;
                    requestScript.setType(1);
                    System.out.println("成功查询！！！！");
                }

                /*success++;
                requestScript.setType(1);
                System.out.println("成功查询！！！！");*/
            } else {
                fail++;
                requestScript.setType(2);
                System.err.println("请求返回:" + state + "(" + url + ")");
            }

        }catch (IOException e){
            e.printStackTrace();
            fail++;
            requestScript.setType(2);
            System.out.println("请求失败");

        }
        finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            long endTime = System.currentTimeMillis(); //结束时间
            System.out.println("线程： "+Thread.currentThread().getName()+" 执行开始时间 "+begaintime+"  接口请求耗时 ： " + (endTime - begaintime));
            requestScript.setStartTime(Long.toString(begaintime));
            requestScript.setEndTime(Long.toString(endTime));
            scriptServices.addScript(requestScript);
        }
        //return null;
    }
}
