package com.heqi.kharazim.archives;

import com.heqi.kharazim.archives.model.HealthCondition;
import com.heqi.kharazim.archives.model.UserProfile;

/**
 * Created by overspark on 2017/3/13.
 */

public interface Archives {

  class State {

    public static final int OFFLINE = 0;

    public static final int LOGINING = 1;

    public static final int ONLINE = 2;

    private State() {}
  }

  interface ArchivesTaskCallback {

    void onTaskSuccess(int code, String msg);

    void onTaskFailed();
  }

  interface ArchivesObserver {

    void onLogin(String userId);

    void onUserProfileUpdated(String userId, UserProfile userProfile);

    void onHealthConditionUpdated(String userId, HealthCondition healthCondition);
  }

  int getState();

  void addObserver(ArchivesObserver observer);

  void removeObserver(ArchivesObserver observer);

  boolean relogin(String userId, ArchivesTaskCallback callback);

  boolean login(String phoneNumber, String zoneNumber, String password,
                ArchivesTaskCallback callback);

  boolean login(String email, String password, ArchivesTaskCallback callback);

  boolean updateCurrentUserProfile(ArchivesTaskCallback callback);

  boolean updateCurrentHealthCondition(ArchivesTaskCallback callback);

  boolean uploadCurrentUserProfile(UserProfile userProfile, ArchivesTaskCallback callback);

  boolean uploadCurrentHealthCondition(HealthCondition healthCondition,
                                       ArchivesTaskCallback callback);

  void logout();

  String getLastUserId();

  String getCurrentUserId();

  String getCurrentAccessToken();

  UserProfile getCurrentUserProfile();

  HealthCondition getCurrentHealthCondition();


}
