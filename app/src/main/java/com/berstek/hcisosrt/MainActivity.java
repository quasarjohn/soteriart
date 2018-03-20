package com.berstek.hcisosrt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.berstek.hcisosrt.firebase_da.DA;
import com.berstek.hcisosrt.view.home.TeamSelectionFragment;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    new DA().log("SHIT");

    getSupportFragmentManager().beginTransaction().
        replace(R.id.content_main, new TeamSelectionFragment()).commit();
  }
}
