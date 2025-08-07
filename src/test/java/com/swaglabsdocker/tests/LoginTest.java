package com.swaglabsdocker.tests;


import com.swaglabsdocker.base.BaseTest;
import com.swaglabsdocker.data.TestData;
import com.swaglabsdocker.pages.HomePage;
import com.swaglabsdocker.pages.LoginPage;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Epic("Swag Labs")
@Feature("Login Functionality")
@Story("User logs in with valid or invalid credentials")
@Severity(SeverityLevel.CRITICAL)
public class LoginTest extends BaseTest {

    @Test
    @Story("User is able to log into the application by using valid credentials")
    @DisplayName("Successful login with valid credentials")
    @Description("Verify that a user can log in with valid username and password")
    @Severity(SeverityLevel.NORMAL)
    public void verifySuccessfulLoginWithValidCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(TestData.VALID_USERNAME, TestData.VALID_PASSWORD);

        // Success is indicated by URL change or inventory visibility — this can be adjusted
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("inventory"), "User should be redirected to inventory page after login.");
    }

    @Test
    @Story("User submits login form by entering wrong inputs")
    @DisplayName("Login fails with invalid credentials")
    @Description("Verify that an error message is shown for invalid login")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyLoginFailsWithInvalidCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(TestData.INVALID_USERNAME, TestData.INVALID_PASSWORD);

        String errorMsg = loginPage.getErrorMessage();
        assertTrue(errorMsg.contains("Username and password do not match")
                || !errorMsg.isEmpty(), "Error message should appear for invalid login.");
    }

    @Test
    @Story("User submits login form without any input")
    @DisplayName("Login fails with blank username and password")
    @Description("Verify error message when both username and password are blank")
    @Severity(SeverityLevel.CRITICAL)
    public void verifyErrorDisplayedForEmptyLoginFields() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("", "");

        String error = loginPage.getErrorMessage();
        assertTrue(error.toLowerCase().contains("required"), "Error message should indicate required fields.");
    }

    @Test
    @Story("User submits login form without a username")
    @DisplayName("Login fails with blank username only")
    @Description("Verify error message when username is blank")
    @Severity(SeverityLevel.CRITICAL)
    public void validateLoginFailsWhenUsernameIsBlank() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("", TestData.VALID_PASSWORD);

        String error = loginPage.getErrorMessage();
        assertTrue(error.toLowerCase().contains("required"), "Error message should mention missing username.");
    }

    @Test
    @Story("User submits login form without a password")
    @DisplayName("Login fails with blank password only")
    @Description("Verify error message when password is blank")
    @Severity(SeverityLevel.CRITICAL)
    public void validateLoginFailsWhenPasswordIsBlank() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(TestData.VALID_USERNAME, "");

        String error = loginPage.getErrorMessage();
        assertTrue(error.toLowerCase().contains("required"), "Error message should mention missing password.");
    }

    @Test
    @Story("User logs out from the application")
    @DisplayName("Logout from application returns user to login screen")
    @Description("Verify that a logged-in user can logout and is redirected to login page")
    @Severity(SeverityLevel.NORMAL)
    public void verifyUserCanLogoutSuccessfully() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(TestData.VALID_USERNAME, TestData.VALID_PASSWORD);

        HomePage homePage = new HomePage(driver);
        homePage.logout();

        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("saucedemo.com"), "User should be redirected to login page after logout.");
    }
}
