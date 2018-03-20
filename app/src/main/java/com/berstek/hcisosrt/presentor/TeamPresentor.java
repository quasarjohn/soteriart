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

  private UserDA userDA;
  private RtDA rtDA;

  public TeamPresentor() {
    userDA = new UserDA();
    rtDA = new RtDA();
  }

  public void init() {

    //first check if the leader_uid of the user is the same as his uid
    //if it is, that means he is a leader
    //just query the RT whose leader is the leader of the user
    userDA.queryUser(Utils.getUid()).addChildEventListener(new ChildEventListener() {
      @Override
      public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        User user = dataSnapshot.getValue(User.class);
        queryRt(user.getLeader_uid());
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

  private void queryRt(String leader_uid) {
    rtDA.queryRt(leader_uid).addChildEventListener(new ChildEventListener() {
      @Override
      public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        ResponseTeam responseTeam = dataSnapshot.getValue(ResponseTeam.class);
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
    void teamLoaded(ResponseTeam responseTeam);
  }

  public void setTeamPresentorCallback(TeamPresentorCallback teamPresentorCallback) {
    this.teamPresentorCallback = teamPresentorCallback;
  }
}
