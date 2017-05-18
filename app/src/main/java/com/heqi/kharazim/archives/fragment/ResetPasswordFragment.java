package com.heqi.kharazim.archives.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.R;
import com.heqi.kharazim.archives.ArchivesService;
import com.heqi.kharazim.archives.view.ArchivesEditTextView;
import com.heqi.kharazim.ui.fragment.async.AsyncLoadFragment;
import com.heqi.kharazim.utils.KharazimUtils;

/**
 * Created by overspark on 2017/5/17.
 */

public class ResetPasswordFragment extends AsyncLoadFragment {

  private ArchivesEditTextView oldPasswordEt;
  private ArchivesEditTextView newPasswordEt;
  private ArchivesEditTextView newPasswordConfirmEt;
  private View submitBtn;
  private View backBtn;

  private ResetPasswordFragmentListener listener;

  public void setListener(ResetPasswordFragmentListener listener) {
    this.listener = listener;
  }

  @Override
  protected void onInflated(View contentView, Bundle savedInstanceState) {
    initView(contentView);
    initListeners();
  }

  private void initView(View contentView) {

  }

  private void initListeners() {
    if (submitBtn != null) {
      submitBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          submit();
        }
      });
    }

    if (backBtn != null) {
      backBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (listener != null) {
            listener.onBack();
          }
        }
      });
    }
  }

  private void submit() {
    String oldPassword = oldPasswordEt.getText();
    String newPassword = newPasswordEt.getText();
    String newPasswordConfirm = newPasswordConfirmEt.getText();

    if (TextUtils.isEmpty(oldPassword)) {
      KharazimUtils.showToast(R.string.archives_lack_old_password_hint_text);
      return;
    }

    if (TextUtils.isEmpty(newPassword)) {
      KharazimUtils.showToast(R.string.archives_lack_new_password_hint_text);
      return;
    }

    if (TextUtils.isEmpty(newPasswordConfirm)) {
      KharazimUtils.showToast(R.string.archives_lack_new_password_confirm_hint_text);
      return;
    }

    if (!newPassword.equals(newPasswordConfirm)) {
      KharazimUtils.showToast(R.string.archives_register_password_confirm_failed_hint_text);
      return;
    }

    ArchivesService.ArchivesTaskCallback resetCallback =
        new ArchivesService.ArchivesTaskCallback() {
          @Override
          public void onTaskSuccess(int code, String msg) {
            if (KharazimUtils.isRetCodeOK(code)) {
              if (listener != null) {
                listener.onReset();
              }
            } else {
              KharazimUtils.showToast(R.string.archives_reset_password_failed_hint_text);
            }
          }

          @Override
          public void onTaskFailed() {
            KharazimUtils.showToast(R.string.archives_reset_password_failed_hint_text);
          }
        };

    if (!KharazimApplication.getArchives().resetPassword(oldPassword, newPassword, resetCallback)) {
      KharazimUtils.showToast(R.string.archives_reset_password_failed_hint_text);
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
  }

  public interface ResetPasswordFragmentListener {

    void onBack();

    void onReset();
  }
}
