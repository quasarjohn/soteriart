package com.berstek.hcisosrt.firebase_da;


import com.google.android.gms.tasks.Task;
import com.google.firebase.database.Query;

public class RtDA extends DA {

  public Query queryRt(String leader_uid) {
    return mRootRef.child("response_teams").orderByChild("leader_uid").equalTo(leader_uid);
  }

  public Query queryRtViaUid(String uid) {
    return mRootRef.child("response_teams").child(uid);
  }

  public Query queryAllRt() {
    return mRootRef.child("response_teams");
  }

  public void addUserToRt(String uid, String leader_uid, String team_uid) {
    mRootRef.child("response_teams").child(team_uid).child("members").child(uid).setValue(true);
    mRootRef.child("users").child(uid).child("leader_uid").setValue(leader_uid);
  }

}
