package com.teachme.teachmeclient.Views.simple_views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.teachme.teachmeclient.BaseActivity.BaseActivity;
import com.teachme.teachmeclient.Entities.CourseProgress;
import com.teachme.teachmeclient.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StudentCourseDetailActivity extends BaseActivity implements android.view.View.OnClickListener {

    Button btnRegister;
    Button btnDelete;
    Button btnClose;
    EditText editTextStudentId;
    EditText editTextCourseId;
    EditText editTextCourseName;
    private String _StudentCourse_Id;
    private String _User_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_course_detail);

        btnRegister = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClose = (Button) findViewById(R.id.btnClose);

        editTextStudentId = (EditText) findViewById(R.id.editTextStudentId);
        editTextCourseId = (EditText) findViewById(R.id.editTextCourseId);
        editTextCourseName = (EditText) findViewById(R.id.editTextCourseName);

        btnRegister.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);


        _StudentCourse_Id = "";
        _User_Id = "";
        Intent intent = getIntent();
        _StudentCourse_Id = intent.getStringExtra("studentcourse_Id");
        _User_Id = intent.getStringExtra("user_Id");


        if (_StudentCourse_Id != null && !_StudentCourse_Id.isEmpty()) {
            Call<CourseProgress> call = restClient.getStudentCourseById(_StudentCourse_Id);
            call.enqueue(new Callback<CourseProgress>() {
                @Override
                public void onResponse(Call<CourseProgress> call, Response<CourseProgress> response) {
                    CourseProgress courseProgress = response.body();
                    editTextStudentId.setText(courseProgress.getUserId());
                    editTextCourseId.setText(courseProgress.getCourseId());
                    editTextCourseName.setText(courseProgress.getName());
                    _User_Id = courseProgress.getUserId();
                }

                @Override
                public void onFailure(Call<CourseProgress> call, Throwable t) {
                    Toast.makeText(StudentCourseDetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {
            editTextStudentId.setText(_User_Id);
        }
    }


    @Override
    public void onClick(View v) {
        if (findViewById(R.id.btnDelete) == v) {
            Call<Void> call = restClient.deleteStudentCourseById(_StudentCourse_Id);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.code() == 204) {
                        Toast.makeText(StudentCourseDetailActivity.this, "StudentCourse Record Deleted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(StudentCourseDetailActivity.this, "Error: Not Deleted" + response.errorBody(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(StudentCourseDetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            finish();
        } else if (v == findViewById(R.id.btnClose)) {
            finish();
        } else if (findViewById(R.id.btnSave) == v) {

            if (_StudentCourse_Id == null || _StudentCourse_Id.isEmpty()) {
                // No Id -> new studentcourse

                CourseProgress courseProgress = new CourseProgress(editTextCourseName.getText().toString().trim(), editTextStudentId.getText().toString().trim(),
                        editTextCourseId.getText().toString().trim());

                restClient.addStudentCourse(courseProgress).enqueue(new Callback<CourseProgress>() {
                    @Override
                    public void onResponse(Call<CourseProgress> call, Response<CourseProgress> response) {
                        if (response.code() == 201) {
                            Toast.makeText(StudentCourseDetailActivity.this, "New StudentCourse Registered.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(StudentCourseDetailActivity.this, "Not Created:" + response.errorBody(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CourseProgress> call, Throwable t) {
                        Toast.makeText(StudentCourseDetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            } else {
                // Update existing studentcourse

                Call<CourseProgress> call = restClient.getStudentCourseById(_StudentCourse_Id);
                call.enqueue(new Callback<CourseProgress>() {
                    @Override
                    public void onResponse(Call<CourseProgress> call, Response<CourseProgress> response) {
                        CourseProgress existingCourseProgress = response.body();
                        existingCourseProgress.setUserId(editTextStudentId.getText().toString());
                        existingCourseProgress.setCourseId(editTextCourseId.getText().toString());

                        // Use Backend API to update studentcourse
                        restClient.updateStudentCourseById(_StudentCourse_Id, existingCourseProgress).enqueue(new Callback<CourseProgress>() {
                            @Override
                            public void onResponse(Call<CourseProgress> call, Response<CourseProgress> response) {
                                Toast.makeText(StudentCourseDetailActivity.this, "StudentCourse updated.", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<CourseProgress> call, Throwable t) {
                                Toast.makeText(StudentCourseDetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<CourseProgress> call, Throwable t) {
                        Toast.makeText(StudentCourseDetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }
}