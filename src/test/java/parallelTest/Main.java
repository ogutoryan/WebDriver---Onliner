package parallelTest;

import com.google.common.base.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.openqa.selenium.interactions.Actions;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;


public class Main {
    public WebDriver driver;

    @BeforeTest
    @Parameters("browser")
    public void setup(java.lang.String browser) throws Exception {
        if (browser.equalsIgnoreCase("FireFox")) {
            System.setProperty("webdriver.gecko.driver", "drivers/geckodriver.exe");
            driver = new FirefoxDriver();
        }
        else if (browser.equalsIgnoreCase("Chrome")) {
            System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
            driver = new ChromeDriver();
            driver.manage().window().maximize();
        }
        else if (browser.equalsIgnoreCase("IE")) {
            System.setProperty("webdriver.ie.driver", "drivers/IEDriverServer.exe");
            driver = new InternetExplorerDriver();
        } else {
            throw new Exception("Browser is not correct");
        }

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @Test
    public void onlinerTests() throws InterruptedException {
        driver.get("https://www.onliner.by/");
        Assert.assertEquals(driver.getTitle(), "Onliner.by");

        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(30, TimeUnit.SECONDS)
                .pollingEvery(5, TimeUnit.SECONDS)
                .ignoring(NoSuchElementException.class);

        WebElement btnLogin = wait.until(new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.xpath("//div[@class='auth-bar__item auth-bar__item--text']"));
            }
        });

        btnLogin.click();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        WebElement login = driver.findElement(By.xpath("//input[contains(@placeholder,'Ник или e-mail')]"));
        login.clear();
        login.sendKeys("onliner.test.olya@gmail.com");
        driver.findElement(By.xpath("//input[contains(@placeholder,'Пароль')]")).sendKeys("onliner123");

        new WebDriverWait(driver, 30)
                .until(ExpectedConditions.elementToBeClickable(By.className("auth-box__auth-submit")));
        driver.findElement(By.className("auth-box__auth-submit")).click();

        new WebDriverWait(driver, 30)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@data-bind, 'visible: $root.currentUser.id()')]")));

        WebElement userProfile = driver.findElement(By.xpath("//div[contains(@data-bind, 'visible: $root.currentUser.id()')]"));
        Assert.assertEquals(userProfile.isDisplayed(), true);

        driver.findElement(By.xpath("//span[contains(@class,'b-main-navigation__text') and contains(.,'Каталог')]")).click();
        WebElement popularCategories = driver.findElement(By.className("catalog-bar__list"));
        popularCategories.getAttribute("catalog-bar__list");
        System.out.println(popularCategories.getText());

        List<WebElement> listOfCategories = driver.findElements(By.className("catalog-bar__item"));

        int countOfVisibleElements = 0;
        for (int i = 0; i < listOfCategories.size(); i++) {
            if (listOfCategories.get(i).isDisplayed()) {
                countOfVisibleElements += 1;
                continue;
            }
            break;
        }

        int randomElement = (int) Math.floor(Math.random() * countOfVisibleElements);
        String expectedCategoryTitle = listOfCategories.get(randomElement).getText();
        listOfCategories.get(randomElement).click();
        String actualRandomElemenTitle = driver.findElement(By.xpath("//h1[@class='schema-header__title']")).getText();

        new WebDriverWait(driver, 30)
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[@class='schema-header__title']")));
        Assert.assertEquals(actualRandomElemenTitle, expectedCategoryTitle);

        driver.findElement(By.className("b-top-profile__item_arrow")).click();
        WebElement logout = new WebDriverWait(driver, 20)
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='b-top-profile__logout']/a")));
        Actions actions = new Actions(driver);
        actions.moveToElement(logout)
               .click()
               .build()
               .perform();

        new WebDriverWait(driver, 10)
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div//*[contains(text(),'Вход')]")));
        String checkLogout = driver.findElement(By.xpath("//div//*[contains(text(),'Вход')]")).getText();
        Assert.assertEquals(checkLogout,"Вход");
    }

    @AfterTest
    public void quit() {
        driver.close();
    }
}
