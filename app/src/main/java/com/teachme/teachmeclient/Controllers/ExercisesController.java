package com.teachme.teachmeclient.Controllers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.teachme.teachmeclient.Interfaces.DatabaseQuery;
import com.teachme.teachmeclient.Entities.Exercise;
import com.teachme.teachmeclient.Models.ExercisesModel;
import com.teachme.teachmeclient.R;

import java.util.List;

/**
 * Created by andyshon on 18.01.18.
 */

public class ExercisesController extends ArrayAdapter<Exercise> {

    private ExercisesModel model;

    public ExercisesController(Context context) {
        super(context, 0);
        model = new ExercisesModel(context);
    }

    public void getExercises(DatabaseQuery callback) {
        // todo: implement real logic to get exercises from model

        model.getExercises(new DatabaseQuery() {
            @Override
            public void getExercisesResult(List<Exercise> exercisesList) {
                callback.getExercisesResult(exercisesList);
            }
        });
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.entry_item, parent, false);
        }
        Exercise exercise = getItem(position);

        TextView tvTitle = convertView.findViewById(R.id.tvTitle);
        tvTitle.setText(exercise != null ? exercise.getName() : "");
        TextView tvId = convertView.findViewById(R.id.user_id);
        tvId.setText(exercise != null ? exercise.getId() : "");

        return convertView;
    }
}
