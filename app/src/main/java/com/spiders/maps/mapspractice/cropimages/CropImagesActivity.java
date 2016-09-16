package com.spiders.maps.mapspractice.cropimages;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.spiders.maps.mapspractice.R;

import java.io.File;
import java.util.Date;

public class CropImagesActivity extends AppCompatActivity {

    String filepath;
    Uri selectedImageUri;
    final int GALLERY_PIC = 1, CAMERA_PIC = 2, CROP_PIC = 3;
    ImageView im;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_images);
        im = (ImageView) findViewById(R.id.cropimageView);

    }

    public void selectImageFromCameraGallery(View view) {
        String[] option = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(CropImagesActivity.this);
        builder.setItems(option, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    // Checking camera availability
                    /*if (!isDeviceSupportCamera()) {
                        Toast.makeText(getApplicationContext(),
                                "Sorry! Your device doesn't support camera",
                                Toast.LENGTH_LONG).show();
                        // will close the app if the device does't have camera
                        finish();
                    }
                    else*/
                    {
                        Camera();
                    }


                }
                if (which == 1) {
                    Gallery();
                }

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void Gallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //intent.setType("image/*");
        //intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), GALLERY_PIC);
    }

    public void Camera() {
        // CAMERA_CAPTURE_IMAGE=1;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, setImageUri());
        // start the image capture Intent
        startActivityForResult(intent, CAMERA_PIC);
    }

    public Uri setImageUri() {
        // Store image in dcim
        File file = null;
        File testexistdir = new File(Environment.getExternalStorageDirectory() + "/practice/");

        if (testexistdir.exists()) {
            file = new File(Environment.getExternalStorageDirectory() + "/practice/", "image" + new Date().getTime() + ".png");
        } else {
            //String makedirname="DCIM";
            File makedir = new File(Environment.getExternalStorageDirectory() + "/practice/");
            makedir.mkdir();
            file = new File(Environment.getExternalStorageDirectory() + "/practice/", "image" + new Date().getTime() + ".png");
        }

        Uri imgUri = Uri.fromFile(file);
        filepath = file.getAbsolutePath();
        return imgUri;
    }


    public String getImagePath() {
        return filepath;
    }

    public String getPath(Uri uri) {
        //	 Toast.makeText(getApplicationContext(), "enters into imagepath", Toast.LENGTH_LONG).show();
        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        //  Toast.makeText(getApplicationContext(), "Int val : "+column_index, Toast.LENGTH_LONG).show();
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void performCrop(Uri picUri) {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(picUri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 650);
            cropIntent.putExtra("outputY", 450);
            cropIntent.putExtra("return-data", true);
            startActivityForResult(cropIntent, 3);
        } catch (ActivityNotFoundException anfe) {
            Toast.makeText(CropImagesActivity.this, "NotSupportCrop", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int arg0, int arg1, Intent arg2) {
        super.onActivityResult(arg0, arg1, arg2);

        if (arg0 == GALLERY_PIC && arg1 == RESULT_OK) {

            if (arg2.getData() != null) {
                selectedImageUri = arg2.getData();
                // performCrop(selectedImageUri);
            } else {
                Log.d("selectedPath1 : ", "Came here its null !");
            }

            // performCrop(selectedImageUri);
            filepath = getPath(selectedImageUri);

            if (filepath != null) {

                Bitmap bitmap = BitmapFactory.decodeFile(filepath);

                if (bitmap != null) {
                    im.setImageBitmap(bitmap);
                    Toast.makeText(getApplicationContext(), "Your image attached successfully", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Please select image from Gallery", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(getApplicationContext(), "Image is not selected please select again", Toast.LENGTH_LONG).show();
            }
        }
        if (arg0 == CAMERA_PIC && arg1 == RESULT_OK) {

            //  performCrop(selectedImageUri);
            if (filepath != null) {
                Bitmap bitmap = BitmapFactory.decodeFile(filepath);
                im.setImageBitmap(bitmap);
                Toast.makeText(getApplicationContext(), "Your image attached successfully", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(getApplicationContext(), "Image is not selected please take pic again", Toast.LENGTH_LONG).show();
            }

        }
        if (arg0 == CROP_PIC && arg1 == RESULT_OK && arg2 != null) {
            Bundle extras = arg2.getExtras();

            Bitmap selectedBitmap = extras.getParcelable("data");
            im.setImageBitmap(selectedBitmap);

        }
    }
}
