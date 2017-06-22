package com.example.lorcan.palo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;

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

        final ArrayList<Product> products = new ArrayList<>();
        Product product1 = new Product("Paul", 400, 0.5);
        Product product2 = new Product("Lorcan", 375, 1.5);
        Product product3 = new Product("Ragnar", 210, 1.0);
        Product product4 = new Product("Floki", 195, 2.5);
        Product product5 = new Product("Rollo", 155, 3.0);
        Product product6 = new Product("Björn", 130, 1.25);
        Product product7 = new Product("Athelstan", 90, 0.75);

        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);
        products.add(product5);
        products.add(product6);
        products.add(product7);

        /*
         * Bind the data to the Fundapter library.
         */

        BindDictionary<Product> dictionary = new BindDictionary<>();
        dictionary.addStringField(R.id.tvName, new StringExtractor<Product>() {
            @Override
            public String getStringValue(Product product, int position) {
                return product.getName();
            }
        });

        dictionary.addStringField(R.id.tvQty, new StringExtractor<Product>() {
            @Override
            public String getStringValue(Product product, int position) {
                return "" + product.getQty();
            }
        });

        dictionary.addStringField(R.id.tvPrice, new StringExtractor<Product>() {
            @Override
            public String getStringValue(Product product, int position) {
                return "" + product.getPrice();
            }
        });

        /*
         * Create an ArrayAdapter to give put the ArrayList items into the ListView.
         * Replace the ArrayAdapter with the Fundapter.

        ArrayAdapter<Product> adapter = new ArrayAdapter<Product>(
                OneCheckedFragment.this.getActivity(),
                android.R.layout.simple_list_item_1,
                products);

         */

        FunDapter adapter = new FunDapter(
                OneCheckedFragment.this.getActivity(),
                products,
                R.layout.product_layout,
                dictionary);

        ListView lvProduct = (ListView)view.findViewById(R.id.lvProduct);
        lvProduct.setAdapter(adapter);
        lvProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Product selectedProduct = products.get(position);
                Toast.makeText(OneCheckedFragment.this.getActivity(), selectedProduct.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
