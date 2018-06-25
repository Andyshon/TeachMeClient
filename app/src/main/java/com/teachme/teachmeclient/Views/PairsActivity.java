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
import com.teachme.teachmeclient.Controllers.PairsController;
import com.teachme.teachmeclient.Interfaces.DatabaseQuery;
import com.teachme.teachmeclient.Entities.Pair;
import com.teachme.teachmeclient.R;
import com.teachme.teachmeclient.Views.simple_views.AnswerDetailActivity;
import com.teachme.teachmeclient.Views.simple_views.PairDetailActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PairsActivity extends BaseActivity implements View.OnClickListener {

    Button btnGetAll, btnAdd, btnBack;
    TextView pair_Id;
    private String _Exercise_Id;

    private PairsController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pairs);

        btnGetAll = (Button) findViewById(R.id.btnGetAll);
        btnGetAll.setOnClickListener(this);

        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);

        _Exercise_Id = "";
        Intent intent = getIntent();
        _Exercise_Id = intent.getStringExtra("exercise_Id");


        controller = new PairsController(this);
        progressBar = findViewById(R.id.spinner);
    }

    //This function will call when the screen is activate
    @Override
    public void onResume() {
        super.onResume();
        getPairs();
    }

    private void getPairs() {
        startSpinner();
        controller.getPairsByExerciseId(new DatabaseQuery() {
            @Override
            public void getPairsByExerciseId(List<Pair> pairs) {
                populatePairs(pairs);
            }
        }, _Exercise_Id);
    }

    private void populatePairs(List<Pair> pairs) {
        ListView lv = (ListView) findViewById(R.id.listView);

        ArrayList<HashMap<String, String>> pairList = new ArrayList<HashMap<String, String>>();

        for (int i = 0; i < pairs.size(); i++) {
            HashMap<String, String> pair = new HashMap<String, String>();
            pair.put("id", pairs.get(i).getId());
            pair.put("name", pairs.get(i).getValue());
            pairList.add(pair);
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pair_Id = (TextView) view.findViewById(R.id.user_Id);
                String answerId = pair_Id.getText().toString();
                Intent objIndent = new Intent(getApplicationContext(), AnswerDetailActivity.class);
                objIndent.putExtra("answer_Id", answerId);
                objIndent.putExtra("exercise_Id", _Exercise_Id);
                startActivity(objIndent);
            }
        });
        ListAdapter adapter = new SimpleAdapter(PairsActivity.this, pairList, R.layout.view_user_entry, new String[]{"id", "name"}, new int[]{R.id.user_Id, R.id.user_name});
        lv.setAdapter(adapter);
        stopSpinner();
    }

    @Override
    public void onClick(View v) {
        if (v == findViewById(R.id.btnAdd)) {

            Intent intent = new Intent(this, PairDetailActivity.class);
            intent.putExtra("pair_Id", "");
            intent.putExtra("exercise_Id", _Exercise_Id);
            startActivity(intent);
        } else if (v == findViewById(R.id.btnBack)) {
            finish();
        } else {
            getPairs();
        }
    }
}