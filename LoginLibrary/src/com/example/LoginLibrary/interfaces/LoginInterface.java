package com.example.LoginLibrary.interfaces;

import java.io.Serializable;

import com.example.LoginLibrary.AuthenticationResponse;

/**
 * Created by adziki on 1/10/14.
 */
public interface LoginInterface extends Serializable {
    AuthenticationResponse login(String username, String password);
}
