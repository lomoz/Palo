package com.example.lorcan.palo.AsyncTasks;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.widget.Button;
import android.widget.TextView;

public class TestAsyncTask extends AsyncTask<Void, Void, String> {

    @SuppressLint("StaticFieldLeak")
    private Context context;
    @SuppressLint("StaticFieldLeak")
    private TextView textView;
    @SuppressLint("StaticFieldLeak")
    private Button button;

    private String android_id;
    private ProgressDialog progressDialog;

    public TestAsyncTask(Context context, TextView textView, Button button, String android_id) {
        this.context = context;
        this.textView = textView;
        this.button = button;
        this.android_id = android_id;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Displaying dialog...");
        progressDialog.show();
    }

    @SuppressLint("HardwareIds")
    @Override
    protected String doInBackground(Void... voids) {

        synchronized (this) {

            try {
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                if (ActivityCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return null;
                }

                if (telephonyManager != null) {
                    android_id = telephonyManager.getDeviceId();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return android_id;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        textView.setText(result);
        button.setEnabled(true);
        progressDialog.dismiss();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
