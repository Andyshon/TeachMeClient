package com.teachme.teachmeclient.Interfaces;

import android.os.Handler;

import com.teachme.teachmeclient.Entities.Answer;
import com.teachme.teachmeclient.Entities.Course;
import com.teachme.teachmeclient.Entities.Exercise;
import com.teachme.teachmeclient.Entities.Lesson;
import com.teachme.teachmeclient.Entities.Pair;
import com.teachme.teachmeclient.Entities.Section;
import com.teachme.teachmeclient.Entities.Space;
import com.teachme.teachmeclient.Entities.User;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by andyshon on 17.01.18.
 */

public interface DatabaseQuery {
    default void getExercisesResult(List<Exercise> exercises){}
    default void getExerciseByIdResult(Exercise exercise){}
    default void getDeletedExerciseResult(){}
    default void getAddExerciseWithInnerObjectsResult(){}
    default void getReplaceExerciseWithInnerObjectsResult(){}
    default void getUsersResult(List<User> users){}
    default void getUserResult(User user){}
    default void getDeletedUserResult(){}
    default void getAddUserResult(){}
    default void getUpdateUserByIdResult(){}

    default void getListOfImagesResult(String[] images, Handler handler){}
    default void getImageResult(ByteArrayOutputStream imageStream, Handler handler){}

    default void getAnswersByExerciseId(List<Answer> answers){}
    default void getPairsByExerciseId(List<Pair> pairs){}
    default void getSpacesByExerciseId(List<Space> spaces){}

    default void getCourses(List<Course> courses){}
    default void getSections(List<Section> sections){}
    default void getLessons(List<Lesson> lessons){}
}
