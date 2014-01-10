package com.example.LoginLibrary;

import java.io.Serializable;

/**
 * Created by adziki on 1/10/14.
 */
public class AuthenticationResponse implements Serializable {
    /**
	 * Unique auto-generated ID for serialized data 
	 */
	private static final long serialVersionUID = 7408595430180601569L;
	
	public String AuthToken = "";
    public boolean Success = false;

    public AuthenticationResponse(){
        this("",false);
    }

    public AuthenticationResponse(String token, boolean success){
        AuthToken = token;
        Success = success;
    }
}
