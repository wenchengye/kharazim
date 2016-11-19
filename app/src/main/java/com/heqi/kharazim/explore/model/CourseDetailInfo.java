package com.heqi.kharazim.explore.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by overspark on 2016/11/14.
 */

public class CourseDetailInfo implements Serializable {

  private String id;
  private String coursename;
  private String coursedec;
  private String courseimg;
  private int actcnt;
  private int acupointcnt;
  private int coursetype;
  private int cptime;
  private int courselev;
  private int lev;
  private long senddatetime;
  private String version;
  private List<ActionDetailInfo> actlist;
  private List<CourseMusicInfo> musiclist;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getCoursename() {
    return coursename;
  }

  public void setCoursename(String coursename) {
    this.coursename = coursename;
  }

  public String getCoursedec() {
    return coursedec;
  }

  public void setCoursedec(String coursedec) {
    this.coursedec = coursedec;
  }

  public String getCourseimg() {
    return courseimg;
  }

  public void setCourseimg(String courseimg) {
    this.courseimg = courseimg;
  }

  public int getActcnt() {
    return actcnt;
  }

  public void setActcnt(int actcnt) {
    this.actcnt = actcnt;
  }

  public int getAcupointcnt() {
    return acupointcnt;
  }

  public void setAcupointcnt(int acupointcnt) {
    this.acupointcnt = acupointcnt;
  }

  public int getCoursetype() {
    return coursetype;
  }

  public void setCoursetype(int coursetype) {
    this.coursetype = coursetype;
  }

  public int getCptime() {
    return cptime;
  }

  public void setCptime(int cptime) {
    this.cptime = cptime;
  }

  public int getCourselev() {
    return courselev;
  }

  public void setCourselev(int courselev) {
    this.courselev = courselev;
  }

  public int getLev() {
    return lev;
  }

  public void setLev(int lev) {
    this.lev = lev;
  }

  public long getSenddatetime() {
    return senddatetime;
  }

  public void setSenddatetime(long senddatetime) {
    this.senddatetime = senddatetime;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public List<ActionDetailInfo> getActlist() {
    return actlist;
  }

  public void setActlist(List<ActionDetailInfo> actlist) {
    this.actlist = actlist;
  }

  public static class CourseMusicInfo implements Serializable {
    private int num;
    private int starttime;
    private int playtype;
    private String musicfile;
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

    public String getMusicfile() {
      return musicfile;
    }

    public void setMusicfile(String musicfile) {
      this.musicfile = musicfile;
    }

    public int getLen() {
      return len;
    }

    public void setLen(int len) {
      this.len = len;
    }
  }
}
