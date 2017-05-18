package com.heqi.kharazim.archives.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.heqi.kharazim.BuildConfig;
import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.R;
import com.heqi.kharazim.activity.NavigationManager;
import com.heqi.kharazim.config.Const;
import com.heqi.kharazim.ui.fragment.async.AsyncLoadFragment;

/**
 * Created by overspark on 2017/5/17.
 */

public class AboutUsFragment extends AsyncLoadFragment {

  private View serviceContractBtn;
  private View privacyContractBtn;
  private View feedbackBtn;
  private TextView versionTv;
  private ImageView backBtn;

  private AboutUsFragmentListener listener;

  public void setListener(AboutUsFragmentListener listener) {
    this.listener = listener;
  }

  @Override
  protected void onInflated(View contentView, Bundle savedInstanceState) {
    initView(contentView);
    initListeners();
  }

  private void initView(View contentView) {
    serviceContractBtn = contentView.findViewById(R.id.archives_about_us_service_contract_tab);
    privacyContractBtn = contentView.findViewById(R.id.archives_about_us_privacy_contract_tab);
    feedbackBtn = contentView.findViewById(R.id.archives_about_us_feed_back_tab);
    versionTv = (TextView) contentView.findViewById(R.id.archives_about_us_version_tv);

    if (versionTv != null) {
      versionTv.setText(
          String.format(KharazimApplication.getAppContext().getString(
              R.string.archives_app_version_text_format), BuildConfig.VERSION_NAME));
    }

    TextView titleView = (TextView) contentView.findViewById(R.id.explore_header_title_text_view);
    titleView.setText(R.string.archives_about_us_title_text);
    backBtn = (ImageView) contentView.findViewById(R.id.explore_header_left_button);
    if (backBtn != null) {
      backBtn.setImageResource(R.drawable.icon_header_navigate_back);
    }
  }

  private void initListeners() {
    if (serviceContractBtn != null) {
      serviceContractBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          NavigationManager.navigateToPage(getActivity(), Const.SERVICE_CONTRACT_URL,
              KharazimApplication.getAppContext().getString(
                  R.string.archives_service_contract_title));
        }
      });
    }

    if (privacyContractBtn != null) {
      privacyContractBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          NavigationManager.navigateToPage(getActivity(), Const.PRIVACY_CONTRACT_URL,
              KharazimApplication.getAppContext().getString(
                  R.string.archives_privacy_contract_title));
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

  @Override
  protected int getLayoutResId() {
    return R.layout.archives_about_us_fragment_layout;
  }

  @Override
  protected void onPrepareLoading() {
  }

  @Override
  protected void onStartLoading() {
  }

  public interface AboutUsFragmentListener {
    void onBack();
  }
}
