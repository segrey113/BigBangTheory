package pages;

import org.openqa.selenium.WebDriver;

public class LastCommentsPage extends MainSiteFrame {
    private WebDriver driver;
    private String correctHeader = super.correctHeader + " » Обратная связь";

    public LastCommentsPage(WebDriver driver) {
        super(driver);
        this.driver=driver;
    }

    @Override
    public String getCorrectHeader() {
        return correctHeader;
    }
}
