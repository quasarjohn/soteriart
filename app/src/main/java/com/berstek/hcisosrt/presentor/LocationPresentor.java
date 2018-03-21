package com.berstek.hcisosrt.presentor;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.location.Location;
import android.os.Looper;

import com.berstek.hcisosrt.firebase_da.EmergencyDA;
import com.berstek.hcisosrt.model.Emergency;
import com.berstek.hcisosrt.model.UserLocation;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;


public class LocationPresentor implements ReverseGeocodePresentor.ReverseGeocodePresentorCallback {

  /*
  gets the user location and updates the data in firebase
   */

  private ReverseGeocodePresentor reverseGeocodePresentor;

  private FusedLocationProviderClient fusedLocationProviderClient;
  private LocationRequest mLocationRequest;
  private LocationPresentorCallback locationPresentorCallback;

  private EmergencyDA emergencyDA;

  private String details, selection;

  private long timeStamp;

  private Activity activity;

  public LocationPresentor(Activity activity) {
    this.activity = activity;
    timeStamp = System.currentTimeMillis();
    emergencyDA = new EmergencyDA();
    reverseGeocodePresentor = new ReverseGeocodePresentor(activity);
    reverseGeocodePresentor.setReverseGeocodePresentorCallback(this);

    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);

    mLocationRequest = new LocationRequest();
    mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    mLocationRequest.setInterval(1000 * 5);
    mLocationRequest.setFastestInterval(1000);

    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
    builder.addLocationRequest(mLocationRequest);
    LocationSettingsRequest locationSettingsRequest = builder.build();

    // Check whether location settings are satisfied
    // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
    SettingsClient settingsClient = LocationServices.getSettingsClient(activity);
    settingsClient.checkLocationSettings(locationSettingsRequest);
  }


  @SuppressLint("MissingPermission")
  public void getLocationUpdates() {

    fusedLocationProviderClient.requestLocationUpdates(mLocationRequest, new LocationCallback() {
          @Override
          public void onLocationResult(LocationResult locationResult) {
            // do work here
            onLocationChanged(locationResult.getLastLocation());
          }
        },
        Looper.myLooper());
  }

  private void onLocationChanged(Location location) {
    UserLocation userLocation = new UserLocation();
    userLocation.setLatitude(location.getLatitude());
    userLocation.setLongitude(location.getLongitude());
    locationPresentorCallback.onLocationUpdated(userLocation);

    reverseGeocodePresentor.getAddressFromLocation(userLocation.getLatitude(),
        userLocation.getLongitude());
  }


  @Override
  public void onReverseGeocode(String address) {
    locationPresentorCallback.onReverseGeocode(address);
  }


  public interface LocationPresentorCallback {
    void onLocationUpdated(UserLocation userLocation);

    void onReverseGeocode(String address);
  }

  public void setLocationPresentorCallback(LocationPresentorCallback locationPresentorCallback) {
    this.locationPresentorCallback = locationPresentorCallback;
  }
}
