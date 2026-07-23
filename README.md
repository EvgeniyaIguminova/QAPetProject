# QAPetProject

UI-автотесты на Selenium WebDriver + TestNG с отчётами Allure.  
Покрыты страницы [DemoQA](https://demoqa.com/) и [UI Testing Playground](http://uitestingplayground.com/).

## Стек

- Java 25
- Maven
- Selenium WebDriver 4
- TestNG
- AssertJ
- Allure

## Требования

- JDK 25+
- Maven 3.9+
- Google Chrome

## Запуск тестов

```bash
mvn clean test
```

## Allure-отчёт

После прогона:

```bash
mvn allure:serve
```

Либо:

```bash
allure serve allure-results
```

Папки `allure-results/` и `allure-report/` — артефакты прогона, в git не коммитятся.

## Что покрыто

| Тест | Сайт | Сценарий |
|------|------|----------|
| TextBoxTest | DemoQA | Заполнение формы Text Box и проверка результата |
| AlertsTest | DemoQA | Работа с alert / confirm / prompt |
| SliderTest | DemoQA | Перемещение слайдера |
| ProgressBarTest | DemoQA | Запуск и проверка Progress Bar |
| ToolTipsTest | DemoQA | Проверка tooltip |
| DynamicTableTest | UI Testing Playground | Поиск значения в динамической таблице |

## Структура

```
src/test/java/demoQATests/
├── base/       # BaseTest, BasePage
├── config/     # URL страниц
├── data/       # Тестовые данные
├── pages/      # Page Object
└── tests/      # Тесты
```

## Архитектура

Используется Page Object Model:

- `BaseTest` — создание и закрытие ChromeDriver перед/после каждого теста
- `BasePage` — общие действия со страницами
- Page-классы — локаторы и шаги (`@Step`) для Allure
- Test-классы — сценарии и проверки через AssertJ
