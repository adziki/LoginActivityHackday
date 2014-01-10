package com.example.net;

import org.json.JSONObject;

import com.raizlabs.net.HttpMethod;
import com.raizlabs.net.requests.BaseWebServiceRequest;
import com.raizlabs.net.requests.RequestBuilder;
import com.raizlabs.net.responses.Response;

public class GithubBasicUserAuthRequest extends BaseWebServiceRequest<JSONObject> {
	private String username;
	private String password;
	
	public GithubBasicUserAuthRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	protected RequestBuilder getRequestBuilder() {		
		RequestBuilder builder = new RequestBuilder(HttpMethod.Post, "https://api.github.com/authorizations");
		builder.setBasicAuth(this.username, this.password);
		builder.setStringInput("{\"scopes\": [\"repo\"]}", null);
		
		return builder;
	}

	@Override
	protected JSONObject translate(Response response) {
		JSONObject data = null;
		if (response != null) {
			data = response.getContentAsJSON();	
		}
		return data;
	}

}
