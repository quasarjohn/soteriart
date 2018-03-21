package com.berstek.hcisosrt.firebase_da;

import com.berstek.hcisosrt.model.Emergency;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.Query;

public class EmergencyDA extends DA {

  public void updateEmergency(Emergency emergency) {
    mRootRef.child("emergencies").child(auth.getCurrentUser().getUid()).setValue(emergency).
        addOnSuccessListener(new OnSuccessListener<Void>() {
          @Override
          public void onSuccess(Void aVoid) {

          }
        });
  }

  public void assignTeamToEmergency(String uid, String team_uid) {
    mRootRef.child("emergencies").child(uid).child("rt_uid").setValue(team_uid);
    mRootRef.child("emergencies").child(uid).child("status").setValue(1);

  }

  public Query queryAllEmergencies() {
    return mRootRef.child("emergencies").orderByChild("status").equalTo(0);
  }

  public Query queryEmergencyByTeamUid(String uid) {
    return mRootRef.child("emergencies").orderByChild("rt_uid").equalTo(uid);
  }

}
