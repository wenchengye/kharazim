package com.heqi.kharazim.archives;

/**
 * Created by overspark on 2017/3/13.
 */

public class ArchivesImpl {

  public static class Const {

    public static final String ARCHIVES_PREFERENCE_NAME = "kharazim_archives";

    public static final String PREFERENCE_KEY_USER_ARCHIVE_BUNDLE_PREFIX = "kharazim_user_archive";

    public static final String PREFERENCE_KEY_LAST_USER_ID_STRING = "kharazim_last_user_id";

    public static final String BUNDLE_KEY_USER_ID_STRING = "kharazim_user_id";

    public static final String BUNDLE_KEY_USER_PROFILE_OBJECT = "kharazim_user_profile";


    private Const() {}
  }

  public static class State {

    public static final int OFFLINE = 0;

    public static final int LOGINING = 1;

    public static final int ONLINE = 2;

    private State() {}
  }

}