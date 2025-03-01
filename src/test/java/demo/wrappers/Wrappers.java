package demo.wrappers;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.NoSuchElementException;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */
    public static void goToURLandWait(WebDriver driver)
        {
            try {
                driver.get("https://www.youtube.com");
            waitForPageLoad(driver);
            System.out.println("Page loaded successfully!");
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println("Could not complete Page load!");
            }
            
    }

    public static void waitForPageLoad(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until((ExpectedCondition<Boolean>) wd ->
                ((JavascriptExecutor) wd).executeScript("return document.readyState").equals("complete"));
    }

    public static void clickUntilDisappears(WebElement elem, WebDriver driver)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        System.out.println("Scrolling to the right most movie");
        while (true) {
            try {
                WebElement button = wait.until(ExpectedConditions.elementToBeClickable(elem));
                button.click();
                Thread.sleep(500); // Small delay to allow the UI to update
            } catch (NoSuchElementException e) {
                
                break; // Exit loop if the button is no longer found
            } catch (Exception e) {
                System.out.println("reached at the end of scroll");
                break; // Exit loop on any unexpected error
                
            }
        }
    }
}
