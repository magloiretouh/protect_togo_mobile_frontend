package com.agia.protecttogo.ui.opinion;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.agia.protecttogo.R;
import androidx.lifecycle.ViewModelProvider;

import com.agia.protecttogo.R;
import com.agia.protecttogo.databinding.FragmentOpinionBinding;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import id.ionbit.ionalert.IonAlert;

public class OpinionFragment extends Fragment {

    private FragmentOpinionBinding binding;
    RadioGroup opinion_type;
    EditText opinion_name;
    EditText opinion_email;
    EditText opinion_text;

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = FragmentOpinionBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        opinion_type = (RadioGroup) binding.opinionType;
        opinion_name = (EditText) binding.opinionName;
        opinion_email = (EditText) binding.opinionEmail;
        opinion_text = (EditText) binding.opinionText;

        Button button = (Button) view.findViewById(R.id.opinion_submit_button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Instantiate the RequestQueue.
                if (!validateName() | !validateEmail() | !validateMessage() | !validateType()) {
                    return;
                }
                RequestQueue queue = Volley.newRequestQueue(getActivity());
                JSONObject jsonBody = null;
                try {
                    jsonBody = new JSONObject();
                    RadioButton radioButton = view.findViewById(opinion_type.getCheckedRadioButtonId());
                    jsonBody.put("type", radioButton.getText().toString());
                    jsonBody.put("full_name", opinion_name.getText().toString());
                    jsonBody.put("email", opinion_email.getText().toString());
                    jsonBody.put("message", opinion_text.getText().toString());
                } catch (JSONException e) {
//                    Exception handle
                }

                final String mRequestBody = jsonBody.toString();

                String url = getResources().getString(R.string.api_url)+"/opinions";
                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display Success Message
                                opinion_type.clearCheck();
                                opinion_name.setText(null);
                                opinion_email.setText(null);
                                opinion_text.setText(null);
                                new IonAlert(getActivity(), IonAlert.SUCCESS_TYPE)
                                        .setTitleText("Succès !!!")
                                        .setContentText("Votre Opinion a été soumis avec succès.")
                                        .show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new IonAlert(getActivity(), IonAlert.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Un problème est survenu!")
                                .show();
                    }
                }) {
                    @Override
                    public String getBodyContentType() {
                        return "application/json; charset=utf-8";
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        try {
                            return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            return null;
                        }
                    }
                };

                // Add the request to the RequestQueue.
                queue.add(stringRequest);
            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private boolean validateName() {
        String val = opinion_name.getText().toString().trim();
        if (val.isEmpty()) {
            opinion_name.setError("Ce champ ne peut pas être vide");
            return false;
        } else {
            opinion_name.setError(null);
            return true;
        }
    }

    private boolean validateEmail() {
        String val = opinion_email.getText().toString().trim();
        String checkEmail = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";
        if (val.isEmpty()) {
            opinion_email.setError("Ce champ ne peut pas être vide");
            return false;
        } else if (!val.matches(checkEmail)) {
            opinion_email.setError("Email Invalide!");
            return false;
        } else {
            opinion_email.setError(null);
            return true;
        }
    }

    private boolean validateMessage() {
        String val = opinion_text.getText().toString().trim();
        if (val.isEmpty()) {
            opinion_text.setError("Ce champ ne peut pas être vide");
            return false;
        } else {
            opinion_text.setError(null);
            return true;
        }
    }

    private boolean validateType() {
        if (opinion_type.getCheckedRadioButtonId() == -1) {
            Toast.makeText(getActivity(), "Prière Selectionner le Type D'Opinion!", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}