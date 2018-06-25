package com.teachme.teachmeclient.Views;

/**
 * Created by andyshon on 05.01.2018.
 */

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.teachme.teachmeclient.Controllers.ListOfImagesController;
import com.teachme.teachmeclient.Interfaces.DatabaseQuery;
import com.teachme.teachmeclient.R;

public class ListImagesActivity extends ListActivity {

    private String[] images;
    private ListOfImagesController controller;

    public void setImages(String[] images) {
        this.images = images;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        controller = new ListOfImagesController(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getListOfImages();
    }

    private void getListOfImages() {

        controller.getListOfImages(new DatabaseQuery() {
            @Override
            public void getListOfImagesResult(String[] images, Handler handler) {
                setImages(images);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        populateImages(images);
                    }
                });
            }
        });
    }

    private void populateImages(String[] images) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListImagesActivity.this, R.layout.text_view_item, images);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(getBaseContext(), ImageActivity.class);
        intent.putExtra("image", images[position]);

        startActivity(intent);
    }

}