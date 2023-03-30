package com.agia.protecttogo.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.agia.protecttogo.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import id.ionbit.ionalert.IonAlert;

public class DenounceActivity extends AppCompatActivity {
//
//    private DenounceActivity binding;
    EditText dn_full_name;
    EditText dn_age;
    Spinner dn_occupation;
    EditText dn_address = null;
    Spinner dn_relation_victime;
    EditText vt_full_name;
    EditText vt_age;
    EditText vt_father_fullname;
    EditText vt_mother_full_name;
    EditText vt_residence;
    RadioGroup vt_ishandicap;
    Spinner vt_typehandicap;
    Spinner type_violence;
    Spinner reason_violence;
    RadioGroup is_in_danger;
    Spinner type_danger;
    Spinner place_violence;
    Spinner author_violence;
    Spinner witnesses;
    RadioGroup separate_child_adult;
    EditText other_details;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denounce);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.denounce_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dn_full_name = findViewById(R.id.nom_prenom_edittext);
        dn_age = findViewById(R.id.age_edittext);
        dn_occupation = findViewById(R.id.occupation_spinner);
        dn_address = null;
        dn_relation_victime = findViewById(R.id.lien_spinner);
        vt_full_name = findViewById(R.id.victime_nom_prenom_edittext);
        vt_age = findViewById(R.id.victime_age_edittext);
        vt_father_fullname = findViewById(R.id.nom_pere_edittext);
        vt_mother_full_name = findViewById(R.id.nom_mere_edittext);
        vt_residence = findViewById(R.id.residence_edittext);
        vt_ishandicap = findViewById(R.id.handicap_radiogroup);
        vt_typehandicap = findViewById(R.id.type_handicap_spinner);
        type_violence = findViewById(R.id.type_violence_spinner);
        reason_violence = findViewById(R.id.raison_violence_spinner);
        is_in_danger = findViewById(R.id.enfant_en_danger_radio_group);
        type_danger = findViewById(R.id.danger_spinner);
        place_violence = findViewById(R.id.violence_lieu_spinner);
        author_violence = findViewById(R.id.violence_auteur_spinner);
        witnesses = findViewById(R.id.temoins_spinner);
        separate_child_adult = findViewById(R.id.sep_group);
        other_details = findViewById(R.id.edit_text_violence_details);

        Button submit = (Button) findViewById(R.id.btn_submit);
        Button cancel = (Button) findViewById(R.id.btn_cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clean_fields();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Instantiate the RequestQueue.
//                if (!validateName() | !validateEmail() | !validateMessage() | !validateType()) {
//                    return;
//                }
                RequestQueue queue = Volley.newRequestQueue(DenounceActivity.this);
                JSONObject jsonBody = null;
                try {
                    jsonBody = new JSONObject();
                    jsonBody.put("dn_full_name", dn_full_name.getText().toString());
                    jsonBody.put("dn_age", dn_age.getText().toString());
                    jsonBody.put("dn_occupation", dn_occupation.getSelectedItem().toString());
                    jsonBody.put("dn_address", "789 Maple Ave, Anytown, USA");
                    jsonBody.put("dn_relation_victime", dn_relation_victime.getSelectedItem().toString());
                    jsonBody.put("vt_full_name", vt_full_name.getText().toString());
                    jsonBody.put("vt_age", vt_age.getText().toString());
                    jsonBody.put("vt_father_fullname", vt_father_fullname.getText().toString());
                    jsonBody.put("vt_mother_full_name", vt_mother_full_name.getText().toString());
                    jsonBody.put("vt_residence", vt_residence.getText().toString());
                    RadioButton ishandicap = (RadioButton) findViewById(vt_ishandicap.getCheckedRadioButtonId());
                    jsonBody.put("vt_ishandicap", ishandicap.getText().toString());
                    jsonBody.put("vt_typehandicap", vt_typehandicap.getSelectedItem().toString());
                    jsonBody.put("type_violence", type_violence.getSelectedItem().toString());
                    jsonBody.put("reason_violence", reason_violence.getSelectedItem().toString());
                    RadioButton indanger = (RadioButton) findViewById(is_in_danger.getCheckedRadioButtonId());
                    jsonBody.put("is_in_danger", (indanger.getText().toString().equalsIgnoreCase("Oui") ? true : false));
                    jsonBody.put("type_danger", type_danger.getSelectedItem().toString());
                    jsonBody.put("place_violence", place_violence.getSelectedItem().toString());
                    jsonBody.put("author_violence", author_violence.getSelectedItem().toString());
                    jsonBody.put("witnesses", witnesses.getSelectedItem().toString());
                    RadioButton sep_child_adult = (RadioButton) findViewById(separate_child_adult.getCheckedRadioButtonId());
                    jsonBody.put("separate_child_adult", (sep_child_adult.getText().toString().equalsIgnoreCase("Oui") ? true : false));
                    jsonBody.put("other_details", other_details.getText().toString());
                } catch (JSONException e) {
//                    Exception handle
                }

                final String mRequestBody = jsonBody.toString();

                String url = getResources().getString(R.string.api_url)+"/violence-report";
                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display Success Message
                                clean_fields();
                                new IonAlert(DenounceActivity.this, IonAlert.SUCCESS_TYPE)
                                        .setTitleText("Succès !!!")
                                        .setContentText("Votre Signalement a été soumis avec succès.")
                                        .show();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new IonAlert(DenounceActivity.this, IonAlert.ERROR_TYPE)
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
    }

    private void clean_fields() {
        dn_full_name.setText(null);
        dn_age.setText(null);
        dn_occupation.clearFocus();
        dn_address = null;
        dn_relation_victime.clearFocus();
        vt_full_name.setText(null);
        vt_age.setText(null);
        vt_father_fullname.setText(null);
        vt_mother_full_name.setText(null);
        vt_residence.setText(null);
        vt_ishandicap.clearCheck();
        vt_typehandicap.clearFocus();
        type_violence.clearFocus();
        reason_violence.clearFocus();
        is_in_danger.clearCheck();
        type_danger.clearFocus();
        place_violence.clearFocus();
        author_violence.clearFocus();
        witnesses.clearFocus();
        separate_child_adult.clearCheck();
        other_details.setText(null);
    }

}