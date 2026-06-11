package demoQATests.pages;

import demoQATests.base.BasePage;
import demoQATests.config.Urls;
import io.qameta.allure.Step;
import org.jspecify.annotations.NonNull;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class ToolTipsPage extends BasePage {
    private final Actions actions;

    @FindBy(id = "toolTipButton")
    private WebElement hoverButton;

    @FindBy(id = "toolTipTextField")
    private WebElement hoverField;

    @FindBy(xpath = "//a[text()='Contrary']")
    private WebElement contraryLink;

    @FindBy(xpath = "//a[text()='1.10.32']")
    private WebElement versionLink;

    // Локаторы для тултипов — используем By,так как тултипы появляются динамически
    private final By buttonTooltip = By.cssSelector("#buttonToolTip .tooltip-inner");
    private final By textFieldTooltip = By.cssSelector("#textFieldToolTip .tooltip-inner");
    private final By contraryTooltip = By.cssSelector("#contraryTexToolTip .tooltip-inner");
    private final By sectionTooltip = By.cssSelector("#sectionToolTip .tooltip-inner");

    public ToolTipsPage(WebDriver driver) {
        super(driver);
        this.actions = new Actions(driver);
    }

    @Step("Открыть страницу Tool Tips")
    public void open() {
        open(Urls.TOOL_TIPS);
        wait.until(ExpectedConditions.urlContains("tool-tips")); // Ждём загрузки страницы
    }

    @Step("Навести курсор на элемент и получить текст тултипа")
    private @NonNull String hoverAndGetTooltipText(WebElement element, By tooltipLocator) {
        JavascriptExecutor js = (JavascriptExecutor) driver;  // Приводим driver к интерфейсу JavascriptExecutor для выполнения JS

        // Скрипт: прокручивает страницу так, чтобы element оказался в центре видимой области (block:'center') и по возможности ближе по горизонтали (inline:'nearest')
        js.executeScript(
                "arguments[0].scrollIntoView({block: 'center', " +
                        "inline: 'nearest'});", element);

        wait.until(ExpectedConditions.elementToBeClickable(element));

        actions.moveToElement(element)     // Наводим курсор к элементу
                .pause(Duration.ofMillis(500)) // Паузу 500 мил.сек., чтобы тултиппоявилься
                .perform();   //  Выполнить действие

        WebElement tooltip = wait.until(
                ExpectedConditions.visibilityOfElementLocated(tooltipLocator));
        return tooltip.getText().trim();
    }

    @Step("Получить текст тултипа для кнопки")
    public String getTooltipTextForButton() {
        return hoverAndGetTooltipText(hoverButton, buttonTooltip);
    }

    @Step("Получить текст тултипа для текстового поля")
    public String getTooltipTextForTextField() {
        return hoverAndGetTooltipText(hoverField, textFieldTooltip);
    }

    @Step("Получить текст тултипа для ссылки Contrary")
    public String getTooltipTextForContraryLink() {
        return hoverAndGetTooltipText(contraryLink, contraryTooltip);
    }

    @Step("Получить текст тултипа для ссылки 1.10.32")
    public String getTooltipTextForVersionLink() {
        return hoverAndGetTooltipText(versionLink, sectionTooltip);
    }
}
