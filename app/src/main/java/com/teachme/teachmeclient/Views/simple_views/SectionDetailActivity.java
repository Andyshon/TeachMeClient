package com.teachme.teachmeclient.Views.simple_views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.teachme.teachmeclient.BaseActivity.BaseActivity;
import com.teachme.teachmeclient.Entities.Section;
import com.teachme.teachmeclient.R;
import com.teachme.teachmeclient.Views.LessonsActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SectionDetailActivity extends BaseActivity implements android.view.View.OnClickListener {

    Button btnRegister;
    Button btnDelete;
    Button btnClose;
    Button btnViewLessons;
    EditText editTextName;
    private String _Section_Id;
    private String _Course_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section_detail);

        btnRegister = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnClose = (Button) findViewById(R.id.btnClose);
        btnViewLessons = (Button) findViewById(R.id.btnViewLessons);

        editTextName = (EditText) findViewById(R.id.editTextName);

        btnRegister.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        btnViewLessons.setOnClickListener(this);


        _Section_Id = "";
        _Course_Id = "";
        Intent intent = getIntent();
        _Section_Id = intent.getStringExtra("section_Id");
        _Course_Id = intent.getStringExtra("course_Id");

        if (_Section_Id != null && !_Section_Id.isEmpty()) {
            Call<Section> call = restClient.getSectionById(_Section_Id);
            call.enqueue(new Callback<Section>() {
                @Override
                public void onResponse(Call<Section> call, Response<Section> response) {
                    Section section = response.body();
                    editTextName.setText(section.getName());
                }

                @Override
                public void onFailure(Call<Section> call, Throwable t) {
                    Toast.makeText(SectionDetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }


    @Override
    public void onClick(View v) {
        if (findViewById(R.id.btnDelete) == v) {
            Call<Void> call = restClient.deleteSectionById(_Section_Id);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.code() == 204) {
                        Toast.makeText(SectionDetailActivity.this, "Section Record Deleted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(SectionDetailActivity.this, "Error: Not Deleted" + response.errorBody(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(SectionDetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            finish();
        } else if (v == findViewById(R.id.btnClose)) {
            finish();
        } else if (v == findViewById(R.id.btnViewLessons)) {
            Intent intent = new Intent(this, LessonsActivity.class);
            intent.putExtra("section_Id", _Section_Id);
            startActivity(intent);
        } else if (findViewById(R.id.btnSave) == v) {

            if (_Section_Id == null || _Section_Id.isEmpty()) {
                // No Id -> new section

                Section section = new Section();
                section.setName(editTextName.getText().toString());
                section.setCourseId(_Course_Id);

                restClient.addSection(section).enqueue(new Callback<Section>() {
                    @Override
                    public void onResponse(Call<Section> call, Response<Section> response) {
                        if (response.code() == 201) {
                            Toast.makeText(SectionDetailActivity.this, "New Section Registered.", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(SectionDetailActivity.this, "Not Created:" + response.errorBody(), Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Section> call, Throwable t) {
                        Toast.makeText(SectionDetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });


            } else {
                // Update existing section

                Call<Section> call = restClient.getSectionById(_Section_Id);
                call.enqueue(new Callback<Section>() {
                    @Override
                    public void onResponse(Call<Section> call, Response<Section> response) {
                        Section existingSection = response.body();
                        existingSection.setName(editTextName.getText().toString());

                        // Use Backend API to update section
                        restClient.updateSectionById(_Section_Id, existingSection).enqueue(new Callback<Section>() {
                            @Override
                            public void onResponse(Call<Section> call, Response<Section> response) {
                                Toast.makeText(SectionDetailActivity.this,  " updated.", Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onFailure(Call<Section> call, Throwable t) {
                                Toast.makeText(SectionDetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Section> call, Throwable t) {
                        Toast.makeText(SectionDetailActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }
}