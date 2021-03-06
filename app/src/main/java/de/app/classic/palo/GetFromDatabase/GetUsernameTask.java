package de.app.classic.palo.GetFromDatabase;

import android.os.AsyncTask;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import de.app.classic.palo.Fragments.ProfileFragment;
import de.app.classic.palo.MainActivity;
import de.app.classic.palo.MyApplicationContext;

import java.util.HashMap;
import java.util.Map;

import de.app.classic.palo.Fragments.ProfileFragment;
import de.app.classic.palo.MainActivity;
import de.app.classic.palo.MyApplicationContext;

public class GetUsernameTask extends AsyncTask<Void, Void, Void> {
    private static final String STR_URL = "http://palo.square7.ch/getUsername.php";
    String android_id;
    ProfileFragment profileFragment;
    TextView tvUsername;
    MainActivity mainActivity;
    public String name;

    public GetUsernameTask(String android_id, ProfileFragment profileFragment, TextView tvUsername) {
        this.android_id = android_id;
        this.profileFragment = profileFragment;
        this.tvUsername = tvUsername;
    }

    public GetUsernameTask() {

    }

    public GetUsernameTask(String android_id, MainActivity mainActivity) {
        this.android_id = android_id;
        this.mainActivity = mainActivity;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        final RequestQueue requestQueue = Volley.newRequestQueue(MyApplicationContext.getAppContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, STR_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if (profileFragment != null) {
                    name = response;
                    handleResponseProfile(response);
                    onPostExecute(null);
                }
                else {
                    handleResponse(response);
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

        requestQueue.add(stringRequest);
        return null;
    }

    private void handleResponse(String response) {
        this.cancel(true);
        mainActivity.setUsernameInNav(response);
    }

    private void handleResponseProfile(String response) {
        this.cancel(true);
        tvUsername.setText(response);
    }
}
