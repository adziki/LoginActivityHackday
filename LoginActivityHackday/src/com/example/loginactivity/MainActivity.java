package com.example.loginactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.example.LoginLibrary.AuthenticationResponse;
import com.example.LoginLibrary.LoginActivity;
import com.example.LoginLibrary.WebLogin;

public class MainActivity extends Activity {

    private final int LOGIN_CODE = 1345;
    private static Button mLoginButton;

    private TextView mAuthToken;
    private TextView mAuthSuccess;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mLoginButton=(Button)findViewById(R.id.loginButton);
        mAuthToken=(TextView)findViewById(R.id.authToken);
        mAuthSuccess=(TextView)findViewById(R.id.authResponse);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                i.putExtra(LoginActivity.LOGIN_CLASS, new WebLogin("http://login.google.com"));
                startActivityForResult(i, LOGIN_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == LOGIN_CODE){
            if(resultCode != RESULT_OK)
            {
                Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();
                return;
            }
            AuthenticationResponse response = (AuthenticationResponse)data.getSerializableExtra(LoginActivity.AUTH_RESPONSE);

            if(response.Success){
                mAuthSuccess.setText("Authentication Succeeded");
                mAuthToken.setText("Token: "+response.AuthToken);
            }
            else{
                mAuthSuccess.setText("Authentication Failed");
                mAuthToken.setText("");
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
