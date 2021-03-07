package com.avinash.readproperties;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

public class LoadProperties 
{

	private static Properties prop;
	public static String baseuri=getProperty("baseuri","");
	public static String userIds = getProperty("userIds","");
	
	
	private static String customMsgForEmail = null;
	public static Boolean splitResult = Boolean.parseBoolean(getProperty("splitResult","false"));
	
	public enum MSGTYPE{
		SUCCESS,
		WARNING,
		ERROR
	}
	
	public static String getCustomMsgForEmail() {
		return customMsgForEmail;
	}

	public static void setCustomMsgForEmail(String msgForEmail) {
		customMsgForEmail = msgForEmail;
	}
	
	public static void setCustomMsgForEmail(String msgForEmail, MSGTYPE typeOfMsg) {
		switch(typeOfMsg){
		case ERROR:{
			customMsgForEmail = "<font face='Calibri' color='red' size='3'>"+msgForEmail+"</font>";
		}
			break;
		case SUCCESS:{
			customMsgForEmail = "<font face='Calibri' color='green' size='3'>"+msgForEmail+"</font>";
		}
			break;
		case WARNING:{
			customMsgForEmail = "<font face='Calibri' color='goldenrod' size='3'>"+msgForEmail+"</font>";
		}
		break;	
		default:{
			customMsgForEmail = msgForEmail;
		}
			break;
		
		}
		
	}

	public static Properties getProp() {
		return prop;
	}

	public static void setProp(Properties prop) {
		LoadProperties.prop = prop;
	}

	public static String getProperty(String key, String defaultValue) {
		// Read properties file.
		if (null == prop) {
			prop = new Properties();

			try {
				prop.load(LoadProperties.class.getClassLoader().getResourceAsStream("env.properties"));
				prop.load(LoadProperties.class.getClassLoader().getResourceAsStream("runnreporting.properties"));
				prop.load(LoadProperties.class.getClassLoader().getResourceAsStream("testspecific.properties"));
				prop.load(LoadProperties.class.getClassLoader().getResourceAsStream("input.properties"));
				
			} catch (IOException e) {
				e.printStackTrace();
			}

			Enumeration<Object> keys = prop.keys();

			System.out.println("Configured properties:");
			while (keys.hasMoreElements()) {
				String elt = (String) keys.nextElement();
				if ((prop.getProperty(elt) != null) && (!prop.getProperty(elt).equals(""))) 

				System.out.println(String.format("\t\t %s : %s", elt, prop.getProperty(elt)));

				System.out.println(String.format("\t\tProperty: %s, value: %s", elt, prop.getProperty(elt)));

			}
			for (Object syskey : prop.keySet()) {
				String value = System.getProperty(syskey.toString());
				if ((value != null) && (!value.equals(""))) {
					prop.setProperty(syskey.toString(), value);
					System.out.println("Overriding "+ syskey+" to  "+value);
//					prop.store(out, comments);
				}
			}
		}


		return prop.getProperty(key, defaultValue);
	}

	}
	
