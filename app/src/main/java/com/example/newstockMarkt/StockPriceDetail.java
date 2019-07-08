package com.example.newstockMarkt;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newstockmarkt.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class StockPriceDetail extends Fragment {


    public StockPriceDetail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stock_price_detail, container, false);
    }

}
