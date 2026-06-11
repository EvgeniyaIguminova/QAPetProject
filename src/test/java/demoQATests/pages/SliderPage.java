package demoQATests.pages;

import demoQATests.base.BasePage;
import demoQATests.config.Urls;
import io.qameta.allure.Step;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SliderPage extends BasePage {
    @FindBy(id = "slider")
    private WebElement slider;   // Поле, соответствующее ползунку

    @FindBy(id = "sliderValue")
    private WebElement sliderValue;  // Поле, отображающее текущее значение слайдера

    public SliderPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открыть страницу Slider")
    public void open() {
        open(Urls.SLIDER);
    }

    @Step("Получить текущее значение слайдера из поля ввода")
    public String getSliderValue() {
        return sliderValue.getAttribute("value");
    }

    @Step("Переместить слайдер в позицию {targetValue} (0-100)")
    public void moveSliderTo(int targetValue) {
        if (targetValue < 0 || targetValue > 100) {
            throw new IllegalArgumentException("targetValue должен быть в диапазоне 0..100");
        }
        slider.click();
        // Нажимаем клавишу Home, которая переводит ползунок в минимальное положение (0)
        slider.sendKeys(Keys.HOME);
        // Потом вправо до нужного значения
        for (int i = 0; i < targetValue; i++) {
            slider.sendKeys(Keys.ARROW_RIGHT);
        }
    }

    @Step("Попытаться очистить поле и ввести значение {text}")
    public void editValueField(String text) {
        sliderValue.click();
        sliderValue.sendKeys(Keys.chord(Keys.CONTROL, "a")); // комбинацию Ctrl+A (выделить всё)
        sliderValue.sendKeys(Keys.DELETE);
        sliderValue.sendKeys(text);
    }
}
