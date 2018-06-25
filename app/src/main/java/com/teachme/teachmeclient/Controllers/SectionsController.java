package com.teachme.teachmeclient.Controllers;

import android.content.Context;

import com.teachme.teachmeclient.Interfaces.DatabaseQuery;
import com.teachme.teachmeclient.Entities.Section;
import com.teachme.teachmeclient.Models.CoursesCommonModel;

import java.util.List;

/**
 * Created by andyshon on 17.01.18.
 */

public class SectionsController {
    private CoursesCommonModel model;

    public SectionsController(Context context) {
        model = new CoursesCommonModel(context);
    }

    public void getSections(DatabaseQuery callback, String _Course_id) {
        model.getSectionsByCourseId(new DatabaseQuery() {
            @Override
            public void getSections(List<Section> sections) {
                callback.getSections(sections);
            }
        }, _Course_id);
    }
}
