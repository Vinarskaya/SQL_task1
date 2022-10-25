package ru.netology.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.SQLHelper.clearDB;


public class VerificationTest {


    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }

    @AfterAll
    static void cleanUp() {
        clearDB();
    }

    @Test
    public void loginVerification() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = SQLHelper.getVerificationCodeFor();
        verificationPage.validVerify(verificationCode);
    }

    @Test
    public void invalidLoginVerification() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getInvalidAuthInfo();
        loginPage.invalidLogin(authInfo);
        loginPage.getErrorNotification();
    }

    @Test
    public void enterThePasswordByMistakeThreeTimes() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getInvalidPassword();
        loginPage.invalidLogin(authInfo);
        loginPage.cleanField();
        loginPage.invalidLogin(authInfo);
        loginPage.cleanField();
        loginPage.invalidLogin(authInfo);
        loginPage.blockNotification();
    }
}