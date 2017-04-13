package com.heqi.kharazim.archives.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.heqi.kharazim.R;
import com.heqi.kharazim.archives.fragment.EditUserProfileFragment;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import java.util.ArrayList;

/**
 * Created by overspark on 2017/4/12.
 */

public class EditUserProfileActivity extends FragmentActivity {

  private static final int IMAGE_PICKER_REQUEST_CODE = 1001;

  private EditUserProfileFragment fragment;

  private EditUserProfileFragment.EditUserProfileFragmentListener editUserProfileFragmentListener =
      new EditUserProfileFragment.EditUserProfileFragmentListener() {
        @Override
        public void onEditUpload() {
          finish();
        }

        @Override
        public void onRequestImagePicker() {
          Intent intent = new Intent(EditUserProfileActivity.this, ImageGridActivity.class);
          startActivityForResult(intent, IMAGE_PICKER_REQUEST_CODE);
        }
      };

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.common_activity_container);

    fragment = new EditUserProfileFragment();
    fragment.setListener(editUserProfileFragmentListener);
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.fragments_container, fragment)
        .commit();
  }

  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
      if (data != null && requestCode == IMAGE_PICKER_REQUEST_CODE) {
        ArrayList<ImageItem> images =
            (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
        if (fragment != null) {
          fragment.onPickedImage(images);
        }
      }
    }
  }
}
