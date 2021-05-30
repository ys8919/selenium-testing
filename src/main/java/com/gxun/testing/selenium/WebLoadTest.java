package com.gxun.testing.selenium;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gxun.testing.selenium.vo.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;

public class WebLoadTest {
    // 阻塞队列长度
    private static final int BLOCK_QUEUE_SIZE = 100;
    // 浏览器driver线程数
    private static final int THREAD_SIZE = 1;
    // Fired when page is about to send HTTP request.
    public static final String NETWORK_REQUEST_WILL_BE_SENT = "Network.requestWillBeSent";
    // Fired when HTTP response is available.
    public static final String NETWORK_RESPONSE_RECEIVED = "Network.responseReceived";
    // Fired when HTTP request has finished loading.
    public static final String NETWORK_LOADING_FAILED = "Network.loadingFailed";
    // Fired when HTTP request has failed to load.
    public static final String NETWORK_LOADING_FINISHED = "Network.loadingFinished";
    // DOM Length JS
    public static final String JS_DOM_LENGTH = "return document.getElementsByTagName('*').length";
    // ScrollingTop JS
    public static final String JS_SCROLLINGTOP = "return $(window).scrollTop( {0} * 1000)";
    // Scrolling Y JS
    public static final String JS_SCROLLINGY = "return window.scrollY";
    // Performance Timing JS
    public static final String JS_PERFORMANCE_TIMING = "return performance.timing";

    private static final String chromedriverPath = "C:\\WebDriver\\bin\\chromedriver.exe";


    private static final BlockingQueue<String> reqQuene = new ArrayBlockingQueue<String>(BLOCK_QUEUE_SIZE);

    private static final HashMap<String, Object> hashMap = new HashMap<>();
    static class DriverRunnable implements Runnable {
        private WebDriver driver;
        CountDownLatch latch;
        public DriverRunnable(CountDownLatch latch ) {
            this.latch=latch;
           /* System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY,
                    System.getProperty("user.dir") + "/target/chromedriver.log");
           *//* System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY,
                    System.getProperty("user.dir") + "/drivers/chromedriver"); *//*
            System.setProperty(ChromeDriverService.CHROME_DRIVER_EXE_PROPERTY,
                    "C:\\WebDriver\\bin\\chromedriver.exe");
            //System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\bin\\chromedriver.exe");*/

          /*  ChromeDriverService chromeDriverService = new ChromeDriverService.Builder()
                    .usingDriverExecutable(new File(chromedriverPath))
                    .withVerbose(true)
                    .usingAnyFreePort()
                    .build();*/
            System.setProperty("webdriver.chrome.driver",chromedriverPath);
            ChromeOptions options = new ChromeOptions();
            // options.addArguments("--headless");
            options.addArguments("--window-size=1980,1000");
            options.addArguments("--disable-web-security");
            // options.addArguments("--start-fullscreen");
            // options.addArguments("--screenshot");
            // options.addArguments("--golden-screenshots-dir=" + chromedriverPath);


            DesiredCapabilities cap = new DesiredCapabilities();
            LoggingPreferences logPrefs = new LoggingPreferences();
            logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
            cap.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
            cap.setCapability(ChromeOptions.CAPABILITY, options);

            driver = new ChromeDriver( cap);
        }

        @Override
        public void run() {
            while (true) {
                try {
                    String url = reqQuene.take();
                    TotalPerformanceDTO totalPerformance = new TotalPerformanceDTO();
                    totalPerformance.setFirstScreenPerformance(
                            detectFirstScreenPerformance(url, driver,latch));
                    /*totalPerformance.setFullPagePerformance(
                            detectFullPagePerformance(url, driver));*/
                    latch.await();
                    System.out.println(totalPerformance.toString());


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 首屏数据统计
     *
     * @param url
     * @param driver
     * @return
     */
    private static FirstScreenPerformanceDTO detectFirstScreenPerformance(String url, WebDriver driver,CountDownLatch latch) {
        driver.get(url);
        // js操作对象
        JavascriptExecutor js = (JavascriptExecutor) driver;

        FirstScreenPerformanceDTO firstScreenPerformance = new FirstScreenPerformanceDTO();
        List<NetworkRequestWillBeSentDTO> firstscreenRequestList =
                new ArrayList<NetworkRequestWillBeSentDTO>();
        List<NetworkResponseReceivedDTO> firstscreenResponseList =
                new ArrayList<NetworkResponseReceivedDTO>();
        List<NetworkLoadingFailedDTO> firstscreenFailList =
                new ArrayList<NetworkLoadingFailedDTO>();
        List<NetworkLoadingFinishedDTO> firstscreenFinishedList =
                new ArrayList<NetworkLoadingFinishedDTO>();
        int pageRequestNum = 0;
        double pageSize = 0.0;
        for (LogEntry entry : driver.manage().logs().get(LogType.PERFORMANCE)) {
            JSONObject jsonObj = JSON.parseObject(entry.getMessage()).getJSONObject("message");
            String method = jsonObj.getString("method");
            String params = jsonObj.getString("params");

            if (method.equals(NETWORK_REQUEST_WILL_BE_SENT)) {

                NetworkRequestWillBeSentDTO request = JSON.parseObject(
                        params, NetworkRequestWillBeSentDTO.class);
                pageRequestNum++;
                System.out.println(method + ":" + request.toString());
                firstscreenRequestList.add(request);
            } else if (method.equals(NETWORK_RESPONSE_RECEIVED)) {

                NetworkResponseReceivedDTO response = JSON.parseObject(
                        params, NetworkResponseReceivedDTO.class);
                System.out.println(method + ":" + response.getResponse().getUrl());
                firstscreenResponseList.add(response);
            } else if (method.equals(NETWORK_LOADING_FINISHED)) {

                NetworkLoadingFinishedDTO finished = JSON.parseObject(
                        params, NetworkLoadingFinishedDTO.class);
                pageSize += finished.getEncodedDataLength();
                System.out.println(method + ":" + finished.toString());
                firstscreenFinishedList.add(finished);
            } else if (method.equals(NETWORK_LOADING_FAILED)) {

                NetworkLoadingFailedDTO failed = JSON.parseObject(
                        params, NetworkLoadingFailedDTO.class);
                System.out.println(method + ":" + failed.toString());
                firstscreenFailList.add(failed);
            }
        }
        // 获取首屏DOM数
        firstScreenPerformance.setPageDomNum(executeDomLengthJS(js));
        // 获取首屏DOM加载完成时间 和 首屏完全加载完成时间
        PerformanceTimingDTO performanceTiming = executePerformanceTimingJS(js);
        firstScreenPerformance.setDomContentLoadedCost(
                performanceTiming.getDomContentLoadedEventEnd() - performanceTiming.getConnectStart());
        firstScreenPerformance.setLoadEventCost(
                performanceTiming.getLoadEventEnd() - performanceTiming.getConnectStart());
        // 获取首屏大小
        firstScreenPerformance.setPageSize(pageSize / (1000 * 1000));
        // 获取首屏请求数
        firstScreenPerformance.setPageRequestNum(pageRequestNum);
        System.out.println("页面" + url + "：首屏请求数:"
                + firstScreenPerformance.getPageRequestNum() + ", 首屏大小:"
                + firstScreenPerformance.getPageSize() + "MB, 首屏DOM总数:"
                + firstScreenPerformance.getPageDomNum() + ", 首屏DOM加载完成时间:"
                + firstScreenPerformance.getDomContentLoadedCost() + "ms, 首屏完全加载完成时间:"
                + firstScreenPerformance.getLoadEventCost() + "ms");
        // 分析异常响应
        PageErrorsDTO pageErrorsDTO = new PageErrorsDTO();
        pageErrorsDTO.setCodeErrorResponseList(
                analysisCodeErrorResponse(firstscreenResponseList));
        if (!pageErrorsDTO.getCodeErrorResponseList().isEmpty()) {
            System.out.println("首屏异常响应：");
            for (int i = 0; i < pageErrorsDTO.getCodeErrorResponseList().size(); i++) {
                System.out.println(pageErrorsDTO.
                        getCodeErrorResponseList().get(i).getUrl() + ": " + pageErrorsDTO.
                        getCodeErrorResponseList().get(i).getStatus());
            }
        }
        // 分析失败响应
        pageErrorsDTO.setFailResponseList(
                analysisFailResponse(firstscreenFailList, firstscreenRequestList));
        if (!pageErrorsDTO.getFailResponseList().isEmpty()) {
            System.out.println("首屏失败响应：");
            for (int i = 0; i < pageErrorsDTO.getFailResponseList().size(); i++) {
                System.out.println(pageErrorsDTO.
                        getFailResponseList().get(i).getUrl() + ": " + pageErrorsDTO.
                        getFailResponseList().get(i).getErrorText() + " " + pageErrorsDTO.
                        getFailResponseList().get(i).getBlockedReason());
            }
        }
        // 分析慢响应
        pageErrorsDTO.setSlowReponseList(
                analysisSlowResponse(firstscreenRequestList, firstscreenFinishedList, 3.0));
        if (!pageErrorsDTO.getSlowReponseList().isEmpty()) {
            System.out.println("首屏慢响应：");
            for (int i = 0; i < pageErrorsDTO.getSlowReponseList().size(); i++) {
                System.out.println(pageErrorsDTO.
                        getSlowReponseList().get(i).getUrl() + ": " + pageErrorsDTO.
                        getSlowReponseList().get(i).getCost());
            }
        }
        firstScreenPerformance.setPageErrorsDTO(pageErrorsDTO);
        driver.quit();
        return firstScreenPerformance;
    }


    /**
     * 全页面数据统计
     *
     * @param url
     * @param driver
     * @return
     */
    /*private static FullPagePerformanceDTO detectFullPagePerformance(String url, WebDriver driver) {
        driver.get(url);
        // js操作对象
        JavascriptExecutor js = (JavascriptExecutor) driver;
        FullPagePerformanceDTO fullPagePerformance = new FullPagePerformanceDTO();

        // 滚动到页面底部
        //scrollToBottom(js);

        List<NetworkRequestWillBeSentDTO> fullPageRequestList =
                new ArrayList<NetworkRequestWillBeSentDTO>();
        List<NetworkResponseReceivedDTO> fullPageResponseList =
                new ArrayList<NetworkResponseReceivedDTO>();
        List<NetworkLoadingFailedDTO> fullPageFailList =
                new ArrayList<NetworkLoadingFailedDTO>();
        List<NetworkLoadingFinishedDTO> fullPageFinishedList =
                new ArrayList<NetworkLoadingFinishedDTO>();
        int pageRequestNum = 0;
        double pageSize = 0.0;
        for (LogEntry entry : driver.manage().logs().get(LogType.PERFORMANCE)) {
            JSONObject jsonObj = JSON.parseObject(entry.getMessage()).getJSONObject("message");
            String method = jsonObj.getString("method");
            String params = jsonObj.getString("params");

            if (method.equals(NETWORK_REQUEST_WILL_BE_SENT)) {

                NetworkRequestWillBeSentDTO request = JSON.parseObject(
                        params, NetworkRequestWillBeSentDTO.class);
                pageRequestNum++;
                System.out.println(method + ":" + request.toString());
                fullPageRequestList.add(request);
            } else if (method.equals(NETWORK_RESPONSE_RECEIVED)) {

                NetworkResponseReceivedDTO response = JSON.parseObject(
                        params, NetworkResponseReceivedDTO.class);
                System.out.println(method + ":" + response.getResponse().getUrl());
                fullPageResponseList.add(response);
            } else if (method.equals(NETWORK_LOADING_FINISHED)) {

                NetworkLoadingFinishedDTO finished = JSON.parseObject(
                        params, NetworkLoadingFinishedDTO.class);
                pageSize += finished.getEncodedDataLength();
                System.out.println(method + ":" + finished.toString());
                fullPageFinishedList.add(finished);
            } else if (method.equals(NETWORK_LOADING_FAILED)) {

                NetworkLoadingFailedDTO failed = JSON.parseObject(
                        params, NetworkLoadingFailedDTO.class);
                System.out.println(method + ":" + failed.toString());
                fullPageFailList.add(failed);
            }
        }
        // 获取全页面DOM数
        fullPagePerformance.setPageDomNum(executeDomLengthJS(js));
        // 获取全页面DOM加载完成时间 和 首屏完全加载完成时间
        PerformanceTimingDTO performanceTiming = executePerformanceTimingJS(js);
        fullPagePerformance.setDomContentLoadedCost(
                performanceTiming.getDomContentLoadedEventEnd() - performanceTiming.getConnectStart());
        fullPagePerformance.setLoadEventCost(
                performanceTiming.getLoadEventEnd() - performanceTiming.getConnectStart());
        // 获取全页面大小
        fullPagePerformance.setPageSize(pageSize / (1000 * 1000));
        // 获取全页面请求数
        fullPagePerformance.setPageRequestNum(pageRequestNum);
        System.out.println("页面" + url + "：全页面请求数:"
                + fullPagePerformance.getPageRequestNum() + ", 全页面大小:"
                + fullPagePerformance.getPageSize() + "MB, 全页面DOM总数:"
                + fullPagePerformance.getPageDomNum() + ", 全页面DOM加载完成时间:"
                + fullPagePerformance.getDomContentLoadedCost() + "ms, 全页面完全加载完成时间:"
                + fullPagePerformance.getLoadEventCost() + "ms");
        // 分析异常响应
        PageErrorsDTO pageErrorsDTO = new PageErrorsDTO();
        pageErrorsDTO.setCodeErrorResponseList(
                analysisCodeErrorResponse(fullPageResponseList));
        if (!pageErrorsDTO.getCodeErrorResponseList().isEmpty()) {
            System.out.println("全页面异常响应：");
            for (int i = 0; i < pageErrorsDTO.getCodeErrorResponseList().size(); i++) {
                System.out.println(pageErrorsDTO.
                        getCodeErrorResponseList().get(i).getUrl() + ": " + pageErrorsDTO.
                        getCodeErrorResponseList().get(i).getStatus());
            }
        }else{
            System.out.println("全页面异常响应：无");
        }
        // 分析失败响应
        pageErrorsDTO.setFailResponseList(
                analysisFailResponse(fullPageFailList, fullPageRequestList));
        if (!pageErrorsDTO.getFailResponseList().isEmpty()) {
            System.out.println("全页面失败响应：");
            for (int i = 0; i < pageErrorsDTO.getFailResponseList().size(); i++) {
                System.out.println(pageErrorsDTO.
                        getFailResponseList().get(i).getUrl() + ": " + pageErrorsDTO.
                        getFailResponseList().get(i).getErrorText() + " " + pageErrorsDTO.
                        getFailResponseList().get(i).getBlockedReason());
            }
        }else{
            System.out.println("全页面失败响应：无");
        }
        // 分析慢响应
        pageErrorsDTO.setSlowReponseList(
                analysisSlowResponse(fullPageRequestList, fullPageFinishedList, 3.0));
        if (!pageErrorsDTO.getSlowReponseList().isEmpty()) {
            System.out.println("全页面慢响应：");
            for (int i = 0; i < pageErrorsDTO.getSlowReponseList().size(); i++) {
                System.out.println(pageErrorsDTO.
                        getSlowReponseList().get(i).getUrl() + ": " + pageErrorsDTO.
                        getSlowReponseList().get(i).getCost());
            }
        }else{
            System.out.println("全页面慢响应：无");
        }
        fullPagePerformance.setPageErrorsDTO(pageErrorsDTO);
        //driver.quit();
        return fullPagePerformance;
    }*/

    /**
     * 滚动到页面底部
     *
     * @param js
     * @return
     */
    private static long scrollToBottom(JavascriptExecutor js) {
        long scrollStart = 0, scrollEnd = 1;
        int i = 1;
        while (scrollStart != scrollEnd) {
            scrollStart = (Long) js.executeScript(JS_SCROLLINGY);
            String scrollTo = MessageFormat.format(JS_SCROLLINGTOP, i++);
            System.out.println(scrollTo);
            js.executeScript(scrollTo);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            scrollEnd = (Long) js.executeScript(JS_SCROLLINGY);
            // System.out.println(scrollTo + ":" + scrollEnd);
        }
        return scrollEnd;
    }

    /**
     * 执行js，获取页面DOM数
     *
     * @param js
     * @return
     */
    private static long executeDomLengthJS(JavascriptExecutor js) {
        return (Long) js.executeScript(JS_DOM_LENGTH);
    }

    /**
     * 执行js，获取Performance Timing
     *
     * @param js
     * @return
     */
    private static PerformanceTimingDTO executePerformanceTimingJS(JavascriptExecutor js) {
        String performance = js.executeScript(JS_PERFORMANCE_TIMING).toString();
        performance = performance.replace("unloadEventEnd=", "\"unloadEventEnd\":")
                .replace("responseEnd=", "\"responseEnd\":")
                .replace("responseStart=", "\"responseStart\":")
                .replace("domInteractive=", "\"domInteractive\":")
                .replace("domainLookupEnd=", "\"domainLookupEnd\":")
                .replace("unloadEventStart=", "\"unloadEventStart\":")
                .replace("domComplete=", "\"domComplete\":")
                .replace("domContentLoadedEventStart=", "\"domContentLoadedEventStart\":")
                .replace("domainLookupStart=", "\"domainLookupStart\":")
                .replace("redirectEnd=", "\"redirectEnd\":")
                .replace("redirectStart=", "\"redirectStart\":")
                .replace("connectEnd=", "\"connectEnd\":")
                .replace("toJSON={},", "")
                .replace("connectStart=", "\"connectStart\":")
                .replace("loadEventStart=", "\"loadEventStart\":")
                .replace("navigationStart=", "\"navigationStart\":")
                .replace("requestStart=", "\"requestStart\":")
                .replace("secureConnectionStart=", "\"secureConnectionStart\":")
                .replace("fetchStart=", "\"fetchStart\":")
                .replace("domContentLoadedEventEnd=", "\"domContentLoadedEventEnd\":")
                .replace("domLoading=", "\"domLoading\":")
                .replace("loadEventEnd=", "\"loadEventEnd\":");
//        System.out.println(performance);
        return JSON.parseObject(
                performance, PerformanceTimingDTO.class);
    }

    /**
     * 分析异常状态码的响应
     *
     * @param networkResponseReceivedList
     * @return
     */
    private static List<CodeErrorResponseDTO> analysisCodeErrorResponse
    (List<NetworkResponseReceivedDTO> networkResponseReceivedList) {

        List<CodeErrorResponseDTO> codeErrorResponseList = new ArrayList<CodeErrorResponseDTO>();
        for (NetworkResponseReceivedDTO r : networkResponseReceivedList) {
            if (r.getResponse().getStatus() >= 400
                    && r.getResponse().getStatus() <= 599) {
                CodeErrorResponseDTO codeErrorResponseDTO = new CodeErrorResponseDTO();
                codeErrorResponseDTO.setUrl(r.getResponse().getUrl());
                codeErrorResponseDTO.setStatus(r.getResponse().getStatus());
                System.out.println(r.toString());

                codeErrorResponseList.add(codeErrorResponseDTO);
            }
        }

        return codeErrorResponseList;
    }

    /**
     * 分析失败的响应
     *
     * @param networkLoadingFailedList
     * @param networkRequestWillBeSentList
     * @return
     */
    private static List<FailResponseDTO> analysisFailResponse(
            List<NetworkLoadingFailedDTO> networkLoadingFailedList,
            List<NetworkRequestWillBeSentDTO> networkRequestWillBeSentList) {
        List<FailResponseDTO> failResponseList = new ArrayList<FailResponseDTO>();
        for (NetworkLoadingFailedDTO f : networkLoadingFailedList) {
            for (NetworkRequestWillBeSentDTO r : networkRequestWillBeSentList) {
                if (f.getRequestId().equals(r.getRequestId())) {
                    FailResponseDTO failResponseDTO = new FailResponseDTO();
                    failResponseDTO.setUrl(r.getRequest().getUrl());
                    failResponseDTO.setErrorText(f.getErrorText());
                    failResponseDTO.setBlockedReason(f.getBlockedReason());

                    failResponseList.add(failResponseDTO);
                }
            }
        }

        return failResponseList;
    }

    /**
     * 分析慢响应，单位s
     *
     * @param networkRequestWillBeSentList
     * @param networkLoadingFinishedList
     * @param slowThreshold
     * @return
     */
    private static List<SlowReponseDTO> analysisSlowResponse(
            List<NetworkRequestWillBeSentDTO> networkRequestWillBeSentList,
            List<NetworkLoadingFinishedDTO> networkLoadingFinishedList,
            double slowThreshold) {
        List<SlowReponseDTO> slowReponseList = new ArrayList<SlowReponseDTO>();
        for (NetworkRequestWillBeSentDTO r : networkRequestWillBeSentList) {
            for (NetworkLoadingFinishedDTO f : networkLoadingFinishedList) {
                if (r.getRequestId().equals(f.getRequestId())) {
                    double cost = f.getTimestamp() - r.getTimestamp();
                    if (cost >= slowThreshold) {
                        SlowReponseDTO slowReponseDTO = new SlowReponseDTO();
                        slowReponseDTO.setUrl(r.getRequest().getUrl());
                        slowReponseDTO.setCost(cost);

                        slowReponseList.add(slowReponseDTO);
                    }
                }
            }
        }

        return slowReponseList;
    }

   /* public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < THREAD_SIZE; i++) {
            Thread driverThread = new Thread(new DriverRunnable(), "driverThread" + i);
            driverThread.start();
        }
        for (int i = 0; i < 1; i++) {
            //reqQuene.put("http://localhost:63342/CurriculumDesign3_Front/login.html?_ijt=ths502ob76o52qf5fcjcm0u81e");
            reqQuene.put("http://134.175.222.24:9091/");
            //reqQuene.put("http://134.175.222.24/");
            //reqQuene.put("https://www.suning.com");
        }
    }*/

   /* public static HashMap<String,Object> webLoadTestStart(String url)  {
        CountDownLatch latch = new CountDownLatch(1);
        for (int i = 0; i < THREAD_SIZE; i++) {
            Thread driverThread = new Thread(new DriverRunnable(latch), "driverThread" + i);
            driverThread.start();
        }
        try{
            for (int i = 0; i < 1; i++) {
                reqQuene.put(url);
            }

        }catch (InterruptedException e) {
            e.printStackTrace();
        }

        return hashMap;
    }*/
    public static HashMap<String,Object> webLoadTestStart(String url)  {
        WebDriver driver;
        CountDownLatch latch = new CountDownLatch(1);
        System.setProperty("webdriver.chrome.driver",chromedriverPath);
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless");
        options.addArguments("--window-size=1980,1000");
        options.addArguments("--disable-web-security");

        DesiredCapabilities cap = new DesiredCapabilities();
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        cap.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        cap.setCapability(ChromeOptions.CAPABILITY, options);

        driver = new ChromeDriver( cap);
        try {
            //String url = reqQuene.take();
            TotalPerformanceDTO totalPerformance = new TotalPerformanceDTO();
           /* totalPerformance.setFirstScreenPerformance(
                    detectFirstScreenPerformance(url, driver));*/
                    /*totalPerformance.setFullPagePerformance(
                            detectFullPagePerformance(url, driver));*/
            hashMap.put("firstScreenPerformance",detectFirstScreenPerformance(url, driver,latch));
            //latch.await();
            System.out.println(totalPerformance.toString());


        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashMap;
    }

    /*WebLoadTest(){
        for (int i = 0; i < THREAD_SIZE; i++) {
            Thread driverThread = new Thread(new DriverRunnable(), "driverThread" + i);
            driverThread.start();
        }
    }*/

}
