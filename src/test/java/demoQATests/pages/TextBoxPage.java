package demoQATests.pages;

import demoQATests.base.BasePage;
import demoQATests.config.Urls;
import demoQATests.data.TextBoxFormData;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TextBoxPage extends BasePage {
    // Локаторы элементов страницы
    @FindBy(id = "userName")
    private WebElement fullName;

    @FindBy(id = "userEmail")
    private WebElement email;

    @FindBy(id = "currentAddress")
    private WebElement currentAddress;

    @FindBy(id = "permanentAddress")
    private WebElement permanentAddress;

    @FindBy(xpath = "//*[text()='Submit']")
    private WebElement submitButton;

    @FindBy(id = "output")
    private WebElement outputBlock; // блок с результатами

    @FindBy(css = "input#userEmail.field-error")
    private WebElement emailWithError; // Для негативного кейса: красная рамка у поля

    public TextBoxPage(WebDriver driver) {
        // Вызов конструктора BasePage: сохранит driver, создаст wait и инициализирует элементы
        super(driver);
    }

    @Step("Открыть страницу Text Box")
    public void open() {
        open(Urls.TEXT_BOX);
    }

    @Step("Заполнить форму валидными данными (имя, email, " +
            "текущий адрес, постоянный адрес) и отправить")
    public void fillFormAndSubmit(TextBoxFormData data) {
        fullName.sendKeys(data.fullName());
        email.sendKeys(data.email());
        currentAddress.sendKeys(data.currentAddress());
        permanentAddress.sendKeys(data.permanentAddress());
        submitButton.click();
    }

    @Step("Отправить форму с невалидным email: {invalidEmail}")
    public void submitWithInvalidEmail(String invalidEmail) {
        email.sendKeys(invalidEmail);
        submitButton.click();
    }

    @Step("Отправить пустую форму (нажимаем Submit, не заполняя полей)")
    public void submitEmptyForm() {
        submitButton.click();
    }

    @Step("Получить текст из блока результатов")
    public String getOutputText() {
        return outputBlock.getText();
    }

    @Step("Проверить, отображается ли блок результатов")
    public boolean isOutputDisplayed() {
        return outputBlock.isDisplayed();
    }

    // Проверяем, есть ли у поля email атрибут class с ошибкой
    @Step("Проверить, подсвечено ли поле email как ошибочное")
    public boolean emailMarkedAsError() {
        return email.getAttribute("class").contains("field-error");
    }
}
