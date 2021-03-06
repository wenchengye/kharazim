package com.heqi.kharazim.archives.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.R;
import com.heqi.kharazim.activity.NavigationManager;
import com.heqi.kharazim.archives.SimpleArchivesObserver;
import com.heqi.kharazim.archives.model.UserProfile;
import com.heqi.kharazim.ui.fragment.async.AsyncLoadFragment;
import com.heqi.kharazim.view.CircleAsyncImageView;

/**
 * Created by overspark on 2017/3/19.
 */

public class SettingsFragment extends AsyncLoadFragment {

  private View userProfileView;
  private View accountManagementView;
  private View healthAimView;
  private View localManagementView;
  private View aboutUsView;
  private CircleAsyncImageView headIconIv;
  private TextView nicknameTv;

  private SettingsFragmentArchivesObserver archivesObserver =
      new SettingsFragmentArchivesObserver();

  @Override
  protected void onInflated(View contentView, Bundle savedInstanceState) {

    initView(contentView);

    UserProfile userProfile = KharazimApplication.getArchives().getCurrentUserProfile();

    if (userProfile != null) {
      headIconIv.loadNetworkImage(userProfile.getHeadimg(), R.drawable.icon_head_image_place_holder);
      nicknameTv.setText(userProfile.getNickname());
    }

    KharazimApplication.getArchives().addObserver(archivesObserver);
  }

  private void initView(View contentView) {
    if (contentView == null) return;

    userProfileView = contentView.findViewById(R.id.archives_settings_user_profile_tab);
    accountManagementView = contentView.findViewById(R.id.archives_settings_account_tab);
    healthAimView = contentView.findViewById(R.id.archives_settings_health_aim_tab);
    localManagementView = contentView.findViewById(R.id.archives_settings_cache_tab);
    aboutUsView = contentView.findViewById(R.id.archives_settings_about_tab);
    headIconIv = (CircleAsyncImageView) contentView.findViewById(
        R.id.archives_settings_user_head_iv);
    nicknameTv = (TextView) contentView.findViewById(R.id.archives_settings_user_nickname_tv);

    userProfileView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        NavigationManager.navigateToEditUserProfile(getActivity());
      }
    });

    accountManagementView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        NavigationManager.navigateToAccountManagement(getActivity());
      }
    });

    healthAimView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        NavigationManager.navigateToEditUserAim(getActivity());
      }
    });

    aboutUsView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        NavigationManager.navigateToAboutUs(getActivity());
      }
    });

  }

  @Override
  protected int getLayoutResId() {
    return R.layout.archives_settings_fragment_layout;
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

      headIconIv.loadNetworkImage(userProfile.getHeadimg(),
          R.drawable.icon_head_image_place_holder);
      nicknameTv.setText(userProfile.getNickname());
    }
  }
}
