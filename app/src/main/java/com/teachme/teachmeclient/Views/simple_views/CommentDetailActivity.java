package com.teachme.teachmeclient.Views.simple_views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.teachme.teachmeclient.BaseActivity.BaseActivity;
import com.teachme.teachmeclient.Entities.Comment;
import com.teachme.teachmeclient.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CommentDetailActivity extends BaseActivity implements android.view.View.OnClickListener {

    Button btnRegister, btnDelete;
    Button btnClose, btnViewCommentRatings;
    EditText editTextUserId;
    EditText editTextUserName;
    EditText editTextOverallRating;
    EditText editTextComment;
    private String _Comment_Id, _Exercise_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_detail);

        btnRegister = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClose = (Button) findViewById(R.id.btnClose);
        btnViewCommentRatings = (Button) findViewById(R.id.btnViewCommentRatings);

        editTextUserId = (EditText) findViewById(R.id.editTextUserId);
        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        editTextComment = (EditText) findViewById(R.id.editTextComment);
        editTextOverallRating = (EditText) findViewById(R.id.editTextOverallRating);


        btnRegister.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        btnViewCommentRatings.setOnClickListener(this);

        _Comment_Id = "";
        _Exercise_Id = "";
        Intent intent = getIntent();
        _Comment_Id = intent.getStringExtra("comment_Id");
        _Exercise_Id = intent.getStringExtra("exercise_Id");

        if (_Comment_Id != null && !_Comment_Id.isEmpty()) {
            Call<Comment> call = restClient.getCommentById(_Comment_Id);
            call.enqueue(new Callback<Comment>() {
                @Override
                public void onResponse(Call<Comment> call, Response<Comment> response) {
                    Comment comment = response.body();
                    editTextUserId.setText(comment.getUserId());
                    editTextUserName.setText(comment.getUser().getFullName());
                    editTextComment.setText(comment.getCommentText());
                    editTextOverallRating.setText(String.valueOf(comment.CalculateOverallRating()));
                    _Exercise_Id = comment.getExerciseId();
                }

                @Override
                public void onFailure(Call<Comment> call, Throwable t) {
                    Toast.makeText(CommentDetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

                }
            });
        }
    }


    @Override
    public void onClick(View v) {
        if (findViewById(R.id.btnDelete) == v) {
            Call<Void> call = restClient.deleteCommentById(_Comment_Id);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.code() == 204) {
                        Toast.makeText(CommentDetailActivity.this, "Comment Record Deleted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(CommentDetailActivity.this, "Error: Not Deleted" + response.errorBody(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(CommentDetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            finish();
        } else if (v == findViewById(R.id.btnClose)) {
            finish();
        } else if (v == findViewById(R.id.btnViewCommentRatings)) {
            Intent intent = new Intent(this, CommentRatingsActivity.class);
            intent.putExtra("comment_Id", _Comment_Id);
            startActivity(intent);
        } else if (findViewById(R.id.btnSave) == v) {

            if (_Comment_Id == null || _Comment_Id.isEmpty()) {
                // No Id -> new comment

                Comment comment = new Comment();
                comment.setUserId(editTextUserId.getText().toString());
                comment.setCommentText(editTextComment.getText().toString());
                comment.setExerciseId(_Exercise_Id);

                restClient.addComment(comment).enqueue(new Callback<Comment>() {
                    @Override
                    public void onResponse(Call<Comment> call, Response<Comment> response) {
                        if (response.code() == 201) {
                            Toast.makeText(CommentDetailActivity.this, "New Comment Registered.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(CommentDetailActivity.this, "Not Created:" + response.errorBody(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Comment> call, Throwable t) {
                        Toast.makeText(CommentDetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            } else {
                // Update existing comment

                Call<Comment> call = restClient.getCommentById(_Comment_Id);
                call.enqueue(new Callback<Comment>() {
                    @Override
                    public void onResponse(Call<Comment> call, Response<Comment> response) {
                        Comment existingComment = response.body();
                        existingComment.setUserId(editTextUserId.getText().toString());
                        existingComment.setCommentText(editTextComment.getText().toString());
                        existingComment.setExerciseId(_Exercise_Id);

                        // Use Backend API to update comment
                        restClient.updateCommentById(_Comment_Id, existingComment).enqueue(new Callback<Comment>() {
                            @Override
                            public void onResponse(Call<Comment> call, Response<Comment> response) {
                                Toast.makeText(CommentDetailActivity.this, " updated.", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<Comment> call, Throwable t) {
                                Toast.makeText(CommentDetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Comment> call, Throwable t) {
                        Toast.makeText(CommentDetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });


            }
        }
    }
}