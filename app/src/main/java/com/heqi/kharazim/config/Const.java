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
  public static final int VIDEO_WIDTH = 16;
  public static final int VIDEO_HEIGHT = 9;

  // http
  //public static final String KHARAZIM_SERVER = "http://115.28.11.62:8080/";
  public static final String KHARAZIM_SERVER = "https://manager.heal361.com/";
  public static final String EMAIL_KEY_WORD = "@";
  public static final int KHARAZIM_FETCHER_SECOND_PAGE_NUMBER = 2;
  // archives
  public static final int MIN_HEIGHT_VALUE = 100;
  public static final int MAX_HEIGHT_VALUE = 250;
  public static final int MIN_WEIGHT_VALUE = 30;
  public static final int MAX_WEIGHT_VALUE = 200;
  public static final String PROVINCE_DATA_FILE_NAME = "province.json";
  public static final String SERVICE_CONTRACT_URL =
      "https://manager.heal361.com/healprovisions/healtos.html";
  public static final String PRIVACY_CONTRACT_URL =
      "https://manager.heal361.com/healprovisions/healpp.html";
  private Const() {
  }
  public enum Gender {

    Male(R.string.gender_male), Female(R.string.gender_female);

    private int nameResId;

    Gender(int nameResId) {
      this.nameResId = nameResId;
    }

    public static Gender fromString(String name) {
      for (Gender gender : Gender.values()) {
        if (gender.getName().equals(name)) {
          return gender;
        }
      }

      return null;
    }

    public String getName() {
      return KharazimApplication.getAppContext().getString(nameResId);
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

    public static Aim fromValue(int value) {
      for (Aim aim : Aim.values()) {
        if (aim.getValue() == value) {
          return aim;
        }
      }

      return null;
    }

    public int getValue() {
      return value;
    }

    public String getName() {
      return KharazimApplication.getAppContext().getString(nameId);
    }
  }

  public enum LoginType {
    Standard(0, R.string.login_type_standard),
    Wechat(1, R.string.login_type_wechat),
    QQ(2, R.string.login_type_qq),
    Weibo(3, R.string.login_type_weibo);

    private int value;
    private int nameId;

    LoginType(int value, int nameId) {
      this.value = value;
      this.nameId = nameId;
    }

    public static LoginType fromValue(int value) {
      for (LoginType type : LoginType.values()) {
        if (type.getValue() == value) {
          return type;
        }
      }

      return null;
    }

    public int getValue() {
      return value;
    }

    public String getName() {
      return KharazimApplication.getAppContext().getString(nameId);
    }
  }

  public static class TabId {
    public static final String NONE = "";

    private TabId() {
    }
  }
}
