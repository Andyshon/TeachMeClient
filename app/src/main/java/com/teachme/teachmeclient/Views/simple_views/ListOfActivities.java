package com.teachme.teachmeclient.Views.simple_views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.teachme.teachmeclient.R;
import com.teachme.teachmeclient.Views.BlobManagerActivity;
import com.teachme.teachmeclient.Views.ExercisesActivity;
import com.teachme.teachmeclient.Views.MainActivity;
import com.teachme.teachmeclient.Views.TeachersActivity;

public class ListOfActivities extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_activities);

        startActivity(new Intent(this, LoginActivity.class));
    }

    public void GoToUsers(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void GoToExercises(View view) {
        Intent intent = new Intent(this, ExercisesActivity.class);
        startActivity(intent);
    }

    public void GoToCourseCreateFlow(View view) {
        Intent intent = new Intent(this, TeachersActivity.class);
        startActivity(intent);
    }

    public void GoToBlobManager(View view) {
        Intent intent = new Intent(this, BlobManagerActivity.class);
        startActivity(intent);
    }

    public void GoToLoginActivity(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void GoToAzureTestLoginActivity(View view) {
        Intent intent = new Intent(this, AzureTestLoginActivity.class);
        startActivity(intent);
    }
}
