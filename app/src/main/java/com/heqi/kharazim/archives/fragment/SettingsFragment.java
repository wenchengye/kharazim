package com.heqi.kharazim.archives.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.heqi.image.view.AsyncImageView;
import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.archives.SimpleArchivesObserver;
import com.heqi.kharazim.archives.model.UserProfile;
import com.heqi.kharazim.ui.fragment.async.AsyncLoadFragment;

/**
 * Created by overspark on 2017/3/19.
 */

public class SettingsFragment extends AsyncLoadFragment {

  private View userProfileView;
  private View accountManagementView;
  private View healthAimView;
  private View localManagementView;
  private View aboutUsView;

  private AsyncImageView headIconIv;
  private TextView nicknameTv;

  private SettingsFragmentArchivesObserver archivesObserver =
      new SettingsFragmentArchivesObserver();


  @Override
  protected void onInflated(View contentView, Bundle savedInstanceState) {

    UserProfile userProfile = KharazimApplication.getArchives().getCurrentUserProfile();

    if (userProfile != null) {
      headIconIv.loadNetworkImage(userProfile.getHeadimg(), 0);
      nicknameTv.setText(userProfile.getNickname());
    }

    KharazimApplication.getArchives().addObserver(archivesObserver);
  }

  @Override
  protected int getLayoutResId() {
    return 0;
  }

  @Override
  protected void onPrepareLoading() {

  }

  @Override
  protected void onStartLoading() {
    KharazimApplication.getArchives().updateCurrentUserProfile(null);
  }

  private class SettingsFragmentArchivesObserver extends SimpleArchivesObserver {
    @Override
    public void onUserProfileUpdated(String userId, UserProfile userProfile) {
      if (userProfile == null) return;

      headIconIv.loadNetworkImage(userProfile.getHeadimg(), 0);
      nicknameTv.setText(userProfile.getNickname());
    }
  }
}
