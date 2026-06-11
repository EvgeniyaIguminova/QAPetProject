package demoQATests.tests;

import demoQATests.base.BaseTest;
import demoQATests.pages.ToolTipsPage;
import io.qameta.allure.Description;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

public class ToolTipsTest extends BaseTest {
    @Test(description = "1) Проверка тултипа кнопки")
    @Description("Наведение на кнопку должно показывать корректный текст тултипа")
    public void testButtonTooltipText() {
        ToolTipsPage page = new ToolTipsPage(driver);
        page.open();

        Assertions.assertThat(page.getTooltipTextForButton())
                .as("Некорректный текст тултипа кнопки")
                .isEqualTo("You hovered over the Button");
    }

    @Test(description = "2) Проверка тултипа поля")
    @Description("Наведение на текстовое поле должно показывать корректный текст тултипа")
    public void testFieldTooltipText() {
        ToolTipsPage page = new ToolTipsPage(driver);
        page.open();
        Assertions.assertThat(page.getTooltipTextForTextField())
                .as("Некорректный текст тултипа поля")
                .isEqualTo("You hovered over the text field");
    }

    @Test(description = "3) Проверка тултипа ссылки Contrary")
    @Description("Наведение на ссылку Contrary должно показывать корректный текст тултипа")
    public void testContraryLinkTooltipText() {
        ToolTipsPage page = new ToolTipsPage(driver);
        page.open();
        Assertions.assertThat(page.getTooltipTextForContraryLink())
                .as("Некорректный текст тултипа ссылки Contrary")
                .isEqualTo("You hovered over the Contrary");
    }

    @Test(description = "4)Проверка тултипа ссылки 1.10.32")
    @Description("Наведение на ссылку 1.10.32 должно показывать корректный текст тултипа")
    public void testVersionLinkTooltipText() {
        ToolTipsPage page = new ToolTipsPage(driver);
        page.open();
        Assertions.assertThat(page.getTooltipTextForVersionLink())
                .as("Некорректный текст тултипа ссылки 1.10.32")
                .isEqualTo("You hovered over the 1.10.32");
    }
}
