package com.berstek.hcisosrt.callback;

import com.google.firebase.auth.FirebaseUser;

public interface AuthCallback {
  void onAuthSuccess(FirebaseUser user);
}
