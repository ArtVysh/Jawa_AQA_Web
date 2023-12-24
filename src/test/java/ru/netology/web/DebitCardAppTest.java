package ru.netology.web;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class DebitCardAppTest {
    private WebDriver driver;

    @BeforeAll
    public static void setupAll() {

        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    public void afterEach() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldPositiveTestSending() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Артур Пирожков");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79279998877");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        var actualTextElement = driver.findElement(By.cssSelector("[data-test-id=order-success]"));
        var actualText = actualTextElement.getText().trim();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actualText);
        assertTrue(actualTextElement.isDisplayed());
    }

    @Test
    void shouldFindErrorNameField() {
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79279998877");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        var actualTextElement = driver.findElement(By.cssSelector("[data-test-id='name'] .input__sub "));
        var actualText = actualTextElement.getText().trim();
        assertEquals("Поле обязательно для заполнения", actualText);
        assertTrue(actualTextElement.isDisplayed());
    }

    @Test
    void shouldFindErrorPhoneField() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Артур Пирожков");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("E123r$");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.cssSelector("button.button")).click();
        var actualTextElement = driver.findElement(By.cssSelector("[data-test-id='phone'] .input__sub "));
        var actualText = actualTextElement.getText().trim();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actualText);
        assertTrue(actualTextElement.isDisplayed());
    }

    @Test
    void shouldFindErrorWhenEmptyCheckbox() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Артур Пирожков");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79279998877");
        driver.findElement(By.cssSelector("button.button")).click();
        var actualTextElement = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid"));
        var actualText = actualTextElement.getText().trim();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", actualText);
        assertTrue(actualTextElement.isDisplayed());
    }
    @Test
    void shouldFindErrorWhenEmptyPhoneField() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Артур Пирожков");
        driver.findElement(By.cssSelector("button.button")).click();
        var actualTextElement = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub"));
        var actualText = actualTextElement.getText().trim();
        assertEquals("Поле обязательно для заполнения", actualText);
        assertTrue(actualTextElement.isDisplayed());
    }

    @Test
    void shouldFindErrorWhenInvalidName() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Joe Biden");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+12024561111");
        driver.findElement(By.cssSelector("button.button")).click();
        var actualTextElement = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub"));
        var actualText = actualTextElement.getText().trim();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actualText);
        assertTrue(actualTextElement.isDisplayed());
    }
}
