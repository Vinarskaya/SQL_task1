package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.*;
import java.util.Locale;

public class DataHelper {
    private DataHelper() {
    }
    public static Faker faker = new Faker(new Locale("en"));

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getInvalidAuthInfo() {
        return new AuthInfo(faker.name().fullName(), faker.internet().password());
    }

    public static AuthInfo getInvalidPassword() {
        return new AuthInfo("vasya", faker.internet().password());
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode generateRandomVerificationCode() {
        return new VerificationCode(faker.numerify("######"));
    }
}
