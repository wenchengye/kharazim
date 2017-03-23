package com.heqi.kharazim.archives.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.R;
import com.heqi.kharazim.archives.http.request.RegisterRequest;
import com.heqi.kharazim.archives.model.RegisterResult;
import com.heqi.kharazim.config.Const;
import com.heqi.kharazim.ui.fragment.async.AsyncLoadFragment;
import com.heqi.kharazim.utils.KharazimUtils;
import com.heqi.rpc.RpcHelper;

/**
 * Created by overspark on 2017/3/16.
 */

public class RegisterFragment extends AsyncLoadFragment {

  private EditText userIdEditText;
  private EditText nicknameEditText;
  private EditText passwordEditText;
  private EditText passwordConfirmEditText;
  private View registerBtn;
  private RegisterFragmentListener listener;

  public void setListener(RegisterFragmentListener listener) {
    this.listener = listener;
  }

  @Override
  protected void onInflated(View contentView, Bundle savedInstanceState) {
    initView(contentView);
    initListeners();
  }

  private void initView(View contentView) {
    if (contentView == null) return;

    userIdEditText = (EditText) contentView.findViewById(R.id.archives_register_user_input_et);
    nicknameEditText = (EditText) contentView.findViewById(
        R.id.archives_register_nickname_input_et);
    passwordEditText = (EditText) contentView.findViewById(
        R.id.archives_register_password_input_et);
    passwordConfirmEditText = (EditText) contentView.findViewById(
        R.id.archives_register_password_confirm_input_et);
    registerBtn = contentView.findViewById(R.id.archives_register_action_register_btn);
  }

  private void initListeners() {
    if (registerBtn != null) {
      registerBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          register();
        }
      });
    }
  }

  private void register() {
    String userId = userIdEditText.getText().toString().trim();
    String nickname = nicknameEditText.getText().toString().trim();
    String password = passwordEditText.getText().toString().trim();
    String passwordConfirm = passwordConfirmEditText.getText().toString().trim();

    if (TextUtils.isEmpty(userId)) {
      KharazimUtils.showToast(KharazimApplication.getAppContext().getString(
          R.string.archives_lack_id_hint_text));
      return;
    }

    if (TextUtils.isEmpty(nickname)) {
      KharazimUtils.showToast(KharazimApplication.getAppContext().getString(
          R.string.archives_lack_nickname_hint_text));
      return;
    }

    if (TextUtils.isEmpty(password)) {
      KharazimUtils.showToast(KharazimApplication.getAppContext().getString(
          R.string.archives_lack_password_hint_text));
      return;
    }

    if (TextUtils.isEmpty(passwordConfirm)) {
      KharazimUtils.showToast(KharazimApplication.getAppContext().getString(
          R.string.archives_lack_password_confirm_hint_text));
      return;
    }

    if (!password.equals(passwordConfirm)) {
      KharazimUtils.showToast(KharazimApplication.getAppContext().getString(
          R.string.archives_register_password_confirm_failed_hint_text));
      return;
    }

    Response.Listener<RegisterResult> successListener = new Response.Listener<RegisterResult>() {
      @Override
      public void onResponse(RegisterResult response) {
        if (KharazimUtils.isRetCodeOK(response.getRet_code())) {
          KharazimUtils.showToast(KharazimApplication.getAppContext().getString(
              R.string.archives_register_success_hint_text));

          if (listener != null) {
            listener.onRegisterFinished();
          }
        } else {
          if (TextUtils.isEmpty(response.getRet_msg())) {
            KharazimUtils.showToast(KharazimApplication.getAppContext().getString(
                R.string.archives_register_failed_hint_text));
          } else {
            KharazimUtils.showToast(response.getRet_msg());
          }
        }
      }
    };

    Response.ErrorListener errorListener = new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        KharazimUtils.showToast(KharazimApplication.getAppContext().getString(
            R.string.archives_register_failed_hint_text));
      }
    };

    RegisterRequest registerRequest = new RegisterRequest(successListener, errorListener);
    if (userId.contains(Const.EMAIL_KEY_WORD)) {
      registerRequest.setEmail(userId);
    } else {
      registerRequest.setPhoneNumber(userId);
    }

    registerRequest.setNickname(nickname);
    registerRequest.setPassword(password);

    RpcHelper.getInstance(KharazimApplication.getAppContext()).executeRequestAsync(registerRequest);

  }

  @Override
  protected int getLayoutResId() {
    return R.layout.archives_register_fragment_layout;
  }

  @Override
  protected boolean needToLoadData() {
    return false;
  }

  @Override
  protected void onPrepareLoading() {
    // is not used
  }

  @Override
  protected void onStartLoading() {
    // is not used
  }

  public interface RegisterFragmentListener {
    void onRegisterFinished();
  }
}
