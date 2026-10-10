package com.qa.tests;

import com.qa.base.BaseTest;
import com.qa.config.Config;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class SmokeTest extends BaseTest {
    @Test
   void homePageShowsProducts(){
        page.navigate(Config.BASE_URL);
     assertThat(page).hasTitle(Pattern.compile("Toolshop"));
   assertThat(page.locator("[data-test='product-name']").first()).isVisible();
   }
}
