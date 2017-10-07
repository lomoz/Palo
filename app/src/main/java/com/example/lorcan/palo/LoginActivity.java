package com.example.lorcan.palo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
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

import java.util.HashMap;
import java.util.Map;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {
    private EditText email,nickname;
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
        android_id = tManager.getDeviceId();

        email = (EditText) findViewById(R.id.email);
        nickname = (EditText) findViewById(R.id.nickname);
        sign_in_register = (Button) findViewById(R.id.email_sign_in_button);

        // using volley lib to create request
        requestQueue = Volley.newRequestQueue(this);

        sign_in_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {


                    // handle the response of the server (success or error)
                    @Override
                    public void onResponse(String response) {
                            System.out.println("ANTWORT VOM LOGIN: " + response);
                            Boolean ok = Boolean.parseBoolean(response);
                            System.out.println(ok);
                            if(!ok){
                                Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }else{
                                Toast.makeText(getApplicationContext(), "Error" , Toast.LENGTH_SHORT).show();
                            }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){

                    // set of parameters in a hashmap, which will be send to the php file (server side)
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String,String> hashMap = new HashMap<String, String>();

                        hashMap.put("email",email.getText().toString());
                        hashMap.put("nickname",nickname.getText().toString());
                        hashMap.put("android_id", android_id);


                        return hashMap;
                    }
                };

                requestQueue.add(request);
            }
        });
    }
}

