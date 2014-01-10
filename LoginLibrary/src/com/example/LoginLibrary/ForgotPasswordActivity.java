package com.example.LoginLibrary;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by adziki on 1/10/14.
 */
public class ForgotPasswordActivity extends Activity {

    private EditText mEmailAddress;
    private Button mSubmitButton;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_forgot_password);

        mEmailAddress = (EditText)findViewById(R.id.tbEmail);
        mSubmitButton = (Button)findViewById(R.id.submitButton);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //do stuff
            }
        });
    }
}