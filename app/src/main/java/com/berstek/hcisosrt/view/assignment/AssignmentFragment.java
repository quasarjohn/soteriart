package com.berstek.hcisosrt.view.assignment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.assent.Assent;
import com.berstek.hcisosrt.R;
import com.berstek.hcisosrt.firebase_da.DA;
import com.berstek.hcisosrt.model.Emergency;
import com.berstek.hcisosrt.model.EtaDetails;
import com.berstek.hcisosrt.model.User;
import com.berstek.hcisosrt.model.UserLocation;
import com.berstek.hcisosrt.presentor.LocationPresentor;
import com.berstek.hcisosrt.utils.Utils;
import com.bumptech.glide.util.Util;

public class AssignmentFragment extends Fragment
    implements AssignmentPresentor.AssignmentPresentorCallback, View.OnClickListener,
    LocationPresentor.LocationPresentorCallback, EtaPresentor.EtaPresentorCallback {

  private View view;

  private String team_uid;

  private AssignmentPresentor assignmentPresentor;
  private EtaPresentor etaPresentor;

  private ImageView typeImg, dp;
  private TextView detailsTxt, addressTxt, etaTxt, nameTxt;

  private Button doneBtn, mapBtn;

  private MapDialogFragment mapDialogFragment;
  private LocationPresentor locationPresentor;

  private double lat, lang, lat1, lang1;

  public AssignmentFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    Assent.setActivity(getActivity(), getActivity());

    mapDialogFragment = new MapDialogFragment();

    etaPresentor = new EtaPresentor(getContext());
    etaPresentor.setEtaPresentorCallback(this);

    locationPresentor = new LocationPresentor(getActivity());
    locationPresentor.setLocationPresentorCallback(this);

    if (!Assent.isPermissionGranted(Assent.ACCESS_FINE_LOCATION)) {
      Assent.requestPermissions(result -> {
      }, 69, Assent.ACCESS_FINE_LOCATION);
    } else {
      locationPresentor.getLocationUpdates();
    }

    view = inflater.inflate(R.layout.fragment_assignment, container, false);
    dp = view.findViewById(R.id.dp);
    typeImg = view.findViewById(R.id.typeImg);
    detailsTxt = view.findViewById(R.id.detailsTxt);
    addressTxt = view.findViewById(R.id.addressTxt);
    etaTxt = view.findViewById(R.id.etaTxt);
    nameTxt = view.findViewById(R.id.nameTxt);
    doneBtn = view.findViewById(R.id.doneBtn);
    mapBtn = view.findViewById(R.id.mapBtn);

    mapBtn.setOnClickListener(this);
    doneBtn.setOnClickListener(this);

    team_uid = getArguments().getString("team_uid");
    assignmentPresentor = new AssignmentPresentor();
    assignmentPresentor.setAssignmentPresentorCallback(this);
    assignmentPresentor.init(team_uid);

    return view;
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    Assent.handleResult(permissions, grantResults);

    locationPresentor.getLocationUpdates();
  }

  @Override
  public void onAssignedEmergencyLoaded(Emergency emergency) {
    UserLocation userLocation = emergency.getUser_location();
    lat1 = userLocation.getLatitude();
    lang1 = userLocation.getLongitude();

    Utils.loadImage(Utils.getIconUrl(emergency.getType()), typeImg, getActivity());
    detailsTxt.setText(emergency.getDetails());
    assignmentPresentor.loadAssignedUser(emergency.getKey());

    new DA().log(userLocation);

    if (mapDialogFragment != null) {
      mapDialogFragment.updateUserMarker(userLocation.getLatitude(), userLocation.getLongitude());
    }

    updateEta(lat, lang, lat1, lang1);
  }

  @Override
  public void onUserLoaded(User user) {
    Utils.loadImage(user.getPhoto_url(), dp, getContext());
    nameTxt.setText(user.getFullName());
  }

  @Override
  public void onClick(View view) {
    int id = view.getId();

    if (id == R.id.mapBtn) {
      mapDialogFragment.show(getFragmentManager(), null);
    }
  }

  @Override
  public void onLocationUpdated(UserLocation userLocation) {
    lat = userLocation.getLatitude();
    lang = userLocation.getLongitude();
    if (mapDialogFragment != null) {
      mapDialogFragment.updateTeamMarker(userLocation.getLatitude(), userLocation.getLongitude());
    }

    updateEta(lat, lang, lat1, lang1);
  }

  @Override
  public void onReverseGeocode(String address) {
    if (getActivity() != null) {
      getActivity().runOnUiThread(() -> addressTxt.setText(address));
    }
  }

  private void updateEta(double lat, double lang, double lat1, double lang1) {
    etaPresentor.getEta(lat, lang, lat1, lang1);
  }

  @Override
  public void onEtaCalculated(EtaDetails eta) {
    etaTxt.setText(eta.getDuration().get("text").toString());
  }
}
