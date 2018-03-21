package com.berstek.hcisosrt.view.emergencies;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.berstek.hcisosrt.R;
import com.berstek.hcisosrt.model.Emergency;
import com.berstek.hcisosrt.presentor.EmergenciesPresentor;

import java.util.ArrayList;

/*
  Shows the list of emergencies and the team leader can select one
 */
public class EmergenciesFragment extends Fragment
    implements EmergenciesPresentor.EmergenciesPresentorCallback,
    EmergenciesAdapter.EmergenciesAdapterCallback {

  private View view;
  private RecyclerView emergenciesRecview;

  private EmergenciesPresentor emergenciesPresentor;
  private EmergenciesAdapter emergenciesAdapter;

  private ArrayList<Emergency> emergencies;

  public EmergenciesFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    view = inflater.inflate(R.layout.fragment_emergencies, container, false);

    emergencies = new ArrayList<>();

    emergenciesPresentor = new EmergenciesPresentor();
    emergenciesPresentor.setEmergenciesPresentorCallback(this);
    emergenciesAdapter = new EmergenciesAdapter(emergencies, getContext());
    emergenciesAdapter.setEmergenciesAdapterCallback(this);

    emergenciesRecview = view.findViewById(R.id.emergenciesRecview);
    emergenciesRecview.setLayoutManager(new LinearLayoutManager(getContext()));
    emergenciesRecview.setAdapter(emergenciesAdapter);

    emergenciesPresentor.init();
    return view;
  }

  @Override
  public void onEmergencyLoaded(Emergency emergency) {
    emergencies.add(emergency);
    emergenciesAdapter.notifyItemInserted(emergencies.size() - 1);
  }

  @Override
  public void onEmergencySelected(int pos) {
    //TODO, set status of emergency to 1
  }
}
