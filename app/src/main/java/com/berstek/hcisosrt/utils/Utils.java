package com.berstek.hcisosrt.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

  private Context context;

  public Utils(Context context) {
    this.context = context;
  }

  public static void loadImage(String url, ImageView img, Context context) {
    Glide.with(context).load(url).skipMemoryCache(true).into(img);
  }

  public void loadImage(String url, ImageView img, int size) {
    Glide.with(context).load(url).skipMemoryCache(true).override(size, size).into(img);
  }

  public static String getUid() {
    return FirebaseAuth.getInstance().getCurrentUser().getUid();
  }

  public void test() {

  }

  public static String formatDate(long t) {
    SimpleDateFormat format = new SimpleDateFormat("mm:hh a");
    return format.format(new Date(t));
  }

  public static String getIconUrl(String type) {
    String typeImg = IconsUrl.AMBULANCE;


    switch (type) {
      case "Fire": typeImg = IconsUrl.FIRE; break;
      case "Ambulance": typeImg = IconsUrl.AMBULANCE; break;
      case "Car Accident": typeImg = IconsUrl.CAR; break;
      case "Crime": typeImg = IconsUrl.BADGE; break;
      case "Other": typeImg = IconsUrl.OTHER; break;

      default: break;

    }

    return typeImg;
  }
 }
