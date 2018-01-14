package parallelTest;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import java.util.concurrent.TimeUnit;


//public class CrossBrowserScript {
//
//    WebDriver driver;

//    @BeforeTest
//    @Parameters("browser")
//    public void setup(String browser) throws Exception {
//        //Check if parameter passed from TestNG is 'firefox'
//        if (browser.equalsIgnoreCase("FireFox")) {
//            //create firefox instance
//            System.setProperty("webdriver.gecko.driver", "drivers/geckodriver.exe");
//            driver = new FirefoxDriver();
//        }
//        //Check if parameter passed as 'chrome'
//        else if (browser.equalsIgnoreCase("Chrome")) {
//            //set path to chromedriver.exe
//            System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
//            //create chrome instance
//            driver = new ChromeDriver();
//        }
//        //Check if parameter passed as 'IE'
//        else if (browser.equalsIgnoreCase("IE")) {
//            //set path to IEDriverServer.exe
//            System.setProperty("webdriver.ie.driver", "drivers/IEDriverServer.exe");
//            //create IE instance
//            driver = new InternetExplorerDriver();
//        } else {
//            //If no browser passed throw exception
//            throw new Exception("Browser is not correct");
//        }
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//    }
//}


