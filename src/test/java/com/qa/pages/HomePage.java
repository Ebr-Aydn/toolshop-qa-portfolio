package com.qa.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.qa.config.Config;

public class HomePage {

    private final Page page;
    private final Locator searchBar;
    private final Locator searchButton;
    //private final Locator productTitle;

    public HomePage(Page page) {
        this.page = page;
        this.searchBar = page.locator("[data-test='search-query']");
        this.searchButton = page.locator("[data-test='search-submit']");
        //this.productTitle=page.locator("[data-test='product-title']");
    }

    public void open() {
        page.navigate(Config.BASE_URL);
    }

    public void searchProducts(String searchTerm) {
        searchBar.fill(searchTerm);
        searchButton.click();
    }


}
