package com.teachme.teachmeclient.Views;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.teachme.teachmeclient.BaseActivity.BaseActivity;
import com.teachme.teachmeclient.Interfaces.DatabaseQuery;
import com.teachme.teachmeclient.Entities.Exercise;
import com.teachme.teachmeclient.Controllers.ExercisesController;
import com.teachme.teachmeclient.R;

import java.util.List;

public class ExercisesActivity extends BaseActivity implements android.view.View.OnClickListener {

    private String _Lesson_Id;
    private ExercisesController controller;

    //This method will call when the screen is first created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);

        Button btnGetAll = (Button) findViewById(R.id.btnGetAll);
        btnGetAll.setOnClickListener(this);
        Button btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        Button btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        _Lesson_Id = "";
        Intent intent = getIntent();
        _Lesson_Id = intent.getStringExtra("lesson_Id");

        controller = new ExercisesController(this);
        progressBar = findViewById(R.id.spinner);
    }

    //This method will call when the screen is activate
    @Override
    protected void onResume() {
        super.onResume();
        getExercises();
    }

    private void getExercises() {
        startSpinner();
        controller.getExercises(new DatabaseQuery() {
            @Override
            public void getExercisesResult(List<Exercise> exercisesList) {
                System.out.println("exercisesList.size:" + exercisesList.size());
                populateExercises(exercisesList);
            }
        });
    }

    private void populateExercises(List<Exercise> exercises) {
        // todo: implement the display of the data

        controller.clear();
        for (Exercise items : exercises){
            controller.add(items);
        }
        stopSpinner();

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String exerciseId = (((TextView) view.findViewById(R.id.user_id)).getText().toString());
                Intent objIndent = new Intent(getApplicationContext(), ExerciseDetailActivity.class);
                objIndent.putExtra("exercise_Id", exerciseId);
                objIndent.putExtra("lesson_Id", _Lesson_Id);
                startActivity(objIndent);
            }
        });

        listView.setAdapter(controller);
    }

    @Override
    public void onClick(View v) {
        if (v == findViewById(R.id.btnAdd)) {
            Intent intent = new Intent(this, ExerciseDetailActivity.class);
            intent.putExtra("exercise_Id", "");
            intent.putExtra("lesson_Id", _Lesson_Id);
            startActivity(intent);
        } else if (v == findViewById(R.id.btnBack)) {
            finish();
        } else {
            getExercises();
        }
    }
}