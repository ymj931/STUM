package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.parse.ParseObject;
import com.parse.ParseUser;


public class AddItem extends ActionBarActivity implements View.OnClickListener{

    EditText siteName,siteId,sitePw,siteMemo;
    Button autoGeneration, addItem;
    ToggleButton toggle;
    String strName, strId, strPw, strMemo;

    ParseObject addPasswords = new ParseObject("Passwords");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        siteName = (EditText) findViewById(R.id.site_name);
        siteId = (EditText) findViewById(R.id.site_id);
        sitePw = (EditText) findViewById(R.id.site_pw);
        siteMemo = (EditText) findViewById(R.id.site_memo);

        autoGeneration = (Button) findViewById(R.id.auto_generate);
        addItem = (Button) findViewById(R.id.add_item);
        addItem.setOnClickListener(this);

        toggle = (ToggleButton) findViewById(R.id.toggle);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.add_item:

                strName = siteName.getText().toString();
                strId = siteId.getText().toString();
                strPw = sitePw.getText().toString();
                strMemo = siteMemo.getText().toString();


                ParseUser user = ParseUser.getCurrentUser();
                addPasswords.put("User", user);
                addPasswords.put("name", strName);
                addPasswords.put("personalID", strId);
                addPasswords.put("password", strPw);
                addPasswords.put("memo", strMemo);
                addPasswords.saveInBackground();

                Intent i = new Intent(AddItem.this, MainActivity.class);
                startActivity(i);
                finish();

                break;
        }

    }

}
