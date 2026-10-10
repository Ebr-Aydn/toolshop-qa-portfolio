package com.qa.tests;

import com.qa.base.BaseTest;
import com.qa.pages.LoginPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@DisplayName("Login")
public class LoginTest extends BaseTest {
    private LoginPage loginPage;

    @BeforeEach
    void setLoginPage() {
        loginPage = new LoginPage(page);
        loginPage.open();
    }

    @Nested
    @DisplayName("Positive scenarios")
    class Positive {
        @DisplayName("Valid user can log in")
        @Test
        void validUserCanLogIn() {
            loginPage.loginAs("customer@practicesoftwaretesting.com", "welcome01");
            assertThat(page).hasURL(Pattern.compile(".*/account"));
        }
    }


    @Nested
    @DisplayName("Negative scenarios")
    class Negative {

        @DisplayName("Invalid password shows an error")
        @Test
        void invalidPasswordShowsError() {
            loginPage.loginAs("customer@practicesoftwaretesting.com", "welcome00");
            assertThat(loginPage.errorMessage()).isVisible();
            assertThat(loginPage.errorMessage()).containsText("Invalid email or password");
            assertThat(page).hasURL(Pattern.compile(".*/auth/login"));
        }

        @DisplayName("Unregistered email shows error")
        @Test
        void unregisteredEmailShowsError(){
            loginPage.loginAs("unknown.user@example.com", "welcome01");
            assertThat(loginPage.errorMessage()).isVisible();
            assertThat(loginPage.errorMessage()).containsText("Invalid email or password");
            assertThat(page).hasURL(Pattern.compile(".*/auth/login"));
        }

        @DisplayName("Invalid email format shows required error")
        @Test
        void invalidEmailShowsRequiredError(){
            loginPage.loginAs("example.", "welcome01");
            assertThat(loginPage.emailError()).isVisible();
            assertThat(loginPage.emailError()).containsText("Email format is invalid");
            assertThat(page).hasURL(Pattern.compile(".*/auth/login"));
        }

        @DisplayName("Empty email shows required error")
        @Test
        void emptyEmailShowsRequiredError() {
            loginPage.loginAs("", "welcome01");
            assertThat(loginPage.emailError()).isVisible();
            assertThat(loginPage.emailError()).containsText("Email is required");
            assertThat(page).hasURL(Pattern.compile(".*/auth/login"));
        }

        @DisplayName("Empty password shows required erro")
        @Test
        void emptyPasswordShowsRequiredError(){
            loginPage.loginAs("customer@practicesoftwaretesting.com", "");
            assertThat(loginPage.passwordError()).isVisible();
            assertThat(loginPage.passwordError()).containsText("Password is required");
            assertThat(page).hasURL(Pattern.compile(".*/auth/login"));
        }

    }
}