package com.example.administrator.slidertest;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2015-03-25.
 */

public class TextFragment extends Fragment {

    public static TextFragment newInstance() {
        TextFragment fragment = new TextFragment();
        return fragment;
    }


    public TextFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_text, container, false);

        // Inflate the layout for this fragment
        return v;
    }
}