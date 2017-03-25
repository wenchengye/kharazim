package com.heqi.kharazim.archives.model;

import java.io.Serializable;

/**
 * Created by overspark on 2017/3/25.
 */

public class UserStatsInfo implements Serializable {

  private static final long serialVersionUID = 4568255393452295854L;

  private String coldate;
  private String colname;
  private int cptimes;
  private int cpcnt;
  private int seriescnt;
  private int daycnt;
  private int vitality;

  public String getColdate() {
    return coldate;
  }

  public void setColdate(String coldate) {
    this.coldate = coldate;
  }

  public String getColname() {
    return colname;
  }

  public void setColname(String colname) {
    this.colname = colname;
  }

  public int getCptimes() {
    return cptimes;
  }

  public void setCptimes(int cptimes) {
    this.cptimes = cptimes;
  }

  public int getCpcnt() {
    return cpcnt;
  }

  public void setCpcnt(int cpcnt) {
    this.cpcnt = cpcnt;
  }

  public int getSeriescnt() {
    return seriescnt;
  }

  public void setSeriescnt(int seriescnt) {
    this.seriescnt = seriescnt;
  }

  public int getDaycnt() {
    return daycnt;
  }

  public void setDaycnt(int daycnt) {
    this.daycnt = daycnt;
  }

  public int getVitality() {
    return vitality;
  }

  public void setVitality(int vitality) {
    this.vitality = vitality;
  }
}
