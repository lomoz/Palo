package com.example.lorcan.palo.AsyncTasks;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.ImageView;

import com.kosalgeek.android.photoutil.ImageBase64;

import java.util.HashMap;


public class ProfileAsyncTask extends AsyncTask<Void, Void, HashMap>{

    private ProgressDialog progressDialog;
    @SuppressLint("StaticFieldLeak")
    private Context context;
    @SuppressLint("StaticFieldLeak")
    private ImageView profileImage;
    @SuppressLint("StaticFieldLeak")
    private EditText editTextStatus;
    @SuppressLint("StaticFieldLeak")
    private EditText editTextJob;
    private String android_id;

    private HashMap hashMap;
    private String encodedProfileImage;
    private Bitmap bitmapProfileImage;
    private String status;
    private String job;

    public ProfileAsyncTask(Context context, ImageView profileImage, EditText editTextStatus, EditText editTextJob, String android_id) {
        this.context = context;
        this.profileImage = profileImage;
        this.editTextStatus = editTextStatus;
        this.editTextJob = editTextJob;
        this.android_id = android_id;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Load Profile from database...");
        progressDialog.show();
    }

    @Override
    protected HashMap doInBackground(Void... voids) {

        hashMap = new HashMap();

        synchronized (this) {

            try {

                // Check if Strings from database are not null before putting them to HashMap.

                // Get String encoded Image from database.
                hashMap.put("encodedProfileImage", encodedProfileImage);

                // Get status from database.
                hashMap.put("status", status);
                System.out.println("User status from android ID: " + android_id + "is " + status);

                // Get job from database.
                System.out.println("User job from android ID: " + android_id + "is " + job);
                hashMap.put("job", job);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return hashMap;
    }

    @Override
    protected void onPostExecute(HashMap result) {
        super.onPostExecute(result);

        // Decode String encoded to Bitmap.
        bitmapProfileImage = ImageBase64.decode(result.get("encodedProfileImage").toString());

        // Set Bitmap to ImageView.
        profileImage.setImageBitmap(Bitmap.createScaledBitmap(bitmapProfileImage, 256, 256, false));

        // Set status to EditText.
        editTextStatus.setText(result.get("status").toString());

        // Set job to EditText.
        editTextJob.setText(result.get("job").toString());

        // Close ProgressDialog.
        progressDialog.dismiss();
    }
}
