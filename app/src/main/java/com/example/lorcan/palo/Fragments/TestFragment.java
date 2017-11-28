package com.example.lorcan.palo.Fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.lorcan.palo.AsyncTasks.TestAsyncTask;
import com.example.lorcan.palo.MyApplicationContext;
import com.example.lorcan.palo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment {

    Button button;
    TextView textView;
    String android_id;

    public TestFragment() {
        // Required empty public constructor
    }

    @SuppressLint("HardwareIds")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test, container, false);

        button = (Button) view.findViewById(R.id.buttonUser);
        textView = (TextView) view.findViewById(R.id.textViewUser);

        TelephonyManager tManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(MyApplicationContext.getAppContext(), android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }

        if (tManager != null) {
            android_id = tManager.getDeviceId();
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestAsyncTask testAsyncTask = new TestAsyncTask(TestFragment.this.getContext(), textView, button, android_id);
                testAsyncTask.execute();
                button.setEnabled(false);
            }
        });

        return view;
    }
}
