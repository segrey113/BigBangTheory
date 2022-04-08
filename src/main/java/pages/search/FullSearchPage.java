package pages.search;

import org.openqa.selenium.WebDriver;

public class FullSearchPage extends SearchPage {
    private WebDriver driver;


    public FullSearchPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @Override
    public String getCorrectHeader() {
       return super.getCorrectHeader();
    }
}
