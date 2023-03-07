package com.agia.protecttogo.ui.map;

import  com.agia.protecttogo.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.agia.protecttogo.databinding.FragmentMapBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment {

    private FragmentMapBinding binding;

    @Override
    public View onCreateView (LayoutInflater inflater,
                              ViewGroup container,
                              Bundle savedInstanceState) {
        binding = FragmentMapBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        
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