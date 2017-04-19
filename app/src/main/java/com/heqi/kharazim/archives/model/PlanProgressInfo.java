package com.heqi.kharazim.archives.model;

import java.io.Serializable;

/**
 * Created by overspark on 2017/4/19.
 */

public class PlanProgressInfo implements Serializable {

  private static final long serialVersionUID = -8667607661011330562L;

  private String nextid;
  private String nextdec;
  private String nextnum;
  private int cpdate;
  private int cptimes;
  private int vitality;
  private int actcnt;

  public String getNextid() {
    return nextid;
  }

  public void setNextid(String nextid) {
    this.nextid = nextid;
  }

  public String getNextdec() {
    return nextdec;
  }

  public void setNextdec(String nextdec) {
    this.nextdec = nextdec;
  }

  public String getNextnum() {
    return nextnum;
  }

  public void setNextnum(String nextnum) {
    this.nextnum = nextnum;
  }

  public int getCpdate() {
    return cpdate;
  }

  public void setCpdate(int cpdate) {
    this.cpdate = cpdate;
  }

  public int getCptimes() {
    return cptimes;
  }

  public void setCptimes(int cptimes) {
    this.cptimes = cptimes;
  }

  public int getVitality() {
    return vitality;
  }

  public void setVitality(int vitality) {
    this.vitality = vitality;
  }

  public int getActcnt() {
    return actcnt;
  }

  public void setActcnt(int actcnt) {
    this.actcnt = actcnt;
  }
}
