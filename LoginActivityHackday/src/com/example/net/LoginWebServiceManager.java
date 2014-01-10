package com.example.net;

import com.raizlabs.net.webservicemanager.RequestMode;
import com.raizlabs.net.webservicemanager.WebServiceManager;

public class LoginWebServiceManager extends WebServiceManager {
	
	private static final int DEFAULT_MAX_CONNECTIONS = 10;
	private static final int MAX_TIMEOUT_SECONDS = 2;
	
	public LoginWebServiceManager() {
		super(DEFAULT_MAX_CONNECTIONS);
		setDefaultRequestMode(RequestMode.HttpURLConnection);
		
		setConnectionTimeout(MAX_TIMEOUT_SECONDS * 1000);
		setReadTimeout(MAX_TIMEOUT_SECONDS * 1000);
	}

}
