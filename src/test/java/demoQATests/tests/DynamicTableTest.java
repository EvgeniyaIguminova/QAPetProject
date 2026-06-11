package demoQATests.tests;

import demoQATests.base.BaseTest;
import demoQATests.pages.DynamicTablePage;
import io.qameta.allure.Description;
import org.assertj.core.api.Assertions;
import org.testng.annotations.Test;

public class DynamicTableTest extends BaseTest {
    @Test(description = "1. Проверка: Name всегда первая колонка")
    @Description("По условию страницы колонка Name должна быть первой")
    public void testNameColumnIsFirst() {
        DynamicTablePage page = new DynamicTablePage(driver);
        page.open();
        Assertions.assertThat(page.isNameColumnFirst())
                .as("Первая колонка должна быть Name")
                .isTrue();
    }

    @Test(description = "2. Все ячейки таблицы заполнены (нет пустых)")
    @Description("Базовая проверка загрузки данных")
    public void testAllTableCellsAreFilled() {
        DynamicTablePage page = new DynamicTablePage(driver);
        page.open();
        Assertions.assertThat(page.allCellsNonEmpty())
                .as("Все ячейки с данными не пустые")
                .isTrue();
    }

    @Test(description = "3. Проверка: CPU Chrome в таблице совпадает с жёлтой плашкой")
    @Description("Сравнить CPU Chrome из таблицы и из желной плашки")
    public void testChromeCpuMatchesYellowLabel() {
        DynamicTablePage page = new DynamicTablePage(driver);
        page.open();
        Assertions.assertThat(page.isChromeCpuMatching())
                .as("CPU Chrome в таблице должен совпадать " +
                        "с CPU на плашке")
                .isTrue();
    }

    @Test(description = "4. Проверка совпадения CPU после refresh")
    @Description("После обновления страницы значения могут измениться, но должны совпадать между собой")
    public void testCpuMatchesAfterRefresh() {
        DynamicTablePage page = new DynamicTablePage(driver);
        page.open();
        page.refresh();
        Assertions.assertThat(page.isChromeCpuMatching())
                .as("После refresh CPU должен совпадать")
                .isTrue();
    }
}
