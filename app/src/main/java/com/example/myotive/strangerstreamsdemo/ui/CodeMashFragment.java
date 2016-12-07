package com.example.myotive.strangerstreamsdemo.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myotive.strangerstreamsdemo.R;

public class CodeMashFragment extends Fragment {

    public static CodeMashFragment newInstance() {
        return new CodeMashFragment();
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_codemash, container, false);
    }
}
