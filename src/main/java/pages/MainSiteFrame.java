package pages;

import pages.search.SearchPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public abstract class MainSiteFrame {
    protected WebDriver driver;
    protected String correctHeader ="ТБВ онлайн";
    protected By navigationHeader = By.xpath("//div[@class='speedbar'] /span ");
    protected By mainPageButton = By.xpath("//div[@class='topmenu']//b[text()='Главная страница']");
    protected By feedbackButton = By.xpath("//div[@class='topmenu']//li/a[@href='/index.php?do=feedback']");
    protected By commentsButton = By.xpath("//div[@class='topmenu']//li/a[@href='/?do=lastcomments']");
    protected By mainSearchField = By.xpath("//input[@id='story']");
    protected By mainSearchButton = By.xpath("//input[@id='story']/..//../li[@class='lbtn']/input");

    public String getNavigationHeader() {
        return driver.findElement(navigationHeader).getText();
    }

    public MainSiteFrame(WebDriver driver) {
        this.driver = driver;
    }

    public abstract String getCorrectHeader() ;

    public SearchPage clickMainSearchButton() {
        driver.findElement(mainSearchButton).click();
        return new SearchPage(driver);
    }

    public void enterMainSearchRequest(String request) {
        driver.findElement(mainSearchField).sendKeys(request);
    }

    public MainPage clickMainPageButton(){
        driver.findElement(mainPageButton).click();
        return new MainPage(driver);
    }

    public FeedBackPage clickFeedbackButton(){
        driver.findElement(feedbackButton).click();
        return new FeedBackPage(driver);
    }


}
