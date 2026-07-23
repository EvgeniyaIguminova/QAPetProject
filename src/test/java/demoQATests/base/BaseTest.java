package demoQATests.base;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public abstract class BaseTest {
    protected WebDriver driver;

    @Step("Открыть браузер")
    @BeforeMethod
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Step("Закрыть браузер")
    @AfterMethod(alwaysRun = true)
    public void cleanup() {
        if (driver != null) {
            try {
                driver.quit();
            } catch (Exception e) {
                // Логируем ошибку закрытия
                System.err.println("Ошибка при закрытии драйвера: " + e.getMessage());
            } finally {
                driver = null; // В любом случае обнулить ссылку для сборщика мусора
            }
        }
    }
}
