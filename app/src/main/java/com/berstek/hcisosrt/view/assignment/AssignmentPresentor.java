package com.berstek.hcisosrt.view.assignment;

import com.berstek.hcisosrt.firebase_da.EmergencyDA;
import com.berstek.hcisosrt.firebase_da.RtDA;
import com.berstek.hcisosrt.firebase_da.UserDA;
import com.berstek.hcisosrt.model.Emergency;

import com.berstek.hcisosrt.model.User;
import com.berstek.hcisosrt.model.UserLocation;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class AssignmentPresentor {

  private EmergencyDA emergencyDA;
  private UserDA userDA;
  private RtDA rtDA;

  public AssignmentPresentor() {
    emergencyDA = new EmergencyDA();
    userDA = new UserDA();
    rtDA = new RtDA();
  }

  public void init(String uid) {
    emergencyDA.queryEmergencyByTeamUid(uid).addChildEventListener(new ChildEventListener() {
      @Override
      public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Emergency emergency = dataSnapshot.getValue(Emergency.class);
        emergency.setKey(dataSnapshot.getKey());
        assignmentPresentorCallback.onAssignedEmergencyLoaded(emergency);

      }

      @Override
      public void onChildChanged(DataSnapshot dataSnapshot, String s) {
        Emergency emergency = dataSnapshot.getValue(Emergency.class);
        emergency.setKey(dataSnapshot.getKey());
        assignmentPresentorCallback.onAssignedEmergencyLoaded(emergency);
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


  public void loadAssignedUser(String uid) {
    userDA.queryUser(uid).addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        for (DataSnapshot child : dataSnapshot.getChildren()) {
          assignmentPresentorCallback.onUserLoaded(child.getValue(User.class));
        }
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });

  }

  private AssignmentPresentorCallback assignmentPresentorCallback;

  public interface AssignmentPresentorCallback {

    void onAssignedEmergencyLoaded(Emergency emergency);

    void onUserLoaded(User user);

  }

  public void setAssignmentPresentorCallback(AssignmentPresentorCallback assignmentPresentorCallback) {
    this.assignmentPresentorCallback = assignmentPresentorCallback;
  }

  public void updateLocationInFirebase(String uid, UserLocation userLocation) {
    rtDA.updateRtLocation(uid, userLocation);
  }
}
