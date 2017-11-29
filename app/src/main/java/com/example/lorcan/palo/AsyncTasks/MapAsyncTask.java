package com.example.lorcan.palo.AsyncTasks;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;

public class MapAsyncTask extends AsyncTask<Void, Void, String>{

    private ProgressDialog progressDialog;
    @SuppressLint("StaticFieldLeak")
    private Context context;
    @SuppressLint("StaticFieldLeak")
    private EditText editTextStatus;
    private String android_id;

    private String status;

    public MapAsyncTask(Context context, EditText editTextStatus, String android_id) {
        this.context = context;
        this.editTextStatus = editTextStatus;
        this.android_id = android_id;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Load status from database");
        progressDialog.show();
    }

    @Override
    protected String doInBackground(Void... voids) {

        synchronized (this) {

            try {

                // Get Status from database
                System.out.println("User status from android ID: " + android_id + "is " + status);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return status;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        // Set status to EditText.
        editTextStatus.setText(result);

        // Close ProgressDialog.
        progressDialog.dismiss();
    }
}
