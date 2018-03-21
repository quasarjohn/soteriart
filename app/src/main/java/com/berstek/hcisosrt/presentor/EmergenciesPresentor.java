package com.berstek.hcisosrt.presentor;

import com.berstek.hcisosrt.firebase_da.EmergencyDA;
import com.berstek.hcisosrt.model.Emergency;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class EmergenciesPresentor {

  private EmergencyDA emergencyDA;

  public EmergenciesPresentor() {
    this.emergencyDA = new EmergencyDA();
  }

  public void init() {
    emergencyDA.queryAllEmergencies().addChildEventListener(new ChildEventListener() {
      @Override
      public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        Emergency emergency = dataSnapshot.getValue(Emergency.class);
        emergency.setKey(dataSnapshot.getKey());
        emergenciesPresentorCallback.onEmergencyLoaded(emergency);
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

  private EmergenciesPresentorCallback emergenciesPresentorCallback;

  public interface EmergenciesPresentorCallback {
    void onEmergencyLoaded(Emergency emergency);
  }

  public void setEmergenciesPresentorCallback(EmergenciesPresentorCallback emergenciesPresentorCallback) {
    this.emergenciesPresentorCallback = emergenciesPresentorCallback;
  }
}
