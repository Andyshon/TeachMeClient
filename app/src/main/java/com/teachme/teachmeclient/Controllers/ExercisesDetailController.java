package com.teachme.teachmeclient.Controllers;

import android.content.Context;

import com.teachme.teachmeclient.Interfaces.DatabaseQuery;
import com.teachme.teachmeclient.Entities.Exercise;
import com.teachme.teachmeclient.Models.ExercisesDetailModel;

/**
 * Created by andyshon on 18.01.18.
 */

public class ExercisesDetailController {

    private ExercisesDetailModel model;

    public ExercisesDetailController(Context context) {
        model = new ExercisesDetailModel(context);
    }

    public void getExerciseById(DatabaseQuery callback, String _Exercise_id) {
        model.getExerciseById(new DatabaseQuery() {
            @Override
            public void getExerciseByIdResult(Exercise exercise) {
                callback.getExerciseByIdResult(exercise);
            }
        }, _Exercise_id);
    }

    public void deleteExerciseById(DatabaseQuery callback, String _Exercise_id) {
        model.deleteExerciseById(new DatabaseQuery() {
            @Override
            public void getDeletedExerciseResult() {
                callback.getDeletedExerciseResult();
            }
        }, _Exercise_id);
    }

    public void addExerciseWithInnerObjects(DatabaseQuery callback, Exercise exercise) {
        model.addExerciseWithInnerObjects(new DatabaseQuery() {
            @Override
            public void getAddExerciseWithInnerObjectsResult() {
                callback.getAddExerciseWithInnerObjectsResult();
            }
        }, exercise);
    }

    public void replaceExerciseWithInnerObjectsById(DatabaseQuery callback, Exercise exercise, String _Exercise_Id) {
        model.replaceExerciseWithInnerObjectsById(new DatabaseQuery() {
            @Override
            public void getReplaceExerciseWithInnerObjectsResult() {
                callback.getReplaceExerciseWithInnerObjectsResult();
            }
        }, exercise, _Exercise_Id);
    }
}
