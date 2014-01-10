package com.example.LoginLibrary;

import java.io.Serializable;

/**
 * Created by adziki on 1/10/14.
 */
public class AuthenticationResponse implements Serializable {
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
