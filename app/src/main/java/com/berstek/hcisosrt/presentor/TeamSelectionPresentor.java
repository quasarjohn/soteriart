package com.berstek.hcisosrt.presentor;

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
        teamSelectionPresentorCallback.onRtLoaded(responseTeam);
      }

      @Override
      public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        ResponseTeam responseTeam = dataSnapshot.getValue(ResponseTeam.class);
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
}
