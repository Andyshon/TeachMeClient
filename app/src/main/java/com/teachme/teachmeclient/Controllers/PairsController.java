package com.teachme.teachmeclient.Controllers;

import android.content.Context;

import com.teachme.teachmeclient.Interfaces.DatabaseQuery;
import com.teachme.teachmeclient.Models.InnerObjectsModel;
import com.teachme.teachmeclient.Entities.Pair;

import java.util.List;

/**
 * Created by andyshon on 07.02.18.
 */

public class PairsController {
    private InnerObjectsModel model;

    public PairsController(Context context) {
        model = new InnerObjectsModel(context);
    }

    public void getPairsByExerciseId(DatabaseQuery callback, String _Exercise_id) {
        model.getPairsByExerciseId(new DatabaseQuery() {
            @Override
            public void getPairsByExerciseId(List<Pair> pairs) {
                callback.getPairsByExerciseId(pairs);
            }
        }, _Exercise_id);
    }
}
