package com.berstek.hcisosrt.view;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.berstek.hcisosrt.R;
import com.berstek.hcisosrt.view.assignment.AssignmentFragment;
import com.berstek.hcisosrt.view.emergencies.EmergenciesFragment;
import com.berstek.hcisosrt.view.team.TeamFragment;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class ViewPagerActivity extends AppCompatActivity
    implements EmergenciesFragment.EmergenciesFragmentCallback {

  private TabLayout tab;
  private ViewPager viewPager;

  private ArrayList<Fragment> fragments;

  private String leader_uid, team_uid;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_pager);

    getSupportActionBar().hide();

    leader_uid = getIntent().getExtras().getString("leader_uid");
    team_uid = getIntent().getExtras().getString("team_uid");

    fragments = new ArrayList<>();

    tab = findViewById(R.id.tab);
    viewPager = findViewById(R.id.viewpager);

    TeamFragment teamFragment = new TeamFragment();
    Bundle teamFragmentBundle = new Bundle();
    teamFragmentBundle.putString("leader_uid", leader_uid);
    teamFragment.setArguments(teamFragmentBundle);
    fragments.add(teamFragment);
    tab.addTab(tab.newTab());

    EmergenciesFragment emergenciesFragment = new EmergenciesFragment();
    emergenciesFragment.setEmergenciesFragmentCallback(this);
    Bundle emBundle = new Bundle();
    emBundle.putString("team_uid", team_uid);
    emergenciesFragment.setArguments(emBundle);
    fragments.add(emergenciesFragment);
    tab.addTab(tab.newTab());

    AssignmentFragment assignmentFragment = new AssignmentFragment();
    assignmentFragment.setArguments(emBundle);
    fragments.add(assignmentFragment);
    tab.addTab(tab.newTab());

    viewPager.setAdapter(new ViewpagerAdapter(getSupportFragmentManager()));
    viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tab));
    tab.setupWithViewPager(viewPager);
    viewPager.setOffscreenPageLimit(3);

    tab.getTabAt(0).setCustomView(getLayoutInflater().inflate(R.layout.tablayout_view, null));
    tab.getTabAt(1).setCustomView(getLayoutInflater().inflate(R.layout.tablayout_view_deselected, null));
    tab.getTabAt(2).setCustomView(getLayoutInflater().inflate(R.layout.tablayout_view_deselected, null));

    tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        tab.setCustomView(null);
        tab.setCustomView(getLayoutInflater().inflate(R.layout.tablayout_view, null));
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {
        tab.setCustomView(null);
        tab.setCustomView(getLayoutInflater().inflate(R.layout.tablayout_view_deselected, null));
      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {

      }
    });
  }

  @Override
  public void onEmergencyAssigned() {
    tab.setScrollPosition(2, 0f, true);
    viewPager.setCurrentItem(2);
  }

  private class ViewpagerAdapter extends FragmentStatePagerAdapter {

    public ViewpagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(int position) {
      return fragments.get(position);
    }

    @Override
    public int getCount() {
      return fragments.size();
    }
  }
}
