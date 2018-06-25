package com.teachme.teachmeclient.Controllers;

import android.content.Context;
import android.os.Handler;

import com.teachme.teachmeclient.Interfaces.DatabaseQuery;
import com.teachme.teachmeclient.Models.ListOfImagesModel;

/**
 * Created by andyshon on 22.01.18.
 */

public class ListOfImagesController {
    private ListOfImagesModel model;

    public ListOfImagesController(Context context) {
        model = new ListOfImagesModel(context);
    }

    public void getListOfImages(DatabaseQuery callback){
        model.getListOfImages(new DatabaseQuery() {
            @Override
            public void getListOfImagesResult(String[] images, Handler handler) {
                callback.getListOfImagesResult(images, handler);
            }
        });
    }
}
