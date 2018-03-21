package com.berstek.hcisosrt.view.team_selection;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.berstek.hcisosrt.R;
import com.berstek.hcisosrt.firebase_da.DA;
import com.berstek.hcisosrt.model.ResponseTeam;
import com.berstek.hcisosrt.utils.Utils;
import com.berstek.hcisosrt.view.ViewPagerActivity;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeamSelectionFragment extends Fragment
    implements TeamSelectionPresentor.TeamSelectionPresentorCallback, TeamSelectionAdapter.TeamSelectionAdapterCallback {

  private ArrayList<ResponseTeam> responseTeams;

  private View view;

  private TeamSelectionPresentor teamSelectionPresentor;

  private TeamSelectionAdapter teamSelectionAdapter;
  private RecyclerView teamRecview;

  public TeamSelectionFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    view = inflater.inflate(R.layout.fragment_team_selection, container, false);
    teamRecview = view.findViewById(R.id.teamRecview);
    teamRecview.setLayoutManager(new LinearLayoutManager(getContext()));
    responseTeams = new ArrayList<>();
    teamSelectionAdapter = new TeamSelectionAdapter(responseTeams, getContext());
    teamSelectionAdapter.setTeamSelectionAdapterCallback(this);
    teamRecview.setAdapter(teamSelectionAdapter);

    teamSelectionPresentor = new TeamSelectionPresentor();
    teamSelectionPresentor.setTeamSelectionPresentorCallback(this);
    teamSelectionPresentor.init();


    return view;
  }

  @Override
  public void onRtLoaded(ResponseTeam responseTeam) {
    new DA().log(responseTeam);
    responseTeams.add(responseTeam);
    teamSelectionAdapter.notifyItemInserted(responseTeams.size() - 1);
  }

  @Override
  public void onRtChanged(ResponseTeam responseTeam) {

  }

  @Override
  public void onTeamSelected(int pos) {
    //TODO add user to team and make his leader the team leader of the team
    ResponseTeam team = responseTeams.get(pos);

    teamSelectionPresentor.addUserToTeam(Utils.getUid(), team.getLeader_uid(), team.getKey());

    Intent intent = new Intent(getActivity(), ViewPagerActivity.class);
    intent.putExtra("leader_uid", team.getLeader_uid());
    intent.putExtra("team_uid", team.getKey());

    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(intent);
  }
}
