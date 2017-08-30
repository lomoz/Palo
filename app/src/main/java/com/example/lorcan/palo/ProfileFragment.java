package com.example.lorcan.palo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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

    String status = "default";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /*
         * Create and return a new View element here.
         */

        View view = inflater.inflate(R.layout.fragment_unchecked, container, false);

        /*
         * Use the created view to get the elements from the xml file.
         */

        etStatus = (EditText)view.findViewById(R.id.etStatus);
        etStudyCourse = (EditText)view.findViewById(R.id.etStudyCourse);
        btnOK = (Button)view.findViewById(R.id.btnOK);

        etStatus.setText(getStatus());

        /*
         * Create an onClickListener for the button.
         *
         * To make i.e. a correct Toast it's important to replace "this"
         * with "ProfileFragment.this.getActivity()"!
         */

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (etStatus.getText().toString().isEmpty()) {
                    status = "leer";
                    Toast.makeText(ProfileFragment.this.getActivity(), "No status", Toast.LENGTH_SHORT).show();
                }
                else {
                    status = etStatus.getText().toString();
                    Toast.makeText(ProfileFragment.this.getActivity(), status, Toast.LENGTH_SHORT).show();
                }

                /*
                if (etStatus.getText().toString().equals("palo") && etStudyCourse.getText().toString().equals("palo")) {
                    Toast.makeText(ProfileFragment.this.getActivity(), "Correct", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(ProfileFragment.this.getActivity(), "Incorrect", Toast.LENGTH_SHORT).show();
                }


                if (etStatus.getText().toString().isEmpty()) {
                    status = "I'm using Palo!";
                }
                else {
                    status = etStatus.getText().toString();
                }

                MapFragment mapFragment = new MapFragment();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle = new Bundle();
                bundle.putString("STATUS", status);
                mapFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.relativelayout_for_fragments, mapFragment);
                fragmentTransaction.commit();

                */
            }
        });

        return view;
    }

    public String getStatus() {
        
        return status;
    }
}
