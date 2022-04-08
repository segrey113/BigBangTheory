package com;
import pages.*;
import pages.search.SearchPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;


public class Main {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "D:\\selenium\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
       driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        driver.manage().window().maximize();
        driver.get("http://big-bang-online.com/");
        MainPage page = new MainPage(driver);
        page.enterMainSearchRequest("сезон 1");
        System.out.println(page.getCorrectHeader());
        System.out.println(page.getNavigationHeader());
        System.out.println(page.getCorrectHeader().equals(page.getNavigationHeader()));
        SearchPage searchPage = page.clickMainSearchButton();
        System.out.println(searchPage.getCorrectHeader());
        System.out.println(searchPage.getNavigationHeader());
        System.out.println(searchPage.getCorrectHeader().equals(searchPage.getNavigationHeader()));
        System.out.println(searchPage.getResultInformation());

        driver.close();
    }
}
