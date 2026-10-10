package com.qa.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;


public class LoginPage {
    private final Page page;
    private final Locator emailInput;
    private final Locator passwordInput;
    private final Locator loginButton;
    private final Locator errorMessage;
    private final Locator emailError;
    private final Locator passwordError;


    public LoginPage(Page page) {
        this.page = page;
        this.emailInput = page.locator("[data-test='email']");
        this.passwordInput = page.locator("[data-test='password']");
        this.loginButton = page.locator("[data-test='login-submit']");
        this.errorMessage = page.locator("[data-test='login-error']");
        this.emailError=page.locator("[data-test='email-error']");
        this.passwordError=page.locator("[data-test='password-error']");
    }

    public void open() {
        page.navigate("https://practicesoftwaretesting.com/auth/login");
    }

    public void loginAs(String email, String password) {
        emailInput.fill(email);
        passwordInput.fill(password);
        loginButton.click();

    }

    public Locator errorMessage() {
        return errorMessage;
    }

    public Locator emailError(){
        return emailError;
    }

    public Locator passwordError(){
        return passwordError;
    }

}