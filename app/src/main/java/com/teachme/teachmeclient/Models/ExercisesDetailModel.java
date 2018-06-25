package com.teachme.teachmeclient.Models;

import android.content.Context;
import android.widget.Toast;

import com.teachme.teachmeclient.Interfaces.BackendService;
import com.teachme.teachmeclient.Interfaces.DatabaseQuery;
import com.teachme.teachmeclient.Entities.Answer;
import com.teachme.teachmeclient.Entities.Exercise;
import com.teachme.teachmeclient.Entities.Pair;
import com.teachme.teachmeclient.Entities.RestClient;
import com.teachme.teachmeclient.Entities.Space;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by andyshon on 10.12.17.
 */

public class ExercisesDetailModel {

    private Context context;
    private BackendService restClient;

    public ExercisesDetailModel(Context context) {
        this.context = context;
        restClient = RestClient.getService();
    }

    public void getExerciseById(DatabaseQuery callback, String _Exercise_id) {
        Call<Exercise> call = restClient.getExerciseById(_Exercise_id);
        call.enqueue(new Callback<Exercise>() {
            @Override
            public void onResponse(Call<Exercise> call, Response<Exercise> response) {
                if (response.isSuccessful()) {
                    Exercise exercise = response.body();
                    callback.getExerciseByIdResult(exercise);
                }
            }

            @Override
            public void onFailure(Call<Exercise> call, Throwable t) {
                Toast.makeText(context, "ooops, no details!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteExerciseById(DatabaseQuery callback, String _Exercise_id) {
        Call<Void> call = restClient.deleteExerciseById(_Exercise_id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Exercise deleted", Toast.LENGTH_LONG).show();
                    callback.getDeletedExerciseResult();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void addExerciseWithInnerObjects(DatabaseQuery callback, Exercise exercise) {
        restClient.addExerciseWithInnerObjects(exercise).enqueue(new Callback<Exercise>() {
            @Override
            public void onResponse(Call<Exercise> call, Response<Exercise> response) {
                if (response.code() == 201) {
                    Toast.makeText(context, "New Exercise Added.", Toast.LENGTH_LONG).show();
                    Exercise exercise1 = response.body();
                    System.out.println("added exercise:" + exercise1.getName() + ":" + exercise1.getAnswer());
                    callback.getAddExerciseWithInnerObjectsResult();
                } else {
                    Toast.makeText(context, "Not Created:" + response.errorBody(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Exercise> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void replaceExerciseWithInnerObjectsById(DatabaseQuery callback, Exercise exercise, String _Exercise_Id) {
        Call<Exercise> call = restClient.getExerciseById(_Exercise_Id);
        call.enqueue(new Callback<Exercise>() {
            @Override
            public void onResponse(Call<Exercise> call, Response<Exercise> response) {
                if (response.isSuccessful()) {
                    Exercise existingExercise = response.body();
                    existingExercise.setText(exercise.getText());
                    existingExercise.setType(exercise.getType());
                    existingExercise.setName(exercise.getName());
                    existingExercise.setLessonId(exercise.getLessonId());
                    existingExercise.setQuestion(exercise.getQuestion());
                    existingExercise.setAnswer(exercise.getAnswer());

                    List<Pair> pairs = Arrays.asList(new Pair("after update value", "after update equal"));
                    existingExercise.setPairs(pairs);

                    List<Answer> answers = Arrays.asList(new Answer("after update title", true));
                    existingExercise.setAnswers(answers);

                    List<Space> spaces = Arrays.asList(new Space("after update value"));
                    existingExercise.setSpaces(spaces);


                    Call<Exercise> call1 = restClient.replaceExerciseWithInnerObjectsById(_Exercise_Id, existingExercise);
                    call1.enqueue(new Callback<Exercise>() {
                        @Override
                        public void onResponse(Call<Exercise> call, Response<Exercise> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(context, "exercise updated", Toast.LENGTH_LONG).show();
                                callback.getReplaceExerciseWithInnerObjectsResult();
                            }
                        }

                        @Override
                        public void onFailure(Call<Exercise> call, Throwable t) {
                            Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Exercise> call, Throwable t) {
                Toast.makeText(context, "not updated", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
