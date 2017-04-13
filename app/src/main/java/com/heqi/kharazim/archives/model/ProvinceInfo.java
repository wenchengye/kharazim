package com.heqi.kharazim.archives.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by overspark on 2017/4/12.
 */

public class ProvinceInfo implements Serializable {

  private static final long serialVersionUID = -1480136485609508498L;

  private String name;
  private List<CityInfo> city;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<CityInfo> getCity() {
    return city;
  }

  public void setCity(List<CityInfo> city) {
    this.city = city;
  }

  public static class CityInfo implements Serializable {

    private static final long serialVersionUID = -5494780653926563311L;

    private String name;
    private List<String> area;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public List<String> getArea() {
      return area;
    }

    public void setArea(List<String> area) {
      this.area = area;
    }
  }
}
