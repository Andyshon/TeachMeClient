package com.teachme.teachmeclient.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.teachme.teachmeclient.BaseActivity.BaseActivity;
import com.teachme.teachmeclient.Controllers.UserDetailController;
import com.teachme.teachmeclient.Interfaces.DatabaseQuery;
import com.teachme.teachmeclient.Models.GlobalConstants;
import com.teachme.teachmeclient.R;
import com.teachme.teachmeclient.Views.simple_views.StudentCoursesActivity;
import com.teachme.teachmeclient.Entities.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class UserDetailActivity extends BaseActivity implements View.OnClickListener {

    Button btnRegister;
    Button btnDelete;
    Button btnClose;
    Button btnViewStudentCourses;
    EditText editTextName;
    EditText editTextEmail;
    EditText editTextLogin;
    private String _User_Id;

    private UserDetailController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        btnRegister = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClose = (Button) findViewById(R.id.btnClose);
        btnViewStudentCourses = (Button) findViewById(R.id.btnViewStudentCourses);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextLogin = (EditText) findViewById(R.id.editTextLogin);

        btnRegister.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        btnViewStudentCourses.setOnClickListener(this);

        _User_Id = "";
        Intent intent = getIntent();
        _User_Id = intent.getStringExtra("user_Id");

        controller = new UserDetailController(this);
        progressBar = findViewById(R.id.spinner);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserDetail();
    }

    private void getUserDetail(){
        if (_User_Id != null && !_User_Id.isEmpty()) {
            startSpinner();
            controller.getUserDetail(new DatabaseQuery() {
                @Override
                public void getUserResult(User user) {
                    populateUserDetail(user);
                }
            }, _User_Id);
        }
        else {
            Toast.makeText(this, "Enter data to create new user", Toast.LENGTH_SHORT).show();
            stopSpinner();
        }
    }

    private void populateUserDetail(User user) {
        editTextName.setText(user.getFullName());
        editTextEmail.setText(user.getEmail());
        editTextLogin.setText(user.getLogin());
        stopSpinner();
    }


    @Override
    public void onClick(View v) {
        if (findViewById(R.id.btnDelete) == v) {

            controller.deleteUserById(new DatabaseQuery() {
                @Override
                public void getDeletedUserResult() {
                    finish();
                }
            }, _User_Id);

        }
        else if (v == findViewById(R.id.btnClose)) {
            finish();
        }
        else if (v == findViewById(R.id.btnViewStudentCourses)) {
            Intent intent = new Intent(this, StudentCoursesActivity.class);
            intent.putExtra("user_Id", _User_Id);
            startActivity(intent);
        }
        else if (findViewById(R.id.btnSave) == v) {

            if (_User_Id == null || _User_Id.isEmpty()) {
                //todo: no id -> new user

                User user = new User();
                user.setEmail(editTextEmail.getText().toString());
                user.setFullName(editTextName.getText().toString());
                user.setLogin(editTextLogin.getText().toString());

                //TODO: Add UI fields
                user.setUserRole(GlobalConstants.UserRole.STUDENT);

                user.setCompletedCoursesCount(0);
                SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                user.setRegisterDate(DATE_FORMAT.format(Calendar.getInstance().getTime()));

                controller.addUser(new DatabaseQuery() {
                    @Override
                    public void getAddUserResult() {
                        Toast.makeText(UserDetailActivity.this, "New user registered", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }, user);

            } else {
                //todo: update existing user

                User updateUser = new User();
                updateUser.setEmail(editTextEmail.getText().toString().trim());
                updateUser.setFullName(editTextName.getText().toString().trim());
                updateUser.setLogin(editTextLogin.getText().toString().trim());

                controller.updateUserById(new DatabaseQuery() {
                    @Override
                    public void getUpdateUserByIdResult() {
                        Toast.makeText(UserDetailActivity.this, "user updated", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }, updateUser, _User_Id);
            }
        }
    }
}