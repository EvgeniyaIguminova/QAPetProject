package demoQATests.pages;

import demoQATests.base.BasePage;
import demoQATests.config.Urls;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AlertsPage extends BasePage {
    // Локаторы
    @FindBy(id="alertButton")
    private WebElement alertButton;

    @FindBy(id="timerAlertButton")
    private WebElement timerAlertButton;

    @FindBy(id="confirmButton")
    private WebElement confirmButton;

    @FindBy(id="promtButton")
    private WebElement promtButton;

    @FindBy(id="confirmResult")
    private WebElement confirmResult;

    @FindBy(id="promptResult")
    private WebElement promptResult;

    public AlertsPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открыть страницу Alerts")
    public void open() {
        open(Urls.ALERTS);
    }

    @Step("Нажать кнопку вызова простого алерта")
    public void clickAlertButton() {
        alertButton.click();
    }

    @Step("Нажать кнопку вызова таймер-алерта")
    public void clickTimerAlertButton() {
        timerAlertButton.click();
    }

    @Step("Нажать кнопку вызова confirm-алерта")
    public void clickConfirmButton() {
        confirmButton.click();
    }

    @Step("Нажать кнопку вызова prompt-алерта")
    public void clickPromptButton() {
        promtButton.click();
    }

    @Step("Нажать OK в алерте")
    public void acceptAlert() {
        driver.switchTo().alert().accept(); // Нажимает OK (или единственную кнопку ) в любом всплывающем окне
    }

    @Step("Нажать кнопки Отмена в алерте")
    public void dismissAlert() {
        driver.switchTo().alert().dismiss();
    }

    @Step("Получить текст из всплывающего алерта")
    public String getAlertText() {
        return driver.switchTo().alert().getText();
    }

    @Step("Ввести текст '{text}' в prompt и нажать OK")
    public void sendPromptText(String text) {
        driver.switchTo().alert().sendKeys(text);
        driver.switchTo().alert().accept();
    }

    @Step("Получить текст результата confirm-алерта")
    public String getConfirmResultText() {
        return confirmResult.getText();
    }

    @Step("Получить текст результата prompt-алерта")
    public String getPromptResultText() {
        return promptResult.getText();
    }
}
