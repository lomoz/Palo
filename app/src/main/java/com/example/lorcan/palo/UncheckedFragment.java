package com.example.lorcan.palo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class UncheckedFragment extends Fragment {


    public UncheckedFragment() {
        // Required empty public constructor
    }

    /*
     * Declare elements here to handle them in the onCreateView method.
     */

    EditText etStatus, etStudyCourse;
    Button btnOK;

    String status = "I'm using Palo!";

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

        /*
         * Create an onClickListener for the button.
         *
         * To make i.e. a correct Toast it's important to replace "this"
         * with "UncheckedFragment.this.getActivity()"!
         */

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*
                if (etStatus.getText().toString().equals("palo") && etStudyCourse.getText().toString().equals("palo")) {
                    Toast.makeText(UncheckedFragment.this.getActivity(), "Correct", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(UncheckedFragment.this.getActivity(), "Incorrect", Toast.LENGTH_SHORT).show();
                }
                */

                getStatus();
            }
        });

        return view;
    }

    public String getStatus() {

        status = etStatus.getText().toString();

        if (status == null || status.isEmpty() ) {
            status = "I'm using Palo!";
        }

        MapFragment mapFragment = new MapFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("STATUS", status);
        mapFragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.relativelayout_for_fragments, mapFragment);
        fragmentTransaction.commit();

        return status;
    }

}
