package com.berstek.hcisosrt.presentor;

import com.berstek.hcisosrt.firebase_da.RtDA;
import com.berstek.hcisosrt.firebase_da.UserDA;
import com.berstek.hcisosrt.model.ResponseTeam;
import com.berstek.hcisosrt.model.User;
import com.berstek.hcisosrt.utils.Utils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class TeamPresentor {

  private RtDA rtDA;

  public TeamPresentor() {
    rtDA = new RtDA();
  }

  public void init(String leader_uid) {
    rtDA.queryRt(leader_uid).addChildEventListener(new ChildEventListener() {
      @Override
      public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        ResponseTeam responseTeam = dataSnapshot.getValue(ResponseTeam.class);
        teamPresentorCallback.onTeamLoaded(responseTeam);
      }

      @Override
      public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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

  private TeamPresentorCallback teamPresentorCallback;

  public interface TeamPresentorCallback {
    void onTeamLoaded(ResponseTeam responseTeam);
  }

  public void setTeamPresentorCallback(TeamPresentorCallback teamPresentorCallback) {
    this.teamPresentorCallback = teamPresentorCallback;
  }
}
