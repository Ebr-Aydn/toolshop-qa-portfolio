package com.qa.tests;

import com.microsoft.playwright.Locator;
import com.qa.base.BaseTest;
import com.qa.pages.CartPage;
import com.qa.pages.HomePage;
import com.qa.pages.ProductPage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class CartTest extends BaseTest {

    private CartPage cartPage;
    private HomePage homePage;
    private ProductPage productPage;

    @BeforeEach
    void setUp() {
        homePage = new HomePage(page);
        productPage = new ProductPage(page);
        cartPage = new CartPage(page);
        homePage.open();
        homePage.searchProducts("Claw Hammer");
        productPage.openProduct("Claw Hammer");

    }


    @Test
    void addedProductAppearsInCart() {
        assertThat(productPage.productTitle()).containsText("Claw Hammer");
        productPage.addToCart();
        assertThat(page.getByText("Product added to shopping cart")).isVisible();
        cartPage.openCart();
        assertThat(cartPage.productTitleInCart()).containsText("Claw Hammer");
    }


    @Test
    void changingQuantityUpdatesPrices() {
        productPage.addToCart();
        cartPage.openCart();

        int quantity = 2;
        BigDecimal expected = cartPage.unitPrice().multiply(BigDecimal.valueOf(quantity));
        cartPage.setQuantity(quantity);

        assertThat(cartPage.linePrice()).hasText("$" + expected.setScale(2));
        assertThat(cartPage.totalPrice()).hasText("$" + expected.setScale(2));

    }

    @Test
    void continueShoppingKeepsExistingItems() {
        productPage.addToCart();
        cartPage.openCart();
        cartPage.continueShopping();
        homePage.searchProducts("Combination Pliers");
        productPage.openProduct("Combination Pliers");
        page.waitForResponse(response -> response.url().contains("/carts/")
                && response.request().method().equals("POST"), ()
                -> productPage.addToCart());
        cartPage.openCart();
        assertThat(cartPage.productTitleInCart()).hasCount(2);
        assertThat(cartPage.productTitleInCart().filter(new Locator.FilterOptions().setHasText("Combination Pliers"))).isVisible();


    }


    @Test
    void removeProductInCart(){
        productPage.addToCart();
        cartPage.openCart();
        cartPage.removeProduct();
        assertThat(page.getByText("Product deleted")).isVisible();
    }


}
