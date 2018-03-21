package com.berstek.hcisosrt.view.assignment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.berstek.hcisosrt.R;
import com.berstek.hcisosrt.firebase_da.DA;
import com.berstek.hcisosrt.model.Emergency;
import com.berstek.hcisosrt.model.User;
import com.berstek.hcisosrt.utils.Utils;
import com.bumptech.glide.util.Util;

public class AssignmentFragment extends Fragment
    implements AssignmentPresentor.AssignmentPresentorCallback, View.OnClickListener {

  private View view;

  private String team_uid;

  private AssignmentPresentor assignmentPresentor;

  private ImageView typeImg, dp;
  private TextView detailsTxt, addressTxt, etaTxt, nameTxt;

  private Button doneBtn, mapBtn;

  private MapDialogFragment mapDialogFragment;

  public AssignmentFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {

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
  public void onAssignedEmergencyLoaded(Emergency emergency) {
    Utils.loadImage(Utils.getIconUrl(emergency.getType()), typeImg, getContext());
    detailsTxt.setText(emergency.getDetails());
    assignmentPresentor.loadAssignedUser(emergency.getKey());
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
      mapDialogFragment = new MapDialogFragment();
      mapDialogFragment.show(getFragmentManager(), null);
    }
  }
}
