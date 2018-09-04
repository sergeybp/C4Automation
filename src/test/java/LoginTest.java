import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;

public class LoginTest {

    static ChromeDriver driver = new ChromeDriver();

    public static void c4ClearElement(WebElement element, Integer retries){
        if(retries == 0){
            System.err.print("FAILED");
            System.exit(0);
        }
        element.click();
        element.clear();
        if(element.getAttribute("value").equals("")) {
        } else c4ClearElement(element, retries - 1);
    }

    public static void c4SendKeys(WebElement element, String input, Integer retries){
        if(retries == 0){
            System.err.print("FAILED");
            System.exit(0);
        }
        c4ClearElement(element, 10);
        element.click();
        element.sendKeys(input);
        if(element.getAttribute("value").equals(input)) {
        } else c4SendKeys(element, input, retries - 1);
    }

    @Test
    public void testLogin() {

        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver.get("http://www.google.com");
        driver.manage().window().maximize();

        driver.navigate().to("https://sydhavn.dev.cone.ee/c4.html");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.className("marker-login")));

        WebElement loginIn = driver.findElement(By.cssSelector("input[type='text']"));
        WebElement passIn = driver.findElement(By.cssSelector("input[type='password']"));

        c4SendKeys(loginIn, "DEV", 30);
        c4SendKeys(passIn,"1234", 30);
        submitButton.click();

        WebElement res =  wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("span[content='Warehouse/Terminal']")));
        assert(res.getText().equals("Warehouse/Terminal"));
    }

    @AfterSuite
    public void closeDriver(){
        driver.quit();
    }
}