package com.berstek.hcisosrt.view.assignment;

import com.berstek.hcisosrt.firebase_da.EmergencyDA;
import com.berstek.hcisosrt.model.Emergency;
import com.berstek.hcisosrt.utils.Utils;
import com.bumptech.glide.util.Util;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class AssignmentPresentor {

  private EmergencyDA emergencyDA;

  public AssignmentPresentor() {
    emergencyDA = new EmergencyDA();
  }

  public void init(String uid) {
    emergencyDA.queryEmergencyByTeamUid(uid).addChildEventListener(new ChildEventListener() {
      @Override
      public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Emergency emergency = dataSnapshot.getValue(Emergency.class);
        assignmentPresentorCallback.onAssignedEmergencyLoaded(emergency);

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

  private AssignmentPresentorCallback assignmentPresentorCallback;

  public interface AssignmentPresentorCallback {

    void onAssignedEmergencyLoaded(Emergency emergency);

  }

  public void setAssignmentPresentorCallback(AssignmentPresentorCallback assignmentPresentorCallback) {
    this.assignmentPresentorCallback = assignmentPresentorCallback;
  }
}
