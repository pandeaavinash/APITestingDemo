package com.avinash.restutils;

/***
 * 
 * @author Avinash
 *
 */
public class RestExecutionException extends RuntimeException{

	
	private static final long serialVersionUID = -5321770676620973443L;

	public RestExecutionException(String message){
		super(message);
	}
	
	public RestExecutionException(String message, Throwable cause){
		super(message, cause);
	}
}
