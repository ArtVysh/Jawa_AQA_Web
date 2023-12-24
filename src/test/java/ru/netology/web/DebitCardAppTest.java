package ru.netology.web;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.*;

public class DebitCardAppTest {
    @Test
    void shouldTestSending() {
        open("http://localhost:9999");
        SelenideElement name = $("[name=name]");
        name.setValue("Артур Пирожков");
        SelenideElement phone = $("[name=phone]");
        phone.setValue("+79279998877");
        SelenideElement checkbox = $(".checkbox__box");
        checkbox.click();
        SelenideElement button = $("button");
        button.click();
        $("#root > div > div > p").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }
}
