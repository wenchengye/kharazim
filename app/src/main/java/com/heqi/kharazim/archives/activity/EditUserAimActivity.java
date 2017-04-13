package com.heqi.kharazim.archives.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.heqi.kharazim.R;
import com.heqi.kharazim.archives.fragment.EditUserAimFragment;

/**
 * Created by overspark on 2017/4/13.
 */

public class EditUserAimActivity extends FragmentActivity {

  private EditUserAimFragment.EditUserAimFragmentListener editUserAimFragmentListener =
      new EditUserAimFragment.EditUserAimFragmentListener() {
        @Override
        public void onUploadUserAim() {
          finish();
        }
      };

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.common_activity_container);

    EditUserAimFragment fragment = new EditUserAimFragment();
    fragment.setListener(editUserAimFragmentListener);
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.fragments_container, fragment)
        .commit();
  }
}
