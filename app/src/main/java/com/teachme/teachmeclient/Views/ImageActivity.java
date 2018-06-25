package com.teachme.teachmeclient.Views;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.teachme.teachmeclient.Controllers.ImageController;
import com.teachme.teachmeclient.Interfaces.DatabaseQuery;
import com.teachme.teachmeclient.R;

import java.io.ByteArrayOutputStream;

public class ImageActivity extends AppCompatActivity {

    private ImageController controller;
    private String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        image = this.getIntent().getStringExtra("image");

        controller = new ImageController(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getImage(image);
    }

    public void getImage(String image) {
        controller.getImage(new DatabaseQuery() {
            @Override
            public void getImageResult(ByteArrayOutputStream imageStream, Handler handler) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        byte[] buffer = imageStream.toByteArray();
                        Bitmap bitmap = BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
                        populateImage(bitmap);
                    }
                });
            }
        }, image);
    }

    private void populateImage(Bitmap bitmap) {
        ((ImageView)(findViewById(R.id.ChildImageView))).setImageBitmap(bitmap);
    }
}
