package com.agia.protecttogo.ui.opinion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.agia.protecttogo.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OpinionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OpinionFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_opinion, container, false);
    }
}