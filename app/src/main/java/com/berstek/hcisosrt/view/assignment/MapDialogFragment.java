package com.berstek.hcisosrt.view.assignment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.berstek.hcisosrt.R;
import com.berstek.hcisosrt.custom_classes.SupportCustomDialogFragment;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.concurrent.ExecutionException;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapDialogFragment extends SupportCustomDialogFragment implements OnMapReadyCallback {

  private GoogleMap map;
  private View view;

  private String userPhotoUrl;

  public MapDialogFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    view = inflater.inflate(R.layout.fragment_map_dialog, container, false);

    SupportMapFragment mapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager()
        .findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);

    return view;
  }

  private boolean ready = false;
  private Marker teamMarker, userMarker;

  @Override
  public void onMapReady(GoogleMap googleMap) {
    map = googleMap;
    LatLng latLng = new LatLng(12.8797, 121.77);

    BitmapDescriptor customMarker =
        BitmapDescriptorFactory.fromResource(R.drawable.img_ambulance);
    teamMarker = map.addMarker(new MarkerOptions().position(latLng).title("RT").icon(customMarker));

    userMarker = map.addMarker(new MarkerOptions().position(latLng).
        title("User"));


    ready = true;

  }


  public void updateTeamMarker(double lat, double lang) {
    if (ready) {
      LatLng latLng = new LatLng(lat, lang);
      teamMarker.setPosition(latLng);

      CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(latLng);
      map.animateCamera(cameraUpdate);
    }
  }

  public void updateUserMarker(double lat, double lang) {
    if (ready) {
      LatLng latLng = new LatLng(lat, lang);
      userMarker.setPosition(latLng);
    }
  }

  public void setUserPhotoUrl(String userPhotoUrl) {
    this.userPhotoUrl = userPhotoUrl;
  }
}
