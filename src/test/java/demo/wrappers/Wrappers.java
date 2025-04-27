package demo.wrappers;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import org.openqa.selenium.io.FileHandler;


public class Wrappers {
    
    public static void goToURLandWait(WebDriver driver)
        {
            try {
                driver.get("https://www.livechat.com/typing-speed-test/#/");
            waitForPageLoad(driver);
            System.out.println("Page loaded successfully!");
            Thread.sleep(3000);
            } catch (Exception e) {
                
                System.out.println("Could not complete Page load!");
            }
            
    }

    public static void waitForPageLoad(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }

    public static void takeScreenshot(WebDriver driver, String fileName) {
        try {
            // Auto-create 'screenshots' folder if not exist
            File dir = new File("screenshots");
            if (!dir.exists()) {
                dir.mkdir();
            }

            // Add timestamp to file name automatically
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String finalName = "screenshots/" + fileName + "_" + timestamp + ".png";

            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileHandler.copy(srcFile, new File(finalName));
            System.out.println("Screenshot saved: " + finalName);

        } catch (IOException e) {
            System.out.println("Failed to take screenshot: " + e.getMessage());
        }
    }

    
}
