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
import com.teachme.teachmeclient.Controllers.SectionsController;
import com.teachme.teachmeclient.Interfaces.DatabaseQuery;
import com.teachme.teachmeclient.Entities.Answer;
import com.teachme.teachmeclient.R;
import com.teachme.teachmeclient.Views.simple_views.SectionDetailActivity;
import com.teachme.teachmeclient.Views.simple_views.AnswerDetailActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SectionsActivity extends BaseActivity implements View.OnClickListener {

    Button btnGetAll, btnAdd, btnBack;
    TextView section_Id;
    private String _Course_Id;

    private SectionsController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sections);

        btnGetAll = (Button) findViewById(R.id.btnGetAll);
        btnGetAll.setOnClickListener(this);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        _Course_Id = "";
        Intent intent = getIntent();
        _Course_Id = intent.getStringExtra("course_Id");


        controller = new SectionsController(this);
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
        controller.getSections(new DatabaseQuery() {
            @Override
            public void getAnswersByExerciseId(List<Answer> answers) {
                populateCourses(answers);
            }
        }, _Course_Id);
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
                section_Id = (TextView) view.findViewById(R.id.user_Id);
                String answerId = section_Id.getText().toString();
                Intent objIndent = new Intent(getApplicationContext(), AnswerDetailActivity.class);
                objIndent.putExtra("answer_Id", answerId);
                objIndent.putExtra("exercise_Id", _Course_Id);
                startActivity(objIndent);
            }
        });
        ListAdapter adapter = new SimpleAdapter(SectionsActivity.this, answerList, R.layout.view_user_entry, new String[]{"id", "name"}, new int[]{R.id.user_Id, R.id.user_name});
        lv.setAdapter(adapter);
        stopSpinner();
    }

    @Override
    public void onClick(View v) {
        if (v == findViewById(R.id.btnAdd)) {

            Intent intent = new Intent(this, SectionDetailActivity.class);
            intent.putExtra("section_Id", "");
            intent.putExtra("course_Id", _Course_Id);
            startActivity(intent);
        } else if (v == findViewById(R.id.btnBack)) {
            finish();
        } else {
            getCourses();
        }
    }
}