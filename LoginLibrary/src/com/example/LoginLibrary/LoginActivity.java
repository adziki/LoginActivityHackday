package com.example.LoginLibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.*;
import com.example.LoginLibrary.interfaces.LoginInterface;

/**
 * Created by adziki on 1/10/14.
 */
public class LoginActivity extends Activity {
    public static final String LOGIN_CLASS = "com.example.loginclass";
    public static final String DIALOG_SPECIFIER = "com.example.isdialog";
    public static final String AUTH_RESPONSE = "com.example.AUTH_RESPONSE";

    private final int mFullScreenResource = R.layout.a_full_screen_login;

    private EditText mUserName;
    private EditText mPassword;
    private Button mLoginButton;
    private ImageButton mForgotPasswordButton;
    private ProgressBar mWaitingImage;


    private LoginInterface mLoginClass;
    private boolean mIsDialog = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(mFullScreenResource);

        Bundle b = getIntent().getExtras();
        try{
            mLoginClass = (LoginInterface)b.getSerializable(LOGIN_CLASS);
            mIsDialog = b.getBoolean(DIALOG_SPECIFIER, mIsDialog);

        }catch (Exception ex){
            setResult(RESULT_CANCELED);
            finish();
        }

        mUserName=(EditText)findViewById(R.id.tbUsername);
        mPassword=(EditText)findViewById(R.id.tbPassword);
        mLoginButton=(Button)findViewById(R.id.loginButton);
        mWaitingImage = (ProgressBar)findViewById(R.id.waitingImage);
        mForgotPasswordButton=(ImageButton)findViewById(R.id.forgotPasswordButton);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mLoginClass != null)
                {
                      new AsyncLogin(mLoginClass).execute();
                }
            }
        });

        mForgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call forgot password activity
                 Intent i = new Intent(getApplicationContext(), ForgotPasswordActivity.class);

                startActivity(i);
            }
        });
    }

    protected void loginSuccess(AuthenticationResponse response){
        Intent result = new Intent();
        result.putExtra(AUTH_RESPONSE,response);

        setResult(RESULT_OK,result);
        finish();
    }

    protected void hideLogin(){
       mLoginButton.setEnabled(false);
       mUserName.setVisibility(View.GONE);
       mPassword.setVisibility(View.GONE);

        mWaitingImage.setVisibility(View.VISIBLE);

    }

    protected void showLogin(){
        mLoginButton.setEnabled(true);
        mUserName.setVisibility(View.VISIBLE);
        mPassword.setVisibility(View.VISIBLE);

        mWaitingImage.setVisibility(View.GONE);

    }

    private class AsyncLogin extends AsyncTask<Void,Void,Void>{

        private LoginInterface mLogin;
        private AuthenticationResponse mResponse;

        public AsyncLogin(LoginInterface login){
               mLogin = login;
        }

        @Override
        protected Void doInBackground(Void... voids) {
           mResponse = mLogin.login();

            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            hideLogin();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(getApplicationContext(),"Login completed", Toast.LENGTH_SHORT).show();
            showLogin();

            //if success
            loginSuccess(mResponse);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
