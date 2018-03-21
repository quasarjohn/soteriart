package com.berstek.hcisosrt.view.team;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.berstek.hcisosrt.R;
import com.berstek.hcisosrt.firebase_da.UserDA;
import com.berstek.hcisosrt.model.User;
import com.berstek.hcisosrt.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TeamAdapter extends RecyclerView.Adapter<TeamAdapter.ListHolder> {

  private ArrayList<String> data;
  private Context context;
  private LayoutInflater inflater;

  private UserDA userDA;

  public TeamAdapter(ArrayList<String> data, Context context) {
    this.data = data;
    this.context = context;
    inflater = LayoutInflater.from(context);
    userDA = new UserDA();
  }

  @Override
  public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ListHolder(inflater.inflate(R.layout.viewholder_team_user, parent, false));
  }

  @Override
  public void onBindViewHolder(final ListHolder holder, final int position) {
    String uid = data.get(position);

    userDA.queryUser(uid).addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        for (DataSnapshot child : dataSnapshot.getChildren()) {
          User user = child.getValue(User.class);
          Utils.loadImage(user.getPhoto_url(), holder.dp, context);
          if (position == 0) {
            holder.subtitle.setText("Leader");
          } else {
            holder.subtitle.setText("Member");
          }
          holder.title.setText(user.getFullName());
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

    private ImageView dp;
    private TextView title, subtitle;

    public ListHolder(View itemView) {
      super(itemView);

      dp = itemView.findViewById(R.id.dp);
      title = itemView.findViewById(R.id.title);
      subtitle = itemView.findViewById(R.id.subtitle);

      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      teamAdapterCallback.onTeamSelected(getAdapterPosition());
    }
  }

  private TeamAdapterCallback teamAdapterCallback;

  public interface TeamAdapterCallback {
    void onTeamSelected(int pos);
  }

  public void setTeamAdapterCallback(TeamAdapterCallback teamAdapterCallback) {
    this.teamAdapterCallback = teamAdapterCallback;
  }
}
