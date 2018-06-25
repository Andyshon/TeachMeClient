package com.teachme.teachmeclient.Models;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import com.teachme.teachmeclient.Interfaces.DatabaseQuery;

/**
 * Created by andyshon on 22.01.18.
 */

public class ListOfImagesModel {
    private Context context;

    public ListOfImagesModel(Context context) {
        this.context = context;
    }

    public void getListOfImages(DatabaseQuery callback){
        final Handler handler = new Handler();

        Thread th = new Thread(new Runnable() {
            public void run() {
                try {
                    final String[] images = ImageManager.ListImages();
                    callback.getListOfImagesResult(images, handler);
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
