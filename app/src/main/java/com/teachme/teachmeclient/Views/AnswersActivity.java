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
import android.widget.Toast;

import com.teachme.teachmeclient.Views.simple_views.AnswerDetailActivity;
import com.teachme.teachmeclient.BaseActivity.BaseActivity;
import com.teachme.teachmeclient.Controllers.AnswersController;
import com.teachme.teachmeclient.Interfaces.DatabaseQuery;
import com.teachme.teachmeclient.Entities.Answer;
import com.teachme.teachmeclient.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AnswersActivity extends BaseActivity implements View.OnClickListener, DatabaseQuery {

    Button btnGetAll, btnAdd, btnBack;
    TextView answer_Id;
    private String _Exercise_Id;

    private AnswersController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);

        btnGetAll = (Button) findViewById(R.id.btnGetAll);
        btnGetAll.setOnClickListener(this);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        _Exercise_Id = "";
        Intent intent = getIntent();
        _Exercise_Id = intent.getStringExtra("exercise_Id");


        controller = new AnswersController(this);
        progressBar = findViewById(R.id.spinner);
    }

    //This function will call when the screen is activate
    @Override
    public void onResume() {
        super.onResume();
        getAnswers();
    }

    @Override
    public void getAnswersByExerciseId(List<Answer> answers) {
        Toast.makeText(this, "get answers!" + answers.size(), Toast.LENGTH_SHORT).show();
    }

    private void getAnswers() {
        startSpinner();
        controller.getAnswersByExerciseId(new DatabaseQuery() {
            @Override
            public void getAnswersByExerciseId(List<Answer> answers) {
                populateAnswers(answers);
            }
        }, _Exercise_Id);
    }

    private void populateAnswers(List<Answer> answers) {
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
                answer_Id = (TextView) view.findViewById(R.id.user_Id);
                String answerId = answer_Id.getText().toString();
                Intent objIndent = new Intent(getApplicationContext(), AnswerDetailActivity.class);
                objIndent.putExtra("answer_Id", answerId);
                objIndent.putExtra("exercise_Id", _Exercise_Id);
                startActivity(objIndent);
            }
        });
        ListAdapter adapter = new SimpleAdapter(AnswersActivity.this, answerList, R.layout.view_user_entry, new String[]{"id", "name"}, new int[]{R.id.user_Id, R.id.user_name});
        lv.setAdapter(adapter);
        stopSpinner();
    }

    @Override
    public void onClick(View v) {
        if (v == findViewById(R.id.btnAdd)) {

            Intent intent = new Intent(this, AnswerDetailActivity.class);
            intent.putExtra("answer_Id", "");
            intent.putExtra("exercise_Id", _Exercise_Id);
            startActivity(intent);
        } else if (v == findViewById(R.id.btnBack)) {
            finish();
        } else {
            getAnswers();
        }
    }
}