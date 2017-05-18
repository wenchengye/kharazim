package com.heqi.kharazim.archives.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.heqi.kharazim.R;
import com.heqi.kharazim.archives.fragment.AboutUsFragment;

/**
 * Created by overspark on 2017/5/23.
 */

public class AboutUsActivity extends FragmentActivity {

  AboutUsFragment.AboutUsFragmentListener listener = new AboutUsFragment.AboutUsFragmentListener() {
    @Override
    public void onBack() {
      finish();
    }
  };

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.common_activity_container);

    AboutUsFragment aboutUsFragment = new AboutUsFragment();
    aboutUsFragment.setListener(listener);
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.fragments_container, aboutUsFragment)
        .commit();
  }
}
