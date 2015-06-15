package com.parse.starter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;


public class ListEdit extends ActionBarActivity implements View.OnClickListener {

    EditText editName,editId,editPw,editMemo;
    Button autoGeneration, editItem;
    ToggleButton toggle;
    String strName, strId, strPw, strMemo, data;


    //ParseObject editPasswords = new ParseObject("Passwords");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_edit);

        editName = (EditText) findViewById(R.id.edit_name);
        editId = (EditText) findViewById(R.id.edit_id);
        editPw = (EditText) findViewById(R.id.edit_pw);
        editMemo = (EditText) findViewById(R.id.edit_memo);

        autoGeneration = (Button) findViewById(R.id.edit_auto_generate);
        editItem = (Button) findViewById(R.id.edit_item);
        editItem.setOnClickListener(this);

        toggle = (ToggleButton) findViewById(R.id.edit_toggle);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    editPw.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }
                else {
                    editPw.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        data = getIntent().getExtras().getString("objectID");

        ParseUser user = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Passwords");
        query.getInBackground(data, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {
                    String name = object.getString("name");
                    String id = object.getString("personalID");
                    String pw = object.getString("password");
                    String memo = object.getString("memo");

                    editName.setText(name, TextView.BufferType.EDITABLE);
                    editId.setText(id, TextView.BufferType.EDITABLE);
                    editPw.setText(pw, TextView.BufferType.EDITABLE);
                    editMemo.setText(memo, TextView.BufferType.EDITABLE);


                } else {
                    Log.d("%%%%%%%%%", e.getMessage());
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edit_item:

                strName = editName.getText().toString();
                strId = editId.getText().toString();
                strPw = editPw.getText().toString();
                strMemo = editMemo.getText().toString();

                ParseQuery<ParseObject> query = ParseQuery.getQuery("Passwords");

                query.getInBackground(data, new GetCallback<ParseObject>() {
                    public void done(ParseObject list, ParseException e) {
                        if (e == null) {
                            list.put("name", strName);
                            list.put("personalID", strId);
                            list.put("password", strPw);
                            list.put("memo", strMemo);
                            list.saveInBackground();

                            Intent intent = new Intent(ListEdit.this, ListInfo.class);
                            intent.putExtra("listName", strName);
                            startActivity(intent);
                            finish();
                        }

                        else {
                            Log.d("*********", e.getMessage());
                        }
                    }
                });

                break;
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            AlertDialog.Builder alertbox = new AlertDialog.Builder(ListEdit.this);
            //alertbox.setIcon(R.drawable.info_icon);
            alertbox.setTitle("취소하시겠습니까?");
            alertbox.setPositiveButton("예", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    // finish used for destroyed activity
                    finish();
                }
            });

            alertbox.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {
                    // Nothing will be happened when clicked on no button
                    // of Dialog
                }
            });
            alertbox.setCancelable(false);
            alertbox.show();
        }
        return super.onKeyDown(keyCode, event);
    }

}
