package com.teachme.teachmeclient.Views.simple_views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.teachme.teachmeclient.BaseActivity.BaseActivity;
import com.teachme.teachmeclient.Entities.Answer;
import com.teachme.teachmeclient.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AnswerDetailActivity extends BaseActivity implements android.view.View.OnClickListener {

    Button btnRegister, btnDelete;
    Button btnClose;
    EditText editTextTitle;
    CheckBox checkBoxIsRight;
    private String _Answer_Id, _Exercise_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_detail);

        btnRegister = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClose = (Button) findViewById(R.id.btnClose);

        editTextTitle = (EditText) findViewById(R.id.editTextTitle);
        checkBoxIsRight = (CheckBox) findViewById(R.id.checkBoxIsRight);

        btnRegister.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        checkBoxIsRight.setOnClickListener(this);


        _Answer_Id = "";
        _Exercise_Id = "";
        Intent intent = getIntent();
        _Answer_Id = intent.getStringExtra("answer_Id");
        _Exercise_Id = intent.getStringExtra("exercise_Id");

        if (_Answer_Id != null && !_Answer_Id.isEmpty()) {
            Call<Answer> call = restClient.getAnswerById(_Answer_Id);
            call.enqueue(new Callback<Answer>() {
                @Override
                public void onResponse(Call<Answer> call, Response<Answer> response) {
                    Answer answer = response.body();
                    editTextTitle.setText(answer.getTitle());
                    checkBoxIsRight.setChecked(answer.getIsRight());
                    _Exercise_Id = answer.getExerciseId();
                }

                @Override
                public void onFailure(Call<Answer> call, Throwable t) {
                    Toast.makeText(AnswerDetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }


    @Override
    public void onClick(View v) {
        if (findViewById(R.id.btnDelete) == v) {
            Call<Void> call = restClient.deleteAnswerById(_Answer_Id);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.code() == 204) {
                        Toast.makeText(AnswerDetailActivity.this, "Answer Record Deleted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(AnswerDetailActivity.this, "Error: Not Deleted" + response.errorBody(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(AnswerDetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            finish();
        } else if (v == findViewById(R.id.btnClose)) {
            finish();
        } else if (findViewById(R.id.btnSave) == v) {

            if (_Answer_Id == null || _Answer_Id.isEmpty()) {
                // No Id -> new answer

                Answer answer = new Answer(editTextTitle.getText().toString(), checkBoxIsRight.isChecked());
                answer.setExerciseId(_Exercise_Id);

                restClient.addAnswer(answer).enqueue(new Callback<Answer>() {
                    @Override
                    public void onResponse(Call<Answer> call, Response<Answer> response) {
                        if (response.code() == 201) {
                            Toast.makeText(AnswerDetailActivity.this, "New Answer Registered.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(AnswerDetailActivity.this, "Not Created:" + response.errorBody(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Answer> call, Throwable t) {
                        Toast.makeText(AnswerDetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            } else {
                // Update existing answer

                Call<Answer> call = restClient.getAnswerById(_Answer_Id);
                call.enqueue(new Callback<Answer>() {
                    @Override
                    public void onResponse(Call<Answer> call, Response<Answer> response) {
                        Answer existingAnswer = response.body();
                        existingAnswer.setTitle(editTextTitle.getText().toString());
                        existingAnswer.setIsRight(checkBoxIsRight.isChecked());
                        existingAnswer.setExerciseId(_Exercise_Id);

                        // Use Backend API to update answer
                        restClient.updateAnswerById(_Answer_Id, existingAnswer).enqueue(new Callback<Answer>() {
                            @Override
                            public void onResponse(Call<Answer> call, Response<Answer> response) {
                                Toast.makeText(AnswerDetailActivity.this, " updated.", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<Answer> call, Throwable t) {
                                Toast.makeText(AnswerDetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Answer> call, Throwable t) {
                        Toast.makeText(AnswerDetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });


            }
        }
    }
}