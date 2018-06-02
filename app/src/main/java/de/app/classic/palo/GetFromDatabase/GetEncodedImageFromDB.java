package de.app.classic.palo.GetFromDatabase;

import android.os.AsyncTask;

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

public class GetEncodedImageFromDB {

    private String android_id;
    private ProfileFragment profileFragment;
    private MainActivity mainActivity;
    public GetImageTask getImgTask;

    public GetEncodedImageFromDB() {

    }

    public void getResponseEncodedImage(String android_id, ProfileFragment profileFragment) {
        this.android_id = android_id;
        this.profileFragment = profileFragment;
        getImgTask = new GetImageTask(android_id, profileFragment);
        getImgTask.execute();
    }

    public void getResponseEncodedImage(String android_id, MainActivity mainActivity) {
        this.android_id = android_id;
        this.mainActivity = mainActivity;
        getImgTask = new GetImageTask(android_id, mainActivity);
                getImgTask.execute();
    }


}
