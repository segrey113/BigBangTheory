package pages;

import org.openqa.selenium.WebDriver;

public class FeedBackPage extends MainSiteFrame {
    private WebDriver driver;
    private String CorrectHeader = super.correctHeader + " » Обратная связь";
    public FeedBackPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @Override
    public String getCorrectHeader() {
        return null;
    }
}
