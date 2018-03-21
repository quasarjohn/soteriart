package com.berstek.hcisosrt.view.team_selection;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.berstek.hcisosrt.R;
import com.berstek.hcisosrt.firebase_da.UserDA;
import com.berstek.hcisosrt.model.ResponseTeam;
import com.berstek.hcisosrt.model.User;
import com.berstek.hcisosrt.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TeamSelectionAdapter extends RecyclerView.Adapter<TeamSelectionAdapter.ListHolder> {

  private ArrayList<ResponseTeam> data;
  private Context context;
  private LayoutInflater inflater;

  private UserDA userDA;

  public TeamSelectionAdapter(ArrayList<ResponseTeam> data, Context context) {
    this.data = data;
    this.context = context;
    inflater = LayoutInflater.from(context);
    userDA = new UserDA();
  }

  @Override
  public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ListHolder(inflater.inflate(R.layout.viewholder_rt, parent, false));
  }

  @Override
  public void onBindViewHolder(final ListHolder holder, int position) {
    ResponseTeam responseTeam = data.get(position);
    holder.teamNameTxt.setText(responseTeam.getTeam_name());

    userDA.queryUser(responseTeam.getLeader_uid()).addListenerForSingleValueEvent(new ValueEventListener() {
      @Override
      public void onDataChange(DataSnapshot dataSnapshot) {
        for (DataSnapshot child : dataSnapshot.getChildren()) {
          User user = child.getValue(User.class);
          Utils.loadImage(user.getPhoto_url(), holder.dp, context);
          holder.leaderTxt.setText(user.getFullName());
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
    private TextView teamNameTxt, leaderTxt;

    public ListHolder(View itemView) {
      super(itemView);

      dp = itemView.findViewById(R.id.dp);
      teamNameTxt = itemView.findViewById(R.id.title);
      leaderTxt = itemView.findViewById(R.id.leaderTxt);

      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      teamSelectionAdapterCallback.onTeamSelected(getAdapterPosition());
    }
  }

  private  TeamSelectionAdapterCallback teamSelectionAdapterCallback;

  public interface TeamSelectionAdapterCallback {
    void onTeamSelected(int pos);
  }

  public void setTeamSelectionAdapterCallback(TeamSelectionAdapterCallback teamSelectionAdapterCallback) {
    this.teamSelectionAdapterCallback = teamSelectionAdapterCallback;
  }
}
