package demo;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.logging.Level;
import demo.wrappers.Wrappers;


public class TestCases{ 
        ChromeDriver driver;

        @Test (enabled = true)
        public void testCase01() throws InterruptedException {
                System.out.println("Testcase01 : Started");
                
                Wrappers.goToURLandWait(driver);
                
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
                Actions actions = new Actions(driver);
        
        while (true) {

                // Check if word count reached 507, to stop reading words
                WebElement wordCount = driver.findElement(By.xpath("(//div[contains(@class,'tst-metric')])[1]"));
                String wordCounted = wordCount.getText();
                int count = Integer.parseInt(wordCounted);
        
                if (count == 507) {
                        System.out.println("You have typed "+ count+ " words");
                break; //we will stop searching for next word from DOM
                }

                // Typing one word
                        WebElement word = wait.until(
                            ExpectedConditions.presenceOfElementLocated(
                                By.xpath("(//div[@class='tst-input-wrapper'])[2]/span[1]")));
                                
                        actions.sendKeys(word.getText() + " ").perform();
                        
                        Thread.sleep(10); // delay to look human
                } 
                Thread.sleep(5000); 
        
        WebElement closebtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'popup-close u-absolute')]")));
        
        if(closebtn.isDisplayed())
        {
                Wrappers.takeScreenshot(driver, "typing_result");
                System.out.println("Screenshot captured");
        }
        
                
        System.out.println("Testcase01 : Completed");
        }

        @BeforeTest
        public void startBrowser() {
                System.setProperty("java.util.logging.config.file", "logging.properties");

                

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