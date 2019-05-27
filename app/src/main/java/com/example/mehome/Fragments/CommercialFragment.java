package com.example.mehome.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mehome.R;


public class CommercialFragment extends Fragment {

    private static final String KEY_TITLE = "Commercial";


    public CommercialFragment() {
        // Required empty public constructor
    }


    public static CommercialFragment newInstance(String param1, String param2) {
        CommercialFragment fragment = new CommercialFragment();
        Bundle args = new Bundle();
        args.putString(KEY_TITLE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_commercial_fragmnet, container, false);
    }

}
