package demoQATests.data;

/**
 * Record для тестовых данных формы Text Box.
 * Автоматически:
 * - конструктор со всеми полями, equals, hashCode, toString
 * - методы доступа fullName(), email(), currentAddress(), permanentAddress()
 */
public record TextBoxFormData(String fullName,
                              String email,
                              String currentAddress,
                              String permanentAddress) {
    // Валидные данные для позитивного сценария
    public static TextBoxFormData valid() {
        return new TextBoxFormData(
                "userName",
                "userName@example.com",
                "г.Москва",
                "г.Воронеж"
        );
    }
}
