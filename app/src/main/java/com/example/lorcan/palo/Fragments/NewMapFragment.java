package com.example.lorcan.palo.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lorcan.palo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewMapFragment extends Fragment {


    public NewMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_map, container, false);

        return  view;
    }

}
