package com.gxun.testing.crossPlatform;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;

public class crossPlatform {

public static void crossPlatformStart()  {
      /*DesiredCapabilities cap = new DesiredCapabilities();
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.PERFORMANCE, Level.ALL);
        cap.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        System.setProperty("webdriver.chrome.driver", "C:\\WebDriver\\bin\\chromedriver.exe");
        WebDriver driver = new ChromeDriver(cap);*/
    DesiredCapabilities capability = new DesiredCapabilities();
    ChromeOptions chromeOptions = new ChromeOptions();
    capability.setBrowserName("chrome");
    capability.setPlatform(Platform.WIN10);
    try{
        WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), chromeOptions);

        //脚本编写区

        driver.get("http://localhost:63342/CurriculumDesign3_Front/login.html?_ijt=nlooc7vb9eso5a15tls422s265");
        driver.manage().window().setSize(new Dimension(1936, 1056));
        driver.findElement(By.cssSelector(".layui-icon > .layui-input")).click();
        driver.findElement(By.cssSelector(".layui-icon > .layui-input")).sendKeys("1002");
        driver.findElement(By.name("passwd")).click();
        driver.findElement(By.name("passwd")).sendKeys("123456");
        //添加事务开始点
        affair.startTrans(driver,"login");
        driver.findElement(By.cssSelector(".layui-form-item:nth-child(6) .layui-btn")).click();
        //添加事务结束点
        affair.endTrans(driver,"login");

        //脚本编写区

    }catch (MalformedURLException e){
        e.printStackTrace();
    }
}
}
