package com.heqi.kharazim.archives.fragment;

import android.os.Bundle;
import android.view.View;

import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.archives.ArchivesService;
import com.heqi.kharazim.archives.SimpleArchivesObserver;
import com.heqi.kharazim.archives.model.UserProfile;
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
      Map<String, Object> params = new HashMap<>();
      params.put(ArchivesService.ParamsKey.PARAMS_KEY_USER_AIM, this.aim.getValue());
      KharazimApplication.getArchives().uploadCurrentHealthCondition(params, null);
    }

    if (listener != null) {
      listener.onUploadUserAim();
    }
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

  private class EditUserAimArchivesObserver extends SimpleArchivesObserver {
    @Override
    public void onUserProfileUpdated(String userId, UserProfile userProfile) {

      if (userProfile != null) {
        selectedAim(Const.Aim.fromValue(userProfile.getUseraim()));
      }
    }
  }

  public interface EditUserAimFragmentListener {
    void onUploadUserAim();
  }
}
