package testng;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class WebDriverHtmlBookTest
{
    private WebDriver driver;

    @BeforeTest(alwaysRun = true)
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);
    }

    @Test
    public void commonSearchTermResultsNotEmpty()
    {
        driver.get("https://html5book.ru/html-html5/");
        waitForElementLocated(driver, By.className("menu-wrapper"));

        WebElement clickElement = driver.findElement(By.xpath("/html/body/header/div/div/div[2]/i"));
        clickElement.click();

        WebElement searchInput = waitForElementLocated(driver, By.className("search-field"));

        searchInput.sendKeys("HTML-теги");
        searchInput.sendKeys(Keys.ENTER);

        List<WebElement> searchResult = driver.findElements(By.className("search-title"));
        Assert.assertTrue(searchResult.size()>0, "search result are empty!");
    }

    private static WebElement waitForElementLocated(WebDriver driver, By by) {
        return new WebDriverWait(driver, 10)
                .until(ExpectedConditions
                        .elementToBeClickable(by));
    }

    @AfterMethod(alwaysRun = true) //наглядно видим выполнение теста;
    public void screenShot() throws java.io.IOException {
        TakesScreenshot scr = ((TakesScreenshot) driver);
        File file1 = scr.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(file1, new File("D:\\3 Курс\\EPAM\\com.epam.automaticTesting\\Screenshot.png"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }
}
