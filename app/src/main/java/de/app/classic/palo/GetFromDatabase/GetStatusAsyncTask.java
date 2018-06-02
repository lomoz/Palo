package de.app.classic.palo.GetFromDatabase;

import android.os.AsyncTask;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import de.app.classic.palo.Fragments.ProfileFragment;
import de.app.classic.palo.MapFragment;
import de.app.classic.palo.MyApplicationContext;

import java.util.HashMap;
import java.util.Map;

import de.app.classic.palo.Fragments.ProfileFragment;
import de.app.classic.palo.MapFragment;
import de.app.classic.palo.MyApplicationContext;

public class GetStatusAsyncTask extends AsyncTask<Void, Void, Void> {
    private static final String strUrl = "http://palo.square7.ch/getOneStatus.php";
    String android_id;
    MapFragment mapFragment;
    EditText etStatusInMap;
    ProfileFragment profileFragment;
    EditText etStatus;
    public GetStatusAsyncTask(String android_id, MapFragment mapFragment, EditText etStatusInMap) {
        this.android_id = android_id;
        this.mapFragment = mapFragment;
        this.etStatusInMap = etStatusInMap;
    }

    public GetStatusAsyncTask(String android_id, ProfileFragment profileFragment, EditText etStatus) {
        this.android_id = android_id;
        this.profileFragment = profileFragment;
        this.etStatus = etStatus;
    }

    @Override
    protected void onPreExecute() {

        if (profileFragment != null) {
            etStatus.setEnabled(false);
        }

        else if (mapFragment != null) {
            etStatusInMap.setEnabled(false);
        }
    }

    @Override
    protected Void doInBackground(Void... voids) {

        RequestQueue requestQueue = Volley.newRequestQueue(MyApplicationContext.getAppContext());
        StringRequest request = new StringRequest(Request.Method.POST, strUrl, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                System.out.println("This is what the PHP File responses: " + response);

                if (profileFragment != null) {
                    handleResponse(response);
                    onPostExecute(null);
                }

                else if (mapFragment != null) {
                    handleResponseMap(response);
                    onPostExecute(null);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {

            // set of parameters in a HashMap, which will be send to the php file (server side)
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("android_id", android_id);
                return hashMap;
            }
        };
        requestQueue.add(request);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        if (profileFragment != null) {
            etStatus.setEnabled(true);
        }

        else if (mapFragment != null) {
            etStatusInMap.setEnabled(true);
        }
        this.cancel(true);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        System.out.println("GET STATUS FROM DB IS CANCELLED");
    }

    private void handleResponse(String response){
        etStatus.setText(response);
        etStatus.setSelection(response.length());
    }

    private void handleResponseMap(String response){
        etStatusInMap.setText(response);
        etStatusInMap.setSelection(response.length());
    }
}