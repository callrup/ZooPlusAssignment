package pages;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.appium.zooplus.AppiumProject.BaseClass;

import org.openqa.selenium.support.ui.ExpectedConditions;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;


public class BasePage {

	
	public static AndroidDriver driver ;
	public static String waitstrategy = "Visible";
	public static WebDriverWait driverWait;

	public BasePage(AndroidDriver driver2) {
		
		 this.driver = driver2;
		 driverWait = new WebDriverWait(driver, 15);
		 
	}

	public static void clickOn(By by) {

		WebElement element = performExplicitWait(waitstrategy, by);
		element.click();

	}
	
	public static void click(By by) {
		try {
		
		WebElement element = performExplicitWait("None", by);
		Actions action = new Actions(driver);
		action.moveToElement(element);
		action.click();
		action.perform();
		}
		catch(StaleElementReferenceException e) {
			
			click(by);
		}

	}
	

	public static boolean isElementDisplayed(By by) {
		boolean flag = false;

		WebElement element = performExplicitWait(waitstrategy, by);
		if (element.isDisplayed()) {
			flag = true;
		}
		return flag;
	}
	
	public static boolean isElementEnabled(By by) {
		boolean flag = false;

		WebElement element = performExplicitWait(waitstrategy, by);
		if (element.isEnabled()) {
			flag = true;
		}
		return flag;
	}
	
	public static void setText(By by, String text) {

		WebElement element = performExplicitWait(waitstrategy, by);
		 element.sendKeys(text);
		 
	}

	public static String getTextOn(By by) {

		WebElement element = performExplicitWait(waitstrategy, by);

		return element.getText();

	}

	public static void wait(int secs) {
		try {
			Thread.sleep(secs * 1000);
		} catch (InterruptedException interruptedException) {
			interruptedException.printStackTrace();
		}
	}

	public static int getNumOfElements(By by) {
		List<WebElement> ele = driver.findElements(by);
		int i = ele.size();
		return i;
	}

	public static List<WebElement> listOfElements(By by) {
		List<WebElement> ele = driver.findElements(by);
		return ele;
	}

	public static WebElement findElement(By by) {
		WebElement ele = driver.findElement(by);
		return ele;
	}


	public static WebElement performExplicitWait(String waitstrategy, By by) {
		WebElement element = null;
		if (waitstrategy.equalsIgnoreCase("Clickable")) {
			element = driverWait.until(ExpectedConditions.elementToBeClickable(by));
		} else if (waitstrategy.equalsIgnoreCase("Visible")) {
			element = driverWait.until(ExpectedConditions.visibilityOfElementLocated(by));
		} else if (waitstrategy.equalsIgnoreCase("None")) {
		element = driver.findElement(by);
		}
		return element;
	}

	@SuppressWarnings("unchecked")
	public static void switchToWebView() {
		Set<String> contextNames = ((AppiumDriver<WebElement>) driver).getContextHandles();
		for (String contextName : contextNames) {
			System.out.println(contextName); // prints out something like NATIVE_APP \n WEBVIEW_1
		}
		((AppiumDriver<WebElement>) driver).context((String) contextNames.toArray()[1]); // set context to WEBVIEW_1

	}

	@SuppressWarnings("unchecked")
	public static void switchToNativeApp() {
		Set<String> contextNames = ((AppiumDriver<WebElement>) driver).getContextHandles();
		for (String contextName : contextNames) {
			System.out.println(contextName); // prints out something like NATIVE_APP \n WEBVIEW_1
		}
		((AppiumDriver<WebElement>) driver).context("NATIVE_APP");
	}
	
	
	public static void getScreenshot(String s) throws IOException
	{
	File scrfile=	((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	FileUtils.copyFile(scrfile,new File(System.getProperty("user.dir")+"\\"+s+".png"));
	
	}
	
}
