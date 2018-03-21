package com.berstek.hcisosrt.view.team_selection;

import com.berstek.hcisosrt.firebase_da.RtDA;
import com.berstek.hcisosrt.model.ResponseTeam;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class TeamSelectionPresentor {

  private RtDA rtDA;

  public void init() {
    rtDA = new RtDA();

    rtDA.queryAllRt().addChildEventListener(new ChildEventListener() {
      @Override
      public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        ResponseTeam responseTeam = dataSnapshot.getValue(ResponseTeam.class);
        responseTeam.setKey(dataSnapshot.getKey());
        teamSelectionPresentorCallback.onRtLoaded(responseTeam);
      }

      @Override
      public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        ResponseTeam responseTeam = dataSnapshot.getValue(ResponseTeam.class);
        responseTeam.setKey(dataSnapshot.getKey());
        teamSelectionPresentorCallback.onRtChanged(responseTeam);
      }

      @Override
      public void onChildRemoved(DataSnapshot dataSnapshot) {

      }

      @Override
      public void onChildMoved(DataSnapshot dataSnapshot, String s) {

      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });
  }

  private TeamSelectionPresentorCallback teamSelectionPresentorCallback;

  public interface TeamSelectionPresentorCallback {
    void onRtLoaded(ResponseTeam responseTeam);

    void onRtChanged(ResponseTeam responseTeam);
  }

  public void setTeamSelectionPresentorCallback(TeamSelectionPresentorCallback teamSelectionPresentorCallback) {
    this.teamSelectionPresentorCallback = teamSelectionPresentorCallback;
  }

  public void addUserToTeam(String user_uid, String leader_uid, String team_uid) {
    rtDA.addUserToRt(user_uid, leader_uid, team_uid);
  }
}
