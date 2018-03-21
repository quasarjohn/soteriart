package com.berstek.hcisosrt.presentor;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.berstek.hcisosrt.firebase_da.DA;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ReverseGeocodePresentor {

  private Activity activity;

  public ReverseGeocodePresentor(Activity activity) {
    this.activity = activity;
  }

  public void getAddressFromLocation(double lat, double lang) {
    Thread thread = new Thread() {
      @Override
      public void run() {
        Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
        String result;
        try {
          List<Address> list = geocoder.getFromLocation(lat, lang, 1);


          if (list != null && list.size() > 0) {
            Address address = list.get(0);
            // sending back first address line and locality
            result = address.getAddressLine(0) + ", " + address.getLocality();
            reverseGeocodePresentorCallback.onReverseGeocode(result);
          }
        } catch (IOException e) {
          Log.e(null, "unable to connect", e);
        }
      }
    };
    thread.start();
  }

  private ReverseGeocodePresentorCallback reverseGeocodePresentorCallback;

  public interface ReverseGeocodePresentorCallback {
    void onReverseGeocode(String address);
  }

  public void setReverseGeocodePresentorCallback(ReverseGeocodePresentorCallback reverseGeocodePresentorCallback) {
    this.reverseGeocodePresentorCallback = reverseGeocodePresentorCallback;
  }
}
