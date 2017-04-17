package com.heqi.kharazim.archives;

import com.heqi.kharazim.archives.model.HealthCondition;
import com.heqi.kharazim.archives.model.UserProfile;
import com.heqi.kharazim.consume.model.ConsumeCourseRecord;

import java.util.Map;

/**
 * Created by overspark on 2017/3/13.
 */

public interface ArchivesService {

  class ParamsKey {
    public static final String PARAMS_KEY_CHINESE_NAME = "chname";
    public static final String PARAMS_KEY_NICKNAME = "nickname";
    public static final String PARAMS_KEY_ADDRESS = "address";
    public static final String PARAMS_KEY_POSTCODE = "postcode";
    public static final String PARAMS_KEY_SEX = "sex";
    public static final String PARAMS_KEY_BIRTHDAY = "birthday";
    public static final String PARAMS_KEY_NAME = "truename";
    public static final String PARAMS_KEY_ID_NUMBER = "idnumber";
    public static final String PARAMS_KEY_ID_NUMBER_OTHER = "idnumberother";
    public static final String PARAMS_KEY_HEIGHT = "stature";
    public static final String PARAMS_KEY_WEIGHT = "weight";
    public static final String PARAMS_KEY_HEART_RATE = "heartrate";
    public static final String PARAMS_KEY_PRESSURE_HIGH = "pressurehigh";
    public static final String PARAMS_KEY_PRESSURE_LOW = "pressurelow";
    public static final String PARAMS_KEY_OTHER_DEC = "otherdec";
  }

  int getState();

  void addObserver(ArchivesObserver observer);

  void removeObserver(ArchivesObserver observer);

  boolean relogin(String userId, ArchivesTaskCallback callback);

  boolean login(String id, String password,
                ArchivesTaskCallback callback);

  boolean updateCurrentUserProfile(ArchivesTaskCallback callback);

  boolean updateCurrentHealthCondition(ArchivesTaskCallback callback);

  boolean uploadCurrentUserProfile(Map<String, Object> params, ArchivesTaskCallback callback);

  boolean uploadCurrentHealthCondition(Map<String, Object> params,
                                       ArchivesTaskCallback callback);

  boolean uploadHeadImage(byte[] image, ArchivesTaskCallback callback);

  boolean uploadUserAim(int aim, ArchivesTaskCallback callback);

  boolean addPlan(String planId, ArchivesTaskCallback callback);

  boolean removePlan(String planId, ArchivesTaskCallback callback);

  boolean uploadConsumeProgress(ConsumeCourseRecord record, ArchivesTaskCallback callback);

  boolean uploadConsumeStar(String userplanid, String dailyid, String courseid, int star,
                            ArchivesTaskCallback callback);

  void logout();

  String getLastUserId();

  String getCurrentUserId();

  String getCurrentAccessToken();

  UserProfile getCurrentUserProfile();

  HealthCondition getCurrentHealthCondition();

  interface ArchivesTaskCallback {

    void onTaskSuccess(int code, String msg);

    void onTaskFailed();
  }

  interface ArchivesObserver {

    void onLogin(String userId);

    void onUserProfileUpdated(String userId, UserProfile userProfile);

    void onUserProfileUpload(String userId);

    void onHealthConditionUpdated(String userId, HealthCondition healthCondition);

    void onHealthConditionUpload(String userId);

    void onAddPlan(String userId, String planId);

    void onRemovePlan(String userId, String planId);
  }

  class State {

    public static final int OFFLINE = 0;

    public static final int LOGINING = 1;

    public static final int ONLINE = 2;

    private State() {
    }
  }


}
