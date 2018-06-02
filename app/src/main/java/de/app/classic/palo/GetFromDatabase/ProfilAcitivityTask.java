package de.app.classic.palo.GetFromDatabase;

import android.os.AsyncTask;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import de.app.classic.palo.MyApplicationContext;
import de.app.classic.palo.ProfilActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.app.classic.palo.MyApplicationContext;
import de.app.classic.palo.ProfilActivity;

public class ProfilAcitivityTask extends AsyncTask<Void, Void, Void> {


    public RequestQueue requestQueue;
    private final String URL = "http://palo.square7.ch/getProfilInfo.php";
    public StringRequest request;
    private String name;
    private ProfilActivity profilActivity;


    public ProfilAcitivityTask(ProfilActivity profilActivity, String name) {
        this.profilActivity = profilActivity;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        requestQueue = Volley.newRequestQueue(MyApplicationContext.getAppContext());
        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                System.out.println("RESPONSE FROM PHP PROFIL: " + response);
                System.out.println(name);
                handleResponse(response, profilActivity);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {

            // set of parameters in a hashmap, which will be send to the php file (server side)
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<String, String>();
                while(name.substring(name.length() - 1).equals(" ")){
                    name = name.substring(0, name.length() - 1);
                }
                hashMap.put("nickname", name);


                return hashMap;
            }
        };

        requestQueue.add(request);
        return null;
    }

    public void handleResponse(String info, ProfilActivity profilActivity){
        this.cancel(true);
        ArrayList<String> list = new ArrayList<>(); //[bild, status, name]
        try {
            JSONObject jsonObject = new JSONObject(info);
            String bild = jsonObject.get("bild").toString();
            String status = jsonObject.get("status").toString();
            list.add(bild);
            list.add(status);
            list.add(name);
            profilActivity.setInfoToScreen(list);
        }catch (JSONException e){
            e.printStackTrace();
        }

    }
}
