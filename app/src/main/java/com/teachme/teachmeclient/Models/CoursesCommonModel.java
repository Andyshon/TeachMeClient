package com.teachme.teachmeclient.Models;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.teachme.teachmeclient.Interfaces.BackendService;
import com.teachme.teachmeclient.Interfaces.DatabaseQuery;
import com.teachme.teachmeclient.Entities.Course;
import com.teachme.teachmeclient.Entities.Lesson;
import com.teachme.teachmeclient.Entities.RestClient;
import com.teachme.teachmeclient.Entities.Section;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by andyshon on 17.01.18.
 */

public class CoursesCommonModel {

    private Context context;
    private BackendService restClient;

    public CoursesCommonModel(Context context) {
        this.context = context;
        restClient = RestClient.getService();
    }

    public void getCoursesByUserId(DatabaseQuery callback, String _User_id) {
        Call<List<Course>> call = restClient.getCoursesByUserId(_User_id, true);
        call.enqueue(new Callback<List<Course>>() {
                         @Override
                         public void onResponse(@NonNull Call<List<Course>> call, @NonNull Response<List<Course>> response) {

                             List<Course> courses = new ArrayList<>();

                             if (response.body() != null) {
                                 courses.addAll(response.body());
                                 callback.getCourses(courses);
                             }
                             else {
                                 Toast.makeText(context, "user has no courses", Toast.LENGTH_SHORT).show();
                             }
                         }

                         @Override
                         public void onFailure(@NonNull Call<List<Course>> call, @NonNull Throwable t) {
                             Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                         }
                     }
        );
    }

    public void getSectionsByCourseId(DatabaseQuery callback, String _Course_id) {
        Call<List<Section>> call = restClient.getSectionsByCourseId(_Course_id);
        call.enqueue(new Callback<List<Section>>() {
                         @Override
                         public void onResponse(@NonNull Call<List<Section>> call, @NonNull Response<List<Section>> response) {

                             List<Section> sections = new ArrayList<>();

                             if (response.body() != null) {
                                 sections.addAll(response.body());
                                 callback.getSections(sections);
                             }
                         }

                         @Override
                         public void onFailure(@NonNull Call<List<Section>> call, @NonNull Throwable t) {
                             Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                         }
                     }
        );
    }

    public void getLessonsBySectionId(DatabaseQuery callback, String _Section_id) {
        Call<List<Lesson>> call = restClient.getLessonsBySectionId(_Section_id);
        call.enqueue(new Callback<List<Lesson>>() {
                         @Override
                         public void onResponse(@NonNull Call<List<Lesson>> call, @NonNull Response<List<Lesson>> response) {

                             List<Lesson> lessons = new ArrayList<>();

                             if (response.body() != null) {
                                 lessons.addAll(response.body());
                                 callback.getLessons(lessons);
                             }
                         }

                         @Override
                         public void onFailure(@NonNull Call<List<Lesson>> call, @NonNull Throwable t) {
                             Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                         }
                     }
        );
    }

}
