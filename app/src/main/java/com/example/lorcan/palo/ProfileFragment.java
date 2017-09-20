package com.example.lorcan.palo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }

    /*
     * Declare elements here to handle them in the onCreateView method.
     */

    EditText etStatus, etStudyCourse;
    Button btnOK;

    String status = "";
    String studyCourse = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getStatusFromDB get = new getStatusFromDB();

        // Create and return a new View element here.
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Use the created view to get the elements from the xml file.
        etStatus = (EditText)view.findViewById(R.id.etStatus);
        etStudyCourse = (EditText)view.findViewById(R.id.etStudyCourse);
        btnOK = (Button)view.findViewById(R.id.btnOK);

        etStatus.setText(status);

        /*
         * Create an onClickListener for the button.
         *
         * To make i.e. a correct Toast it's important to replace "this"
         * with "ProfileFragment.this.getActivity()"!
         */

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MapFragment mapFragment = new MapFragment();
                Bundle bundle = new Bundle();

                //check if editText status is empty or not.
                if (etStatus.getText().toString().isEmpty()) {
                    status = "no status";
                    Toast.makeText(ProfileFragment.this.getActivity(), "No status.", Toast.LENGTH_SHORT).show();
                }
                else {
                    status = etStatus.getText().toString();
                    Toast.makeText(ProfileFragment.this.getActivity(), status, Toast.LENGTH_SHORT).show();
                }

                //check if editText studyCourse is empty or not.
                if (etStudyCourse.getText().toString().isEmpty()) {
                    studyCourse = "no study course";
                    Toast.makeText(ProfileFragment.this.getActivity(), "No study course.", Toast.LENGTH_SHORT).show();
                }
                else {
                    studyCourse = etStudyCourse.getText().toString();
                    Toast.makeText(ProfileFragment.this.getActivity(), studyCourse, Toast.LENGTH_SHORT).show();
                }

                //bundle the data from status and study course to "send" them to MapFragment.java
                bundle.putString("status", status);
                bundle.putString("study course", studyCourse);

                //send status to database
                sendStatusToDB statusToDB = new sendStatusToDB();
                statusToDB.sendStatus("testmail@gmail.com", status, 5.33, 334.344);

                mapFragment.setArguments(bundle);

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.anim_slide_in_from_left, R.anim.anim_slide_out_from_left)
                        .replace(R.id.relativelayout_for_fragments,
                                mapFragment,
                                mapFragment.getTag()
                        ).commit();
            }
        });

        return view;
    }
    
}
