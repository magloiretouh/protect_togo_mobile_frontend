package com.agia.protecttogo.ui.opinion;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.agia.protecttogo.R;
import androidx.lifecycle.ViewModelProvider;

import com.agia.protecttogo.R;
import com.agia.protecttogo.databinding.FragmentOpinionBinding;
import com.google.android.material.snackbar.Snackbar;

public class OpinionFragment extends Fragment {

    private FragmentOpinionBinding binding;

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = FragmentOpinionBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        Button button = (Button) view.findViewById(R.id.opinion_submit_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Snackbar.make(view, "Submit the form data", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}