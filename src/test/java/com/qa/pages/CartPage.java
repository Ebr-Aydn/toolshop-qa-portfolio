package com.qa.pages;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Locator;

import java.math.BigDecimal;

public class CartPage {

    private final Page page;
    private final Locator cartButton;
    private final Locator productTitle;
    private final Locator quantityInput;
    private final Locator removeProductButton;
    private final Locator continueShoppingButton;
    private final Locator unitPriceLabel;
    private final Locator linePriceLabel;
    private final Locator cartTotalLabel;


    public CartPage(Page page) {

        this.page = page;
        this.cartButton = page.locator("[data-test='nav-cart']");
        this.productTitle=page.locator("[data-test='product-title']");
        this.quantityInput = page.locator("[data-test='product-quantity']");
        this.removeProductButton = page.locator("[data-icon='xmark']");
        this.continueShoppingButton = page.locator("[data-test='continue-shopping']");
        this.unitPriceLabel = page.locator("[data-test='product-price']");
        this.linePriceLabel=page.locator("[data-test='line-price']");
        this.cartTotalLabel = page.locator("[data-test='cart-total']");
    }


    public void openCart() {
        cartButton.click();
    }


    public Locator productTitleInCart(){
        return productTitle;
    }


    public void continueShopping(){
        continueShoppingButton.click();
    }

    public void removeProduct(){
        removeProductButton.click();
    }


    public void setQuantity(int quantity){
        quantityInput.fill(String.valueOf(quantity));
        quantityInput.press("Tab"); // it helps to see the figures
    }

    public BigDecimal unitPrice(){
        String text=unitPriceLabel.textContent();
        return new BigDecimal(text.replace("$", "").trim());
    }

    public Locator linePrice(){
      return linePriceLabel;
    }

    public Locator totalPrice(){
       return cartTotalLabel;
    }



}
