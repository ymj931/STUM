package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    private ListView listView;
    private ArrayAdapter<String> adapter;

    private EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listview);

        adapter = new ArrayAdapter<String>(this, R.layout.listview);
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onClickListItem);

        ParseUser user = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Passwords");
        query2.addAscendingOrder("name");
        //query2.whereEqualTo("User", user);

        query2.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> List, ParseException e) {

                if (e == null) {
                    for (int i = 0; i < List.size(); i++) {
                        ParseObject course = List.get(i);

                        String temp = course.getString("name");
                        adapter.add(temp);

                    }
                } else {
                    Log.d("#######", "Error: " + e.getMessage());
                }
            }
        });

    }

    private AdapterView.OnItemClickListener onClickListItem = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            // 이벤트 발생 시 해당 아이템 위치의 텍스트를 출력
            Toast.makeText(getApplicationContext(), adapter.getItem(arg2), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainActivity.this, ListInfo.class);
            intent.putExtra("listName", adapter.getItem(arg2));
            startActivity(intent);
        }
    };

    @Override
    public void onRestart() {
        super.onRestart();
        adapter.clear();

        ParseUser user = ParseUser.getCurrentUser();
        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Passwords");
        query2.addAscendingOrder("name");
        //query2.whereEqualTo("User", user);

        query2.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> List, ParseException e) {

                if (e == null) {
                    for (int i = 0; i < List.size(); i++) {
                        ParseObject course = List.get(i);

                        String temp = course.getString("name");
                        adapter.add(temp);

                    }
                } else {
                    Log.d("#######", "Error: " + e.getMessage());
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchViewItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) searchViewItem.getActionView();
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT);
        searchView.setLayoutParams(params);
        searchViewItem.expandActionView();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add_list:
                Intent i = new Intent(MainActivity.this, AddItem.class);
                startActivity(i);
                finish();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
