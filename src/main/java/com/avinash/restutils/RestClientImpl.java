package com.avinash.restutils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Arrays;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;


/**
 * Returns singleton 
 * @author Avinash Pande
 *
 */
public enum RestClientImpl implements RestClient{

	INSTANCE;
	
	private CloseableHttpClient  httpClient;
	
	private RestClientImpl() {
			httpClient = HttpClients.createDefault();
	}
	
	public String getResponse(URI uri){
		HttpGet getRequest = new HttpGet(uri);
		getRequest.addHeader("User-Agent", "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)/SHC Deadlinks Automation/1.0 KTXN");
		String singleJSonObject = null;
		getRequest.addHeader("accept", "application/json");
		
		CloseableHttpResponse response;
		try {
			
			response = httpClient.execute(getRequest);
			
			if(response.getStatusLine().getStatusCode() != 200)
				throw new RestExecutionException("Response code not 200.  Got : "+ response.getStatusLine().getStatusCode() );
			
			
			BufferedReader r = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			try{
				singleJSonObject = r.readLine();
//				System.out.println(singleJSonObject);
			}catch(IOException e){
				throw new RestExecutionException("Error on reading response", e.getCause());
			}
			
			return  singleJSonObject; 
		} catch (ClientProtocolException e) {
			throw new RestExecutionException("An error occurred in http protocol.", e.getCause());
		} catch (IOException e) {
			throw new RestExecutionException("An IO exception occurred in rest call.", e.getCause());
		}
		
	}
	
	public String postResponseXml(URI uri, String body){
	
		return null;
	}
	public String postResponse(URI uri, String body){
		
		HttpRequestBase req =  createRequest(REQUESTTYPE.POST, uri,  body);
		
		return executeAndReturnResponse(req, new Integer[]{200,201});
		/*HttpPost postRequest = new HttpPost(uri);
		postRequest.addHeader("content-type", "application/json");
		postRequest.addHeader("Authorization", "Basic " + encodeCredentials(username, password));
		try {
		HttpEntity entity;
		
			entity = new StringEntity(body);
			postRequest.setEntity(entity);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String singleJSonObject;
		
		CloseableHttpResponse response;
		try {
			response = httpClient.execute(postRequest);
			
			if(response.getStatusLine().getStatusCode() != 200)
				throw new RestExecutionException("Response code not 200.  Got : "+ response.getStatusLine().getStatusCode() );
			
			
			BufferedReader r = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			try{
				singleJSonObject = r.readLine();
				System.out.println(singleJSonObject);
			}catch(IOException e){
				throw new RestExecutionException("Error on reading response", e.getCause());
			}
			
			return  singleJSonObject; 
		} catch (ClientProtocolException e) {
			throw new RestExecutionException("An error occurred in http protocol.", e.getCause());
		} catch (IOException e) {
			throw new RestExecutionException("An IO exception occurred in rest call.", e.getCause());
		}*/
		
	}
	
	/**
	 * Creates a put request with contentype as json and putparams
	 */
	@Override
	public String putResponse(URI uri, String putParams) {
		HttpRequestBase req =  createRequest(REQUESTTYPE.PUT, uri, putParams);
		
		return executeAndReturnResponse(req, new Integer[]{200,204});
	}

	/**
	 * Creates a request (get, post, put) based on requesttype and params. 
	 * Contentype and accept headers are set to json
	 * @param req  - Request Type (get, post, put)
	 * @param uri  - URI to hit
	 * @param username - Username if authentication is required, null otherwise
	 * @param password - - Password if authenticatio66n is required, null otherwise
	 * @param params  - Body in case of put and post, null otherwise
	 * @return request with headers set
	 */
	private HttpRequestBase createRequest(REQUESTTYPE req, URI uri,  String params){
		switch(req){
		case GET:{
			HttpGet getRequest = new HttpGet(uri);
			
			return getRequest;
		}
			
		case POST:{
			HttpPost postRequest = new HttpPost(uri);
			postRequest.addHeader("content-type", "application/json");
			postRequest.addHeader("Authorization", "Bearer 4598673377ea48cfeb398bd94221cfcb6db865471c08d2db90d3c89c0893d946");
			try {
			HttpEntity entity;
			
				entity = new StringEntity(params);
				postRequest.setEntity(entity);
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return postRequest;
		}
		case PUT:{
			
			HttpPut putRequest = new HttpPut(uri);
			putRequest.addHeader("content-type", "application/json");
			putRequest.addHeader("Accept", "application/json");
			try {
				HttpEntity entity;
				
					entity = new StringEntity(params);
					putRequest.setEntity(entity);
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return putRequest;
			
			
		}
		case DELETE:{
			
			HttpDelete deleteRequest = new HttpDelete(uri);
			deleteRequest.addHeader("content-type", "application/json");
			deleteRequest.addHeader("Accept", "application/json");
			
			return deleteRequest;
		}
		}
		return null;
		
	}
	
	/**
	 * Takes a request and executes it.  Checks response code
	 * @param req
	 * @return
	 */
	private String executeAndReturnResponse(HttpRequestBase req, Integer[] acceptableCodes){
		String singleJSonObject = null;
		CloseableHttpResponse response;
		try {
			
			
			response = httpClient.execute(req);
			
			System.out.println("Response got : "+ response);
			
			int responseCode = response.getStatusLine().getStatusCode();
			
			if(!Arrays.asList(acceptableCodes).contains(responseCode))
				throw new RestExecutionException("Response code not in acceptable code list"
						+Arrays.asList(acceptableCodes)+".  Got : "+ response.getStatusLine().getStatusCode() );
			
			if(!(responseCode == 204)){
				BufferedReader r = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				singleJSonObject = r.readLine();
				System.out.println(singleJSonObject);
				return  singleJSonObject; 
			}else
				return "204";
			
		} catch (ClientProtocolException e) {
			throw new RestExecutionException("An error occurred in http protocol.", e.getCause());
		} catch (IOException e) {
			throw new RestExecutionException("An IO exception occurred in rest call.", e.getCause());
		}
		
	}
	
	
	

	
	private enum REQUESTTYPE{
		GET,
		POST,
		PUT,
		DELETE
	}





	@Override
	public String deleteResponse(URI uri,String param) {
		HttpRequestBase req =  createRequest(REQUESTTYPE.DELETE, uri,param);
		
		return executeAndReturnResponse(req, new Integer[]{200,204});
	};
	
}
