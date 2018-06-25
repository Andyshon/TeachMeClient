package com.teachme.teachmeclient.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.teachme.teachmeclient.BaseActivity.BaseActivity;
import com.teachme.teachmeclient.Controllers.CoursesController;
import com.teachme.teachmeclient.Interfaces.DatabaseQuery;
import com.teachme.teachmeclient.Entities.Answer;
import com.teachme.teachmeclient.Entities.AuthTokenCacheManager;
import com.teachme.teachmeclient.R;
import com.teachme.teachmeclient.Views.simple_views.AnswerDetailActivity;
import com.teachme.teachmeclient.Views.simple_views.CourseDetailActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CoursesActivity extends BaseActivity implements View.OnClickListener {

    Button btnGetAll, btnAdd, btnBack;
    TextView course_Id;
    private String _Teacher_Id;

    private CoursesController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);

        TextView titleText = (TextView) findViewById(R.id.textView4);
        titleText.setText(String.format("Hello %s!", AuthTokenCacheManager.loadUserTokenCache(CoursesActivity.this).getUserName()));

        btnGetAll = (Button) findViewById(R.id.btnGetAll);
        btnGetAll.setOnClickListener(this);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        _Teacher_Id = "";
        Intent intent = getIntent();
        _Teacher_Id = intent.getStringExtra("teacher_Id");


        controller = new CoursesController(this);
        progressBar = findViewById(R.id.spinner);
    }

    //This function will call when the screen is activate
    @Override
    public void onResume() {
        super.onResume();
        getCourses();
    }

    private void getCourses() {
        startSpinner();
        controller.getCourses(new DatabaseQuery() {
            @Override
            public void getAnswersByExerciseId(List<Answer> answers) {
                populateCourses(answers);
            }
        }, _Teacher_Id);
    }

    private void populateCourses(List<Answer> answers) {
        ListView lv = (ListView) findViewById(R.id.listView);

        ArrayList<HashMap<String, String>> answerList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < answers.size(); i++) {
            HashMap<String, String> answer = new HashMap<String, String>();
            answer.put("id", answers.get(i).getId());
            answer.put("name", answers.get(i).getTitle());
            answerList.add(answer);
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                course_Id = (TextView) view.findViewById(R.id.user_Id);
                String answerId = course_Id.getText().toString();
                Intent objIndent = new Intent(getApplicationContext(), AnswerDetailActivity.class);
                objIndent.putExtra("answer_Id", answerId);
                objIndent.putExtra("exercise_Id", _Teacher_Id);
                startActivity(objIndent);
            }
        });
        ListAdapter adapter = new SimpleAdapter(CoursesActivity.this, answerList, R.layout.view_user_entry, new String[]{"id", "name"}, new int[]{R.id.user_Id, R.id.user_name});
        lv.setAdapter(adapter);
        stopSpinner();
    }


    @Override
    public void onClick(View v) {
        if (v == findViewById(R.id.btnAdd)) {

            Intent intent = new Intent(this, CourseDetailActivity.class);
            intent.putExtra("course_Id", "");
            intent.putExtra("teacher_Id", _Teacher_Id);
            startActivity(intent);
        } else if (v == findViewById(R.id.btnBack)) {
            finish();
        } else {
            getCourses();
        }
    }
}