package com.wordpress.jonyonandroidcraftsmanship.sqlitedemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

    private EditText etUserName;
    private EditText etPassword;
    private EditText etQuery;
    DBAdapter dbAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        etUserName = (EditText) findViewById(R.id.etUserName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etQuery = (EditText) findViewById(R.id.etQuery);
        dbAdapter=new DBAdapter(this);
    }

    public void addUser(View view) {
        String name = etUserName.getText().toString();
        String password = etPassword.getText().toString();
        long id=dbAdapter.insertData(name,password);
        if (id<0){
            Logger.toast(this,"Unsuccessfull");
        }else{
            Logger.toast(this,"Successfully inserted a row!");
        }
    }

    public void viewDetails(View view) {
        String data=dbAdapter.getAllData();
        Logger.toast(this,data);
    }

    public void getDetails(View view) {
        String string=etQuery.getText().toString();
        //Jony abc
        String name= string.substring(0,string.indexOf(" "));
        String password=string.substring(string.indexOf(" ")+1);
        String id=dbAdapter.getData(name,password);
        Logger.toast(this,id);
    }

    public void update(View view) {
        dbAdapter.updateName("test","Jony");
    }

    public void delete(View view) {
        int count=dbAdapter.deleteRow();
        Logger.toast(this,""+count);
    }
}
