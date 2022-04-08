package pages.search;

import pages.MainSiteFrame;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.search.field.SearchField;

import java.util.ArrayList;
import java.util.List;

public class SearchPage extends MainSiteFrame {
    protected WebDriver driver;
    protected String correctHeader = super.correctHeader + " » Поиск по сайту";
    protected By searchField = By.xpath("//input[@id='searchinput']");
    protected By searchButton = By.xpath("//input[@id='dosearch']");
    protected By fullSearchButton = By.xpath("//input[@id='dofullsearch']");
    protected By BadSearchResultMessage = By.xpath("//div[@class='berrors'] ");
    protected By searchResultMessage = By.xpath("//td[@class='search'] /div[contains(text(),\"По Вашему запросу\")]");
    protected By searchResults = By.xpath("//div[@class='base shortstory']");
    protected By nextPageButton = By.xpath("//a[@id='nextlink']");


    public SearchPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
    }

    @Override
    public String getCorrectHeader() {
        return correctHeader;
    }

    public boolean hasNextPage() {
        return driver.findElements(nextPageButton).size() > 0 ? true : false;
    }

    public boolean clickNextPageButton() {
        if (hasNextPage()) {
            driver.findElement(nextPageButton).click();
            return true;
        } else{
            return false;
        }
    }

    public void clickSearchButton() {

        driver.findElement(searchButton).click();
    }

    public void enterSearchRequest(String request) {

        driver.findElement(searchField).sendKeys(request);
    }

    public String getSearchRequest() {
        return driver.findElement(searchField).getAttribute("value");
    }

    public FullSearchPage clickFullSearchButton() {
        driver.findElement(fullSearchButton).click();
        return new FullSearchPage(driver);
    }

    public boolean isFound() {
        return driver.findElements(BadSearchResultMessage).size() > 0 ? false : true;
    }

    public String getResultInformation() {
        if (isFound()) {
            return driver.findElement(searchResultMessage).getText();
        }
        return driver.findElement(BadSearchResultMessage).getText();

    }

    public int getResultsCountFromMessage() {
        if (isFound()) {
            String message = driver.findElement(searchResultMessage).getText();
            String value = message.substring(message.indexOf("найдено ") + 8, message.indexOf(" ответов"));
            return Integer.parseInt(value);
        }
        return 0;
    }

    public int getPageResultsCountFromMessage() {
        if (isFound()) {
            String message = driver.findElement(searchResultMessage).getText();
            String[] values = message.substring(0, message.length() - 3)           //delete ") :" from end of string
                    .substring(message.indexOf("Результаты запроса ") + 19)     // find range (like 1-10)
                    .split(" ");                                          //get array (like [1,-,10)
            int firstNumber = Integer.parseInt(values[0]);
            int secondNumber = Integer.parseInt(values[2]);
            return secondNumber - firstNumber + 1;
        }
        return 0;
    }

    public int getPageResultsCount() {
        return driver.findElements(searchResults).size();
    }

    public List<SearchField> getPageSearchResult() {
        List<SearchField> result = new ArrayList<>();
        for (int i = 0; i < getPageResultsCount(); i++) {
            result.add(new SearchField(driver, i));
        }
        return result;
    }
}
