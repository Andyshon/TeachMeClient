package com.teachme.teachmeclient.Interfaces;

import com.teachme.teachmeclient.Entities.Answer;
import com.teachme.teachmeclient.Entities.Comment;
import com.teachme.teachmeclient.Entities.CommentRating;
import com.teachme.teachmeclient.Entities.Course;
import com.teachme.teachmeclient.Entities.CourseProgress;
import com.teachme.teachmeclient.Entities.Exercise;
import com.teachme.teachmeclient.Entities.ExerciseStudent;
import com.teachme.teachmeclient.Entities.Lesson;
import com.teachme.teachmeclient.Entities.LoginResult;
import com.teachme.teachmeclient.Entities.LoginViewModel;
import com.teachme.teachmeclient.Entities.Pair;
import com.teachme.teachmeclient.Entities.Section;
import com.teachme.teachmeclient.Entities.Space;
import com.teachme.teachmeclient.Entities.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by andyshon on 04.12.2017.
 */

public interface BackendService {

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/student
    @GET("api/v1.0/users")
    Call<List<User>> getUser();

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/user/21a1573d-9862-40b1-a179-767fbfc57a8f
    //Get user record base on ID
    @GET("api/v1.0/users/{id}")
    Call<User> getUserById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/user
    //Add user record and post content in HTTP request BODY
    @POST("api/v1.0/users")
    Call<User> addUser(@Body User user);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/user/21a1573d-9862-40b1-a179-767fbfc57a8f
    //Delete user record base on ID
    @DELETE("api/v1.0/users/{id}")
    Call<Void> deleteUserById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/user/21a1573d-9862-40b1-a179-767fbfc57a8f
    //Update user record with PATCH (only delta is updated) and post content in HTTP request BODY
    @PATCH("api/v1.0/users/{id}")
    Call<User> updateUserById(@Path("id") String id, @Body User user);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/exercise
    @GET("api/v1.0/exercises")
    Call<List<Exercise>> getExercise();

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/exercise/21a1573d-9862-40b1-a179-767fbfc57a8f
    //Get exercise record base on ID
    @GET("api/v1.0/exercises/{id}")
    Call<Exercise> getExerciseById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/exercise
    //Add exercise record and post content in HTTP request BODY
    @POST("api/v1.0/exercises")
    Call<Exercise> addExercise(@Body Exercise exercise);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/exercise/21a1573d-9862-40b1-a179-767fbfc57a8f
    //Delete exercise record base on ID
    @DELETE("api/v1.0/exercises/{id}")
    Call<Void> deleteExerciseById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/exercise/21a1573d-9862-40b1-a179-767fbfc57a8f
    //Update exercise record with PATCH (only delta is updated) and post content in HTTP request BODY
    @PATCH("api/v1.0/exercises/{id}")
    Call<Exercise> updateExerciseById(@Path("id") String id, @Body Exercise exercise);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/course
    @GET("api/v1.0/course")
    Call<List<Course>> getCourse();

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/course/b04070c377c24b7295fda8ec8484dca5
    //Get course record base on ID
    @GET("api/v1.0/course/{id}")
    Call<Course> getCourseById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/course
    //Add course record and post content in HTTP request BODY
    @POST("api/v1.0/course")
    Call<Course> addCourse(@Body Course course);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/course/b04070c377c24b7295fda8ec8484dca5
    //Update course record with PATCH (only delta is updated) and post content in HTTP request BODY
    @PATCH("api/v1.0/course/{id}")
    Call<Course> updateCourseById(@Path("id") String id, @Body Course course);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/section
    @GET("api/v1.0/section")
    Call<List<Section>> getSection();

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/section/b04070c377c24b7295fda8ec8484dca5
    //Get section record base on ID
    @GET("api/v1.0/section/{id}")
    Call<Section> getSectionById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/section
    //Add section record and post content in HTTP request BODY
    @POST("api/v1.0/section")
    Call<Section> addSection(@Body Section section);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/section/b04070c377c24b7295fda8ec8484dca5
    //Update section record with PATCH (only delta is updated) and post content in HTTP request BODY
    @PATCH("api/v1.0/section/{id}")
    Call<Section> updateSectionById(@Path("id") String id, @Body Section section);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/lesson
    @GET("api/v1.0/lesson")
    Call<List<Lesson>> getLesson();

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/lesson/b04070c377c24b7295fda8ec8484dca5
    //Get lesson record base on ID
    @GET("api/v1.0/lesson/{id}")
    Call<Lesson> getLessonById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/lesson
    //Add lesson record and post content in HTTP request BODY
    @POST("api/v1.0/lesson")
    Call<Lesson> addLesson(@Body Lesson lesson);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/lesson/b04070c377c24b7295fda8ec8484dca5
    //Delete lesson record base on ID
    @DELETE("api/v1.0/lesson/{id}")
    Call<Void> deleteLessonById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/lesson/b04070c377c24b7295fda8ec8484dca5
    //Update lesson record with PATCH (only delta is updated) and post content in HTTP request BODY
    @PATCH("api/v1.0/lesson/{id}")
    Call<Lesson> updateLessonById(@Path("id") String id, @Body Lesson lesson);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/exercisestudent
    @GET("api/v1.0/exercisestudents")
    Call<List<ExerciseStudent>> getExerciseStudent();

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/exercisestudent/b04070c377c24b7295fda8ec8484dca5
    //Get exercisestudent record base on ID
    @GET("api/v1.0/exercisestudents/{id}")
    Call<ExerciseStudent> getExerciseStudentById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/exercisestudent
    //Add exercisestudent record and post content in HTTP request BODY
    @POST("api/v1.0/exercisestudents")
    Call<ExerciseStudent> addExerciseStudent(@Body ExerciseStudent exercisestudent);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/exercisestudent/b04070c377c24b7295fda8ec8484dca5
    //Delete exercisestudent record base on ID
    @DELETE("api/v1.0/exercisestudents/{id}")
    Call<Void> deleteExerciseStudentById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/exercisestudent/b04070c377c24b7295fda8ec8484dca5
    //Update exercisestudent record with PATCH (only delta is updated) and post content in HTTP request BODY
    @PATCH("api/v1.0/exercisestudents/{id}")
    Call<ExerciseStudent> updateExerciseStudentById(@Path("id") String id, @Body ExerciseStudent exercisestudent);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/comment
    @GET("api/v1.0/comment")
    Call<List<Comment>> getComment();

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/comment?$expand=user,commentRatings&$filter=exerciseId eq 'a3dce27b9f0b425a859f1bdb47b35af1'
    //Get comments records base on query
    @GET("api/v1.0/comment?$expand=user,commentRatings")
    Call<List<Comment>> getCommentByQuery(@Query("$filter") String filter);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/comment/b04070c377c24b7295fda8ec8484dca5$expand=user,commentRatings
    //Get comment record base on ID
    @GET("api/v1.0/comment/{id}?$expand=user,commentRatings")
    Call<Comment> getCommentById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/comment
    //Add comment record and post content in HTTP request BODY
    @POST("api/v1.0/comment")
    Call<Comment> addComment(@Body Comment comment);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/comment/b04070c377c24b7295fda8ec8484dca5
    //Delete comment record base on ID
    @DELETE("api/v1.0/comment/{id}")
    Call<Void> deleteCommentById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/comment/b04070c377c24b7295fda8ec8484dca5
    //Update comment record with PATCH (only delta is updated) and post content in HTTP request BODY
    @PATCH("api/v1.0/comment/{id}")
    Call<Comment> updateCommentById(@Path("id") String id, @Body Comment comment);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/commentrating
    @GET("api/v1.0/commentrating")
    Call<List<CommentRating>> getCommentRating();

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/commentrating?$expand=user&$filter=exerciseId eq 'a3dce27b9f0b425a859f1bdb47b35af1'
    //Get comments records base on query
    @GET("api/v1.0/commentrating?$expand=user")
    Call<List<CommentRating>> getCommentRatingByQuery(@Query("$filter") String filter);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/commentrating/b04070c377c24b7295fda8ec8484dca5
    //Get commentrating record base on ID
    @GET("api/v1.0/commentrating/{id}?$expand=user")
    Call<CommentRating> getCommentRatingById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/commentrating
    //Add commentrating record and post content in HTTP request BODY
    @POST("api/v1.0/commentrating")
    Call<CommentRating> addCommentRating(@Body CommentRating commentrating);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/commentrating/b04070c377c24b7295fda8ec8484dca5
    //Delete commentrating record base on ID
    @DELETE("api/v1.0/commentrating/{id}")
    Call<Void> deleteCommentRatingById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/commentrating/b04070c377c24b7295fda8ec8484dca5
    //Update commentrating record with PATCH (only delta is updated) and post content in HTTP request BODY
    @PATCH("api/v1.0/commentrating/{id}")
    Call<CommentRating> updateCommentRatingById(@Path("id") String id, @Body CommentRating commentrating);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/pair
    @GET("api/v1.0/pairs")
    Call<List<Pair>> getPair();

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/pair/b04070c377c24b7295fda8ec8484dca5
    //Get pair record base on ID
    @GET("api/v1.0/pairs/{id}")
    Call<Pair> getPairById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/pair
    //Add pair record and post content in HTTP request BODY
    @POST("api/v1.0/pairs")
    Call<Pair> addPair(@Body Pair pair);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/pair/b04070c377c24b7295fda8ec8484dca5
    //Delete pair record base on ID
    @DELETE("api/v1.0/pairs/{id}")
    Call<Void> deletePairById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/pair/b04070c377c24b7295fda8ec8484dca5
    //Update pair record with PATCH (only delta is updated) and post content in HTTP request BODY
    @PATCH("api/v1.0/pairs/{id}")
    Call<Pair> updatePairById(@Path("id") String id, @Body Pair pair);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/answer
    @GET("api/v1.0/answers")
    Call<List<Answer>> getAnswer();

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/answer/b04070c377c24b7295fda8ec8484dca5
    //Get answer record base on ID
    @GET("api/v1.0/answers/{id}")
    Call<Answer> getAnswerById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/answer
    //Add answer record and post content in HTTP request BODY
    @POST("api/v1.0/answers")
    Call<Answer> addAnswer(@Body Answer answer);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/answer/b04070c377c24b7295fda8ec8484dca5
    //Delete answer record base on ID
    @DELETE("api/v1.0/answers/{id}")
    Call<Void> deleteAnswerById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/answer/b04070c377c24b7295fda8ec8484dca5
    //Update answer record with PATCH (only delta is updated) and post content in HTTP request BODY
    @PATCH("api/v1.0/answers/{id}")
    Call<Answer> updateAnswerById(@Path("id") String id, @Body Answer answer);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/space
    @GET("api/v1.0/spaces")
    Call<List<Space>> getSpace();

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/space/b04070c377c24b7295fda8ec8484dca5
    //Get space record base on ID
    @GET("api/v1.0/spaces/{id}")
    Call<Space> getSpaceById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/space
    //Add space record and post content in HTTP request BODY
    @POST("api/v1.0/spaces")
    Call<Space> addSpace(@Body Space space);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/space/b04070c377c24b7295fda8ec8484dca5
    //Delete space record base on ID
    @DELETE("api/v1.0/spaces/{id}")
    Call<Void> deleteSpaceById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/space/b04070c377c24b7295fda8ec8484dca5
    //Update space record with PATCH (only delta is updated) and post content in HTTP request BODY
    @PATCH("api/v1.0/spaces/{id}")
    Call<Space> updateSpaceById(@Path("id") String id, @Body Space space);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/studentcourse/b04070c377c24b7295fda8ec8484dca5
    //Get studentcourse record base on ID
    @GET("api/v1.0/courseprogresses/{id}")
    Call<CourseProgress> getStudentCourseById(@Path("id") String id);


    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/studentcourse
    //Add studentcourse record and post content in HTTP request BODY
    @POST("api/v1.0/courseprogresses")
    Call<CourseProgress> addStudentCourse(@Body CourseProgress courseProgress);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/studentcourse/b04070c377c24b7295fda8ec8484dca5
    //Delete studentcourse record base on ID
    @DELETE("api/v1.0/studentcourse/{id}")
    Call<Void> deleteStudentCourseById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/studentcourse/b04070c377c24b7295fda8ec8484dca5
    //Update studentcourse record with PATCH (only delta is updated) and post content in HTTP request BODY
    @PATCH("api/v1.0/courseprogresses/{id}")
    Call<CourseProgress> updateStudentCourseById(@Path("id") String id, @Body CourseProgress courseProgress);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/courses/b04070c377c24b7295fda8ec8484dca5
    //Delete course record including all its children( lessons)
    @DELETE("api/v1.0/courses/{id}")
    Call<Void> deleteCourseById(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/sections/b04070c377c24b7295fda8ec8484dca5
    //Delete section record including all its children( lessons)
    @DELETE("api/v1.0/sections/{id}")
    Call<Void> deleteSectionById(@Path("id") String id);

    //i.e.  http://teachmeserv.azurewebsites.net/api/v1.0/users/95777a45afc241dd87f3cae3274fe0af/courses
    //Get courses records by parent User id
    @GET("api/v1.0/users/{id}/courses")
    Call<List<Course>> getCoursesByUserId(@Path("id") String id, @Query("isTeacher") boolean isTeacher);

    //i.e.  http://teachmeserv.azurewebsites.net/api/v1.0/courses/95777a45afc241dd87f3cae3274fe0af/sections
    //Get sections records by parent Course id
    @GET("api/v1.0/courses/{id}/sections")
    Call<List<Section>> getSectionsByCourseId(@Path("id") String id);

    //i.e.  http://teachmeserv.azurewebsites.net/api/v1.0/sections/95777a45afc241dd87f3cae3274fe0af/lessons
    //Get lessons records by parent Section id
    @GET("api/v1.0/sections/{id}/lessons")
    Call<List<Lesson>> getLessonsBySectionId(@Path("id") String id);

    //i.e.  http://teachmeserv.azurewebsites.net/api/v1.0/lessons/95777a45afc241dd87f3cae3274fe0af/exercises
    //Get exercises records by parent Lesson id
    @GET("api/v1.0/lessons/{id}/exercises")
    Call<List<Exercise>> getExercisesByLessonId(@Path("id") String id);

    //i.e.  http://teachmeserv.azurewebsites.net/api/v1.0/exercises/95777a45afc241dd87f3cae3274fe0af/exercisestudents
    //Get exercisestudents records by parent Exercise id
    @GET("api/v1.0/exercises/{id}/exercisestudents")
    Call<List<ExerciseStudent>> getExerciseStudentsByExerciseId(@Path("id") String id);

    //i.e.  http://teachmeserv.azurewebsites.net/api/v1.0/exercises/95777a45afc241dd87f3cae3274fe0af/comments
    //Get comments records by parent Exercise id
    @GET("api/v1.0/exercises/{id}/comments")
    Call<List<Comment>> getCommentsByExerciseId(@Path("id") String id);

    //i.e.  http://teachmeserv.azurewebsites.net/api/v1.0/comments/95777a45afc241dd87f3cae3274fe0af/commentratings
    //Get commentratings records by parent Comment id
    @GET("api/v1.0/comments/{id}/commentratings")
    Call<List<CommentRating>> getCommentRatingsByCommentId(@Path("id") String id);

    //i.e.  http://teachmeserv.azurewebsites.net/api/v1.0/exercises/95777a45afc241dd87f3cae3274fe0af/answers
    //Get answers records by parent Exercise id
    @GET("api/v1.0/exercises/{id}/answers")
    Call<List<Answer>> getAnswersByExerciseId(@Path("id") String id);

    //i.e.  http://teachmeserv.azurewebsites.net/api/v1.0/exercises/95777a45afc241dd87f3cae3274fe0af/pairs
    //Get pairs records by parent Exercise id
    @GET("api/v1.0/exercises/{id}/pairs")
    Call<List<Pair>> getPairsByExerciseId(@Path("id") String id);

    //i.e.  http://teachmeserv.azurewebsites.net/api/v1.0/exercises/95777a45afc241dd87f3cae3274fe0af/spaces
    //Get spaces records by parent Exercise id
    @GET("api/v1.0/exercises/{id}/spaces")
    Call<List<Space>> getSpacesByExerciseId(@Path("id") String id);

    //i.e.  http://teachmeserv.azurewebsites.net/api/v1.0/users/95777a45afc241dd87f3cae3274fe0af/studentcourses
    //Get studentcourses records by parent User id
    @GET("api/v1.0/courseprogresses/{id}")
    Call<List<CourseProgress>> getStudentCoursesByUserId(@Path("id") String id);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/exercises
    //Add exercise record and post content in HTTP request BODY
    @POST("api/v1.0/exercises")
    Call<Exercise> addExerciseWithInnerObjects(@Body Exercise exercise);

    //i.e. http://teachmeserv.azurewebsites.net/api/v1.0/exercises/95777a45afc241dd87f3cae3274fe0af
    //Replace exercise record and post content in HTTP request BODY
    @PUT("api/v1.0/exercises/{id}")
    Call<Exercise> replaceExerciseWithInnerObjectsById(@Path("id") String id, @Body Exercise exercise);

    //i.e. https://teachmetest.azurewebsites.net/api/v1.0/auth/login
    //login
    @POST("api/v1.0/auth/login")
    Call<LoginResult> login(@Body LoginViewModel loginViewModel);

}

