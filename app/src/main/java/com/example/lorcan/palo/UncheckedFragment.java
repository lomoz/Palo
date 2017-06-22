package com.example.lorcan.palo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


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

    EditText etEmail, etPassword;
    Button btnOK;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /*
         * Create and return a new View element here.
         */

        View view = inflater.inflate(R.layout.fragment_unchecked, container, false);


        /*
         * use the created view to get the elements from the xml file.
         */

        etEmail = (EditText)view.findViewById(R.id.etEmail);
        etPassword = (EditText)view.findViewById(R.id.etPassword);
        btnOK = (Button)view.findViewById(R.id.btnOK);

        return view;
    }

}
