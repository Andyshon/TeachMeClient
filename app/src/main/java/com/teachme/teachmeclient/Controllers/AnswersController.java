package com.teachme.teachmeclient.Controllers;

import android.content.Context;

import com.teachme.teachmeclient.Interfaces.DatabaseQuery;
import com.teachme.teachmeclient.Entities.Answer;
import com.teachme.teachmeclient.Models.InnerObjectsModel;

import java.util.List;

/**
 * Created by andyshon on 12.02.18.
 */

public class AnswersController {
    private InnerObjectsModel model;

    public AnswersController(Context context) {
        model = new InnerObjectsModel(context);
    }

    public void getAnswersByExerciseId(DatabaseQuery callback, String _Exercise_id) {
        model.getAnswersByExerciseId(new DatabaseQuery() {
            @Override
            public void getAnswersByExerciseId(List<Answer> answers) {
                callback.getAnswersByExerciseId(answers);
            }
        }, _Exercise_id);
    }
}
