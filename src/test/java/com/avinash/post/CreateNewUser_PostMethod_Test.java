package com.avinash.post;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.avinash.get.GetUserDetails_GetMethod_Test;


public class CreateNewUser_PostMethod_Test 
{
  
  @Test(dataProvider="postmessage")
  public void createUser(ArrayList<String> postMessagesList) 
  {

	  System.out.println("Testing UserId: "+postMessagesList.get(0));

	  System.out.print("Hi");
	  final ExecutorService pool = Executors.newFixedThreadPool(10);
	  try
	  {
		  pool.execute(new CreateNewUser_PostMethod_Test_Verification(postMessagesList.get(0)) );
	  }
	  catch (Exception e) 
	  {
		System.out.println("Exception: "+ e.getMessage());
	  }
	  pool.shutdown();

		try 
		{
			pool.awaitTermination(40, TimeUnit.MINUTES);
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		} 
  
  }
  
  @DataProvider(name="postmessage")
  public Object[][] getPostMessage()
  {

	  Object[][] obj = null;
	  Properties prop = new Properties();
	  try
	  {
		  prop.load(GetUserDetails_GetMethod_Test.class.getClassLoader().getResourceAsStream("input.properties"));
		  String postmessages = prop.getProperty("postmessage");
		  //String[] listOfPostMessages = postmessages.split(",");
		  
		  ArrayList<String> al = new ArrayList<>();
		  al.add(postmessages);
		  
		 
		  
		  obj = new Object[][] {{al}};
		  
		  
	  }catch(Exception e)
	  {
		  System.out.println("Exception occured wile reading input.properties file !!");
	  }
	  return obj;
  
  }
  
  
}
