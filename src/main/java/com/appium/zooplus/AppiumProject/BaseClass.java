package com.appium.zooplus.AppiumProject;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;


public class BaseClass {
	public static ExtentTest logger;

	
	static AppiumDriver<MobileElement> driver ;
	 public static AppiumDriverLocalService service;
	 
	 public static void startEmulator() throws IOException, InterruptedException
	 {

	 	Runtime.getRuntime().exec(System.getProperty("user.dir")+"\\src\\main\\java\\resources\\Apps\\startEmulator.bat");
	 	Thread.sleep(6000);
	 }
	 
	 public static boolean checkIfServerIsRunnning(int port)
	 {
		 boolean isServerRunning = false;
			ServerSocket serverSocket;
			try {
				serverSocket = new ServerSocket(port);
				
				serverSocket.close();
			} catch (IOException e) {
				//If control comes here, then it means that the port is in use
				isServerRunning = true;
			} finally {
				serverSocket = null;
			}
			return isServerRunning;
	 }
	
    public static void getScreenshot(String s) throws IOException
	{
	File scrfile=	((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	FileUtils.copyFile(scrfile,new File(System.getProperty("user.dir")+"\\"+s+".png"));
	
	}
	
	
    public AppiumDriverLocalService startAppiumServer() {
    	boolean flag=checkIfServerIsRunnning(4723);
    	if(!flag)
    	{

        service = AppiumDriverLocalService.buildDefaultService();
        service.start();
    	}
        return service;
    }
   
	
	@BeforeTest
	public void setup()
	{
		try 
		{
			
		// incase connected to real device no need to call startEmulator method
			
		startEmulator();
			
		DesiredCapabilities cap= new DesiredCapabilities();
		
		File root = new File(System.getProperty("user.dir"));
	    File app = new File(root, "/src/main/java/resources/Apps/app-mock-debug.apk");
		
		cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Appium emulator");

		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME,"uiautomator2");//new step
		cap.setCapability("noReset", false);
		
		cap.setCapability("ignoreHiddenApiPolicyError", true);
		cap.setCapability("autoGrantPermissions", true);

		
	     
		cap.setCapability("uiautomator2ServerLaunchTimeout",90000);
		cap.setCapability("appPackage", "com.example.android.architecture.blueprints.master.mock");
		cap.setCapability("appActivity", "com.example.android.architecture.blueprints.todoapp.tasks.TasksActivity");

		URL url =new URL("http://127.0.0.1:4723/wd/hub");
		
		driver =new AppiumDriver<MobileElement>(url,cap);
		}
		
	    catch(Exception e)
	    {
	       System .out.println("Cause is: "+e.getCause());
	       System .out.println("Message is: "+e.getMessage());
	       e.printStackTrace();
	    }
	
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		
	}
	
	@AfterMethod
	public void afterMethodMethod(ITestResult result)
	{
		
		if(result.getStatus() == ITestResult.SUCCESS)
		{
			String methodName = result.getMethod().getMethodName();
			String logText ="Test Case:" + methodName + "Passed";
			logger.log(Status.PASS, MarkupHelper.createLabel(logText+" PASSED ", ExtentColor.GREEN));

			}
		else if (result.getStatus() == ITestResult.FAILURE)
		{
			String methodName = result.getMethod().getMethodName();
			String logText ="Test Case:" + methodName + "Failed";
			logger.log(Status.FAIL, MarkupHelper.createLabel(logText+" FAILED ", ExtentColor.RED));
			logger.fail(result.getThrowable());

		}
		
	}
	
	@AfterTest
	public void teardown()
	{
		
	}
	
	
}
