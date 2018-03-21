package com.berstek.hcisosrt.view.team_selection;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.berstek.hcisosrt.R;
import com.berstek.hcisosrt.firebase_da.DA;
import com.berstek.hcisosrt.view.team_selection.TeamSelectionFragment;

public class TeamSelectionActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    getSupportActionBar().hide();

    getSupportFragmentManager().beginTransaction().
        replace(R.id.content_main, new TeamSelectionFragment()).commit();
  }
}
