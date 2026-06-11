package demoQATests.pages;

import demoQATests.base.BasePage;
import demoQATests.config.Urls;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProgressBarPage extends BasePage {
    @FindBy(xpath = "//*[@id='progressBar']/div")
    private WebElement progressBar;

    //  Универсальный локатор: кнопка с любым из трёх возможных текстов
//  @FindBy(id="startStopButton")
    @FindBy(xpath = "//button[text()='Start' or text()='Stop' or text()='Reset']")
    private WebElement startStopButton;

    @FindBy(id="resetButton")
    private WebElement resetButton;

    public ProgressBarPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открыть страницу Progress Bar")
    public void open() {
        open(Urls.PROGRESS_BAR);
    }

    @Step("Нажать кнопку Start")
    public void clickStart() {
        startStopButton.click();
    }

    @Step("Нажать кнопку Stop")
    public void clickStop() {
        startStopButton.click();
    }

    @Step("Нажать кнопку Reset")
    public void clickReset() {
        resetButton.click();
    }

    @Step("Получить текущее значение прогресс-бара")
    public int getProgressValue() {
        String value = progressBar.getDomAttribute("aria-valuenow");
        if (value == null || value.isBlank()) {
            value = progressBar.getDomAttribute("aria-valuenow"); // или getAttribute()
        }
        return Integer.parseInt(value);
    }

    @Step("Дождаться, пока прогресс-бар станет больше {minValue}")
    public void waitProgress(int minValue) {
        wait.until(d -> getProgressValue() > minValue);
    }

    @Step("Получить текст кнопки Start/Stop/Reset")
    public String getStartStopButtonText() {
        return startStopButton.getText();
    }

    @Step("Дождаться, что кнопка имеет текст '{expectedText}'")
    public void waitForButtonText(String expectedText) {
        wait.until(ExpectedConditions.textToBePresentInElement(startStopButton, expectedText));
    }
}
