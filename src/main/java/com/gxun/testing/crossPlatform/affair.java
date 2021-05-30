package com.gxun.testing.crossPlatform;

import com.gxun.entity.WebScript;
import com.gxun.services.ScriptServices;
import com.gxun.services.WebScriptServices;
import com.gxun.testing.concurrent.PostRequestTest;
import com.gxun.util.RandIdUtil;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Component
public class affair {
    private static WebScriptServices webScriptServices;

    @Autowired
    public affair(WebScriptServices webScriptServices) {
        mapPool=new HashMap<>();
        affair.webScriptServices = webScriptServices;
    }
    private static Map<String,Object> mapPool;
    public static void startTrans(WebDriver driver, String transName){
        mapPool.put(transName,System.currentTimeMillis());
    }
    public static void endTrans(WebDriver driver,String transName){
        long endTime=System.currentTimeMillis();
        long startTime= (long) mapPool.get(transName);
        String timesTamp=String.valueOf(endTime-startTime);
        WebScript webScript = new WebScript();
        webScript.setId(RandIdUtil.randomWebLoadId());
        webScript.setEndTime(Long.toString(endTime));
        webScript.setStartTime(Long.toString(startTime));
        webScript.setScriptName(transName);
        webScript.setBrowserName("chrome");
        webScript.setSystemName("win10");
        webScript.setLoadTime(timesTamp);
        webScriptServices.addWebScript(webScript);
        System.out.println("timesTamp时间:  "+timesTamp );
        mapPool.remove(transName);

    }
    public static Map<String,Object>  getData(){
        return mapPool;
    }
}
