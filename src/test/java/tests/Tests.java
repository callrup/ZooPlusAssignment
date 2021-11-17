package tests;

import org.testng.annotations.Test;

import java.io.IOException;

import org.testng.annotations.BeforeTest;
import com.appium.zooplus.AppiumProject.BaseClass;
import pages.ToDoList;

import pages.ToDoList;

public class Tests extends BaseClass {
	
	
	// added this method incase there are multiple test cases where we need to kill and restart the services
	public void killAllNodes() throws IOException, InterruptedException
	{
	//taskkill /F /IM node.exe
		Runtime.getRuntime().exec("taskkill /F /IM node.exe");
		Thread.sleep(3000);
		
	}
	
	
	@Test
	public void createTodoList()
	{
		service = startAppiumServer();
		ToDoList.verifyHomeScreen();
		ToDoList.createTask(2,"Task","Task Description" );
		ToDoList.markTaskAsCompleted();
		ToDoList.activeTaskStatisticPercentage("50");
		ToDoList.completedTaskStatisticPercentage("50");
		ToDoList.clearCompletedTasks();
		
		service.stop();
	}
	

	
}
