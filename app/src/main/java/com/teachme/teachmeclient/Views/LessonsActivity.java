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
import com.teachme.teachmeclient.Controllers.LessonsController;
import com.teachme.teachmeclient.Interfaces.DatabaseQuery;
import com.teachme.teachmeclient.Entities.Lesson;
import com.teachme.teachmeclient.R;
import com.teachme.teachmeclient.Views.simple_views.AnswerDetailActivity;
import com.teachme.teachmeclient.Views.simple_views.LessonDetailActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class LessonsActivity extends BaseActivity implements View.OnClickListener {

    Button btnGetAll, btnAdd, btnBack;
    TextView lesson_Id;
    private String _Section_Id;

    private LessonsController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);

        btnGetAll = (Button) findViewById(R.id.btnGetAll);
        btnGetAll.setOnClickListener(this);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        _Section_Id = "";
        Intent intent = getIntent();
        _Section_Id = intent.getStringExtra("section_Id");


        controller = new LessonsController(this);
        progressBar = findViewById(R.id.spinner);
    }

    //This function will call when the screen is activate
    @Override
    public void onResume() {
        super.onResume();
        getLessons();
    }

    private void getLessons() {
        startSpinner();
        controller.getLessons(new DatabaseQuery() {
            @Override
            public void getLessons(List<Lesson> lessons) {
                populateLessons(lessons);
            }
        }, _Section_Id);
    }

    private void populateLessons(List<Lesson> lessons) {
        ListView lv = (ListView) findViewById(R.id.listView);

        ArrayList<HashMap<String, String>> answerList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < lessons.size(); i++) {
            HashMap<String, String> answer = new HashMap<String, String>();
            answer.put("id", lessons.get(i).getId());
            answer.put("name", lessons.get(i).getName());
            answerList.add(answer);
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                lesson_Id = (TextView) view.findViewById(R.id.user_Id);
                String answerId = lesson_Id.getText().toString();
                Intent objIndent = new Intent(getApplicationContext(), AnswerDetailActivity.class);
                objIndent.putExtra("answer_Id", answerId);
                objIndent.putExtra("exercise_Id", _Section_Id);
                startActivity(objIndent);
            }
        });
        ListAdapter adapter = new SimpleAdapter(LessonsActivity.this, answerList, R.layout.view_user_entry, new String[]{"id", "name"}, new int[]{R.id.user_Id, R.id.user_name});
        lv.setAdapter(adapter);
        stopSpinner();
    }


    @Override
    public void onClick(View v) {
        if (v == findViewById(R.id.btnAdd)) {

            Intent intent = new Intent(this, LessonDetailActivity.class);
            intent.putExtra("lesson_Id", "");
            intent.putExtra("section_Id", _Section_Id);
            startActivity(intent);
        } else if (v == findViewById(R.id.btnBack)) {
            finish();
        } else {
            getLessons();
        }
    }
}