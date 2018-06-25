package com.teachme.teachmeclient.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.teachme.teachmeclient.BaseActivity.BaseActivity;
import com.teachme.teachmeclient.Controllers.UsersController;
import com.teachme.teachmeclient.Interfaces.DatabaseQuery;
import com.teachme.teachmeclient.R;
import com.teachme.teachmeclient.Entities.User;

import java.util.List;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    Button btnGetAll, btnAdd, btnBackToMain;

    private UsersController controller;

    //This method will call when the screen is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers);

        btnGetAll = (Button) findViewById(R.id.btnGetAll);
        btnGetAll.setOnClickListener(this);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnBackToMain = (Button) findViewById(R.id.btnBackToMain);
        btnBackToMain.setOnClickListener(this);

        controller = new UsersController(this);
        progressBar = findViewById(R.id.spinner);
    }

    //This function will call when the screen is activate
    @Override
    public void onResume() {
        super.onResume();
        getUsers();
    }

    @Override
    public void onClick(View v) {
        if (v == findViewById(R.id.btnAdd)) {

            Intent intent = new Intent(this, UserDetailActivity.class);
            intent.putExtra("teacher_Id", "");
            startActivity(intent);
        } else if (v == findViewById(R.id.btnBackToMain)) {
            finish();
        } else {
            getUsers();
        }
    }

    private void getUsers() {
        startSpinner();
        controller.getUsers(new DatabaseQuery() {
            @Override
            public void getUsersResult(List<User> teachers) {
                populateUsers(teachers);
            }
        }, false);
    }

    private void populateUsers(List<User> teachers) {
        controller.clear();
        for (User user : teachers){
            controller.add(user);
        }
        stopSpinner();

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView user_Id = (TextView) view.findViewById(R.id.user_id);
                String userId = user_Id.getText().toString();
                Intent objIndent = new Intent(getApplicationContext(), UserDetailActivity.class);
                objIndent.putExtra("user_Id", userId);
                startActivity(objIndent);
            }
        });

        listView.setAdapter(controller);
    }
}