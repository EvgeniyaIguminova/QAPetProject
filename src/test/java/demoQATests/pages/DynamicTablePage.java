package demoQATests.pages;

import demoQATests.base.BasePage;
import demoQATests.config.Urls;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class DynamicTablePage extends BasePage {
    // Список всех строк таблицы: 1-я — заголовок, далее 4 строки с данными
    @FindBy(css = "[role='row']")
    private List<WebElement> rows;

    // Список всех заголовков колонок
    @FindBy(css = "[role='columnheader']")
    private List<WebElement> columnHeaders;

    // Список всех ячейк данных таблицы
    @FindBy(css = "[role='cell']")
    private List<WebElement> tableCells;

    // Элемент жёлтой информационной плашки
    @FindBy(css = ".bg-warning")
    private WebElement yellowWarning;

    public DynamicTablePage(WebDriver driver) {
        super(driver);
    }


    @Step("Дождаться загрузки таблицы")
    public void waitForTableLoaded() {
        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("[role='columnheader']")));
        wait.until(d -> getColumnNames().stream()
                .anyMatch(h -> h.equalsIgnoreCase("Name")));
        wait.until(d -> !tableCells.isEmpty());
    }

    @Step("Открыть страницу Dynamic Table")
    public void open() {
        open(Urls.DYNAMIC_TABLE);
        waitForTableLoaded();
    }

    @Step("Обновить страницу")
    public void refresh() {
        driver.navigate().refresh();
        waitForTableLoaded();
    }

    @Step("Получить полный текст жёлтой плашки")
    public String getYellowLabelText() {
        return yellowWarning.getText().trim(); // Получаем текст элемента, убираем пробелы по краям
    }

    @Step("Проверить, что жёлтая плашка отображается и не пуста")
    public boolean isYellowLabelDisplayed() {
        return yellowWarning.isDisplayed()
                && !getYellowLabelText().isEmpty();
    }

    @Step("Получить значение CPU для Chrome с жёлтой плашки")
    public String getChromeCpuFromLabel() {
        String text = getYellowLabelText();
        String prefix = "Chrome CPU:";
        if (!text.contains(prefix)) {
            throw new IllegalStateException("На плашке нет 'Chrome CPU:': " + text);
        }
        return text.substring(text.indexOf(prefix) + prefix.length()).trim();
    }

    @Step("Проверить, что таблица отображается и список строк не пуст")
    public boolean isTableDisplayed() {
        return !rows.isEmpty()
                && rows.get(0).isDisplayed();
    }

    @Step("Получить количество всех строк (role='row')")
    public int getAllRowsCount() {
        return rows.size();
    }

    @Step("Получить количество строк данных (без заголовка)")
    public int getDataRowCount() {
        return getAllRowsCount() - 1;
    }

    @Step("Проверить, что все ячейки таблицы не пустые")
    public boolean allCellsNonEmpty() {
        for (WebElement cell : tableCells) {
            if (cell.getText().trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Step("Получить названия всех колонок")
    public List<String> getColumnNames() {
        List<String> names = new ArrayList<>();
        for (WebElement header : columnHeaders) {
            names.add(header.getText().trim());
        }
        return names;
    }

    @Step("Получить количество колонок")
    public int getColumnCount() {
        return columnHeaders.size();
    }

    // Универсальный метод: ищем индекс колонки по ее имени (например, CPU)
    private int getColumnIndex(String columnName) {
        List<String> headers = getColumnNames();
        for (int i = 0; i < headers.size(); i++) {
            if (headers.get(i).equalsIgnoreCase(columnName)) {
                return i;
            }
        }
        throw new IllegalStateException(
                "Колонка '" + columnName + "' не найдена. Найдены: " + headers);
    }

    @Step("Получить индекс колонки Name")
    public int getNameColumnIndex() {
        return getColumnIndex("Name");
    }

    @Step("Получить индекс колонки CPU")
    public int getCpuColumnIndex() {
        return getColumnIndex("CPU");
    }

    @Step("Проверить, что колонка Name первая")
    public boolean isNameColumnFirst() {
        return getColumnNames().get(0).equalsIgnoreCase("Name");
    }

    // Чтение из tableCells значения ячейки по строке и колонке
    private String getCellValue(int dataRowIndex, int columnIndex) {
        int cols = getColumnCount(); // Количество колонок
        int flatIndex = dataRowIndex * cols + columnIndex;
        return tableCells.get(flatIndex).getText().trim();
    }

    @Step("Получить CPU для Chrome из таблицы")
    public String getChromeCpuFromTable() {
        int nameIndex = getNameColumnIndex(); // Индекс колонки Name
        int cpuIndex = getCpuColumnIndex();   // Индекс колонки CPU
        // Перебираем строки данных и ищем строку, где Name == Chrome
        for (int row = 0; row < getDataRowCount(); row++) {
            String browserName = getCellValue(row, nameIndex);
            if ("Chrome".equals(browserName)) {
                return getCellValue(row, cpuIndex);
            }
        }
        throw new IllegalStateException("Строка 'Chrome' не найдена в таблице");
    }

    @Step("Проверить, что CPU Chrome в таблице совпадает с плашкой")
    public boolean isChromeCpuMatching() {
        return getChromeCpuFromTable().equals(getChromeCpuFromLabel());
    }

//    @Step("Получить список значений из колонки Name")
//    public List<String> getBrowserNamesFromTable() {
//        int nameIndex = getNameColumnIndex();
//        List<String> names = new ArrayList<>();
//        for (int row = 0; row < getDataRowCount(); row++) {
//            names.add(getCellValue(row, nameIndex));
//        }
//        return names;
//    }
}
