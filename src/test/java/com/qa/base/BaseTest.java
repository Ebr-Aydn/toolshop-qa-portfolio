package com.qa.base;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;

import java.nio.file.Paths;

public class BaseTest {

        protected static Playwright playwright;
        protected static Browser browser;
        protected BrowserContext context;
        protected Page page;

        @BeforeAll
        static void launchBrowser() {
            playwright = Playwright.create();
            boolean isCI = System.getenv("CI") != null;
            browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions().setHeadless(isCI));
        }

        @AfterAll
        static void closeBrowser() {
            playwright.close();
        }

        @BeforeEach
        void createContext() {
            context = browser.newContext();
            page = context.newPage();
        }

        @AfterEach
            void closeContext(TestInfo testInfo) {
                page.screenshot(new Page.ScreenshotOptions()
                        .setPath(Paths.get("target/screenshots/" + testInfo.getDisplayName() + ".png"))
                        .setFullPage(true));
                context.close();
        }


    }
