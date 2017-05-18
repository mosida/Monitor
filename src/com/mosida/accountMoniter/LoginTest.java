package com.mosida.accountMoniter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created by mosida on 4/23/17.
 */
public class LoginTest {

    public  static Logger logger = LogManager.getLogger(Main.class);


    public static void main(String[] args) throws Exception {
        String info = "LoginTest";
        System.setProperty("log4j2.saveFile",info);
        org.apache.logging.log4j.core.Logger rootLogger = (org.apache.logging.log4j.core.Logger) LogManager
                .getRootLogger();
        LoggerContext context = rootLogger.getContext();
        context.reconfigure();

        ChromeOptions options = new ChromeOptions();
        // 设置隐身模式
        options.addArguments("--incognito");
        // 设置语言
        options.addArguments("--lang=en");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        //引入geckodriver驱动
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        //新建一个 Chrome 浏览器实例
        WebDriver driver = new ChromeDriver(capabilities);

        driver.get("https://www.google.com?hl=en");

        WebElement webElement = null;
        webElement = driver.findElement(By.linkText("Sign in"));
        webElement.click();

        sleepQuick();

        driver.findElement(By.id("Email")).clear();
        driver.findElement(By.id("Email")).sendKeys("QuintionFlynn6979858");
        driver.findElement(By.id("next")).click();
        sleepQuick();

        driver.findElement(By.id("Passwd")).clear();
        driver.findElement(By.id("Passwd")).sendKeys("redwater0342");
        driver.findElement(By.id("signIn")).click();

        sleepQuick();
        driver.findElement(By.xpath(".//*[@type='text' and @class='Kc fh Q-Sb-Q']")).sendKeys("mosidahuang@gmail.com");
        logger.info("设置辅助邮箱:"+"mosidahuang@gmail.com");
        logger.info("点击完成");
        driver.findElement(By.xpath(".//*[@role='button' and text()='Done']")).click();


    }


    public static void sleepSlow(){
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void sleepQuick(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
