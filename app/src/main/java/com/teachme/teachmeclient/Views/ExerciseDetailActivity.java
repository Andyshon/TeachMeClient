package com.teachme.teachmeclient.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.teachme.teachmeclient.BaseActivity.BaseActivity;
import com.teachme.teachmeclient.Views.simple_views.CommentsActivity;
import com.teachme.teachmeclient.Controllers.ExercisesDetailController;
import com.teachme.teachmeclient.Interfaces.DatabaseQuery;
import com.teachme.teachmeclient.Entities.Answer;
import com.teachme.teachmeclient.Entities.Exercise;
import com.teachme.teachmeclient.Views.simple_views.ExerciseStudentsActivity;
import com.teachme.teachmeclient.Entities.Pair;
import com.teachme.teachmeclient.R;
import com.teachme.teachmeclient.Entities.Space;

import java.util.Arrays;
import java.util.List;


public class ExerciseDetailActivity extends BaseActivity implements android.view.View.OnClickListener {

    private EditText editTextName;
    private EditText editLessonId;
    private EditText editTextText;
    private EditText editTextQuestion;
    private EditText editTextAnswer;
    private EditText editTextType;
    private String _Exercise_Id;

    private ExercisesDetailController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);

        Button btnAdd = (Button) findViewById(R.id.btnSave);
        Button btnDelete = (Button) findViewById(R.id.btnDelete);
        Button btnClose = (Button) findViewById(R.id.btnClose);
        Button btnViewExerciseStudents = (Button) findViewById(R.id.btnViewExerciseStudents);
        Button btnViewComments = (Button) findViewById(R.id.btnViewComments);
        Button btnViewPairs = (Button) findViewById(R.id.btnViewPairs);
        Button btnViewAnswers = (Button) findViewById(R.id.btnViewAnswers);
        Button btnViewSpaces = (Button) findViewById(R.id.btnViewSpaces);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextText = (EditText) findViewById(R.id.editTextText);
        editLessonId = (EditText) findViewById(R.id.editLessonId);
        editTextType = (EditText) findViewById(R.id.editTextType);
        editTextQuestion = (EditText) findViewById(R.id.editTextQuestion);
        editTextAnswer = (EditText) findViewById(R.id.editTextAnswer);

        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        btnViewExerciseStudents.setOnClickListener(this);
        btnViewComments.setOnClickListener(this);
        btnViewPairs.setOnClickListener(this);
        btnViewAnswers.setOnClickListener(this);
        btnViewSpaces.setOnClickListener(this);

        _Exercise_Id = "";
        String _Lesson_Id = "";
        Intent intent = getIntent();
        _Exercise_Id = intent.getStringExtra("exercise_Id");
        _Lesson_Id = intent.getStringExtra("lesson_Id");
        editLessonId.setText(_Lesson_Id);

        controller = new ExercisesDetailController(this);
        progressBar = findViewById(R.id.spinner);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getExercise();
    }

    private void getExercise(){
        if (_Exercise_Id != null && !_Exercise_Id.isEmpty()) {
            startSpinner();
            controller.getExerciseById(new DatabaseQuery() {
                @Override
                public void getExerciseByIdResult(Exercise exercise) {
                    System.out.println("exercise id:" + exercise.getId() + "\tname:" + exercise.getName());
                    populateExercise(exercise);
                }
            }, _Exercise_Id);
        }
        else {
            Toast.makeText(this, "Enter data to create new exercise", Toast.LENGTH_SHORT).show();
            stopSpinner();
        }
    }

    private void populateExercise(Exercise exercise){
        editTextName.setText(exercise.getName());
        editTextText.setText(exercise.getText());
        editLessonId.setText(exercise.getLessonId());
        editTextType.setText(exercise.getType());
        editTextQuestion.setText(exercise.getQuestion());
        editTextAnswer.setText(exercise.getAnswer());
        stopSpinner();
    }


    @Override
    public void onClick(View v) {
        if (findViewById(R.id.btnDelete) == v) {

            controller.deleteExerciseById(new DatabaseQuery() {
                @Override
                public void getDeletedExerciseResult() {
                    finish();
                }
            }, _Exercise_Id);

        } else if (v == findViewById(R.id.btnClose)) {
            finish();
        } else if (v == findViewById(R.id.btnViewExerciseStudents)) {
            Intent intent = new Intent(this, ExerciseStudentsActivity.class);
            intent.putExtra("exercise_Id", _Exercise_Id);
            startActivity(intent);
        } else if (v == findViewById(R.id.btnViewComments)) {
            Intent intent = new Intent(this, CommentsActivity.class);
            intent.putExtra("exercise_Id", _Exercise_Id);
            startActivity(intent);
        } else if (v == findViewById(R.id.btnViewPairs)) {
            Intent intent = new Intent(this, PairsActivity.class);
            intent.putExtra("exercise_Id", _Exercise_Id);
            startActivity(intent);
        } else if (v == findViewById(R.id.btnViewAnswers)) {
            Intent intent = new Intent(this, AnswersActivity.class);
            intent.putExtra("exercise_Id", _Exercise_Id);
            startActivity(intent);
        } else if (v == findViewById(R.id.btnViewSpaces)) {
            Intent intent = new Intent(this, SpacesActivity.class);
            intent.putExtra("exercise_Id", _Exercise_Id);
            startActivity(intent);
        } else if (findViewById(R.id.btnSave) == v) {

            if (_Exercise_Id == null || _Exercise_Id.isEmpty()) {
                //todo: no Id -> new exercise

                Exercise exercise = new Exercise();
                exercise.setText(editTextText.getText().toString());
                exercise.setType(editTextType.getText().toString());
                exercise.setName(editTextName.getText().toString());
                exercise.setLessonId(editLessonId.getText().toString());
                exercise.setQuestion(editTextQuestion.getText().toString());
                exercise.setAnswer(editTextAnswer.getText().toString());

                List<Pair> pairs = Arrays.asList(new Pair("some Value", "some Equal"));
                exercise.setPairs(pairs);

                List<Answer> answers = Arrays.asList(new Answer("some Title", false));
                exercise.setAnswers(answers);

                List<Space> spaces = Arrays.asList(new Space("some Value"));
                exercise.setSpaces(spaces);


                controller.addExerciseWithInnerObjects(new DatabaseQuery() {
                    @Override
                    public void getAddExerciseWithInnerObjectsResult() {
                        finish();
                    }
                }, exercise);

            } else {
                //todo: update existing exercise

                Exercise exercise = new Exercise();
                exercise.setText(editTextText.getText().toString());
                exercise.setType(editTextType.getText().toString());
                exercise.setName(editTextName.getText().toString());
                exercise.setLessonId(editLessonId.getText().toString());
                exercise.setQuestion(editTextQuestion.getText().toString());
                exercise.setAnswer(editTextAnswer.getText().toString());

                List<Pair> pairs = Arrays.asList(new Pair("some Value", "some Equal"));
                exercise.setPairs(pairs);

                List<Answer> answers = Arrays.asList(new Answer("some Title", false));
                exercise.setAnswers(answers);

                List<Space> spaces = Arrays.asList(new Space("some Value"));
                exercise.setSpaces(spaces);


                controller.replaceExerciseWithInnerObjectsById(new DatabaseQuery() {
                    @Override
                    public void getReplaceExerciseWithInnerObjectsResult() {
                        finish();
                    }
                }, exercise, _Exercise_Id);
            }
        }
    }
}