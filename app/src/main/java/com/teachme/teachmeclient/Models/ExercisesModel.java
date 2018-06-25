package com.teachme.teachmeclient.Models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.teachme.teachmeclient.Interfaces.BackendService;
import com.teachme.teachmeclient.Interfaces.DatabaseQuery;
import com.teachme.teachmeclient.Entities.Exercise;
import com.teachme.teachmeclient.Entities.RestClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by andyshon on 10.12.17.
 */

public class ExercisesModel {

    public Context context;
    private BackendService restClient;

    public ExercisesModel(Context context) {
        this.context = context;
        restClient = RestClient.getService();
    }

    public void getExercises(DatabaseQuery callback) {
        // todo: implement real logic to get exercises from server

        Call<List<Exercise>> call = restClient.getExercise();
        call.enqueue(new Callback<List<Exercise>>() {
                         @Override
                         public void onResponse(@NonNull Call<List<Exercise>> call, @NonNull Response<List<Exercise>> response) {

                             if (response.body() != null) {
                                 List<Exercise> list = new ArrayList<>();
                                 for (int i = 0; i < response.body().size(); i++) {
                                     Exercise exercise = new Exercise();
                                     exercise.setId(String.valueOf(response.body().get(i).getId()));
                                     exercise.setName(String.valueOf(response.body().get(i).getName()));
                                     list.add(exercise);
                                 }
                                 callback.getExercisesResult(list);
                             }
                             else {
                                 Toast.makeText(context, "no exercises", Toast.LENGTH_SHORT).show();
                             }
                         }

                         @Override
                         public void onFailure(@NonNull Call<List<Exercise>> call, @NonNull Throwable t) {
                             Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                         }
                     }
        );
    }
}
