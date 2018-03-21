package com.berstek.hcisosrt.view.team;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.berstek.hcisosrt.R;
import com.berstek.hcisosrt.firebase_da.DA;
import com.berstek.hcisosrt.firebase_da.RtDA;
import com.berstek.hcisosrt.model.ResponseTeam;
import com.berstek.hcisosrt.model.User;
import com.berstek.hcisosrt.presentor.TeamPresentor;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeamFragment extends Fragment implements TeamPresentor.TeamPresentorCallback {

  private View view;
  private RecyclerView membersRecview;

  private String leader_uid;

  private TeamPresentor teamPresentor;

  private ArrayList<String> members;


  public TeamFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    view = inflater.inflate(R.layout.fragment_team, container, false);
    teamPresentor = new TeamPresentor();
    teamPresentor.setTeamPresentorCallback(this);
    leader_uid = getArguments().getString("leader_uid");
    new DA().log(leader_uid );

    teamPresentor.init(leader_uid);

    members = new ArrayList<>();

    membersRecview = view.findViewById(R.id.membersRecview);
    membersRecview.setLayoutManager(new LinearLayoutManager(getContext()));

    return view;
  }

  @Override
  public void onTeamLoaded(ResponseTeam responseTeam) {

    new DA().log(responseTeam);

    members.add(responseTeam.getLeader_uid());

    for (String k : responseTeam.getMembers().keySet()) {
      members.add(k);
    }

    TeamAdapter teamAdapter = new TeamAdapter(members, getContext());
    membersRecview.setAdapter(teamAdapter);

  }
}
