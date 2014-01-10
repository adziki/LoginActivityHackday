package com.example.net;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.LoginLibrary.AuthenticationResponse;
import com.example.LoginLibrary.interfaces.LoginInterface;
import com.example.Managers.Managers;
import com.raizlabs.net.webservicemanager.ResultInfo;

public class GithubLoginHandler implements LoginInterface {

	/**
	 * Unique auto-generated ID for serialized data
	 */
	private static final long serialVersionUID = -6197305391518941352L;

	@Override
	public AuthenticationResponse login(String username, String password) {
		AuthenticationResponse authResponse = new AuthenticationResponse();
    	GithubBasicUserAuthRequest request = new GithubBasicUserAuthRequest(username, password);
    	ResultInfo<JSONObject> result = Managers.getMainWebServiceManager().doRequest(request);
  
		if (result != null && result.isStatusOK()) {
			JSONObject json = result.getResult();
			if (json != null) {
				try {
					final String token = json.getString("token");
					authResponse.AuthToken = token;
					authResponse.Success = true;
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	
		return authResponse;
	}
}
