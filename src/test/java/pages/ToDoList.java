package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import pages.BasePage;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class ToDoList extends BasePage {

	public ToDoList(AndroidDriver driver2) {
		super(driver2);
		
	}

	private static By headerText = By.xpath("//android.view.ViewGroup/android.widget.TextView");
	private static By addTask = By.xpath(
			"//android.widget.ImageButton [@resource-id='com.example.android.architecture.blueprints.master.mock:id/add_task_fab']");
	private static By addTitile = By.xpath(
			"//android.widget.EditText[@resource-id='com.example.android.architecture.blueprints.master.mock:id/add_task_title_edit_text']");
	private static By addDescription = By.xpath(
			"//android.widget.EditText[@resource-id='com.example.android.architecture.blueprints.master.mock:id/add_task_description_edit_text']");
	private static By saveTask = By.xpath(
			"//android.widget.ImageButton [@resource-id='com.example.android.architecture.blueprints.master.mock:id/save_task_fab']");
	private static By completeTask = By
			.xpath("(//android.widget.CheckBox [@resource-id='com.example.android.architecture.blueprints.master.mock:id/complete_checkbox'])[1]");
	private static By toDoMenu = By.xpath("//android.widget.ImageButton[@content-desc='Open navigation drawer']");
	private static By toDoTaskList = By.xpath("//androidx.appcompat.widget.LinearLayoutCompat [@resource-id='com.example.android.architecture.blueprints.master.mock:id/tasks_fragment_dest']");
	private static By toDoStatistics = By.xpath("//androidx.appcompat.widget.LinearLayoutCompat [@resource-id='com.example.android.architecture.blueprints.master.mock:id/statistics_fragment_dest']");
	private static By activeTask = By.xpath("//android.widget.TextView [@resource-id='com.example.android.architecture.blueprints.master.mock:id/stats_active_text']");
	private static By completedTask = By.xpath("//android.widget.TextView [@resource-id='com.example.android.architecture.blueprints.master.mock:id/stats_completed_text']");
	
	
	private static By moreOptions = By.xpath("//android.widget.ImageView[@content-desc='More options']");
	private static By clearCompletedTask = By.xpath("//android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.RelativeLayout/android.widget.TextView");
	
	

	
	public static void verifyHomeScreen() {
		wait(2);
		boolean flag = isElementDisplayed(headerText);
		Assert.assertEquals(flag, true);
	}
	


	public static void createTask(int tasks, String taskName, String taskDescription) {
		wait(2);
		for (int i =1 ; i<= tasks; i++) {
		clickOn(addTask);
		setText(addTitile,taskName+" "+Integer.toString(i) );
		setText(addDescription,taskDescription +" "+ Integer.toString(i));
		clickOn(saveTask);
		
		}
		
	}
	
	
	public static void markTaskAsCompleted() {
		wait(2);
		
		clickOn(completeTask);
		
	}
	
	public static void goToTaskList() {
		wait(2);
		
		clickOn(toDoMenu);
		clickOn(toDoTaskList);
		
	}
	
	
	public static void goToStatistics() {
		wait(2);
		
		clickOn(toDoMenu);
		clickOn(toDoStatistics);
		
	}
	
	
	
	
	
	public static void activeTaskStatisticPercentage(String active) {
		goToStatistics();
		wait(2);
		String activeTaskStatisticPercentageText = getTextOn(activeTask);
		
		boolean assertvalue = false;
		if (activeTaskStatisticPercentageText.contains(active)) {
			assertvalue = true;
			
		}
		Assert.assertEquals(assertvalue, true);
		
	}
	
	
	public static void completedTaskStatisticPercentage(String completed) {
		wait(2);
		String headerTextValue = getTextOn(completedTask);
		String completedTaskStatisticPercentageText = getTextOn(activeTask);
		
		boolean assertvalue = false;
		if (completedTaskStatisticPercentageText.contains(completed)) {
			assertvalue = true;
			
		}
		Assert.assertEquals(assertvalue, true);
	}

	

	public static void clearCompletedTasks() {
		wait(2);
		goToTaskList();
		clickOn(moreOptions);
		clickOn(clearCompletedTask);
		
	}
	
	public static void verifyHeaderText(String text) {
		wait(2);
		String headerTextValue = getTextOn(headerText);
		Assert.assertEquals(headerTextValue, text);
	}
}
