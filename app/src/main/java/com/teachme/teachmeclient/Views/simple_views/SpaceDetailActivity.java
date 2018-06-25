package com.teachme.teachmeclient.Views.simple_views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.teachme.teachmeclient.BaseActivity.BaseActivity;
import com.teachme.teachmeclient.R;
import com.teachme.teachmeclient.Entities.Space;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SpaceDetailActivity extends BaseActivity implements android.view.View.OnClickListener {

    Button btnRegister, btnDelete;
    Button btnClose;
    EditText editTextValue;
    EditText editTextEqual;
    private String _Space_Id, _Exercise_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_space_detail);

        btnRegister = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClose = (Button) findViewById(R.id.btnClose);

        editTextValue = (EditText) findViewById(R.id.editTextValue);
        editTextEqual = (EditText) findViewById(R.id.editTextEqual);

        btnRegister.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);


        _Space_Id = "";
        _Exercise_Id = "";
        Intent intent = getIntent();
        _Space_Id = intent.getStringExtra("space_Id");
        _Exercise_Id = intent.getStringExtra("exercise_Id");

        if (_Space_Id != null && !_Space_Id.isEmpty()) {
            Call<Space> call = restClient.getSpaceById(_Space_Id);
            call.enqueue(new Callback<Space>() {
                @Override
                public void onResponse(Call<Space> call, Response<Space> response) {
                    Space space = response.body();
                    editTextValue.setText(space.getValue());
                    _Exercise_Id = space.getExerciseId();
                }

                @Override
                public void onFailure(Call<Space> call, Throwable t) {
                    Toast.makeText(SpaceDetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();

                }
            });
        }
    }


    @Override
    public void onClick(View v) {
        if (findViewById(R.id.btnDelete) == v) {
            Call<Void> call = restClient.deleteSpaceById(_Space_Id);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.code() == 204) {
                        Toast.makeText(SpaceDetailActivity.this, "Space Record Deleted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(SpaceDetailActivity.this, "Error: Not Deleted" + response.errorBody(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(SpaceDetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            finish();
        } else if (v == findViewById(R.id.btnClose)) {
            finish();
        } else if (findViewById(R.id.btnSave) == v) {

            if (_Space_Id == null || _Space_Id.isEmpty()) {
                // No Id -> new space

                Space space = new Space(editTextValue.getText().toString());
                space.setExerciseId(_Exercise_Id);

                restClient.addSpace(space).enqueue(new Callback<Space>() {
                    @Override
                    public void onResponse(Call<Space> call, Response<Space> response) {
                        if (response.code() == 201) {
                            Toast.makeText(SpaceDetailActivity.this, "New Space Registered.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SpaceDetailActivity.this, "Not Created:" + response.errorBody(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Space> call, Throwable t) {
                        Toast.makeText(SpaceDetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            } else {
                // Update existing space

                Call<Space> call = restClient.getSpaceById(_Space_Id);
                call.enqueue(new Callback<Space>() {
                    @Override
                    public void onResponse(Call<Space> call, Response<Space> response) {
                        Space existingSpace = response.body();
                        existingSpace.setValue(editTextValue.getText().toString());
                        existingSpace.setExerciseId(_Exercise_Id);

                        // Use Backend API to update space
                        restClient.updateSpaceById(_Space_Id, existingSpace).enqueue(new Callback<Space>() {
                            @Override
                            public void onResponse(Call<Space> call, Response<Space> response) {
                                Toast.makeText(SpaceDetailActivity.this, " updated.", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<Space> call, Throwable t) {
                                Toast.makeText(SpaceDetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Space> call, Throwable t) {
                        Toast.makeText(SpaceDetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }
}