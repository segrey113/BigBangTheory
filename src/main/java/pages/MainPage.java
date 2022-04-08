package pages;

import org.openqa.selenium.WebDriver;

public class MainPage extends MainSiteFrame {
    private WebDriver driver;
    public MainPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @Override
    public String getCorrectHeader() {
        return super.correctHeader;
    }

}
