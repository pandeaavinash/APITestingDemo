package com.avinash.post;

import com.avinash.restutils.RestExecutor;
import com.generated.avinash.createuser.CreateUser;
import com.google.gson.Gson;

public class CreateNewUser_PostMethod_Test_Verification implements Runnable 
{
	String inputMessage = null;
	
	public CreateNewUser_PostMethod_Test_Verification(String message) 
	{
		this.inputMessage = message;
	}

	@Override
	public void run() 
	{
		String postForJSonResponse = RestExecutor.postForJSonResponse("https://gorest.co.in//public-api/users", inputMessage);
		Gson gson = new Gson();
		CreateUser createUser = gson.fromJson(postForJSonResponse, CreateUser.class);
		System.out.println(postForJSonResponse);
		
		System.out.println("Status Code: " + createUser.getCode());
		System.out.println("User ID:	" + createUser.getData().getId());
		System.out.println("User name:	" + createUser.getData().getName());
		System.out.println("User Email:	" + createUser.getData().getEmail());
		System.out.println("User Status:	" + createUser.getData().getStatus());
		System.out.println("User Gender:	" + createUser.getData().getGender());
		
	}

}
