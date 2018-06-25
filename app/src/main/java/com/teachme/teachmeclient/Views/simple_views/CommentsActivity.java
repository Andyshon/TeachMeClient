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
import com.teachme.teachmeclient.Entities.Comment;
import com.teachme.teachmeclient.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Call;
import retrofit2.Response;


public class CommentsActivity extends BaseActivity implements android.view.View.OnClickListener {

    Button btnGetAll, btnAdd, btnBack;
    TextView comment_Id;
    private String _Exercise_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

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

            Intent intent = new Intent(this, CommentDetailActivity.class);
            intent.putExtra("comment_Id", "");
            intent.putExtra("exercise_Id", _Exercise_Id);
            startActivity(intent);
        } else if (v == findViewById(R.id.btnBack)) {
            finish();
        } else {
            refreshScreen();
        }
    }

    private String buildQueryExerciseId(String param) {
        String query = String.format("exerciseId eq '%s'", param);
        return query;
    }

    private void refreshScreen() {
        Call<List<Comment>> call = restClient.getCommentByQuery(buildQueryExerciseId(_Exercise_Id));
        call.enqueue(new Callback<List<Comment>>() {
                         @Override
                         public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                             ListView lv = (ListView) findViewById(R.id.listView);

                             ArrayList<HashMap<String, String>> commentList = new ArrayList<HashMap<String, String>>();

                             if (response.body().size() == 0) {
                                 Toast.makeText(CommentsActivity.this, "that exercise has no comments", Toast.LENGTH_SHORT).show();
                             }
                             else {
                                 for (int i = 0; i < response.body().size(); i++) {
                                     HashMap<String, String> comment = new HashMap<String, String>();
                                     comment.put("id", String.valueOf(response.body().get(i).getId()));
                                     comment.put("name", String.valueOf(response.body().get(i).getCommentText()));
                                     commentList.add(comment);
                                 }

                                 lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                     @Override
                                     public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                         comment_Id = (TextView) view.findViewById(R.id.user_Id);
                                         String commentId = comment_Id.getText().toString();
                                         Intent objIndent = new Intent(getApplicationContext(), CommentDetailActivity.class);
                                         objIndent.putExtra("comment_Id", commentId);
                                         objIndent.putExtra("exercise_Id", _Exercise_Id);
                                         startActivity(objIndent);
                                     }
                                 });
                                 ListAdapter adapter = new SimpleAdapter(CommentsActivity.this, commentList, R.layout.view_user_entry, new String[]{"id", "name"}, new int[]{R.id.user_Id, R.id.user_name});
                                 lv.setAdapter(adapter);
                             }
                         }

                         @Override
                         public void onFailure(Call<List<Comment>> call, Throwable t) {
                             Toast.makeText(CommentsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                         }
                     }
        );
    }
}