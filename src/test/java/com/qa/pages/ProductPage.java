package com.qa.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;


public class ProductPage {

    private final Page page;
    private final Locator addToCartButton;
    private final Locator productCards;

    public ProductPage(Page page) {
        this.page = page;
        this.addToCartButton = page.locator("[data-test='add-to-cart']");
        this.productCards = page.locator("[data-test='product-name']");
    }

    public void openProduct(String productName) {
        productCards.filter(new Locator.FilterOptions().setHasText(productName)).first().click();

    }

    public void addToCart() {
        addToCartButton.click();
    }

    public Locator productTitle(){
        return productCards;
    }


}
