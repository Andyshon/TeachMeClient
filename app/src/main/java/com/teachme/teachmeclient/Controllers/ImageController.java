package com.teachme.teachmeclient.Controllers;

import android.content.Context;
import android.os.Handler;

import com.teachme.teachmeclient.Interfaces.DatabaseQuery;
import com.teachme.teachmeclient.Models.ImageModel;

import java.io.ByteArrayOutputStream;

/**
 * Created by andyshon on 22.01.18.
 */

public class ImageController {
    private ImageModel model;

    public ImageController(Context context) {
        model = new ImageModel(context);
    }

    public void getImage(DatabaseQuery callback, String image) {
        model.getImage(new DatabaseQuery() {
            @Override
            public void getImageResult(ByteArrayOutputStream imageStream, Handler handler) {
                callback.getImageResult(imageStream, handler);
            }
        }, image);
    }
}
