package com.avinash.restutils;

import java.net.URI;

public interface RestClient {
	public String getResponse(URI uri);
	public String postResponse(URI uri, String body);
	public String postResponseXml(URI uri, String body);
	public String putResponse(URI uri, String postParams);
	public String deleteResponse(URI uri,String param);
}
