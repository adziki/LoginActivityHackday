package com.example.LoginLibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import android.widget.TextView.OnEditorActionListener;

import com.example.LoginLibrary.interfaces.LoginInterface;

/**
 * Created by adziki on 1/10/14.
 */
public class LoginActivity extends Activity {
    public static final String LOGIN_CLASS = "com.example.loginclass";
    public static final String DIALOG_SPECIFIER = "com.example.isdialog";
    public static final String AUTH_RESPONSE = "com.example.AUTH_RESPONSE";

    private final int mFullScreenResource = R.layout.a_full_screen_login;
    private final int mDialogScreenResource = R.layout.a_dialog_screen_login;

    private EditText mUserName;
    private EditText mPassword;
    private Button mLoginButton;
    private ImageButton mForgotPasswordButton;
    private ImageView mWaitingImage;
    private TextView mUsernameLabel;
    private TextView mPasswordLabel;
    private ProgressBar mLoadingSpinner;
    private ViewFlipper mViewFlipper;

    private LoginInterface mLoginClass;
    private boolean mIsDialog = false;
    
    public static final Intent createIntentForWebLogin(Context context, LoginInterface webLoginHandler) {
    	Intent loginIntent = new Intent(context, LoginActivity.class);
    	loginIntent.putExtra(LoginActivity.LOGIN_CLASS, webLoginHandler);
        return loginIntent;
    }
    
    // -------------------------------------
    // Activity Lifecycle
    // -------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(mFullScreenResource);

        Bundle b = getIntent().getExtras();
        try{
            mLoginClass = (LoginInterface)b.getSerializable(LOGIN_CLASS);
            mIsDialog = b.getBoolean(DIALOG_SPECIFIER, mIsDialog);

        }catch (Exception ex){
            setResult(RESULT_CANCELED);
            finish();
        }

        // TODO: Move into a viewHolder
        mUserName=(EditText)findViewById(R.id.tbUsername);
        mPassword=(EditText)findViewById(R.id.tbPassword);
        mUsernameLabel=(TextView)findViewById(R.id.textView);
        mPasswordLabel=(TextView)findViewById(R.id.textView2);
        mLoginButton=(Button)findViewById(R.id.loginButton);
        mWaitingImage = (ImageView)findViewById(R.id.waitingImage);
        mForgotPasswordButton=(ImageButton)findViewById(R.id.forgotPasswordButton);
        mLoadingSpinner=(ProgressBar)findViewById(R.id.loginLoadingSpinner);
        mViewFlipper=(ViewFlipper)findViewById(R.id.loginLoadingViewFlipper);
        
        mUserName.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					userNameValid();
				}
			}
		});
		
        mPassword.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if (!hasFocus) {
					passwordValid();
				}
			}
		});
        
        // Show the keyboard when the view is created
 		mUserName.requestFocus();
 		this.getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
 		
 		// Perform the done action on the done button for the password field
 		mPassword.setOnEditorActionListener(new OnEditorActionListener() {
 			@Override
 			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
 		        if ( actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO  ||
 		           ( event.getAction() 	== KeyEvent.ACTION_DOWN &&
 		             event.getKeyCode() == KeyEvent.KEYCODE_ENTER )) {
 		        	validateAndPerformLogin();
 		            return true;
 		        }
 		        return false;
 			}
 		});

 		// Login Button Click Listener
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateAndPerformLogin();
            }
        });

        // Forgot Button Click Listener
        mForgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: call forgot password activity
            }
        });
    }

    protected void loginSuccess(AuthenticationResponse response){
    	//mViewFlipper.showNext();
        Intent result = new Intent();
        result.putExtra(AUTH_RESPONSE,response);

        setResult(RESULT_OK,result);
        finish();
    }
    
    // -------------------------------------
    // Login Action and Validation
    // -------------------------------------
    
    protected void validateAndPerformLogin() {
    	if(mLoginClass != null && validateInput())
        {
    		//mViewFlipper.showNext();
    		new AsyncUsernamePassLogin(mLoginClass, mUserName.getText().toString(), mPassword.getText().toString()).execute();
        }
    }
    
    protected boolean validateInput() {
    	return (userNameValid() && passwordValid());
    }
    
    protected boolean userNameValid() {
    	boolean valid = (mUserName.getText().toString() != null && mUserName.getText().toString().length() > 0);
		setTextErrorOrClearFromResource(!valid, mUserName, R.string.LoginActivity_UserNameValidation);
    	return valid;
    }
    
    protected boolean passwordValid() {
    	boolean valid = (mPassword.getText().toString() != null && mPassword.getText().toString().length() > 0);
    	setTextErrorOrClearFromResource(!valid, mPassword, R.string.LoginActivity_PasswordValidation);
    	return valid;
    }
    
    public static void setTextErrorOrClearFromResource(boolean isError, TextView editText, int stringResourceId) {
		if (editText == null) {
			return;
		}
		
		Context context = editText.getContext().getApplicationContext();
		if (isError) {
			editText.setError(context.getString(stringResourceId));	
		} else {
			editText.setError(null);	
		}
	}
    
    // -------------------------------------
    // UI Updates
    // -------------------------------------
    
    // TODO: Show Progress Spinner ViewFlipper
    protected void hideLogin(){
       mLoginButton.setEnabled(false);
       mUsernameLabel.setVisibility(View.GONE);
       mUserName.setVisibility(View.GONE);
       mPasswordLabel.setVisibility(View.GONE);
       mPassword.setVisibility(View.GONE);

       mWaitingImage.setVisibility(View.VISIBLE);
    }

    protected void showLogin(){
        mLoginButton.setEnabled(true);
        mUsernameLabel.setVisibility(View.VISIBLE);
        mUserName.setVisibility(View.VISIBLE);
        mPasswordLabel.setVisibility(View.VISIBLE);
        mPassword.setVisibility(View.VISIBLE);

        mWaitingImage.setVisibility(View.GONE);
    }
    
    // -------------------------------------
    // Async Login Task
    // -------------------------------------

    private class AsyncUsernamePassLogin extends AsyncTask<Void,Void,Void>{

        private LoginInterface loginHandler;
        private String username;
        private String password;
        private AuthenticationResponse mResponse;

        public AsyncUsernamePassLogin(LoginInterface loginHandler, String username, String password) {
            this.loginHandler = loginHandler;
            this.username = username;
            this.password = password;
        }

        @Override
        protected Void doInBackground(Void... voids) {
        	mResponse = loginHandler.login(this.username, this.password);
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
            showLogin();

            // TODO: Check for success? -SB
            // If Success
            loginSuccess(mResponse);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
