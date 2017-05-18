package com.heqi.kharazim.archives.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.R;
import com.heqi.kharazim.archives.SimpleArchivesObserver;
import com.heqi.kharazim.archives.model.UserProfile;
import com.heqi.kharazim.archives.view.ArchivesSettingDividerView;
import com.heqi.kharazim.archives.view.ArchivesUserAimView;
import com.heqi.kharazim.config.Const;
import com.heqi.kharazim.ui.fragment.async.AsyncLoadFragment;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by overspark on 2017/4/13.
 */

public class EditUserAimFragment extends AsyncLoadFragment {

  private View uploadUserAimView;
  private LinearLayout contentLayout;
  private Map<Const.Aim, ArchivesUserAimView> aimViewMap = new HashMap<>();

  private Const.Aim aim;

  private EditUserAimFragmentListener listener;
  private EditUserAimArchivesObserver archivesObserver = new EditUserAimArchivesObserver();


  public void setListener(EditUserAimFragmentListener listener) {
    this.listener = listener;
  }

  @Override
  protected void onInflated(View contentView, Bundle savedInstanceState) {
    initView(contentView);
    initListener();
    KharazimApplication.getArchives().addObserver(archivesObserver);
  }

  private void initView(View contentView) {

    contentLayout = (LinearLayout) contentView.findViewById(
        R.id.archives_edit_user_aim_content_layout);
    uploadUserAimView = contentView.findViewById(R.id.archives_edit_user_aim_commit_button);

    for (Const.Aim aim : Const.Aim.values()) {
      ArchivesSettingDividerView dividerView =
          ArchivesSettingDividerView.newInstance(contentLayout);
      contentLayout.addView(dividerView);

      ArchivesUserAimView aimView = ArchivesUserAimView.newInstance(contentLayout);
      aimView.setData(aim);
      aimViewMap.put(aim, aimView);
      contentLayout.addView(aimView);
    }

    if (KharazimApplication.getArchives().getCurrentUserProfile() != null) {
      selectedAim(Const.Aim.fromValue(
          KharazimApplication.getArchives().getCurrentUserProfile().getUseraim()));
    }
  }

  private void initListener() {
    for (Iterator<Const.Aim> iterator = aimViewMap.keySet().iterator(); iterator.hasNext(); ) {
      final Const.Aim aim = iterator.next();

      aimViewMap.get(aim).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          selectedAim(aim);
        }
      });
    }

    uploadUserAimView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        uploadAim();
      }
    });
  }

  private void selectedAim(Const.Aim aim) {
    this.aim = aim;

    for (Iterator<Const.Aim> iterator = aimViewMap.keySet().iterator(); iterator.hasNext(); ) {
      final Const.Aim item = iterator.next();

      aimViewMap.get(item).setSelected(this.aim != null && item.ordinal() == this.aim.ordinal());
    }
  }

  private void uploadAim() {
    if (this.aim != null) {
      KharazimApplication.getArchives().uploadUserAim(this.aim.getValue(), null);
    }

    if (listener != null) {
      listener.onUploadUserAim();
    }
  }

  @Override
  protected int getLayoutResId() {
    return R.layout.archives_edit_user_aim_fragment_layout;
  }

  @Override
  protected void onPrepareLoading() {

  }

  @Override
  protected void onStartLoading() {
    KharazimApplication.getArchives().updateCurrentUserProfile(null);
  }

  public interface EditUserAimFragmentListener {
    void onUploadUserAim();
  }

  private class EditUserAimArchivesObserver extends SimpleArchivesObserver {
    @Override
    public void onUserProfileUpdated(String userId, UserProfile userProfile) {

      if (userProfile != null) {
        selectedAim(Const.Aim.fromValue(userProfile.getUseraim()));
      }
    }
  }
}
