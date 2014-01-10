package com.example.LoginLibrary;

import android.os.Parcel;
import android.os.Parcelable;
import com.example.LoginLibrary.interfaces.LoginInterface;

import java.io.Serializable;

/**
 * Created by adziki on 1/10/14.
 */
public class WebLogin implements LoginInterface, Serializable {

    public WebLogin(String baseUrl){

    }


    @Override
    public AuthenticationResponse login() {
           //do stuff

        return new AuthenticationResponse("1234567890", true);
    }
}
