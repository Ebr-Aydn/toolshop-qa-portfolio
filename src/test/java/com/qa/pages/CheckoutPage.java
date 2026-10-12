package com.qa.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class CheckoutPage {

    //declaration section
    private final Page page;
    private final Locator checkOut;
    private final Locator proceedToCheckOut2;

    //pay as guest locators
    private final Locator continueAsGuestTab;
    private final Locator guestEmail;
    private final Locator guestFirstName;
    private final Locator guestLastName;
    private final Locator guestSubmit;

    //error messages related to guest
    private final Locator guestEmailError;
    private final Locator guestNameError;
    private final Locator guestLastNameError;

    private final Locator proceedCheckOutAsGuest;

    //billing adress locators
    private final Locator countryList;
    private final Locator postCode;
    private final Locator houseNumber;
    private final Locator streetInput;
    private final Locator cityInput;
    private final Locator stateInput;
    private final Locator proceedToCheckOut3;

    private final Locator paymentMethod;

    //Bank Transfer loctors
    private final Locator bankNameInput;
    private final Locator accountNameInput;
    private final Locator accountNumberInput;


    //credit card locators
    private final Locator creditCardNum;
    private final Locator expireDate;
    private final Locator cvvInput;
    private final Locator cardHolderName;

    //Buy Now Pay Later locator
    private final Locator monthlyInstallment;

    //giftCard locators
    private final Locator giftCardNumInput;
    private final Locator validationCodeInput;

    private final Locator checkPaymentButton;
    private final Locator confirmPaymentButton;
    private final Locator paymentSuccessMessage;
    private final Locator confirmationMessage;


    //method-construction section

    public CheckoutPage(Page page) {
        this.page = page;
        this.checkOut = page.locator("[data-test='proceed-1']");
        this.proceedToCheckOut2=page.locator("[data-test='proceed-2']");
        this.continueAsGuestTab=page.getByRole(AriaRole.TAB, new Page.GetByRoleOptions().setName("Continue as Guest")); //if there is no data-test,then getByRole data-test->getByRole->id
        this.guestEmail = page.locator("[data-test='guest-email']");
        this.guestFirstName = page.locator("[data-test='guest-first-name']");
        this.guestLastName = page.locator("[data-test='guest-last-name']");
        this.guestSubmit = page.locator("[data-test='guest-submit']");
        this.guestEmailError = page.locator("[data-test='guest-email-error']");
        this.guestNameError = page.locator("[data-test='guest-first-name-error']");
        this.guestLastNameError = page.locator("[data-test='guest-last-name-error']");
        this.proceedCheckOutAsGuest = page.locator("[data-test='proceed-2-guest']");
        this.countryList = page.locator("[data-test='country']");
        this.postCode = page.locator("[data-test='postal_code']");
        this.houseNumber = page.locator("[data-test='house_number']");
        this.streetInput = page.locator("[data-test='street']");
        this.cityInput = page.locator("[data-test='city']");
        this.stateInput = page.locator("[data-test='state']");
        this.proceedToCheckOut3 = page.locator("[data-test='proceed-3']");
        this.paymentMethod = page.locator("[data-test='payment-method']");
        this.bankNameInput = page.locator("[data-test='bank_name']");
        this.accountNameInput = page.locator("[data-test='account_name']");
        this.accountNumberInput = page.locator("[data-test='account_number']");
        this.creditCardNum = page.locator("[data-test='credit_card_number']");
        this.expireDate = page.locator("[data-test='expiration_date']");
        this.cvvInput = page.locator("[data-test='cvv']");
        this.cardHolderName = page.locator("[data-test='card_holder_name']");
        this.monthlyInstallment = page.locator("[data-test='monthly_installments']");
        this.giftCardNumInput = page.locator("[data-test='gift_card_number']");
        this.validationCodeInput = page.locator("[data-test='validation_code']");
        this.checkPaymentButton=page.locator("[data-test='finish']");
        this.confirmPaymentButton = page.locator("[data-test='finish']");
        this.paymentSuccessMessage = page.locator("[data-test='payment-success-message']"); //Payment was successful
        this.confirmationMessage = page.locator("[id='order-confirmation']"); //Thanks for your order! Your invoice number is  successful

    }


    public void checkingOut() {
        checkOut.click();
    }

    public void checkOutButtonForRegisteredUser(){
        proceedToCheckOut2.click();
        assertThat(streetInput).not().hasValue("");//only checking via streetInput is fine because the system fills everything
        //at the same time, so if one place is filled then others also filled
    }

    public void continueAsGuest(String email, String firstName, String lastName) {
        continueAsGuestTab.click();
        guestEmail.fill(email);
        guestFirstName.fill(firstName);
        guestLastName.fill(lastName);
        guestSubmit.click();
        proceedCheckOutAsGuest.click();
    }

    public void billingAdress(String country, String postNumber, String houseNum, String streetName, String cityName, String stateName) {

        countryList.selectOption(country);
        postCode.fill(postNumber);
        page.waitForResponse(
                r -> r.url().contains("/postcode-lookup"),
                () -> houseNumber.fill(houseNum));
        streetInput.fill(streetName);
        cityInput.fill(cityName);
        stateInput.fill(stateName);
        proceedToCheckOut3.click();

    }

    //payment methods: Cash on Delivery, Bank Transfer, Credit Card, Buy Now Pay Later, Gift Card
    public void bankTransfer(String bankName, String accountName, String accountNumber) {
        paymentMethod.selectOption("bank-transfer"); //"bank-transfer" is value of selected option, so value is more safe therefore it is used.
        bankNameInput.fill(bankName);
        accountNameInput.fill(accountName);
        accountNumberInput.fill(accountNumber);

    }

    public void cofirmClickingPaymentButtons(){
        checkPaymentButton.click();
        confirmPaymentButton.click();
    }

    public void cashOnDelivery(){
        paymentMethod.selectOption("cash-on-delivery");

    }


    public void creditCardPayment(String creditCardNumber, String expirationDate, String cvv, String holderName){
        paymentMethod.selectOption("Credit Card");
        creditCardNum.fill(creditCardNumber);
        expireDate.fill(expirationDate);
        cvvInput.fill(cvv);
        cardHolderName.fill(holderName);
    }


    public void buyNowPayLaterOption(int month ){
        paymentMethod.selectOption("buy-now-pay-later");
        monthlyInstallment.selectOption(String.valueOf(month));

    }


    public void giftCardPayment(String giftCardValue, String validationCodeNum){
        paymentMethod.selectOption("gift-card");
        giftCardNumInput.fill(giftCardValue);
        validationCodeInput.fill(validationCodeNum);
    }



    public Locator paymentMessage(){
        return paymentSuccessMessage;
    }

    public Locator confirm(){
        return confirmationMessage;
    }


    public Locator guestInvalidEmail(){
        return guestEmailError;
    }

   public Locator guestInvalidName(){
        return  guestNameError;
   }

   public Locator guestInvalidLastName(){
       return guestLastNameError;
   }

    public Locator fieldErrors() {
        return page.locator(".alert-danger");
    }

    public Locator disableCheckPayment(){
        return checkPaymentButton;
    }

}
