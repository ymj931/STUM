package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class SignUp extends ActionBarActivity implements View.OnClickListener{

    EditText name,email,password,password2;
    Button signUp;

    String strName, strEmail, strPw, strPw2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        name = (EditText) findViewById(R.id.username);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        password2 = (EditText) findViewById(R.id.password2);

        signUp = (Button) findViewById(R.id.signUp2);

        signUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signUp2:

                strName = name.getText().toString();
                strEmail = email.getText().toString();
                strPw = password.getText().toString();
                strPw2 = password2.getText().toString();

                // Force user to fill up the form
                if (strName.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "이름을 입력해주세요",
                            Toast.LENGTH_LONG).show();
                }
                else if (strEmail.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "이메일을 입력해주세요",
                            Toast.LENGTH_LONG).show();
                }
                else if (strPw.equals("") && strPw2.equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "비밀번호를 입력해주세요",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    // Save new user data into Parse.com Data Storage
                    ParseUser user = new ParseUser();
                    user.setUsername(strEmail);
                    user.setPassword(strPw);
                    user.put("name", strName);
                    //user.setEmail(strEmail);

                    user.signUpInBackground(new SignUpCallback() {
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(getApplicationContext(), "회원가입이 완료 되었습니다.", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(SignUp.this, Login.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "회원가입 에러", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }

                break;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sign_up, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
