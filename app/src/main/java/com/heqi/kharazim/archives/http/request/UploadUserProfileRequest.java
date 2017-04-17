package com.heqi.kharazim.archives.http.request;

import com.android.volley.Response;
import com.heqi.kharazim.archives.model.ArchivesCommonResult;

import java.util.Map;

/**
 * Created by overspark on 2017/3/13.
 */

public class UploadUserProfileRequest extends AbstractKharazimArchivesHttpRequest<ArchivesCommonResult> {

  private static final String UPLOAD_USER_PROFILE_DIRECTORY = "usr/infoupdate";
  private static final String GET_PARAMS_KEY_CHINESE_NAME = "chname";
  private static final String GET_PARAMS_KEY_NICKNAME = "nickname";
  private static final String GET_PARAMS_KEY_ADDRESS = "address";
  private static final String GET_PARAMS_KEY_POSTCODE = "postcode";
  private static final String GET_PARAMS_KEY_SEX = "sex";
  private static final String GET_PARAMS_KEY_BIRTHDAY = "birthday";
  private static final String GET_PARAMS_KEY_NAME = "truename";
  private static final String GET_PARAMS_KEY_ID_NUMBER = "idnumber";
  private static final String GET_PARAMS_KEY_ID_NUMBER_OTHER = "idnumberother";


  private String chineseName;
  private String nickname;
  private String address;
  private String postcode;
  private String sex;
  private String birthday;
  private String name;
  private String idNumber;
  private String otherIdNumber;

  public UploadUserProfileRequest(Response.Listener<ArchivesCommonResult> listener,
                                  Response.ErrorListener errorListener,
                                  String accessToken) {
    super(ArchivesCommonResult.class, listener, errorListener, accessToken);
  }

  @Override
  protected String getBaseUrlDirectory() {
    return UPLOAD_USER_PROFILE_DIRECTORY;
  }

  @Override
  protected void setGetParams(Map<String, String> params) {
    super.setGetParams(params);
    if (chineseName != null) {
      params.put(GET_PARAMS_KEY_CHINESE_NAME, this.chineseName);
    }

    if (this.nickname != null) {
      params.put(GET_PARAMS_KEY_NICKNAME, this.nickname);
    }

    if (this.address != null) {
      params.put(GET_PARAMS_KEY_ADDRESS, this.address);
    }

    if (this.postcode != null) {
      params.put(GET_PARAMS_KEY_POSTCODE, this.postcode);
    }

    if (this.sex != null) {
      params.put(GET_PARAMS_KEY_SEX, this.sex);
    }

    if (this.birthday != null) {
      params.put(GET_PARAMS_KEY_BIRTHDAY, this.birthday);
    }

    if (this.name != null) {
      params.put(GET_PARAMS_KEY_NAME, this.name);
    }

    if (this.idNumber != null) {
      params.put(GET_PARAMS_KEY_ID_NUMBER, this.idNumber);
    }

    if (this.otherIdNumber != null) {
      params.put(GET_PARAMS_KEY_ID_NUMBER_OTHER, this.otherIdNumber);
    }
  }

  public String getChineseName() {
    return chineseName;
  }

  public void setChineseName(String chineseName) {
    this.chineseName = chineseName;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getPostcode() {
    return postcode;
  }

  public void setPostcode(String postcode) {
    this.postcode = postcode;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public String getBirthday() {
    return birthday;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getIdNumber() {
    return idNumber;
  }

  public void setIdNumber(String idNumber) {
    this.idNumber = idNumber;
  }

  public String getOtherIdNumber() {
    return otherIdNumber;
  }

  public void setOtherIdNumber(String otherIdNumber) {
    this.otherIdNumber = otherIdNumber;
  }
}
