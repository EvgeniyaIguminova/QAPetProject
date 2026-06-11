package demoQATests.tests;

import demoQATests.base.BaseTest;
import demoQATests.pages.ProgressBarPage;
import io.qameta.allure.Description;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

public class ProgressBarTest extends BaseTest {
    @Test(description = "0) Начальное состояние страницы")
    @Description("После открытия: прогресс 0%, кнопка Start")
    public void testInitialState() {
        ProgressBarPage progressBarPage = new ProgressBarPage(driver);
        progressBarPage.open();
        Assertions.assertThat(progressBarPage.getProgressValue())
                .as("Начальное значение прогресс-бара")
                .isZero();
        Assertions.assertThat(progressBarPage.getStartStopButtonText())
                .as("Начальный текст кнопки Start")
                .isEqualTo("Start");
    }

    @Test(description = "1) Остановка прогресс-бара после значения 65")
    @Description("Нажать Start, дождаться прогресса > 65, нажать Stop и проверить результат")
    public void testStopProgressBarAfter65() {
        ProgressBarPage progressBarPage = new ProgressBarPage(driver);
        progressBarPage.open();
        progressBarPage.clickStart();

        progressBarPage.waitProgress(65);
        progressBarPage.clickStop();

        Assertions.assertThat(progressBarPage.getProgressValue())
                .as("Прогресс после Stop должен быть больше 65")
                .isGreaterThan(65);        // Утверждаем, что значение > 65

        Assertions.assertThat(progressBarPage.getStartStopButtonText())
                .as("После остановки кнопка должна стать Start")
                .isEqualTo("Start");
    }

    @Test(description = "2) Проверка загрузки прогресс-бара до 100%")
    @Description("Start → дождаться 100% → кнопка Reset, значение 100")
    public void testProgressBarLoading() {
        ProgressBarPage progressBarPage = new ProgressBarPage(driver);
        progressBarPage.open();
        progressBarPage.clickStart();
        progressBarPage.waitProgress(99);

        // Ждём, когда кнопка станет "Reset" (это и подтверждает завершение)
        progressBarPage.waitForButtonText("Reset");

        Assertions.assertThat(progressBarPage.getProgressValue())
                .as("Прогресс должен дойти до 100%")
                .isEqualTo(100);

        Assertions.assertThat(progressBarPage.getStartStopButtonText())
                .as("После завершения загрузки кнопка Reset")
                .isEqualTo("Reset");
    }

    @Test(description = "3) Проверка сброса прогресс-бара")
    @Description("Дойти до 100%, нажать Reset, прогресс 0, кнопка Start")
    public void testProgressBarReset() {
        ProgressBarPage progressBarPage = new ProgressBarPage(driver);
        progressBarPage.open();
        progressBarPage.clickStart();
        progressBarPage.waitProgress(99);
        progressBarPage.waitForButtonText("Reset");

        progressBarPage.clickReset();   // Нажимаем Reset
        progressBarPage.waitForButtonText("Start");

        Assertions.assertThat(progressBarPage.getProgressValue())
                .as("После сброса прогресс должен быть 0")
                .isZero();
        Assertions.assertThat(progressBarPage.getStartStopButtonText())
                .as("После сброса кнопка Start")
                .isEqualTo("Start");
    }

    @Test(description = "4) Кнопка меняется на Stop после Start")
    @Description("Проверка смены Start → Stop при запуске")
    public void testButtonChangesToStopOnStart() {
        ProgressBarPage progressBarPage = new ProgressBarPage(driver);
        progressBarPage.open();
        progressBarPage.clickStart();

        Assertions.assertThat(progressBarPage.getStartStopButtonText())
                .as("Во время загрузки кнопка Stop")
                .isEqualTo("Stop");
    }
}
