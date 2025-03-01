package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.time.Duration;
import java.util.List;
import java.util.logging.Level;

import demo.utils.ExcelDataProvider;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;
import dev.failsafe.Timeout;
import dev.failsafe.internal.util.Assert;

public class TestCases extends ExcelDataProvider{ // Lets us read the data
        ChromeDriver driver;

        /*
         * TODO: Write your tests here with testng @Test annotation.
         * Follow `testCase01` `testCase02`... format or what is provided in
         * instructions
         */
        @Test (enabled = true)
        public void testCase01() {
                System.out.println("Testcase01 : Started");
                
                Wrappers.goToURLandWait(driver);
                String currentURL = driver.getCurrentUrl();
                SoftAssert sa = new SoftAssert();
                sa.assertTrue(currentURL.contains("youtube.com"), "URL does not match!");
                sa.assertAll();

                try {
                        WebElement aboutLink = driver.findElement(By.xpath("//a[@href = 'https://www.youtube.com/about/']"));
                        aboutLink.click();
                        //Wrappers.waitForPageLoad(driver);
                        WebElement aboutText = driver.findElement(By.xpath("//section[@class = 'ytabout__content']"));  
                        System.out.println("About Message: " + aboutText.getText());
                } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println("Testcase01 Failed: Failed to extract about content");
                }
                
                System.out.println("Testcase01 : Completed");
        }

        @Test (enabled = true)
        public void testCase02() {
                System.out.println("Testcase02 : Started");
                SoftAssert softAssert = new SoftAssert();
                Wrappers.goToURLandWait(driver);

                try {
                WebElement filmsButton = driver.findElement(By.xpath("//a[@href='/feed/storefront?bp=ogUCKAU%3D']"));
                filmsButton.click();
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//button[@aria-label='Next' and not(@aria-disabled='true')])")));
                
                WebElement scrollArrow = driver.findElement(By.xpath("(//button[@aria-label='Next' and not(@aria-disabled='true')])"));
        
                Wrappers.clickUntilDisappears(scrollArrow,driver);

                }
                catch (Exception e) {
                        // TODO: handle exception
                        
                }

                try{
                List<WebElement> movies = driver.findElements(By.xpath("//div[@id='items']//ytd-grid-movie-renderer"));
                WebElement lastMovie = movies.get(movies.size() - 1);

                // Step 5: Extract movie rating (A, U/A, U, etc.)
                WebElement ratingElement = lastMovie.findElement(By.xpath(".//p[contains (text(),'A') or contains(text(),'U/A') or contains(text(),'U')]"));
                String rating = ratingElement.getText();
                System.out.println("Movie Rating: " + rating);

                // Step 6: Apply Soft Assert for Mature (A) rating
                softAssert.assertFalse(rating.equals("A"), "The last movie is marked as 'A' (Mature Content)!");

                // Step 7: Extract category (Comedy, Drama, etc.)
                WebElement categoryElement = lastMovie.findElement(By.xpath(".//span[contains(text(),'Comedy') or contains(text(),'Drama') or contains(text(),'Animation') or contains(text(),'Indian cinema')]"));
                String category = categoryElement.getText();
                System.out.println("Movie Category: " + category);

                // Step 8: Apply Soft Assert to verify category exists
                softAssert.assertTrue(category.matches("Comedy|Drama|Animation|Indian cinema"), "Category does not match expected values!");
                }
                catch (Exception e) {
                        // TODO: handle exception
                        
                }

                System.out.println("Testcase02 : Completed");
        }

        @Test (enabled = true)
        public void testCase03() throws InterruptedException {
                System.out.println("Testcase03: Started");
                Wrappers.goToURLandWait(driver);
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                
                
                //go to music tab
                try {
                        WebElement musicTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title=\"Music\"]")));
                        musicTab.click();
                } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println("Testcase03 Failed: Could not find Music tab");
                }

                //Go to Indias Biggest Hits
                By playlistSection = By.xpath("//span[contains(text(), \"India's Biggest Hits\")]/ancestor::div[@id='dismissible']");
                wait.until(ExpectedConditions.visibilityOfElementLocated(playlistSection));
        
        // Find all playlists under that section
        List<WebElement> indianHits = driver.findElements(By.xpath("//a[@class='yt-lockup-metadata-view-model-wiz__title']/span"));
                Thread.sleep(5000);
                // Ensure there are enough elements before accessing index 3
                if (indianHits.size() > 3) {
                String playlistTitle = indianHits.get(3).getText();
                System.out.println("Rightmost Playlist: " + playlistTitle); //print title
                } 
                else 
                {
                System.out.println("Not enough playlists found!");
                }
        //count
        SoftAssert sa = new SoftAssert();
        List<WebElement> songCounts = driver.findElements(By.xpath(".//div[@class='badge-shape-wiz__text']"));
                Thread.sleep(5000);
                if (songCounts.size() > 3) {
                        String songCount = songCounts.get(3).getText();
                        
                        int count = Integer.parseInt(songCount.replaceAll("[^0-9]", ""));
                        System.out.println("count of songs in the playlist: " + count);
                        sa.assertTrue(count <= 50, "The number of tracks listed is less than or equal to 50");
                }
                else {
                System.out.println("Not enough songcount found!");
                }
                
                System.out.println("Testcase03 : Completed");
        }

        @Test (enabled = true)
        public void testCase04() {
                System.out.println("Testcase04 : Started");
                Wrappers.goToURLandWait(driver);
                WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(10));
                
                //click on News tab
                try {
                        WebElement newsTab = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//a[@id=\"endpoint\"])[12]")));
                        newsTab.click();  
                        Thread.sleep(3000);
                } catch (Exception e) {
                        // TODO: handle exception
                        System.out.println("Testcase04 Failed : Could not find News Tab");
                }

                // Get section text from the second matching element
String section = driver.findElement(By.xpath("(//div[@id='title-text'])[2]/span")).getText();
System.out.println("Section: " + section);

// Variable to store total likes
int totalLikes = 0;

for (int i = 1; i <= 3; i++) {  // Start from 1, since XPath index starts from 1
    // Construct XPaths for header, body, and like count
    String headerXPath = "(//span[text()='" + section + "']/ancestor::ytd-rich-shelf-renderer//div[@id='header'])[" + i + "]";
    String bodyXPath = "(//span[text()='" + section + "']/ancestor::ytd-rich-shelf-renderer//div[@id='body'])[" + i + "]";
    String likeCountXPath = "(//span[text()='" + section + "']/ancestor::ytd-rich-shelf-renderer//span[@id='vote-count-middle'])[" + i + "]";

    // Find elements safely
    List<WebElement> headerElements = driver.findElements(By.xpath(headerXPath));
    List<WebElement> bodyElements = driver.findElements(By.xpath(bodyXPath));
    List<WebElement> likeCountElements = driver.findElements(By.xpath(likeCountXPath));

    // Extract and print values
    String headerText = !headerElements.isEmpty() ? headerElements.get(0).getText() : "No header found";
    String bodyText = !bodyElements.isEmpty() ? bodyElements.get(0).getText() : "No body found";
    String likeCountText = !likeCountElements.isEmpty() ? likeCountElements.get(0).getText() : "0"; // If no likes, set to "0"

    // Convert like count to integer (handles non-numeric cases)
    int likeCount = 0;
    try {
        likeCount = Integer.parseInt(likeCountText.replaceAll("[^0-9]", "")); // Remove non-numeric characters
    } catch (NumberFormatException e) {
        likeCount = 0; // Default to 0 if parsing fails
    }

    // Add to total likes
    totalLikes += likeCount;

    System.out.println("Header [" + i + "]: " + headerText);
    System.out.println("Body [" + i + "]: " + bodyText);
    System.out.println("Like Count [" + i + "]: " + likeCount);
    System.out.println("----------------------------------------");
}

// Print the total number of likes
System.out.println("Total Likes: " + totalLikes);

        
}

@Test (enabled = true)
        public void testCase05() {
                System.out.println("skipped");
        }
        /*
         * Do not change the provided methods unless necessary, they will help in
         * automation and assessment
         */
        @BeforeTest
        public void startBrowser() {
                System.setProperty("java.util.logging.config.file", "logging.properties");

                // NOT NEEDED FOR SELENIUM MANAGER
                // WebDriverManager.chromedriver().timeout(30).setup();

                ChromeOptions options = new ChromeOptions();
                LoggingPreferences logs = new LoggingPreferences();

                logs.enable(LogType.BROWSER, Level.ALL);
                logs.enable(LogType.DRIVER, Level.ALL);
                options.setCapability("goog:loggingPrefs", logs);
                options.addArguments("--remote-allow-origins=*");

                System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

                driver = new ChromeDriver(options);

                driver.manage().window().maximize();
        }

        @AfterTest
        public void endTest() {
                driver.close();
                driver.quit();

        }
}