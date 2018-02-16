package com.example.lorcan.palo.Fragments.OptionsMenu;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.lorcan.palo.MyApplicationContext;
import com.example.lorcan.palo.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {

    private String android_id;

    EditText et_topic, et_message;
    Button btn_contact;
    String contact_topic, contact_message;

    public ContactFragment() {
        // Required empty public constructor
    }

    @SuppressLint("HardwareIds")
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        et_topic = (EditText) view.findViewById(R.id.et_enter_topic);
        et_message = (EditText) view.findViewById(R.id.et_enter_message_multiline2);
        btn_contact = (Button) view.findViewById(R.id.btn_send_message);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;

        int lines = (int) ((height * 0.30) / 55);
        et_message.setLines(lines);

        TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(MyApplicationContext.getAppContext(), android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

            return null;
        }

        if (telephonyManager != null) {
            android_id = telephonyManager.getDeviceId();
        }

        btn_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (et_topic.getText().toString().isEmpty() && et_message.getText().toString().isEmpty()) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(ContactFragment.this.getActivity());
                    builder.setTitle(R.string.alert_empty_contact_title);
                    builder.setMessage(R.string.alert_empty_contact_message);
                    builder.show();
                }

                else if (et_topic.getText().toString().isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ContactFragment.this.getActivity());
                    builder.setTitle(R.string.alert_empty_contact_topic_title);
                    builder.setMessage(R.string.alert_empty_contact_topic_message);
                    builder.show();
                }

                else if (et_message.getText().toString().isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ContactFragment.this.getActivity());
                    builder.setTitle(R.string.alert_empty_contact_body_title);
                    builder.setMessage(R.string.alert_empty_contact_body_message);
                    builder.show();
                }

                else {
                    contact_topic = et_topic.getText().toString();
                    contact_message = et_message.getText().toString();

                    System.out.println("Contact Message ANDROID ID: " + android_id);
                    System.out.println("Contact Message TOPIC: " + contact_topic);
                    System.out.println("Contact Message MESSAGE: " + contact_message);
                }
            }
        });

        return view;
    }
}
