package com.example.lorcan.palo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private EditText email, nickname;
    private Button sign_in_register;
    private RequestQueue requestQueue;
    private static final String URL = "http://palo.square7.ch/control_users.php";
    private StringRequest request;

    private String android_id;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TelephonyManager tManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        android_id = tManager.getDeviceId();

        email = (EditText) findViewById(R.id.email);
        nickname = (EditText) findViewById(R.id.nickname);
        sign_in_register = (Button) findViewById(R.id.email_sign_in_button);

        // using volley lib to create request
        requestQueue = Volley.newRequestQueue(this);

        sign_in_register.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                if (isValidEmail(email.getText())) {
                    request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {


                        // handle the response of the server (success or error)
                        @Override
                        public void onResponse(String response) {
                            System.out.println("ANTWORT VOM LOGIN: " + response);
                            String res = response.toString().trim();
                            if (res.equals("0")) {
                                FileWriter file = null;
                                FileWriter file1 = null;
                                try {
                                    file = new FileWriter(MyApplicationContext.getAppContext().getFilesDir().getPath() + "/" + "chats.json");
                                    String nameJSON = "{ \"Users\" : [\"\"]}";
                                    file.write(nameJSON);
                                    file.flush();
                                    file.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), "E-Mailadresse oder Nickname schon vorhanden.", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {

                        // set of parameters in a hashmap, which will be send to the php file (server side)
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> hashMap = new HashMap<String, String>();

                            hashMap.put("email", email.getText().toString());
                            hashMap.put("nickname", nickname.getText().toString());
                            hashMap.put("android_id", android_id);


                            return hashMap;
                        }
                    };

                    requestQueue.add(request);
                }else{
                    Toast.makeText(LoginActivity.this, "keine korrekte e-Mailadresse ;-)",
                            Toast.LENGTH_LONG).show();
                }
            }
        });


    }
    public final static boolean isValidEmail(CharSequence target) {
        System.out.println("TARGET: " + target);
        Boolean bool = false;
        if (target == null || target == "" || target.length() == 0) {
            bool = true;
        }else {
            bool = android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
        return bool;
    }

}

