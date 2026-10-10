package com.qa.config;

public class Config {
    public static final String BASE_URL =
            System.getenv().getOrDefault("BASE_URL", "http://localhost:4200");
}
