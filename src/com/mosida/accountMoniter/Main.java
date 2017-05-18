package com.mosida.accountMoniter;

import com.google.network.*;
import com.mosida.accountMoniter.account.AccountInfoBuilder;
import com.mosida.accountMoniter.account.RegisterAccount;
import org.apache.http.util.TextUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.HashMap;
import java.util.Random;

public class Main {
    public static boolean requestUseProxy = false;
    public static boolean socks5Proxy = false;
    public static int timeSplit = 1000;
    //加入指定的城市,输入指定的城市，
    public static String specficCountry = null;
    public static String vpnCountry = "TW";
    public static Logger logger = LogManager.getLogger(Main.class);
    public static int num = 5000;

    public static int loop = 0;
    public static boolean ipSuccess = false;

    public static void main(String[] args) throws Exception{
        // log 配置
        String info = "M-AccountRegister";
        System.setProperty("log4j2.saveFile",info);
        org.apache.logging.log4j.core.Logger rootLogger = (org.apache.logging.log4j.core.Logger) LogManager
                .getRootLogger();
        LoggerContext context = rootLogger.getContext();
        context.reconfigure();

        for (int i=0; i<num; i++){
//            if (ipSuccess == false || loop >= 10) {
//                // vpn
//                List<String> countryList = VpnParseUtils.getVpnSupportCountry(Main.vpnCountry);
//                IpUtils.changeVpn(countryList);
//
//                ipSuccess = false;
//                loop = 0;
//            }

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
            //// 等待 10s
            //driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            //打开 Google
            driver.get("https://accounts.google.com/embedded/setup/chrome/usermenu?hl=en");

            // 创建帐号数据信息
            boolean maleOrFemale = new Random().nextBoolean();
            RegisterAccount registerAccount = AccountInfoBuilder.getRegisterAccount(maleOrFemale);


            Actions action=new Actions(driver);
            Thread.sleep(timeSplit);
            action.sendKeys(Keys.TAB).perform();
            Thread.sleep(timeSplit);
            action.sendKeys(Keys.TAB).perform();
            Thread.sleep(timeSplit);
            action.sendKeys(Keys.ENTER).perform();
            Thread.sleep(timeSplit);
            action.sendKeys(Keys.TAB).perform();
            Thread.sleep(timeSplit);
            action.sendKeys(Keys.ENTER).perform();
            sleepSlow();
            action.sendKeys(registerAccount.firstName);
            Thread.sleep(timeSplit);
            action.sendKeys(Keys.TAB).perform();
            Thread.sleep(timeSplit);

            action.sendKeys(registerAccount.lastName);
            action.sendKeys(Keys.TAB).perform();
            Thread.sleep(timeSplit);
            action.sendKeys(Keys.ENTER).perform();
            Thread.sleep(timeSplit);
            Thread.sleep(timeSplit);
            Thread.sleep(timeSplit);

            // 填写电话界面
            logger.info("获取电话号码");
            String token = EmaPhoneService.getEMALoginToken();

            int getPhoneNumber = 0;
            boolean hasGetPhoneNumber = false;
            boolean isGetPhoneError = false;
            String phoneNumber = null;

            while(!hasGetPhoneNumber){
                getPhoneNumber++;
                if (getPhoneNumber > 3) {
                    logger.info("获取电话超过8次，退出");
                    isGetPhoneError = true;
                    break;
                }
                phoneNumber = EmaPhoneService.getEMAPhoneNumber(token);
                if (!TextUtils.isEmpty(phoneNumber)) {
                    hasGetPhoneNumber = true;
                }
            }

            if(isGetPhoneError){
                logger.info("获取电话时候出现错误，退出");
                driver.quit();
                ipSuccess=false;
                continue;
            }

            phoneNumber = phoneNumber.substring(0, phoneNumber.length() - 1);
            logger.info("获取电话号码是:" + phoneNumber);
            action.sendKeys("+86" + phoneNumber).perform();
            Thread.sleep(timeSplit);
            action.sendKeys(Keys.TAB).perform();
            Thread.sleep(timeSplit);
            action.sendKeys(Keys.ENTER).perform();
            registerAccount.phone = phoneNumber;

            // 弹出对话框
            Thread.sleep(timeSplit);
            Thread.sleep(timeSplit);
            action.sendKeys(Keys.TAB).perform();
            Thread.sleep(timeSplit);
            action.sendKeys(Keys.ENTER).perform();
            sleepSlow();

            // 填写验证码界面
            action.sendKeys(Keys.TAB).perform();
            Thread.sleep(timeSplit);

            String result = null;
            String code = null;
            logger.info("等待收到短信");
            int tryNumber = 0;
            boolean isGetCodeError = false;
            boolean isMatch = false;

            while (true) {
                tryNumber++;
                Thread.sleep(5000);
                result = EmaPhoneService.getCode(token);

                // 如果内容不为空
                if (!TextUtils.isEmpty(result)) {
                    logger.info("验证码是:" + result);
                    int index = result.indexOf("G-");
                    code = result.substring(index + 2, index + 8);
                    logger.info("验证码是:" + code);
                    isMatch = code.matches("([0-9]{6})");
                    if (isMatch){
                        break;
                    }else {
                    }
                }
                if (tryNumber > 6) {
                    isGetCodeError = true;
                    logger.info("尝试次数过多");
                    logger.info("异常情况解除号码");
                    EmaPhoneService.balckListPhoneNumber(token, phoneNumber);
                    break;
                }
            }

            if (isGetCodeError) {
                logger.info("获取短信出现异常，退出");
                Thread.sleep(3000);
                driver.quit();
                ipSuccess=false;
                continue;
            }

            logger.info("解除号码");
            EmaPhoneService.balckListPhoneNumber(token, phoneNumber);
            if (!isMatch) {
                logger.info("验证码格式不匹配");
                driver.quit();
                ipSuccess=false;
                continue;
            }

            logger.info("填写验证码");
            action.sendKeys(code).perform();
            registerAccount.code = code;
            logger.info("填写验证码完毕");
            logger.info("点击完成");

            Thread.sleep(timeSplit);
            action.sendKeys(Keys.TAB).perform();
            Thread.sleep(timeSplit);
            action.sendKeys(Keys.ENTER).perform();
            Thread.sleep(timeSplit);
            sleepSlow();

            // Basic information 界面
            Random random = new Random();
            // 包含 0 不包含 12
            int randomMonth = random.nextInt(12) + 1;
            // 包含 0 不包含 28
            int randomDay = random.nextInt(28) + 1;
            // 包含 1970 不包含 2000
            int randomYear = random.nextInt(30) + 1970;

            for (int j=0; j<randomMonth; j++){
                action.sendKeys(Keys.ARROW_DOWN).perform();
                Thread.sleep(timeSplit);
            }
            action.sendKeys(Keys.TAB).perform();
            Thread.sleep(timeSplit);
            action.sendKeys(""+randomDay).perform();
            Thread.sleep(timeSplit);
            action.sendKeys(Keys.TAB).perform();
            Thread.sleep(timeSplit);
            action.sendKeys(""+randomYear).perform();
            Thread.sleep(timeSplit);
            action.sendKeys(Keys.TAB).perform();
            Thread.sleep(timeSplit);
            if (maleOrFemale==true){
                // male
                action.sendKeys(Keys.ARROW_DOWN).perform();
                Thread.sleep(timeSplit);
            } else {
                // female
                action.sendKeys(Keys.ARROW_DOWN).perform();
                Thread.sleep(timeSplit);
                action.sendKeys(Keys.ARROW_DOWN).perform();
                Thread.sleep(timeSplit);
            }
            registerAccount.male = true;

            action.sendKeys(Keys.TAB).perform();
            Thread.sleep(timeSplit);
            action.sendKeys(Keys.ENTER).perform();
            Thread.sleep(timeSplit);
            sleepSlow();

            // 进入设置帐号界面
            action.sendKeys(registerAccount.accountName).perform();
            Thread.sleep(timeSplit);
            action.sendKeys(Keys.TAB).perform();
            Thread.sleep(timeSplit);
            action.sendKeys(Keys.ENTER).perform();
            Thread.sleep(timeSplit);
            sleepSlow();

            // 进入设置密码界面
            action.sendKeys(registerAccount.password).perform();
            Thread.sleep(timeSplit);
            action.sendKeys(Keys.TAB).perform();
            Thread.sleep(timeSplit);
            action.sendKeys(registerAccount.password).perform();
            Thread.sleep(timeSplit);
            action.sendKeys(Keys.TAB).perform();
            Thread.sleep(timeSplit);
            action.sendKeys(Keys.ENTER).perform();
            Thread.sleep(timeSplit);

            // add phone number, try to skip
            action.sendKeys(Keys.TAB).perform();
            Thread.sleep(timeSplit);
            action.sendKeys(Keys.TAB).perform();
            Thread.sleep(timeSplit);
            action.sendKeys(Keys.TAB).perform();
            Thread.sleep(timeSplit);
            action.sendKeys(Keys.TAB).perform();
            Thread.sleep(timeSplit);
            action.sendKeys(Keys.ENTER).perform();
            Thread.sleep(timeSplit);
            sleepSlow();

            // Privacy and Terms
            action.sendKeys(Keys.TAB).perform();
            Thread.sleep(timeSplit);
            action.sendKeys(Keys.TAB).perform();
            Thread.sleep(timeSplit);
            action.sendKeys(Keys.TAB).perform();
            Thread.sleep(timeSplit);
            action.sendKeys(Keys.TAB).perform();
            Thread.sleep(timeSplit);
            action.sendKeys(Keys.ENTER).perform();
            sleepSlow();

            // Your Google Account
            action.sendKeys(Keys.TAB).perform();
            Thread.sleep(timeSplit);
            action.sendKeys(Keys.ENTER).perform();
            sleepSlow();

            logger.info("帐号信息："+registerAccount.toString());

            // 进去新的界面设置辅助邮箱
            driver.get("https://www.google.com?hl=en");

            WebElement webElement = null;
            webElement = driver.findElement(By.linkText("Sign in"));
            webElement.click();

            sleepSlow();

            driver.findElement(By.id("Email")).clear();
            driver.findElement(By.id("Email")).sendKeys(registerAccount.accountName);
            driver.findElement(By.id("next")).click();
            sleepSlow();

            driver.findElement(By.id("Passwd")).clear();
            driver.findElement(By.id("Passwd")).sendKeys(registerAccount.password);
            driver.findElement(By.id("signIn")).click();

            sleepSlow();
            driver.findElement(By.xpath(".//*[@type='text' and @class='Kc fh Q-Sb-Q']")).sendKeys(registerAccount.email);
            logger.info("设置辅助邮箱:"+registerAccount.email);
            logger.info("点击完成");
            driver.findElement(By.xpath(".//*[@role='button' and text()='Done']")).click();

            String sqlResult = addAccount(registerAccount);
            if (sqlResult.equals("ok")){
                logger.info("insert sql ok");
            }else{
                logger.info("insert sql fail");
            }

            // 销毁
            driver.quit();

            ipSuccess = true;
            loop++;

//            if (ipSuccess == true){
//
//            }else{
//                String[] disconnectCommands = new String[3];
//                disconnectCommands[0] = "sleep 5";
//                disconnectCommands[1] = "expressvpn disconnect";
//                disconnectCommands[2] = "sleep 10";
//                ShellUtils.CommandResult disResult = ShellUtils.execCommand(disconnectCommands,false,true);
//                logger.info(disResult);
//            }

            sleepSlow();
        }
    }

    public static String addAccount(RegisterAccount registerAccount){
        String result;
        try {

            // 请求参数
            String baseUrl = "http://35.188.39.166/addAccount.php?";
            String reqUrl = baseUrl
                    + "accountName=" + registerAccount.accountName + "@gmail.com" + "&"
                    + "password=" + registerAccount.password + "&"
                    + "phone=" + registerAccount.phone + "&"
                    + "vcode=" + registerAccount.code + "&"
                    + "gender=" + (registerAccount.male?"1":"0") + "&"
                    + "recEmail=" + registerAccount.email;

            RequestEntity<Void> entity = new RequestEntity<Void>(reqUrl, Request.Method.GET, Void.class, new HashMap(), null);
            ProtoRequest<Void> request = new ProtoRequest<>(entity);
            HttpStack httpStack = new HurlStack();
            ((HurlStack) httpStack).setUseProxy(false);
            BasicNetwork network = new BasicNetwork(httpStack);
            NetworkResponse response = network.performRequest(request);
            result = new String(response.data);
            logger.info("records:"+result);

        }catch (Exception e){
            logger.error(e);
            result = "fail";
        }
        return result;
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
