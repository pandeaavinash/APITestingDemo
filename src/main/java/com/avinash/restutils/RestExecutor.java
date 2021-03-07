package com.avinash.restutils;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

import com.avinash.readproperties.*;


/**
 * Utility class to wrap all APIs exposed by Database
 * @author Avinash
 *
 */
public class RestExecutor {

	public static int TotalAPIHitCounter = 0;
	public static long TotalAPIResponseTime = 0;
	static ContentLogger logger = ContentLogger.getLogger();
	private static RestClient getRestClient(){

		
			return RestClientImpl.INSTANCE;
	}


	private static RestClient getNonPipelinedRestClient(){
		return RestClientImpl.INSTANCE;
	}
	/**
	 * Executed a get request against the URI
	 * @param uri
	 * @return
	 */
	public static String getJSonResponse(URI uri){
		logger.fine("Executing "+ uri);
		return getRestClient().getResponse(uri);
	}

	/**
	 * Executed a get request against the URI
	 * @param uri
	 * @return
	 */
	public static String getJSonResponse(String url){
		TotalAPIHitCounter++;
//		logger.info("Executing "+ url);
		long start = System.currentTimeMillis();
		try {
			return getRestClient().getResponse(new URI(url));
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}finally{
			TotalAPIResponseTime+= System.currentTimeMillis()-start;
		}
	}

	
	public static String postForXmlResponse(String url, String body){
		try {
			return getRestClient().postResponseXml(new URI(url), body);
		} catch (URISyntaxException e) {
			return "";
		}
	}
	
	/**
	 * Executed a post request against the URI
	 * @param uri
	 * @return
	 */
	public static String postForJSonResponse(String url, String body){
		try {
			return getRestClient().postResponse(new URI(url), body);
		} catch (URISyntaxException e) {
			return "";
		}
	}
	
	

	
	public static String putRequest(String uriToConvert, String postParams){
		URI uri;
		try {
			uri = new URI(uriToConvert);
			return getNonPipelinedRestClient().putResponse(uri, postParams);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Please check uri");
		}
		return null;

	}

	public static String deleteRequest(String uriToConvert, String deleteParams){
		URI uri;
		try {
			uri = new URI(uriToConvert+"/"+deleteParams);
			return getNonPipelinedRestClient().deleteResponse(uri, deleteParams);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Please check uri");
		}
		return null;

	}

}
