package de.app.classic.palo.GetFromDatabase;

import android.annotation.SuppressLint;
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

/**
 * Created by Win10 Home x64 on 17.01.2018.
 */

public class GetProfilInfoFromDB {


    public ProfilAcitivityTask profilAcitivityTask;
    String name;

    public void getInfo(ProfilActivity profilActivity, String name){
        ArrayList<String> list = new ArrayList<>();
        this.name = name;
        profilAcitivityTask = new ProfilAcitivityTask(profilActivity, name);
        profilAcitivityTask.execute();
    }




}
