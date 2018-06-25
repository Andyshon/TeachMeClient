package com.teachme.teachmeclient.Views.simple_views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.teachme.teachmeclient.BaseActivity.BaseActivity;
import com.teachme.teachmeclient.Entities.Course;
import com.teachme.teachmeclient.R;
import com.teachme.teachmeclient.Views.SectionsActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CourseDetailActivity extends BaseActivity implements android.view.View.OnClickListener {

    Button btnRegister;
    Button btnDelete;
    Button btnClose;
    Button btnViewSections;
    EditText editTextName;
    EditText editTextDays;
    EditText editTextDescription;
    EditText editTextKeywords;
    private String _Course_Id;
    private String _Teacher_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);

        btnRegister = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClose = (Button) findViewById(R.id.btnClose);
        btnViewSections = (Button) findViewById(R.id.btnViewSections);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextDays = (EditText) findViewById(R.id.editTextDays);
        editTextDescription = (EditText) findViewById(R.id.editTextDescription);
        editTextKeywords = (EditText) findViewById(R.id.editTextKeywords);

        btnRegister.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        btnViewSections.setOnClickListener(this);


        _Course_Id = "";
        _Teacher_Id = "";
        Intent intent = getIntent();
        _Course_Id = intent.getStringExtra("course_Id");
        _Teacher_Id = intent.getStringExtra("teacher_Id");

        if (_Course_Id != null && !_Course_Id.isEmpty()) {
            Call<Course> call = restClient.getCourseById(_Course_Id);
            call.enqueue(new Callback<Course>() {
                @Override
                public void onResponse(Call<Course> call, Response<Course> response) {
                    Course course = response.body();
                    editTextName.setText(course.getName());
                    editTextDays.setText(String.valueOf(course.getDays()));
                    editTextDescription.setText(course.getDescription());
                    editTextKeywords.setText(course.getKeywords());

                }

                @Override
                public void onFailure(Call<Course> call, Throwable t) {
                    Toast.makeText(CourseDetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

                }
            });
        }
    }


    @Override
    public void onClick(View v) {
        if (findViewById(R.id.btnDelete) == v) {
            Call<Void> call = restClient.deleteCourseById(_Course_Id);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.code() == 204) {
                        Toast.makeText(CourseDetailActivity.this, "Course Record Deleted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(CourseDetailActivity.this, "Error: Not Deleted" + response.errorBody(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(CourseDetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            finish();
        } else if (v == findViewById(R.id.btnClose)) {
            finish();
        } else if (v == findViewById(R.id.btnViewSections)) {
            Intent intent = new Intent(this, SectionsActivity.class);
            intent.putExtra("course_Id", _Course_Id);
            startActivity(intent);
        } else if (findViewById(R.id.btnSave) == v) {

            if (_Course_Id == null || _Course_Id.isEmpty()) {
                // No Id -> new course

                Course course = new Course();
                course.setName(editTextName.getText().toString());
                course.setDays(Integer.parseInt(editTextDays.getText().toString()));
                course.setDescription(editTextDescription.getText().toString());
                course.setKeywords(editTextKeywords.getText().toString());
                course.setUserId(_Teacher_Id);

                restClient.addCourse(course).enqueue(new Callback<Course>() {
                    @Override
                    public void onResponse(Call<Course> call, Response<Course> response) {
                        if (response.code() == 201) {
                            Toast.makeText(CourseDetailActivity.this, "New Course Registered.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(CourseDetailActivity.this, "Not Created:" + response.errorBody(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Course> call, Throwable t) {
                        Toast.makeText(CourseDetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


            } else {
                // Update existing course

                Call<Course> call = restClient.getCourseById(_Course_Id);
                call.enqueue(new Callback<Course>() {
                    @Override
                    public void onResponse(Call<Course> call, Response<Course> response) {
                        Course existingCourse = response.body();
                        existingCourse.setName(editTextName.getText().toString());
                        existingCourse.setDays(Integer.parseInt(editTextDays.getText().toString()));
                        existingCourse.setDescription(editTextDescription.getText().toString());
                        existingCourse.setKeywords(editTextKeywords.getText().toString());

                        // Use Backend API to update course
                        restClient.updateCourseById(_Course_Id, existingCourse).enqueue(new Callback<Course>() {
                            @Override
                            public void onResponse(Call<Course> call, Response<Course> response) {
                                Toast.makeText(CourseDetailActivity.this, " updated.", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<Course> call, Throwable t) {
                                Toast.makeText(CourseDetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Course> call, Throwable t) {
                        Toast.makeText(CourseDetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }
}