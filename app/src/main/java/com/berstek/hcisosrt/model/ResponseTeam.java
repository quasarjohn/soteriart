package com.berstek.hcisosrt.model;

import com.google.firebase.database.Exclude;

import java.util.HashMap;

public class ResponseTeam {

  private String leader_uid;
  private HashMap<String, Boolean> members;

  private String team_name;

  //uid of the emergency the team is dispatched to
  private String dispatched_to;
  private long dispatched_on;

  private UserLocation user_location;

  @Exclude
  private String key;

  public String getLeader_uid() {
    return leader_uid;
  }

  public void setLeader_uid(String leader_uid) {
    this.leader_uid = leader_uid;
  }

  public HashMap<String, Boolean> getMembers() {
    return members;
  }

  public void setMembers(HashMap<String, Boolean> members) {
    this.members = members;
  }

  public String getDispatched_to() {
    return dispatched_to;
  }

  public void setDispatched_to(String dispatched_to) {
    this.dispatched_to = dispatched_to;
  }

  public long getDispatched_on() {
    return dispatched_on;
  }

  public void setDispatched_on(long dispatched_on) {
    this.dispatched_on = dispatched_on;
  }

  public String getTeam_name() {
    return team_name;
  }

  public void setTeam_name(String team_name) {
    this.team_name = team_name;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public UserLocation getUser_location() {
    return user_location;
  }

  public void setUser_location(UserLocation user_location) {
    this.user_location = user_location;
  }
}
