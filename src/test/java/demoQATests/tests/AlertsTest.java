package demoQATests.tests;

import demoQATests.base.BaseTest;
import demoQATests.pages.AlertsPage;
import io.qameta.allure.Description;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import java.time.Duration;

public class AlertsTest extends BaseTest {
    @Test(description = "1) Тест простого алерта")
    @Description("Нажать кнопку вызова алерта, " +
            "проверить текст и закрыть алерт")
    public void testSimpleAlert() {
        AlertsPage alertsPage = new AlertsPage(driver);
        alertsPage.open();
        alertsPage.clickAlertButton();

        String alertText = alertsPage.getAlertText();
        alertsPage.acceptAlert(); // нажимаем OK

        Assertions.assertThat(alertText)
                .as("Текст алерта должен быть корректным")
                .contains("You clicked a button");
    }

    @Test(description = "2) Тест таймер-алерта (появляется через 5 секунд)")
    @Description("Нажать кнопку таймер-алерта, дождаться появления, " +
            "проверить текст и закрыть алерт")
    public void testTimerAlert() {
        AlertsPage alertsPage = new AlertsPage(driver);
        alertsPage.open();
        alertsPage.clickTimerAlertButton();

        // Явное ожидание в 10 секунд перед переключением на алерт
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Встроенный метод Selenium 'ExpectedConditions.alertIsPresent()'.
        wait.until(ExpectedConditions.alertIsPresent());

        String alertText = alertsPage.getAlertText();
        alertsPage.acceptAlert();

        Assertions.assertThat(alertText)
                .as("Текст алерта с таймером должен быть корректным")
                .contains("This alert appeared after 5 seconds");

    }

    @Test(description = "3) Тест для confirm алерта - подтверждение")
    @Description("Нажать кнопку confirm, принять алерт (OK), проверить появление 'Ok'")
    public void testConfirmAlertAccept() {
        AlertsPage alertsPage = new AlertsPage(driver);
        alertsPage.open();
        alertsPage.clickConfirmButton();
        alertsPage.acceptAlert();

        Assertions.assertThat(alertsPage.getConfirmResultText())
                .as("Результат confirm алерта при подтверждении")
                .contains("Ok");
    }

    @Test(description = "4) Тест для confirm алерта - отмена")
    @Description("Нажать кнопку confirm, отклонить алерт, " +
            "роверить появление 'Cancel'")
    public void testConfirmAlertDismiss() {
        AlertsPage alertsPage = new AlertsPage(driver);
        alertsPage.open();
        alertsPage.clickConfirmButton();
        alertsPage.dismissAlert();

        Assertions.assertThat(alertsPage.getConfirmResultText())
                .as("Результат confirm алерта при отмене")
                .contains("Cancel");
    }

    @Test(description = "5) Тест для prompt алерта")
    @Description("Нажать кнопку prompt, ввести 'Test User', " +
            "подтвердить и проверить, что текст появился в результате")
    public void testPromptAlert() {
        AlertsPage alertsPage = new AlertsPage(driver);
        alertsPage.open();
        alertsPage.clickPromptButton();
        alertsPage.sendPromptText("Test User");

        Assertions.assertThat(alertsPage.getPromptResultText())
                .as("Результат prompt алерта с текстом")
                .contains("Test User");
    }
}
