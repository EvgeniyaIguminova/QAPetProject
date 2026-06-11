package demoQATests.tests;

import demoQATests.base.BaseTest;
import demoQATests.pages.SliderPage;
import io.qameta.allure.Description;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

public class SliderTest extends BaseTest {
    @Test(description = "1. Проверка перемещения слайдера")
    @Description("Перетащить слайдер на позицию 62 и проверить," +
            " что значение в поле равно 62")
    public void testSliderMovement() {
        SliderPage sliderPage = new SliderPage(driver);
        sliderPage.open();

        sliderPage.moveSliderTo(62);

        Assertions.assertThat(sliderPage.getSliderValue())
                .as("Слайдер должен находиться на позиции 62")
                .isEqualTo("62");
    }

    @Test(description = "2. Проверка граничных значений слайдера")
    @Description("Переместить слайдер в минимальное значение (0) и максимальное значение (100)" +
            " и проверить результаты")
    public void testSliderBoundaryValues() {
        SliderPage sliderPage = new SliderPage(driver);
        sliderPage.open();
        sliderPage.moveSliderTo(0);
        Assertions.assertThat(sliderPage.getSliderValue())
                .as("Минимальное значение слайдера должно быть 0")
                .isEqualTo("0");

        sliderPage.moveSliderTo(100);
        Assertions.assertThat(sliderPage.getSliderValue())
                .as("Максимальное значение слайдера должно быть 100")
                .isEqualTo("100");

    }

    @Test(description = "3. Проверка ввода значений в поле")
    @Description("Значение в поле нельзя удалить и ввести новое; изменение только через ползунок")
    public void testSliderInputFieldNotEditable() {
        SliderPage sliderPage = new SliderPage(driver);
        sliderPage.open();
        String valueBeforeEdit = sliderPage.getSliderValue();
        sliderPage.editValueField("50");

        Assertions.assertThat(sliderPage.getSliderValue())
                .as("Клавиатурный ввод не должен менять значение в поле")
                .isEqualTo(valueBeforeEdit)
                .isNotEqualTo("50");
    }

    @Test(description = "4. Проверка перемещения слайдера на позицию 11")
    @Description("Перетащить слайдер на позицию 11 и проверить, что значение в поле равно 11")
    public void testSliderMovementTo11() {
        SliderPage sliderPage = new SliderPage(driver);
        sliderPage.open();
        sliderPage.moveSliderTo(11);
        Assertions.assertThat(sliderPage.getSliderValue())
                .as("Слайдер должен находиться на позиции 11")
                .isEqualTo("11");
    }
}
