package com.berstek.hcisosrt.firebase_da;


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

}
