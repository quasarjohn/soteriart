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
import com.berstek.hcisosrt.utils.Utils;

public class AssignmentFragment extends Fragment implements AssignmentPresentor.AssignmentPresentorCallback {

  private View view;

  private String team_uid;

  private AssignmentPresentor assignmentPresentor;

  private ImageView typeImg, dp;
  private TextView detailsTxt, addressTxt, etaTxt, nameTxt;

  private Button doneBtn, mapBtn;

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

  }
}
