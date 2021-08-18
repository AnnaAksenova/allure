package ru.netology.card;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class DeliveryTest {

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replane meeting")
    void shouldSuccessfulPlanAndReplaneMeeting() {
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);

        SelenideElement form = $(".form");
        form.$("[data-test-id=city] input").setValue(DataGenerator.generateCity());
        $$(".menu-item__control").last().click();
        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        form.$("[data-test-id=date] input").setValue(firstMeetingDate);
        form.$("[data-test-id=name] input").setValue(DataGenerator.generateName());
        form.$("[data-test-id=phone] input").setValue("+7" + DataGenerator.generatePhone());
        form.$(".checkbox__box").click();
        form.$(".button__text").click();
        $(withText("Успешно!")).should(Condition.visible);
        $(".notification__content").should(Condition.text("Встреча успешно запланирована на " + firstMeetingDate)).should(Condition.visible);

        form.$("[data-test-id=date] input").doubleClick().sendKeys(Keys.DELETE);
        form.$("[data-test-id=date] input").setValue(secondMeetingDate);
        form.$(".button__text").click();
        $$(".button__content").find(Condition.exactText("Перепланировать")).click();
        $(withText("Успешно!")).should(Condition.visible);
        $(".notification__content").should(Condition.text("Встреча успешно запланирована на " + secondMeetingDate)).should(Condition.visible);

    }
}