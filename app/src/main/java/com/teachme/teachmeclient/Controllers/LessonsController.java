package com.teachme.teachmeclient.Controllers;

import android.content.Context;

import com.teachme.teachmeclient.Interfaces.DatabaseQuery;
import com.teachme.teachmeclient.Entities.Lesson;
import com.teachme.teachmeclient.Models.CoursesCommonModel;

import java.util.List;

/**
 * Created by andyshon on 18.01.18.
 */

public class LessonsController {
    private CoursesCommonModel model;

    public LessonsController(Context context) {
        model = new CoursesCommonModel(context);
    }

    public void getLessons(DatabaseQuery callback, String _Section_id) {
        model.getLessonsBySectionId(new DatabaseQuery() {
            @Override
            public void getLessons(List<Lesson> lessons) {
                callback.getLessons(lessons);
            }
        }, _Section_id);
    }
}
