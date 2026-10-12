package com.qa.tests;

import com.qa.base.BaseTest;
import com.qa.pages.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CheckoutTest extends BaseTest {

    private HomePage homePage;
    private CartPage cartPage;
    private ProductPage productPage;
    private LoginPage loginPage;
    private CheckoutPage checkoutPage;


    @BeforeEach
    void addProductToCart() {
        homePage = new HomePage(page);
        cartPage = new CartPage(page);
        productPage = new ProductPage(page);
        loginPage = new LoginPage(page);
        checkoutPage = new CheckoutPage(page);

        homePage.open();
        homePage.searchProducts("Claw Hammer");
        productPage.openProduct("Claw Hammer");
        productPage.addToCart();
        cartPage.openCart();
        checkoutPage.checkingOut();

    }

    @Nested
    @DisplayName("Registered user - positive payment scenarios")
    class registeredUser {
        @BeforeEach
        void loginCredentials() {
            loginPage.loginAs("customer@practicesoftwaretesting.com", "welcome01");
            checkoutPage.checkOutButtonForRegisteredUser();
            checkoutPage.billingAdress("Sweden", "41712", "3D", "Linnébacken", "Rolfs", "Sweden");
        }


        @Test
        void bankTransferPaymentSucceeds() {
            checkoutPage.bankTransfer("Nordea", "123456677", "678911234");
        }

        @AfterEach
        void orderMessagesSucceeds() {
            checkoutPage.cofirmClickingPaymentButtons();
            assertThat(checkoutPage.paymentMessage()).hasText("Payment was successful");  //hasText - text should be exactly same with the result
            assertThat(checkoutPage.confirm()).containsText("Thanks for your order! Your invoice number is "); //containsText check "xxx" containes in the result
        }

    }

    @Nested
    @DisplayName("Guest user - positive payment scenarios")
    class guestUser {
        @BeforeEach
        void guestLoginAndAdressProcess() {
            checkoutPage.continueAsGuest("ay@example.com", "Test", "Test");
            checkoutPage.billingAdress("Sweden", "41712", "3D", "Linnébacken", "Rolfs", "Sweden");
        }

        @Test
        void bankTransferPaymentSucceeds() {
            checkoutPage.bankTransfer("Nordea", "123456677", "678911234");
        }

        @Test
        void cashOnDeliverySucceeds() {
            checkoutPage.cashOnDelivery();
        }

        @Test
        void creditCardSucceeds() {
            checkoutPage.creditCardPayment("5555-5555-5555-5555", "12/2030", "123", "Test Test");
        }


        @ParameterizedTest(name = "Buy Now Pay Later with {0} months")
        //this helps to test different int values saparetly
        @ValueSource(ints = {3, 6, 9, 12})
        void buyNowPayLaterPaymentSucceeds(int month) {
            checkoutPage.buyNowPayLaterOption(month);
        }

        @Test
        void giftCardPaymentSucceeds() {
            checkoutPage.giftCardPayment("1234567890123456", "ASCV");
        }


        @AfterEach
        void orderMessagesSucceeds() {
            checkoutPage.cofirmClickingPaymentButtons();
            assertThat(checkoutPage.paymentMessage()).hasText("Payment was successful");  //hasText - text should be exactly same with the result
            assertThat(checkoutPage.confirm()).containsText("Thanks for your order! Your invoice number is "); //containsText check "xxx" containes in the result
        }

    }


    @Nested
    @DisplayName("Registered user negative scenarios")
    class invalidPaymentInputs {
        @BeforeEach
        void loginCredentials() {
            loginPage.loginAs("customer@practicesoftwaretesting.com", "welcome01");
            checkoutPage.checkOutButtonForRegisteredUser();
            checkoutPage.billingAdress("Sweden", "41712", "3D", "Linnébacken", "Rolfs", "Sweden");
        }

        @Test
        void bankTransferNotSucceeds() {
            checkoutPage.bankTransfer("", "", "aaa");
            assertThat(checkoutPage.disableCheckPayment()).isDisabled();
            assertThat(checkoutPage.fieldErrors()).containsText(new String[]{
                    "Bank name can only contain letters and spaces.",
                    "Account name can contain letters, numbers, spaces, periods, apostrophes, and hyphens.",
                    "Account number must be numeric. "
            });
        }

        @Test
        void creditCardPaymentNotSucceeds() {

            checkoutPage.creditCardPayment("aa", "aa", "aa", "Test Test");
            assertThat(checkoutPage.disableCheckPayment()).isDisabled();
            assertThat(checkoutPage.fieldErrors()).containsText(new String[]{
                    "Invalid card number format.",
                    "Invalid date format. Use MM/YYYY.",
                    "CVV must be 3 or 4 digits."
            });

        }

        @Test
        void giftCardNotSucceeds() {

            checkoutPage.giftCardPayment("wd", "wd");
            assertThat(checkoutPage.disableCheckPayment()).isDisabled();
            assertThat(checkoutPage.fieldErrors()).containsText(new String[]{
                    "Please enter a valid gift card number: exactly 16 letters and/or digits.",
                    "Please enter a valid validation code: exactly 4 letters and/or digits. ",
            });
        }





    }

    //Remain Negative tests: invalid guest inputs and Billing Adress
}
