package com.example.lorcan.palo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OneCheckedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OneCheckedFragment extends Fragment {

    /*
     * Define a string TAG to pass the data to the log.
     */

    private final String TAG = this.getClass().getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public OneCheckedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OneCheckedFragment.
     */
    // TODO: Rename and change types and number of parameters

    /*
     * Factory method is created by the first checked option.
     * This is useful to pass data from one activity or fragment to another.
     */

    public static OneCheckedFragment newInstance(String param1, String param2) {
        OneCheckedFragment fragment = new OneCheckedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        /*
         * Display the parameters in the log,
         * using the string TAG defined above.
         */

        Log.d(TAG, mParam1);
        Log.d(TAG, mParam2);

        /*
         * Create and return view here as well.
         */

        View view = inflater.inflate(R.layout.fragment_one_checked, container, false);

        /*
         * Create an ArrayList to set the data for the listView.
         */

        ArrayList<Product> products = new ArrayList<>();
        Product product1 = new Product("Water", 100, 0.5);
        Product product2 = new Product("Juice", 30, 1.5);
        Product product3 = new Product("Coke", 400, 1.0);
        Product product4 = new Product("Beer", 65, 2.5);

        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);

        /*
         * Create an ArrayAdapter to give put the ArrayList items into the ListView.
         */

        ArrayAdapter<Product> adapter = new ArrayAdapter<Product>(
                OneCheckedFragment.this.getActivity(),
                android.R.layout.simple_list_item_1,
                products);

        ListView lvProduct = (ListView)view.findViewById(R.id.lvProduct);
        lvProduct.setAdapter(adapter);


        return view;
    }
}
