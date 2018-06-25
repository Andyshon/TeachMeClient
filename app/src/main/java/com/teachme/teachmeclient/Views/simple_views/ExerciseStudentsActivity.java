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
import com.teachme.teachmeclient.Entities.ExerciseStudent;
import com.teachme.teachmeclient.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Call;
import retrofit2.Response;


public class ExerciseStudentsActivity extends BaseActivity implements android.view.View.OnClickListener {

    Button btnGetAll, btnAdd, btnBack;
    TextView exercisestudent_Id;
    private String _Exercise_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_students);

        btnGetAll = (Button) findViewById(R.id.btnGetAll);
        btnGetAll.setOnClickListener(this);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        _Exercise_Id = "";
        Intent intent = getIntent();
        _Exercise_Id = intent.getStringExtra("exercise_Id");
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

            Intent intent = new Intent(this, ExerciseStudentDetailActivity.class);
            intent.putExtra("exercisestudent_Id", "");
            intent.putExtra("exercise_Id", _Exercise_Id);
            startActivity(intent);
        } else if (v == findViewById(R.id.btnBack)) {
            finish();
        } else {
            refreshScreen();
        }
    }


    private void refreshScreen() {
        Call<List<ExerciseStudent>> call = restClient.getExerciseStudentsByExerciseId(_Exercise_Id);
        call.enqueue(new Callback<List<ExerciseStudent>>() {
                         @Override
                         public void onResponse(Call<List<ExerciseStudent>> call, Response<List<ExerciseStudent>> response) {
                             ListView lv = (ListView) findViewById(R.id.listView);

                             ArrayList<HashMap<String, String>> exercisestudentList = new ArrayList<HashMap<String, String>>();

                             for (int i = 0; i < response.body().size(); i++) {
                                 HashMap<String, String> exercisestudent = new HashMap<String, String>();
                                 exercisestudent.put("id", String.valueOf(response.body().get(i).getId()));
                                 exercisestudent.put("name", String.valueOf(response.body().get(i).getUserId()));
                                 exercisestudentList.add(exercisestudent);
                             }

                             lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                 @Override
                                 public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                     exercisestudent_Id = (TextView) view.findViewById(R.id.user_Id);
                                     String exercisestudentId = exercisestudent_Id.getText().toString();
                                     Intent objIndent = new Intent(getApplicationContext(), ExerciseStudentDetailActivity.class);
                                     objIndent.putExtra("exercisestudent_Id", exercisestudentId);
                                     objIndent.putExtra("exercise_Id", _Exercise_Id);
                                     startActivity(objIndent);
                                 }
                             });
                             ListAdapter adapter = new SimpleAdapter(ExerciseStudentsActivity.this, exercisestudentList, R.layout.view_user_entry, new String[]{"id", "name"}, new int[]{R.id.user_Id, R.id.user_name});
                             lv.setAdapter(adapter);
                         }

                         @Override
                         public void onFailure(Call<List<ExerciseStudent>> call, Throwable t) {
                             Toast.makeText(ExerciseStudentsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                         }
                     }
        );
    }
}