package com.parse.starter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;


public class ListInfo extends ActionBarActivity {

    TextView account, pw, memo;
    String data, objectId;
    ToggleButton toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_info);

        account = (TextView) findViewById(R.id.account);
        pw = (TextView) findViewById(R.id.pw);
        memo = (TextView) findViewById(R.id.memo);

        toggle = (ToggleButton) findViewById(R.id.list_toggle);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    pw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                else {
                    pw.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        data = getIntent().getExtras().getString("listName");

        ParseUser user = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Passwords");
        query2.whereEqualTo("name", data);
        //query2.whereEqualTo("User", user);

        query2.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> List, ParseException e) {

                if (e == null) {
                    for (int i = 0; i < List.size(); i++) {
                        ParseObject course = List.get(i);

                        String personalID = course.getString("personalID");
                        String password = course.getString("password");
                        String title = course.getString("name");
                        String strMemo = course.getString("memo");
                        String dataID = course.getObjectId();

                        account.setText(personalID);
                        pw.setText(password);
                        memo.setText(strMemo);
                        setDataId(dataID);

                        ActionBar actionBar = getSupportActionBar();
                        actionBar.setTitle(title);

                    }
                } else {
                    Log.d("#######", "Error: " + e.getMessage());
                }
            }
        });
    }

    void setDataId(String t) {
        objectId = t;
    }
    String getDataId() {
        return objectId;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.edit) {

            Intent intent = new Intent(ListInfo.this, ListEdit.class);
            intent.putExtra("objectID", getDataId());
            startActivity(intent);
            finish();

            return true;
        }

        if (id == R.id.delete) {

            //String tempId = getDataID();

            AlertDialog.Builder alertbox = new AlertDialog.Builder(ListInfo.this);
            //alertbox.setIcon(R.drawable.info_icon);
            alertbox.setTitle("삭제하시겠습니까?");
            alertbox.setPositiveButton("예", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface arg0, int arg1) {

                    ParseQuery<ParseObject> query = ParseQuery.getQuery("Passwords");
                    query.getInBackground(getDataId(), new GetCallback<ParseObject>() {
                        public void done(ParseObject list, ParseException e) {
                            if (e == null) {
                                list.deleteInBackground();

                                Intent intent = new Intent(ListInfo.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }

                            else {
                                Log.d("*********", e.getMessage());
                            }
                        }
                    });

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

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
