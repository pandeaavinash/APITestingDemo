package com.avinash.get;


import java.util.Hashtable;

import com.avinash.bean.User;
import com.avinash.readproperties.LoadProperties;
import com.avinash.restutils.RestExecutor;
import com.generated.avinash.user.UserSchema;
import com.google.gson.Gson;

public class GetUserDetails_GetMethod_Test_Verification implements Runnable 
{
	int id = -1;
	Hashtable<Integer, User> ht = null;
	User u = null;
	
	
	public GetUserDetails_GetMethod_Test_Verification(int userID, User uu) 
	{
		this.id = userID;
		//this.ht = ht;
		this.u = uu;
	}
	
	String apiResponse = null;
	@Override
	public void run() 
	{
		try
		{
			apiResponse = RestExecutor.getJSonResponse(LoadProperties.baseuri+id);
			
			Gson gson = new Gson();
			 UserSchema user = gson.fromJson(apiResponse, UserSchema.class);
			if(user != null)
			{
				if(user.getCode().equals(200))
				{
					if(user.getData()!=null)
					{
						//Create new object of User class & add/map the details
						User userr = new User();
						System.out.println("Test ID:    "+user.getData().getId());
						userr.setId(user.getData().getId());
						System.out.println("User ID		: " +user.getData().getId());
						
						System.out.println("User Name	: " +user.getData().getName());
						userr.setName(user.getData().getName());
						
						System.out.println("User Email	: " +user.getData().getEmail());
						userr.setEmail(user.getData().getEmail());
						
						System.out.println("User Gender	: " +user.getData().getGender());
						userr.setGender(user.getData().getGender());
						
						System.out.println("User Status	: " +user.getData().getStatus());
						userr.setStatus(user.getData().getStatus());
						
						System.out.println("User CreatedAt	: " +user.getData().getCreatedAt());
						userr.setCreated_at(user.getData().getCreatedAt());
						
						System.out.println("User UpdatedAt	: " +user.getData().getUpdatedAt());
						userr.setUpdated_at(user.getData().getUpdatedAt());
						//ht.put(user.getData().getId(), userr);
						u.addNewUser(user.getData().getId(), userr);
						
						
					}
				}
			}
		}catch(Exception e)
		{
			System.out.println("Exception occured: "+e.getMessage());
		}
	}
}
