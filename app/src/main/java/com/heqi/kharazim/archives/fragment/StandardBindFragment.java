package com.heqi.kharazim.archives.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.R;
import com.heqi.kharazim.archives.ArchivesService;
import com.heqi.kharazim.archives.view.ArchivesEditTextView;
import com.heqi.kharazim.ui.fragment.async.AsyncLoadFragment;
import com.heqi.kharazim.utils.KharazimUtils;

/**
 * Created by overspark on 2017/5/17.
 */

public class StandardBindFragment extends AsyncLoadFragment {

  private ArchivesEditTextView targetEt;
  private TextView targetLabelTv;
  private ArchivesEditTextView verifyCodeEt;
  private ArchivesEditTextView passwordEt;
  private ArchivesEditTextView passwordConfirmEt;
  private View submitBtn;
  private ImageView backBtn;

  private int type = ArchivesService.LoginFlag.PHONE_FLAG;
  private boolean needPassword = true;
  private StandardBindFragmentListener listener;

  public void setType(int type) {
    this.type = type;
  }

  public void setListener(StandardBindFragmentListener listener) {
    this.listener = listener;
  }

  @Override
  protected void onInflated(View contentView, Bundle savedInstanceState) {
    initView(contentView);
    initListeners();

    contentView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        KharazimUtils.hideSoftKeyBoard(v.getWindowToken(), KharazimApplication.getAppContext());
      }
    });
  }

  private void initView(View contentView) {

    targetEt = (ArchivesEditTextView) contentView.findViewById(
        R.id.archives_standard_bind_target_tab);
    targetLabelTv = (TextView) contentView.findViewById(
        R.id.archives_standard_bind_target_label_tv);
    verifyCodeEt = (ArchivesEditTextView) contentView.findViewById(
        R.id.archives_standard_bind_verify_code_tab);
    passwordEt = (ArchivesEditTextView) contentView.findViewById(
        R.id.archives_standard_bind_password_tab);
    passwordConfirmEt = (ArchivesEditTextView) contentView.findViewById(
        R.id.archives_standard_bind_confirm_password_tab);
    submitBtn = contentView.findViewById(R.id.archives_bind_commit_btn);

    TextView titleView = (TextView) contentView.findViewById(R.id.explore_header_title_text_view);
    if (titleView != null) {
      if (type == ArchivesService.LoginFlag.PHONE_FLAG) {
        titleView.setText(R.string.archives_bind_phone_title_text);
      } else {
        titleView.setText(R.string.archives_bind_mail_title_text);
      }
    }
    backBtn = (ImageView) contentView.findViewById(R.id.explore_header_left_button);
    if (backBtn != null) {
      backBtn.setImageResource(R.drawable.icon_header_navigate_back);
    }


    int loginCode = KharazimApplication.getArchives().getLoginCode();
    if (type == ArchivesService.LoginFlag.PHONE_FLAG) {
      needPassword = (loginCode & ArchivesService.LoginFlag.MAIL_FLAG) == 0;
    } else {
      needPassword = (loginCode & ArchivesService.LoginFlag.PHONE_FLAG) == 0;
    }

    passwordEt.setVisibility(needPassword ? View.VISIBLE : View.GONE);
    passwordConfirmEt.setVisibility(passwordEt.getVisibility());

    if (targetEt != null && targetEt.getEditText() != null) {
      if (type == ArchivesService.LoginFlag.PHONE_FLAG) {
        targetEt.getEditText().setHint(R.string.archives_lack_phone_number_hint_text);
      } else {
        targetEt.getEditText().setHint(R.string.archives_lack_email_hint_text);
      }
    }

    if (targetLabelTv != null) {
      if (type == ArchivesService.LoginFlag.PHONE_FLAG) {
        targetLabelTv.setText(R.string.archives_bind_type_phone_label_text);
      } else {
        targetLabelTv.setText(R.string.archives_bind_type_mail_label_text);
      }
    }
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
    String target = targetEt.getText();
    String verifyCode = verifyCodeEt.getText();
    String password = passwordEt.getText();
    String passwordConfirm = passwordConfirmEt.getText();

    if (TextUtils.isEmpty(target)) {
      if (type == ArchivesService.LoginFlag.PHONE_FLAG) {
        KharazimUtils.showToast(R.string.archives_lack_phone_number_hint_text);
      } else {
        KharazimUtils.showToast(R.string.archives_lack_email_hint_text);
      }
      return;
    }

    if (TextUtils.isEmpty(verifyCode)) {
      KharazimUtils.showToast(R.string.archives_lack_verify_code_hint_text);
      return;
    }

    if (needPassword) {
      if (TextUtils.isEmpty(password)) {
        KharazimUtils.showToast(R.string.archives_lack_password_hint_text);
        return;
      }

      if (TextUtils.isEmpty(passwordConfirm)) {
        KharazimUtils.showToast(R.string.archives_lack_password_confirm_hint_text);
        return;
      }

      if (!password.equals(passwordConfirm)) {
        KharazimUtils.showToast(R.string.archives_register_password_confirm_failed_hint_text);
        return;
      }
    }

    ArchivesService.ArchivesTaskCallback bindCallback = new ArchivesService.ArchivesTaskCallback() {
      @Override
      public void onTaskSuccess(int code, String msg) {
        if (KharazimUtils.isRetCodeOK(code)) {
          if (listener != null) {
            listener.onBind();
          }
        } else {
          KharazimUtils.showToast(R.string.archives_bind_failed_hint_text);
        }
      }

      @Override
      public void onTaskFailed() {
        KharazimUtils.showToast(R.string.archives_bind_failed_hint_text);
      }
    };

    if (type == ArchivesService.LoginFlag.PHONE_FLAG) {
      if (!KharazimApplication.getArchives().bindPhone(target, null, verifyCode,
          needPassword ? password : null, bindCallback)) {
        KharazimUtils.showToast(R.string.archives_bind_failed_hint_text);
      }
    } else {
      if (!KharazimApplication.getArchives().bindMail(target, verifyCode,
          needPassword ? password : null, bindCallback)) {
        KharazimUtils.showToast(R.string.archives_bind_failed_hint_text);
      }
    }
  }

  @Override
  protected int getLayoutResId() {
    return R.layout.archives_standard_bind_fragment_layout;
  }

  @Override
  protected void onPrepareLoading() {
  }

  @Override
  protected void onStartLoading() {
  }

  public interface StandardBindFragmentListener {

    void onBack();

    void onBind();
  }
}
