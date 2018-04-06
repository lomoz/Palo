package com.example.lorcan.palo.Fragments.OptionsMenu;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.lorcan.palo.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShareFragment extends Fragment {


    public ShareFragment() {
        // Required empty public constructor
    }

    ImageButton btn_share;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share, container, false);

        btn_share = (ImageButton) view.findViewById(R.id.imgBtn_share);

        btn_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_subject));
                sendIntent.putExtra(Intent.EXTRA_TEXT, "https://uni-duesseldorf.sciebo.de/f/50457499");
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Share using"));
            }
        });

        return view;
    }
}
