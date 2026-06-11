package demoQATests.tests;

import demoQATests.base.BaseTest;
import demoQATests.data.TextBoxFormData;
import demoQATests.pages.TextBoxPage;
import io.qameta.allure.Description;
import org.assertj.core.api.Assertions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class TextBoxTest extends BaseTest {
    // DataProvider для негативных email-тестов (без изменений)
    @DataProvider(name = "invalidEmails")
    public Object[][] invalidEmails() {
        return new Object[][] {
                {"invalid.mail.ru",        "email без @"},
                {"invalid-email@",         "email без домена"},
                {"invalid email@mail.ru",  "email с пробелом"},
                {"@example.com",           "email без локальной части"}
        };
    }

    @Test(description = "1. Положительный тест заполнения формы")
    @Description("Проверка заполнения формы и подтверждения введенных данных.")
    public void testSubmitValidForm() {
        TextBoxFormData data = TextBoxFormData.valid();
        TextBoxPage textBoxPage = new TextBoxPage(driver);
        textBoxPage.open();
        textBoxPage.fillFormAndSubmit(data);

        // читаем текст из блока результатов сохраняет его в переменную
        String outputText = textBoxPage.getOutputText();

        Assertions.assertThat(outputText)
                .as("Имя пользователя должно присутствовать")
                .containsIgnoringCase("Name")
                .contains(data.fullName());

        Assertions.assertThat(outputText)
                .as("Email должен присутствовать")
                .containsIgnoringCase("Email")
                .contains(data.email());

        Assertions.assertThat(outputText)
                .as("Текущий адрес должен присутствовать")
                .containsIgnoringCase("Current Address")
                .contains(data.currentAddress());

        Assertions.assertThat(outputText)
                .as("Постоянный адрес должен присутствовать")
                .containsIgnoringCase("Permananet Address")
                .contains(data.permanentAddress());
    }

    @Test(dataProvider = "invalidEmails",
          description = "2. Негативные тесты – невалидный email")
    @Description("Отправить форму с невалидным email, убедиться, " +
            "что блок результатов не появился, а поле подсвечено красным")
    public void testSubmitWithInvalidEmail(String invalidEmail, String caseDescription) {
        TextBoxPage textBoxPage = new TextBoxPage(driver);
        textBoxPage.open();
        textBoxPage.submitWithInvalidEmail(invalidEmail);

        Assertions.assertThat(textBoxPage.isOutputDisplayed())
                .as("Блок с результатами не должен появляться " +
                        "при невалидном email: " + caseDescription)
                .isFalse();
        Assertions.assertThat(textBoxPage.emailMarkedAsError())
                .as("Поле email должно подсветиться " +
                        "красным при ошибке: " + caseDescription)
                .isTrue();
    }

    @Test(description = "3. Негативный тест – отправка пустой формы")
    @Description("Нажать Submit с пустыми полями, проверить, " +
            "что блок результатов не отображается, поле email не подсвечено ошибкой")
    public void testSubmitEmptyForm() {
        TextBoxPage textBoxPage = new TextBoxPage(driver);
        textBoxPage.open();
        textBoxPage.submitEmptyForm();

        Assertions.assertThat(textBoxPage.isOutputDisplayed())
                .as("Блок с результатами не должен появляться, если поля не заполнены")
                .isFalse();
        Assertions.assertThat(textBoxPage.emailMarkedAsError())
                .as("Поле email не подсветиться красным при ошибке ")
                .isFalse();
    }
}


//    @Test(description = "Логин тест")
//    public void testLogin1() {
//        driver.navigate().to("https://demoqa.com/text-box");
//        driver.findElement(By.id("userName")).sendKeys("login");
//        driver.findElement(By.id("userEmail")).sendKeys("login@example.com");
//        driver.findElement(By.id("currentAddress")).sendKeys("г.Москва");
//        driver.findElement(By.id("permanentAddress")).sendKeys("г.Санкт-Петербург");
//        driver.findElement(By.xpath("//*[text()='Submit']")).click();
//    }