package com.teachme.teachmeclient.Controllers;

import android.content.Context;

import com.teachme.teachmeclient.Interfaces.DatabaseQuery;
import com.teachme.teachmeclient.Entities.Course;
import com.teachme.teachmeclient.Models.CoursesCommonModel;

import java.util.List;

/**
 * Created by andyshon on 17.01.18.
 */

public class CoursesController {
    private CoursesCommonModel model;

    public CoursesController(Context context) {
        model = new CoursesCommonModel(context);
    }

    public void getCourses(DatabaseQuery callback, String _User_id) {
        model.getCoursesByUserId(new DatabaseQuery() {
            @Override
            public void getCourses(List<Course> courses) {
                callback.getCourses(courses);
            }
        }, _User_id);
    }
}
