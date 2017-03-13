package com.heqi.kharazim.archives.model;

/**
 * Created by overspark on 2017/3/13.
 */

public class HealthCondition {
  private int stature;
  private int weight;
  private int heartrate;
  private int pressurehigh;
  private int pressurelow;
  private String otherdec;

  public int getStature() {
    return stature;
  }

  public void setStature(int stature) {
    this.stature = stature;
  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  public int getHeartrate() {
    return heartrate;
  }

  public void setHeartrate(int heartrate) {
    this.heartrate = heartrate;
  }

  public int getPressurehigh() {
    return pressurehigh;
  }

  public void setPressurehigh(int pressurehigh) {
    this.pressurehigh = pressurehigh;
  }

  public int getPressurelow() {
    return pressurelow;
  }

  public void setPressurelow(int pressurelow) {
    this.pressurelow = pressurelow;
  }

  public String getOtherdec() {
    return otherdec;
  }

  public void setOtherdec(String otherdec) {
    this.otherdec = otherdec;
  }
}
