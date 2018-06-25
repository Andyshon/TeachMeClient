package com.teachme.teachmeclient.Models;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.teachme.teachmeclient.Interfaces.DatabaseQuery;

import java.io.ByteArrayOutputStream;

/**
 * Created by andyshon on 22.01.18.
 */

public class ImageModel {
    private Context context;

    public ImageModel(Context context) {
        this.context = context;
    }

    public void getImage(DatabaseQuery callback, String image) {
        final Handler handler = new Handler();

        Thread th = new Thread(new Runnable() {
            public void run() {
                final ByteArrayOutputStream imageStream = new ByteArrayOutputStream();
                try {
                    long imageLength = 0;
                    ImageManager.GetImage(image, imageStream, imageLength);
                    callback.getImageResult(imageStream, handler);
                }
                catch (Exception ex) {
                    final String exceptionMessage = ex.getMessage();
                    handler.post(new Runnable() {
                        public void run() {
                            Toast.makeText(context, exceptionMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        th.start();
    }
}
