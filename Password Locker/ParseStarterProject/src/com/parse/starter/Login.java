package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;


public class Login extends ActionBarActivity implements View.OnClickListener{

    Button signUp, Login;
    EditText email, password;
    String strEmail, strPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            // do stuff with the user
            Intent intent = new Intent(Login.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            // show the signup or login screen
        }

        email = (EditText) findViewById(R.id.useremail);
        password = (EditText) findViewById(R.id.password);

        signUp = (Button) findViewById(R.id.signUp);
        signUp.setOnClickListener(this);
        Login = (Button) findViewById(R.id.login);
        Login.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.signUp:
                Intent i = new Intent(Login.this, SignUp.class);
                startActivity(i);
                finish();

                break;

            case R.id.login:
                strEmail = email.getText().toString();
                strPw = password.getText().toString();

                // Send data to Parse.com for verification
                ParseUser.logInInBackground(strEmail, strPw,
                        new LogInCallback() {
                            public void done(ParseUser user, ParseException e) {
                                if (user != null) {
                                    // If user exist and authenticated, send user to Welcome.class
                                    Intent intent = new Intent(Login.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(),
                                            "아이디나 비밀번호가 틀린 것 같습니다.", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                break;
        }
    }
}
