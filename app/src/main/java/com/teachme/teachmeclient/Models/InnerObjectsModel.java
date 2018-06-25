package com.teachme.teachmeclient.Models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.teachme.teachmeclient.Interfaces.BackendService;
import com.teachme.teachmeclient.Interfaces.DatabaseQuery;
import com.teachme.teachmeclient.Entities.Answer;
import com.teachme.teachmeclient.Entities.Pair;
import com.teachme.teachmeclient.Entities.RestClient;
import com.teachme.teachmeclient.Entities.Space;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by andyshon on 12.02.18.
 */

public class InnerObjectsModel {
    private Context context;
    private BackendService restClient;

    public InnerObjectsModel(Context context) {
        this.context = context;

        restClient = RestClient.getService();
    }

    public void getAnswersByExerciseId(DatabaseQuery callback, String _Exercise_id) {
        Call<List<Answer>> call = restClient.getAnswersByExerciseId(_Exercise_id);
        call.enqueue(new Callback<List<Answer>>() {
                         @Override
                         public void onResponse(@NonNull Call<List<Answer>> call, @NonNull Response<List<Answer>> response) {

                             List<Answer> answers = new ArrayList<>();

                             if (response.body() != null) {
                                 for (int i = 0; i < response.body().size(); i++) {
                                     Answer answer = new Answer(String.valueOf(response.body().get(i).getTitle().trim()), response.body().get(i).getIsRight());
                                     answers.add(answer);
                                 }
                                 callback.getAnswersByExerciseId(answers);
                             }
                             else{
                                 Toast.makeText(context, "no answers", Toast.LENGTH_SHORT).show();
                             }
                         }

                         @Override
                         public void onFailure(@NonNull Call<List<Answer>> call, @NonNull Throwable t) {
                             Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                         }
                     }
        );
    }

    public void getPairsByExerciseId(DatabaseQuery callback, String _Exercise_id) {
        Call<List<Pair>> call = restClient.getPairsByExerciseId(_Exercise_id);
        call.enqueue(new Callback<List<Pair>>() {
                         @Override
                         public void onResponse(@NonNull Call<List<Pair>> call, @NonNull Response<List<Pair>> response) {

                             List<Pair> pairs = new ArrayList<>();

                             if (response.body() != null) {
                                 for (int i = 0; i < response.body().size(); i++) {
                                     Pair pair = new Pair(String.valueOf(response.body().get(i).getValue()), String.valueOf(response.body().get(i).getEqual()));
                                     pairs.add(pair);
                                 }
                                 callback.getPairsByExerciseId(pairs);
                             }
                             else{
                                 Toast.makeText(context, "no pairs", Toast.LENGTH_SHORT).show();
                             }
                         }

                         @Override
                         public void onFailure(@NonNull Call<List<Pair>> call, @NonNull Throwable t) {
                             Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                         }
                     }
        );
    }

    public void getSpacesByExerciseId(DatabaseQuery callback, String _Exercise_id) {
        Call<List<Space>> call = restClient.getSpacesByExerciseId(_Exercise_id);
        call.enqueue(new Callback<List<Space>>() {
                         @Override
                         public void onResponse(@NonNull Call<List<Space>> call, @NonNull Response<List<Space>> response) {

                             List<Space> spaces = new ArrayList<>();

                             if (response.body() != null) {
                                 for (int i = 0; i < response.body().size(); i++) {
                                     Space space = new Space(String.valueOf(response.body().get(i).getValue()));
                                     spaces.add(space);
                                 }
                                 callback.getSpacesByExerciseId(spaces);
                             }
                             else {
                                 Toast.makeText(context, "no spaces", Toast.LENGTH_SHORT).show();
                             }
                         }

                         @Override
                         public void onFailure(@NonNull Call<List<Space>> call, @NonNull Throwable t) {
                             Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                         }
                     }
        );
    }
}
