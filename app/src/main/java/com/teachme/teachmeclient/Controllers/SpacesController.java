package com.teachme.teachmeclient.Controllers;

import android.content.Context;

import com.teachme.teachmeclient.Interfaces.DatabaseQuery;
import com.teachme.teachmeclient.Models.InnerObjectsModel;
import com.teachme.teachmeclient.Entities.Space;

import java.util.List;

/**
 * Created by andyshon on 12.02.18.
 */

public class SpacesController {
    private InnerObjectsModel model;

    public SpacesController(Context context) {
        model = new InnerObjectsModel(context);
    }

    public void getSpacesByExerciseId(DatabaseQuery callback, String _Exercise_id) {
        model.getSpacesByExerciseId(new DatabaseQuery() {
            @Override
            public void getSpacesByExerciseId(List<Space> spaces) {
                callback.getSpacesByExerciseId(spaces);
            }
        }, _Exercise_id);
    }
}
