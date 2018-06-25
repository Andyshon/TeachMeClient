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
import com.teachme.teachmeclient.Controllers.SpacesController;
import com.teachme.teachmeclient.Interfaces.DatabaseQuery;
import com.teachme.teachmeclient.R;
import com.teachme.teachmeclient.Entities.Space;
import com.teachme.teachmeclient.Views.simple_views.AnswerDetailActivity;
import com.teachme.teachmeclient.Views.simple_views.SpaceDetailActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SpacesActivity extends BaseActivity implements View.OnClickListener {

    Button btnGetAll, btnAdd, btnBack;
    TextView space_Id;
    private String _Exercise_Id;

    private SpacesController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spaces);

        btnGetAll = (Button) findViewById(R.id.btnGetAll);
        btnGetAll.setOnClickListener(this);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        _Exercise_Id = "";
        Intent intent = getIntent();
        _Exercise_Id = intent.getStringExtra("exercise_Id");


        controller = new SpacesController(this);
        progressBar = findViewById(R.id.spinner);
    }

    //This function will call when the screen is activate
    @Override
    public void onResume() {
        super.onResume();
        getSpaces();
    }

    private void getSpaces() {
        startSpinner();
        controller.getSpacesByExerciseId(new DatabaseQuery() {
            @Override
            public void getSpacesByExerciseId(List<Space> spaces) {
                populateSpaces(spaces);
            }
        }, _Exercise_Id);
    }

    private void populateSpaces(List<Space> spaces) {
        ListView lv = (ListView) findViewById(R.id.listView);

        ArrayList<HashMap<String, String>> spaceList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < spaces.size(); i++) {
            HashMap<String, String> space = new HashMap<String, String>();
            space.put("id", spaces.get(i).getId());
            space.put("name", spaces.get(i).getValue());
            spaceList.add(space);
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                space_Id = (TextView) view.findViewById(R.id.user_Id);
                String answerId = space_Id.getText().toString();
                Intent objIndent = new Intent(getApplicationContext(), AnswerDetailActivity.class);
                objIndent.putExtra("answer_Id", answerId);
                objIndent.putExtra("exercise_Id", _Exercise_Id);
                startActivity(objIndent);
            }
        });
        ListAdapter adapter = new SimpleAdapter(SpacesActivity.this, spaceList, R.layout.view_user_entry, new String[]{"id", "name"}, new int[]{R.id.user_Id, R.id.user_name});
        lv.setAdapter(adapter);
        stopSpinner();
    }

    @Override
    public void onClick(View v) {
        if (v == findViewById(R.id.btnAdd)) {

            Intent intent = new Intent(this, SpaceDetailActivity.class);
            intent.putExtra("space_Id", "");
            intent.putExtra("exercise_Id", _Exercise_Id);
            startActivity(intent);
        } else if (v == findViewById(R.id.btnBack)) {
            finish();
        } else {
            getSpaces();
        }
    }
}