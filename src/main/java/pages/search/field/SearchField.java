package pages.search.field;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class SearchField {
    private WebDriver driver;
    private By header;
    private By descriptions;
    private By seasonLink;
    private By seriesLink;
    private By imgLink;
    private final int serialNumber;

    public SearchField(WebDriver driver, int serialNumber) {
        serialNumber++;//in html index of table start from 1
        this.driver = driver;
        this.header = By.xpath("//div[@class='base shortstory'][" + serialNumber + "]//a[contains(text(),\"сери\")]  ");
        this.descriptions = By.xpath("//div[@class='base shortstory'][" + serialNumber + "]//div[@class='sstory']//p");
        this.seasonLink = By.xpath("//div[@class='base shortstory'][" + serialNumber + "]//li[@class='icat']/a[contains(text(),\"сезон\")]");
        this.seriesLink = By.xpath("//div[@class='base shortstory'][" + serialNumber + "]//span[@class='argmore']/a");
        this.imgLink = By.xpath("//div[@class='base shortstory'][" + serialNumber + "]//div[@class='sstory']/a");
        this.serialNumber = serialNumber;
    }

    public WebElement getSeriesLink() {
        return driver.findElement(seriesLink);
    }

    public WebElement getImgLink() {
        return driver.findElement(imgLink);
    }

    public int getSerialNumber() {
        return serialNumber;
    }

    public String getHeader() {
        return driver.findElement(header).getText();
    }

    public List<String> getDescriptions() {
        List<String> result = new ArrayList<>();
        for (WebElement description : driver.findElements(descriptions)
        ) {
            result.add(description.getText());
        }
        return result;
    }

    public WebElement getSeasonLink() {
        return driver.findElement(seasonLink);
    }
}
