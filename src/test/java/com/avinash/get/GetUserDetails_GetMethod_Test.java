package com.avinash.get;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.avinash.bean.User;
import com.avinash.emailutility.EmailSend;
import com.avinash.readproperties.LoadProperties;
import com.avinash.report.GenerateReport;



public class GetUserDetails_GetMethod_Test 
{
	User uu = new User();
	static Hashtable<Integer, User> ht = new Hashtable<>();
	
	
  @Test(dataProvider="getUserIds")
  public void DemoTest(ArrayList<Integer> userId)// changed to execute on jenkins 
	//public void DemoTest() 
  {
	  
	  System.out.println("Testing UserId: "+userId.get(0));

	  System.out.print("Hi");
	  final ExecutorService pool = Executors.newFixedThreadPool(10);
	  try
	  {
		  pool.execute(new GetUserDetails_GetMethod_Test_Verification(userId.get(0),uu ));
	  }
	  catch (Exception e) 
	  {
		System.out.println("Exception: "+ e.getMessage());
	  }
	  pool.shutdown();

	  System.out.println("Ht:"+ht.size());
		try 
		{
			pool.awaitTermination(40, TimeUnit.MINUTES);
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
  
		//calling to report file
		//System.out.println("Hi");
		}
  
  
  @AfterClass
  public void giveCallToReport()
  {
	  System.out.println("Final uu:"+uu.getUserSize());
	  Hashtable<Integer, User> alreadyAddedUsers = uu.getAlreadyAddedUsers();
	TreeMap<Integer, User> convertToTreeMap = ConvertToTreeMap(alreadyAddedUsers);
	String reportFile = GenerateReport.createReport("UserValidation", "UserValidation", convertToTreeMap);
	
	File file = new File(reportFile);
	if(file.exists()) 
	{
		System.out.println("File exists to attached to Report & Send it !!");
		//EmailSend.sendEmail(LoadProperties.EMAIL_EMAILFROM, LoadProperties.EMAIL_EMAILTOSEND, ExtentReport.reportFullPath);
		EmailSend.sendEmail("aavinashpande@gmail.com", "aavinashpande@gmail.com", file);
	}
	else
	{
		System.out.println("No file to attached email so email not sent !!!");
	}	
	
	
  }
  
 
 @DataProvider(name="getUserIds") //commenting for trying to execute on jenkins
  public Object[][] getData()
  {
	  Object[][] obj = null;
	  Properties prop = new Properties();
	  String userIds = null;
	  try
	  {
		  prop.load(GetUserDetails_GetMethod_Test.class.getClassLoader().getResourceAsStream("input.properties"));
		  
		  ArrayList<Integer> al = new ArrayList<>();
		  //String value = System.getProperty("userids");
		  String value = System.getProperty("userIds");
		  
			  
			  if(value==null)
			  {
				  userIds = prop.getProperty("userids"); //reading parameters from input.properties file while executing at local system
				  String[] split = userIds.split(",");
				  for (String st : split) {
					al.add(Integer.parseInt(st));
				}
			  }
			  else
			  {
				  userIds = System.getProperty("userIds"); //reading parameters from jenkins job while executing on jenkins.
				  System.out.println("Reading value of userids from Jenkins!!");
				  System.out.println("userIds:"+userIds);
				  String[] split = userIds.split(",");
				  for (String st : split) {
					al.add(Integer.parseInt(st));
				  
			  }
			  
			 
		  }
			  obj = new Object[][] {{al}};  
		  
		  
		  
	  }catch(Exception e)
	  {
		  System.out.println("Exception occured wile reading input.properties file !!");
	  }
	  return obj;
  }
  
  /***
   * Sort the HashTable so converting to treemap 
   * @return
   */
  public TreeMap<Integer, User> ConvertToTreeMap(Hashtable<Integer, User> ht)
  {
	  TreeMap<Integer, User> tm = new TreeMap<>();
	  tm.putAll(ht);
	  return tm;
  }
  
}
