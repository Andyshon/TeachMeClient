package com.teachme.teachmeclient.Views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.teachme.teachmeclient.Models.ImageManager;
import com.teachme.teachmeclient.R;

import java.io.InputStream;

public class BlobManagerActivity extends AppCompatActivity {

    private static final int SELECT_IMAGE = 100;

    private ImageView imageView;
    private Uri imageUri;
    private Button uploadImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blob_manager);

        Button selectImageButton = (Button) findViewById(R.id.Select);
        selectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImageFromGallery();
            }
        });

        this.uploadImageButton = (Button) findViewById(R.id.Upload);
        this.uploadImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadImage();
            }
        });
        this.uploadImageButton.setEnabled(false);

        Button showImageButton = (Button) findViewById(R.id.Show);
        showImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListImages();
            }
        });

        this.imageView = (ImageView) findViewById(R.id.imageView);
    }

    private void ListImages() {
        Intent intent = new Intent(getBaseContext(), ListImagesActivity.class);
        startActivity(intent);
    }

    private void SelectImageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_IMAGE);
    }

    private void UploadImage() {
        try {
            final InputStream imageStream = getContentResolver().openInputStream(this.imageUri);
            final int imageLength = imageStream.available();

            final Handler handler = new Handler();

            Thread th = new Thread(new Runnable() {
                public void run() {
                    try {
                        final String imageName = ImageManager.UploadImage(imageStream, imageLength);

                        handler.post(new Runnable() {
                            public void run() {
                                Toast.makeText(BlobManagerActivity.this, "Image uploaded successfully. Name = " + imageName, Toast.LENGTH_SHORT).show();
                                imageView.setImageBitmap(null);
                                uploadImageButton.setEnabled(false);
                            }
                        });
                    } catch (Exception ex) {
                        final String exceptionMessage = ex.getMessage();
                        handler.post(new Runnable() {
                            public void run() {
                                Toast.makeText(BlobManagerActivity.this, exceptionMessage, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
            th.start();
        } catch (Exception ex) {

            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case SELECT_IMAGE:
                if (resultCode == RESULT_OK) {
                    this.imageUri = imageReturnedIntent.getData();
                    this.imageView.setImageURI(this.imageUri);
                    this.uploadImageButton.setEnabled(true);
                }
        }
    }
}