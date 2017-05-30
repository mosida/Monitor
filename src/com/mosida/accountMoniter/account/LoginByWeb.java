package com.mosida.accountMoniter.account;

import com.mosida.accountMoniter.MainReviews;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 * Created by mosida on 5/15/17.
 */
public class LoginByWeb {

    public static boolean loginByChrome(GmailInfo gmailInfo){
        ChromeOptions options = new ChromeOptions();
        // 设置隐身模式
        options.addArguments("--incognito");
        // 设置语言
        options.addArguments("--lang=en");
//            // 设置 ua
//            options.addArguments("--user-agent=Mozilla/5.0 (iPad; CPU OS 5_0 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A334 Safari/7534.48.3");

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        //引入geckodriver驱动
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        //新建一个 Chrome 浏览器实例
        WebDriver driver = new ChromeDriver(capabilities);
        try {
            //// 等待 10s
            //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            //打开 Google
            driver.get("https://www.google.com?hl=en");
            // 点击登录
            WebElement webElement = null;
            webElement = driver.findElement(By.linkText("Sign in"));
            webElement.click();

            // 旧版登录入口
            if (driver.getCurrentUrl().contains("accounts.google.com/ServiceLogin?")) {
                driver.findElement(By.id("Email")).clear();
                driver.findElement(By.id("Email")).sendKeys(gmailInfo.email);
                sleepSecond();
                driver.findElement(By.id("next")).click();
                sleepQuick();
                driver.findElement(By.name("Passwd")).clear();
                driver.findElement(By.name("Passwd")).sendKeys(gmailInfo.password);
                sleepSecond();
                driver.findElement(By.id("signIn")).click();
                sleepSecond();
                driver.findElement(By.xpath(".//*[text()='Confirm your recovery email']")).click();
                sleepSecond();
                driver.findElement(By.name("email")).sendKeys(gmailInfo.recMail);
                sleepQuick();
                driver.findElement(By.id("submit")).click();

            } else {
                driver.findElement(By.id("identifierId")).clear();
                driver.findElement(By.id("identifierId")).sendKeys(gmailInfo.email);
                sleepSecond();
                driver.findElement(By.id("identifierNext")).click();
                sleepQuick();
                driver.findElement(By.name("password")).clear();
                driver.findElement(By.name("password")).sendKeys(gmailInfo.password);
                sleepSecond();
                driver.findElement(By.id("passwordNext")).click();
                sleepSlow();
                if (driver.getCurrentUrl().contains("hl=en&gws_rd")) {
                    driver.quit();
                    return true;
                }
                sleepQuick();
                try {
                    WebElement webElement1 = driver.findElement(By.ByClassName.className("vdE7Oc"));
                    if (webElement1 != null) {
                        webElement1.click();
                        sleepQuick();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        driver.findElement(By.id("knowledge-preregistered-email-response")).sendKeys(gmailInfo.recMail);
                        sleepSecond();
                        driver.findElement(By.id("next")).click();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            sleepSlow();
            sleepSlow();

            if (driver.getCurrentUrl().contains("https://myaccount.google.com/interstitials/recoveryoptions?hl=en")) {
                // 销毁
                driver.quit();
                return true;
            }
            if (driver.getCurrentUrl().contains("https://www.google.com/?hl=en")) {
                // 销毁
                driver.quit();
                return true;
            }
            if (driver.getCurrentUrl().contains("newfeatures")) {
                // 销毁
                driver.quit();
                return true;
            }
            if (driver.getCurrentUrl().contains("hl=en&gws_rd")) {
                // 销毁
                driver.quit();
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //MainReviews.logger.info("Fail : "+driver.getCurrentUrl());
            // 销毁
            driver.quit();
        }

        return true;
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

    public static void sleepSecond(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
