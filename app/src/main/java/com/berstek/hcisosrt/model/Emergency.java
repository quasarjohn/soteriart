package com.berstek.hcisosrt.model;

import com.google.firebase.database.Exclude;

import java.util.ArrayList;

public class Emergency {

  private UserLocation user_location;
  private long time_stamp;
  private String type, details;
  private ArrayList<String> attachments_url;
  //0 pending 1 dispatched 2 finished
  private int status;

  private String rt_uid;

  @Exclude
  private String key;

  public UserLocation getUser_location() {
    return user_location;
  }

  public void setUser_location(UserLocation user_location) {
    this.user_location = user_location;
  }

  public long getTime_stamp() {
    return time_stamp;
  }

  public void setTime_stamp(long time_stamp) {
    this.time_stamp = time_stamp;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public ArrayList<String> getAttachments_url() {
    return attachments_url;
  }

  public void setAttachments_url(ArrayList<String> attachments_url) {
    this.attachments_url = attachments_url;
  }


  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }


  public String getRt_uid() {
    return rt_uid;
  }

  public void setRt_uid(String rt_uid) {
    this.rt_uid = rt_uid;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }


}
