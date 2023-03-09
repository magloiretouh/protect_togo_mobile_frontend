package com.agia.protecttogo.ui.map;

import com.agia.protecttogo.MainActivity;
import  com.agia.protecttogo.R;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.agia.protecttogo.databinding.FragmentMapBinding;
import com.agia.protecttogo.ui.DenounceActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MapFragment extends Fragment {

    private FragmentMapBinding binding;
    FloatingActionButton denounce, denounce_call, denounce_form;
    boolean status_btns = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        // set floating buttons
        denounce = view.findViewById(R.id.denounce_float_btn);
        denounce_call = view.findViewById(R.id.denounce_call);
        denounce_form = view.findViewById(R.id.denounce_form);

        // add listener to denounce button
        denounce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(status_btns) {
                    denounce_call.show();
                    denounce_form.show();
                    status_btns = false;
                } else {
                    denounce_call.hide();
                    denounce_form.hide();
                    status_btns = true;
                }
            }
        });

        // add listener to call button
        denounce_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:1011"));

                if (ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    Snackbar.make(v, "Please grant phone call permission to Protect Togo !", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                startActivity(callIntent);
            }
        });

        // add listener to form button
        denounce_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), DenounceActivity.class));
            }
        });


        //Initialize Google Maps on the Fragment Map
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map);

        //Listen to Map events
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                //When the map is ready
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(@NonNull LatLng latLng) {
                        // When the user click on the map
                        // Initialize Marker options
                        MarkerOptions markerOptions = new MarkerOptions();
                        // Set Marker Position
                        markerOptions.position(latLng);
                        // Set Title of the Marker
                        markerOptions.title(latLng.latitude + " : " + latLng.longitude);
                        // Remove al marker
                        googleMap.clear();
                        // Animate the zoom to the marker
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                        // Add the marker on the map
                        googleMap.addMarker(markerOptions);
                    }
                });
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