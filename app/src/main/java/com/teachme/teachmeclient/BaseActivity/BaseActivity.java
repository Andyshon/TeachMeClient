package com.teachme.teachmeclient.BaseActivity;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.teachme.teachmeclient.Interfaces.BackendService;
import com.teachme.teachmeclient.Entities.RestClient;

/**
 * Created by andyshon on 21.06.18.
 */

public class BaseActivity extends AppCompatActivity {

    public ProgressBar progressBar;
    public BackendService restClient;

    public BaseActivity(){
        restClient = RestClient.getService();
    }

    public void startSpinner() {
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
    }

    public void stopSpinner() {
        progressBar.setIndeterminate(false);
        progressBar.setVisibility(View.INVISIBLE);
    }
}
