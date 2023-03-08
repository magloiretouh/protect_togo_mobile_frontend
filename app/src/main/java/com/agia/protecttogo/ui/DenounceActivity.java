package com.agia.protecttogo.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.agia.protecttogo.R;
import com.agia.protecttogo.databinding.ActivityMainBinding;

import android.app.ActionBar;
import android.os.Bundle;

public class DenounceActivity extends AppCompatActivity {
//
//    private DenounceActivity binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denounce);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.denounce_toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}