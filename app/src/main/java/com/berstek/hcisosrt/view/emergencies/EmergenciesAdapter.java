package com.berstek.hcisosrt.view.emergencies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.berstek.hcisosrt.R;
import com.berstek.hcisosrt.firebase_da.UserDA;
import com.berstek.hcisosrt.model.Emergency;
import com.berstek.hcisosrt.model.User;
import com.berstek.hcisosrt.utils.IconsUrl;
import com.berstek.hcisosrt.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EmergenciesAdapter extends RecyclerView.Adapter<EmergenciesAdapter.ListHolder> {

  private ArrayList<Emergency> data;
  private Context context;
  private LayoutInflater inflater;

  private UserDA userDA;

  public EmergenciesAdapter(ArrayList<Emergency> data, Context context) {
    this.data = data;
    this.context = context;
    inflater = LayoutInflater.from(context);
    userDA = new UserDA();
  }

  @Override
  public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ListHolder(inflater.inflate(R.layout.viewholder_emergency, parent, false));
  }

  @Override
  public void onBindViewHolder(final ListHolder holder, final int position) {
    final Emergency emergency = data.get(position);

    String typeImg = IconsUrl.AMBULANCE;


    switch (emergency.getType()) {
      case "Fire": typeImg = IconsUrl.FIRE; break;
      case "Ambulance": typeImg = IconsUrl.AMBULANCE; break;
      case "Car Accident": typeImg = IconsUrl.CAR; break;
      case "Crime": typeImg = IconsUrl.BADGE; break;
      case "Other": typeImg = IconsUrl.OTHER; break;

      default: break;

    }

    Utils.loadImage(typeImg, holder.typeImg, context);

    userDA.queryUser(emergency.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        for (DataSnapshot child : dataSnapshot.getChildren()) {
          User user = child.getValue(User.class);
          Utils.loadImage(user.getPhoto_url(), holder.dp, context);
          holder.title.setText(user.getFullName());
          holder.subtitle.setText(Utils.formatDate(emergency.getTime_stamp()));
        }
      }

      @Override
      public void onCancelled(DatabaseError databaseError) {

      }
    });
  }

  @Override
  public int getItemCount() {
    return data.size();
  }

  class ListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ImageView dp, typeImg;
    private TextView title, subtitle;

    public ListHolder(View itemView) {
      super(itemView);

      dp = itemView.findViewById(R.id.dp);
      title = itemView.findViewById(R.id.title);
      subtitle = itemView.findViewById(R.id.subtitle);
      typeImg = itemView.findViewById(R.id.typeImg);

      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      emergenciesAdapterCallback.onEmergencySelected(getAdapterPosition());
    }
  }

  private EmergenciesAdapterCallback emergenciesAdapterCallback;

  public interface EmergenciesAdapterCallback {
    void onEmergencySelected(int pos);
  }

  public void setEmergenciesAdapterCallback(EmergenciesAdapterCallback emergenciesAdapterCallback) {
    this.emergenciesAdapterCallback = emergenciesAdapterCallback;
  }
}
