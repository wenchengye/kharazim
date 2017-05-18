package com.heqi.kharazim.archives.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.R;
import com.heqi.kharazim.activity.NavigationManager;
import com.heqi.kharazim.archives.ArchivesService;
import com.heqi.kharazim.archives.SimpleArchivesObserver;
import com.heqi.kharazim.archives.model.UserProfile;
import com.heqi.kharazim.archives.view.ArchivesLoginTypeView;
import com.heqi.kharazim.archives.view.ArchivesSettingDividerView;
import com.heqi.kharazim.config.Const;
import com.heqi.kharazim.third.ThirdPlatformService;
import com.heqi.kharazim.ui.fragment.async.AsyncLoadFragment;
import com.heqi.kharazim.utils.KharazimUtils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by overspark on 2017/5/17.
 */

public class AccountManagementFragment extends AsyncLoadFragment {

  private TextView currentLoginTypeTv;
  private View phoneBindedView;
  private TextView phoneNumberTv;
  private View phoneResetPwView;
  private View phoneUnbindedView;
  private View bindPhoneView;
  private View mailBindedView;
  private TextView mailTv;
  private View mailResetPwView;
  private View mailUnbindedView;
  private View bindMailView;
  private View logoutView;
  private LinearLayout contentLayout;
  private Map<Const.LoginType, ArchivesLoginTypeView> loginTypeViewMap = new HashMap<>();
  private ImageView backBtn;

  private AccountManagementArchivesObserver archivesObserver =
      new AccountManagementArchivesObserver();

  private AccountManagementFragmentListener listener;

  public void setListener(AccountManagementFragmentListener listener) {
    this.listener = listener;
  }

  public void update() {
    requestLoad();
  }

  @Override
  protected void onInflated(View contentView, Bundle savedInstanceState) {
    initView(contentView);
    initListeners();
    KharazimApplication.getArchives().addObserver(archivesObserver);
  }

  private void initView(View contentView) {

    if (contentView == null) return;

    currentLoginTypeTv = (TextView) contentView.findViewById(
        R.id.archives_account_management_current_login_type_tv);

    phoneBindedView = contentView.findViewById(
        R.id.archives_account_management_binded_phone_layout);
    phoneBindedView.setVisibility(View.GONE);
    phoneNumberTv = (TextView) contentView.findViewById(
        R.id.archives_account_management_binded_phone_tv);
    phoneResetPwView = contentView.findViewById(
        R.id.archives_account_management_reset_password_with_phone_button);
    phoneUnbindedView = contentView.findViewById(
        R.id.archives_account_management_bind_phone_button);
    phoneUnbindedView.setVisibility(View.GONE);
    bindPhoneView = phoneUnbindedView;

    mailBindedView = contentView.findViewById(
        R.id.archives_account_management_binded_mail_layout);
    mailBindedView.setVisibility(View.GONE);
    mailTv = (TextView) contentView.findViewById(
        R.id.archives_account_management_binded_mail_tv);
    mailResetPwView = contentView.findViewById(
        R.id.archives_account_management_reset_password_with_mail_button);
    mailUnbindedView = contentView.findViewById(
        R.id.archives_account_management_bind_mail_button);
    mailUnbindedView.setVisibility(View.GONE);
    bindMailView = mailUnbindedView;

    logoutView = contentView.findViewById(R.id.archives_account_management_logout_button);
    contentLayout = (LinearLayout) contentView.findViewById(
        R.id.archives_account_management_third_layout);

    TextView titleView = (TextView) contentView.findViewById(R.id.explore_header_title_text_view);
    if (titleView != null) {
      titleView.setText(R.string.archives_account_manage_title_text);
    }

    backBtn = (ImageView) contentView.findViewById(R.id.explore_header_left_button);
    if (backBtn != null) {
      backBtn.setImageResource(R.drawable.icon_header_navigate_back);
    }

    for (int i = 0; i < Const.LoginType.values().length; ++i) {
      Const.LoginType type = Const.LoginType.values()[i];

      if (type == null || type.ordinal() == Const.LoginType.Standard.ordinal()) continue;

      ArchivesSettingDividerView dividerView =
          ArchivesSettingDividerView.newInstance(contentLayout);
      contentLayout.addView(dividerView);

      ArchivesLoginTypeView view = ArchivesLoginTypeView.newInstance(contentLayout);
      view.setData(type);
      loginTypeViewMap.put(type, view);
      contentLayout.addView(view);
    }
  }

  private void initListeners() {
    if (phoneResetPwView != null) {
      phoneResetPwView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          resetPassword();
        }
      });
    }

    if (bindPhoneView != null) {
      bindPhoneView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          bindPhone();
        }
      });
    }

    if (mailResetPwView != null) {
      mailResetPwView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          resetPassword();
        }
      });
    }

    if (bindMailView != null) {
      bindMailView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          bindMail();
        }
      });
    }

    if (logoutView != null) {
      logoutView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          logout();
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

  private void logout() {
    KharazimApplication.getArchives().logout();
  }

  private void bindThird(final Const.LoginType loginType) {

    final ArchivesService.ArchivesTaskCallback bindCallback = new ArchivesService.ArchivesTaskCallback() {
      @Override
      public void onTaskSuccess(int code, String msg) {
        if (KharazimUtils.isRetCodeOK(code)) {
          requestLoad();
        }
      }

      @Override
      public void onTaskFailed() {
      }
    };


    if (loginType.ordinal() == Const.LoginType.Wechat.ordinal()) {
      KharazimApplication.getThirdPlatform().wechatLogin(
          new ThirdPlatformService.ThirdPlatformTaskCallback() {
            @Override
            public void onTaskSuccess() {
              KharazimApplication.getArchives().bindThird(loginType,
                  KharazimApplication.getThirdPlatform().getWechatOpenId(),
                  bindCallback);
            }

            @Override
            public void onTaskFailed() {
            }
          });
    } else if (loginType.ordinal() == Const.LoginType.QQ.ordinal()) {
      KharazimApplication.getThirdPlatform().qqLogin(getActivity(),
          new ThirdPlatformService.ThirdPlatformTaskCallback() {
            @Override
            public void onTaskSuccess() {
              KharazimApplication.getArchives().bindThird(loginType,
                  KharazimApplication.getThirdPlatform().getQQOpenId(),
                  bindCallback);
            }

            @Override
            public void onTaskFailed() {
            }
          });
    } else if (loginType.ordinal() == Const.LoginType.Weibo.ordinal()) {
      KharazimApplication.getThirdPlatform().weiboLogin(getActivity(),
          new ThirdPlatformService.ThirdPlatformTaskCallback() {
            @Override
            public void onTaskSuccess() {
              KharazimApplication.getArchives().bindThird(loginType,
                  KharazimApplication.getThirdPlatform().getWeiboOpenId(),
                  bindCallback);
            }

            @Override
            public void onTaskFailed() {
            }
          });
    }
  }

  private void bindPhone() {
    if (listener != null) {
      listener.onBindPhone();
    }
  }

  private void bindMail() {
    if (listener != null) {
      listener.onBindMail();
    }
  }

  private void resetPassword() {
    if (listener != null) {
      listener.onResetPassword();
    }
  }

  private void setData(UserProfile profile) {
    int currentLoginType = KharazimApplication.getArchives().getCurrentLoginType();
    int loginCode = KharazimApplication.getArchives().getLoginCode();

    String loginTypeName = null;
    if (currentLoginType == Const.LoginType.Wechat.ordinal()) {
      loginTypeName = Const.LoginType.Wechat.getName();
    } else if (currentLoginType == Const.LoginType.QQ.ordinal()) {
      loginTypeName = Const.LoginType.QQ.getName();
    } else if (currentLoginType == Const.LoginType.Weibo.ordinal()) {
      loginTypeName = Const.LoginType.Weibo.getName();
    } else if (currentLoginType == Const.LoginType.Standard.ordinal()) {
      if ((loginCode & ArchivesService.LoginFlag.PHONE_FLAG) > 0) {
        loginTypeName = KharazimApplication.getAppContext().getString(R.string.login_type_phone);
      } else if ((loginCode & ArchivesService.LoginFlag.MAIL_FLAG) > 0) {
        loginTypeName = KharazimApplication.getAppContext().getString(R.string.login_type_mail);
      }
    }
    if (TextUtils.isEmpty(loginTypeName)) {
      loginTypeName = Const.LoginType.Standard.getName();
    }

    if (currentLoginTypeTv != null) {
      currentLoginTypeTv.setText(String.format(
          KharazimApplication.getAppContext().getString(
              R.string.archives_account_manage_current_login_type_text_format),
          loginTypeName));
    }

    if (phoneBindedView != null) {
      phoneBindedView.setVisibility((loginCode & ArchivesService.LoginFlag.PHONE_FLAG) > 0 ?
          View.VISIBLE : View.GONE);
    }

    if (phoneUnbindedView != null) {
      phoneUnbindedView.setVisibility((loginCode & ArchivesService.LoginFlag.PHONE_FLAG) > 0 ?
          View.GONE : View.VISIBLE);
    }

    if (phoneNumberTv != null) {
      phoneNumberTv.setText(profile.getPhoneno());
    }

    if (mailBindedView != null) {
      mailBindedView.setVisibility((loginCode & ArchivesService.LoginFlag.MAIL_FLAG) > 0 ?
          View.VISIBLE : View.GONE);
    }

    if (mailUnbindedView != null) {
      mailUnbindedView.setVisibility((loginCode & ArchivesService.LoginFlag.MAIL_FLAG) > 0 ?
          View.GONE : View.VISIBLE);
    }

    if (mailTv != null) {
      mailTv.setText(profile.getEmail());
    }

    for (Iterator<Const.LoginType> iterator = loginTypeViewMap.keySet().iterator();
         iterator.hasNext(); ) {
      final Const.LoginType type = iterator.next();
      ArchivesLoginTypeView view = loginTypeViewMap.get(type);

      if (type.ordinal() == Const.LoginType.Wechat.ordinal()) {
        view.setSelected((loginCode & ArchivesService.LoginFlag.WECHAT_FLAG) > 0);
        view.setOnClickListener(view.getSelected() ? null : new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            bindThird(type);
          }
        });
      } else if (type.ordinal() == Const.LoginType.QQ.ordinal()) {
        view.setSelected((loginCode & ArchivesService.LoginFlag.QQ_FLAG) > 0);
        view.setOnClickListener(view.getSelected() ? null : new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            bindThird(type);
          }
        });
      } else if (type.ordinal() == Const.LoginType.Weibo.ordinal()) {
        view.setSelected((loginCode & ArchivesService.LoginFlag.WEIBO_FLAG) > 0);
        view.setOnClickListener(view.getSelected() ? null : new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            bindThird(type);
          }
        });
      }
    }
  }


  @Override
  protected int getLayoutResId() {
    return R.layout.archives_account_management_fragment_layout;
  }

  @Override
  protected void onPrepareLoading() {
  }

  @Override
  protected void onStartLoading() {
    KharazimApplication.getArchives().updateCurrentUserProfile(null);
  }

  public interface AccountManagementFragmentListener {

    void onBack();

    void onBindPhone();

    void onBindMail();

    void onResetPassword();
  }

  private class AccountManagementArchivesObserver extends SimpleArchivesObserver {
    @Override
    public void onUserProfileUpdated(String userId, UserProfile userProfile) {
      if (userProfile != null) {
        setData(userProfile);
      }
    }

    @Override
    public void onLogout() {
      NavigationManager.navigateToInit(getActivity());
    }
  }

}
