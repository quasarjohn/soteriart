package com.berstek.hcisosrt.firebase_da;


import com.berstek.hcisosrt.model.User;
import com.berstek.hcisosrt.utils.Utils;
import com.google.firebase.database.Query;

public class UserDA extends DA {

  public void addUser(User user) {
    mRootRef.child("users").child(Utils.getUid()).setValue(user);
  }

  public Query queryUser(String uid) {
    return mRootRef.child("users").orderByKey().equalTo(uid);
  }
}
