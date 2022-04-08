package pages.search;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.search.field.SearchField;

import java.time.Duration;
import java.util.List;
import java.util.regex.Pattern;

public class SearchPageTest {
    private WebDriver driver;
    private SearchPage searchPage;
    private final String nothingFoundMessage = "Информация\n" +
            "К сожалению, поиск по сайту не дал никаких результатов. Попробуйте изменить или сократить Ваш запрос.";
    private final String badRequestMessage = "Информация\n" +
            "Введено пустое поле для поиска или строка поиска содержит менее 4 символов, в связи с чем поиск был приостановлен.";

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "D:\\selenium\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.manage().window().maximize();
        driver.get("http://big-bang-online.com/index.php?do=search");
        searchPage = new SearchPage(driver);
    }

    @AfterEach
    public void close() {
        driver.quit();
    }

    @Test
    public void headerTest() {
        Assertions.assertEquals(searchPage.getCorrectHeader(), searchPage.getNavigationHeader());
    }

    @Test
    public void searchFieldTakeTextTest() {
        String testText = "test text";
        searchPage.enterSearchRequest(testText);
        searchPage.clickSearchButton();
        Assertions.assertEquals(testText, searchPage.getSearchRequest());
    }

    @Test
    public void searchFieldTakeTextFromMainFieldTest() {
        String testText = "test text";
        searchPage.enterMainSearchRequest(testText);
        searchPage.clickMainSearchButton();
        Assertions.assertEquals(testText, searchPage.getSearchRequest());
    }

    @Test
    public void searchFieldTakeMaxSizeRequestTextLengthTest() {
        String testText = "test text length is 90                                                                   !";
        searchPage.enterSearchRequest(testText);
        searchPage.clickSearchButton();
        Assertions.assertEquals(testText, searchPage.getSearchRequest());
    }

    @Test
    public void searchFieldTakeMoreMaxSizeRequestTextLengthTest() {
        String testText = "test text length is more than 90                                                                     !";
        searchPage.enterSearchRequest(testText);
        searchPage.clickSearchButton();
        Assertions.assertEquals(testText.substring(0, 90), searchPage.getSearchRequest());
    }

    @Test
    public void searchFieldTakeMoreMaxSizeRequestTextLengthFromMainFieldTest() {
        String testText = "test text length is more than 90                                                                    !";
        searchPage.enterMainSearchRequest(testText);
        searchPage.clickMainSearchButton();
        Assertions.assertEquals(testText.substring(0, 90), searchPage.getSearchRequest());
    }

    @Test
    public void searchFieldTakenEmptyRequestTest() {
        searchPage.enterSearchRequest("");
        searchPage.clickSearchButton();
        Assertions.assertEquals("", searchPage.getSearchRequest());
    }

    @Test
    public void EmptyRequestTest() {
        searchPage.enterSearchRequest("");
        searchPage.clickSearchButton();
        Assertions.assertEquals(badRequestMessage, searchPage.getResultInformation());
    }

    @Test
    public void ShortRequestTest() {
        searchPage.enterSearchRequest("123");
        searchPage.clickSearchButton();
        Assertions.assertEquals(badRequestMessage, searchPage.getResultInformation());
    }

    @Test
    public void MinimalLengthRequestTest() {
        searchPage.enterSearchRequest("1234");
        searchPage.clickSearchButton();
        if (!searchPage.isFound()) {
            Assertions.assertEquals(nothingFoundMessage, searchPage.getResultInformation());
        } else {
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void NormalRequestFromShortWordsTest() {
        searchPage.enterSearchRequest("мы в лес");
        searchPage.clickSearchButton();
        if (!searchPage.isFound()) {
            Assertions.assertEquals(nothingFoundMessage, searchPage.getResultInformation());
        } else {
            Assertions.assertTrue(true);
        }
    }

    @Test
    public void GoodRequestTextMessageTest() {
        searchPage.enterSearchRequest("11 сезон 2 серия");
        searchPage.clickSearchButton();
        System.out.println(searchPage.getResultInformation());
        Assertions.assertTrue(Pattern.matches("По Вашему запросу найдено [1-9][0-9]? ответов \\(\\Результаты запроса [1-9][0-9]? - [1-9][0-9]?\\)\\ :",
                searchPage.getResultInformation()));
    }

    @Test
    public void ResultsByPageCountTest() {
        searchPage.enterSearchRequest("11 сезон 2 серия");
        searchPage.clickSearchButton();
        if (!searchPage.isFound()) {
            Assertions.assertEquals(nothingFoundMessage, searchPage.getResultInformation());
        } else {
            Assertions.assertEquals(searchPage.getPageResultsCountFromMessage(), searchPage.getPageResultsCount());
        }
    }

    @Test
    public void ResultsCountTest() {
        searchPage.enterSearchRequest("11 сезон 2 серия");
        searchPage.clickSearchButton();
        if (!searchPage.isFound()) {
            Assertions.assertEquals(nothingFoundMessage, searchPage.getResultInformation());
        } else {
            int resultCount = 0;
            int pageNumber = 1;
            do {
                resultCount += searchPage.getPageResultsCount();
                Assertions.assertEquals(searchPage.getPageResultsCount(), searchPage.getPageResultsCountFromMessage(), "On page №" + pageNumber);
                pageNumber++;
            }
            while (searchPage.clickNextPageButton());
            Assertions.assertEquals(resultCount, searchPage.getResultsCountFromMessage());

        }
    }

    @Test
    public void ResultsContainsRequestWords() {
        String request="шелд";
        String requestWords[] = request.split("\\p{P}|[ ]");
        searchPage.enterSearchRequest(request);
        searchPage.clickSearchButton();
        if(!searchPage.isFound()){
            Assertions.assertTrue(false,"Enter search request that contains results");
        }else {
            int pageNumber = 1;
            do {
                List<SearchField> results = searchPage.getPageSearchResult();
                for (SearchField searchField:results
                     ) {
                    Assertions.assertTrue(findWordsInText(requestWords,searchField.getHeader(),searchField.getDescriptions()),"SearchField № "+searchField.getSerialNumber()+" on page № "+pageNumber+" doesn't contain request words");
                }
                pageNumber++;
            }
            while (searchPage.clickNextPageButton());
        }

    }

    public boolean findWordsInText(String[] requestWords, String header, List<String> descriptions) {
        for (String requestWord:requestWords
             ) {
            if(header.contains(requestWord))return true;
            for (String description:descriptions
                 ) {
                if(description.contains(requestWord))return true;
            }
        }
        return false;
    }


}
