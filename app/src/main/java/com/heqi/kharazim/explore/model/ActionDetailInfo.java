package com.heqi.kharazim.explore.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by overspark on 2016/11/14.
 */

public class ActionDetailInfo implements Serializable {

  private String id;
  private String actname;
  private String actdec;
  private String acupointid;
  private String actvediofile;
  private String actimg;
  private String acttype;
  private int cptime;
  private int cpcnt;
  private List<ActionSoundInfo> actSoundDtoList;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getActname() {
    return actname;
  }

  public void setActname(String actname) {
    this.actname = actname;
  }

  public String getActdec() {
    return actdec;
  }

  public void setActdec(String actdec) {
    this.actdec = actdec;
  }

  public String getAcupointid() {
    return acupointid;
  }

  public void setAcupointid(String acupointid) {
    this.acupointid = acupointid;
  }

  public String getActvediofile() {
    return actvediofile;
  }

  public void setActvediofile(String actvediofile) {
    this.actvediofile = actvediofile;
  }

  public String getActimg() {
    return actimg;
  }

  public void setActimg(String actimg) {
    this.actimg = actimg;
  }

  public String getActtype() {
    return acttype;
  }

  public void setActtype(String acttype) {
    this.acttype = acttype;
  }

  public int getCptime() {
    return cptime;
  }

  public void setCptime(int cptime) {
    this.cptime = cptime;
  }

  public int getCpcnt() {
    return cpcnt;
  }

  public void setCpcnt(int cpcnt) {
    this.cpcnt = cpcnt;
  }

  public List<ActionSoundInfo> getActSoundDtoList() {
    return actSoundDtoList;
  }

  public void setActSoundDtoList(List<ActionSoundInfo> actSoundDtoList) {
    this.actSoundDtoList = actSoundDtoList;
  }

  public static class ActionSoundInfo implements Serializable {
    private int num;
    private int starttime;
    private int playtype;
    private String soundfile;
    private int len;

    public int getNum() {
      return num;
    }

    public void setNum(int num) {
      this.num = num;
    }

    public int getStarttime() {
      return starttime;
    }

    public void setStarttime(int starttime) {
      this.starttime = starttime;
    }

    public int getPlaytype() {
      return playtype;
    }

    public void setPlaytype(int playtype) {
      this.playtype = playtype;
    }

    public String getSoundfile() {
      return soundfile;
    }

    public void setSoundfile(String soundfile) {
      this.soundfile = soundfile;
    }

    public int getLen() {
      return len;
    }

    public void setLen(int len) {
      this.len = len;
    }
  }
}
