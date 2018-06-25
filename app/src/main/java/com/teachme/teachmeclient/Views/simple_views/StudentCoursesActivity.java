package com.teachme.teachmeclient.Views.simple_views;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.teachme.teachmeclient.BaseActivity.BaseActivity;
import com.teachme.teachmeclient.Entities.CourseProgress;
import com.teachme.teachmeclient.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Call;
import retrofit2.Response;


public class StudentCoursesActivity extends BaseActivity implements android.view.View.OnClickListener {

    Button btnGetAll;
    Button btnAdd;
    Button btnBack;
    TextView studentcourse_Id;
    private String _User_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_courses);

        btnGetAll = (Button) findViewById(R.id.btnGetAll);
        btnGetAll.setOnClickListener(this);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        _User_Id = "";
        Intent intent = getIntent();
        _User_Id = intent.getStringExtra("user_Id");
    }

    //This function will call when the screen is activate
    @Override
    public void onResume() {
        super.onResume();
        refreshScreen();
    }

    @Override
    public void onStart() {
        super.onStart();
        refreshScreen();
    }


    @Override
    public void onClick(View v) {
        if (v == findViewById(R.id.btnAdd)) {

            Intent intent = new Intent(this, StudentCourseDetailActivity.class);
            intent.putExtra("studentcourse_Id", "");
            intent.putExtra("user_Id", _User_Id);
            startActivity(intent);
        } else if (v == findViewById(R.id.btnBack)) {
            finish();
        } else {
            refreshScreen();
        }
    }


    private void refreshScreen() {
        Call<List<CourseProgress>> call = restClient.getStudentCoursesByUserId(_User_Id);
        call.enqueue(new Callback<List<CourseProgress>>() {
                         @Override
                         public void onResponse(Call<List<CourseProgress>> call, Response<List<CourseProgress>> response) {
                             ListView lv = (ListView) findViewById(R.id.listView);


                             ArrayList<HashMap<String, String>> studentcourseList = new ArrayList<HashMap<String, String>>();

                             if (response.body() == null) {
                                 Toast.makeText(StudentCoursesActivity.this, "that author has no courses", Toast.LENGTH_SHORT).show();
                             }
                             else {
                                 for (int i = 0; i < response.body().size(); i++) {
                                     System.out.println("id:" + response.body().get(i).getId() + "\tname:" + response.body().get(i).getName());
                                     HashMap<String, String> studentcourse = new HashMap<String, String>();
                                     studentcourse.put("id", String.valueOf(response.body().get(i).getId()));
                                     studentcourse.put("name", String.valueOf(response.body().get(i).getCourseId()));
                                     studentcourseList.add(studentcourse);
                                 }

                                 lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                     @Override
                                     public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                         studentcourse_Id = (TextView) view.findViewById(R.id.user_Id);
                                         String studentcourseId = studentcourse_Id.getText().toString();
                                         Intent objIndent = new Intent(getApplicationContext(), StudentCourseDetailActivity.class);
                                         objIndent.putExtra("studentcourse_Id", studentcourseId);
                                         objIndent.putExtra("user_Id", _User_Id);
                                         startActivity(objIndent);
                                     }
                                 });
                                 ListAdapter adapter = new SimpleAdapter(StudentCoursesActivity.this, studentcourseList, R.layout.view_user_entry, new String[]{"id", "name"}, new int[]{R.id.user_Id, R.id.user_name});
                                 lv.setAdapter(adapter);
                             }
                         }

                         @Override
                         public void onFailure(Call<List<CourseProgress>> call, Throwable t) {
                             Toast.makeText(StudentCoursesActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                         }
                     }
        );
    }
}