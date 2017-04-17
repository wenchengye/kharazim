package com.heqi.kharazim.config;

import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.R;

/**
 * Created by overspark on 2016/11/19.
 */

public class Const {

  // common
  public static final long SECOND = 1000L;
  public static final long MINTUE = SECOND * 60;
  public static final long HOUR = MINTUE * 60;
  public static final long DAY = HOUR * 24;

  // http
  //public static final String KHARAZIM_SERVER = "http://115.28.11.62:8080/";
  public static final String KHARAZIM_SERVER = "https://manager.heal361.com/";
  public static final String EMAIL_KEY_WORD = "@";
  public static final int KHARAZIM_FETCHER_SECOND_PAGE_NUMBER = 2;

  private Const() {
  }

  public static class TabId {
    public static final String NONE = "";

    private TabId() {
    }
  }

  // archives
  public static final int MIN_HEIGHT_VALUE = 100;
  public static final int MAX_HEIGHT_VALUE = 250;
  public static final int MIN_WEIGHT_VALUE = 30;
  public static final int MAX_WEIGHT_VALUE = 200;
  public static final String PROVINCE_DATA_FILE_NAME = "province.json";

  public enum Gender {

    Male(R.string.gender_male), Female(R.string.gender_female);

    private int nameResId;

    Gender(int nameResId) {
      this.nameResId = nameResId;
    }

    public String getName() {
      return KharazimApplication.getAppContext().getString(nameResId);
    }

    public static Gender fromString(String name) {
      for (Gender gender : Gender.values()) {
        if (gender.getName().equals(name)) {
          return gender;
        }
      }

      return null;
    }
  }

  public enum Aim {

    Junior(1, R.string.aim_junior), Middle(2, R.string.aim_middle), Senior(3, R.string.aim_senior);

    private int value;
    private int nameId;

    Aim(int value, int nameId) {
      this.value = value;
      this.nameId = nameId;
    }

    public int getValue() {
      return value;
    }

    public String getName() {
      return KharazimApplication.getAppContext().getString(nameId);
    }

    public static Aim fromValue(int value) {
      for (Aim aim : Aim.values()) {
        if (aim.getValue() == value) {
          return aim;
        }
      }

      return null;
    }
  }
}
