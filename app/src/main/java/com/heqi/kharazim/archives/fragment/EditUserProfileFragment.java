package com.heqi.kharazim.archives.fragment;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.R;
import com.heqi.kharazim.archives.ArchivesService;
import com.heqi.kharazim.archives.SimpleArchivesObserver;
import com.heqi.kharazim.archives.model.HealthCondition;
import com.heqi.kharazim.archives.model.ProvinceInfo;
import com.heqi.kharazim.archives.model.UserProfile;
import com.heqi.kharazim.config.Const;
import com.heqi.kharazim.ui.fragment.async.AsyncLoadFragment;
import com.heqi.kharazim.utils.KharazimUtils;
import com.heqi.kharazim.view.CircleAsyncImageView;
import com.heqi.rpc.GsonFactory;
import com.lzy.imagepicker.bean.ImageItem;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by overspark on 2017/4/12.
 */

public class EditUserProfileFragment extends AsyncLoadFragment {

  private View editHeadIconView;
  private View editGenderView;
  private View editBirthdayView;
  private View editAreaView;
  private View editHeightView;
  private View editWeightView;
  private EditText editNicknameEv;
  private CircleAsyncImageView headIconIv;
  private TextView genderDisplayTv;
  private TextView birthdayDisplayTv;
  private TextView areaDisplayTv;
  private TextView heightDisplayTv;
  private TextView weightDisplayTv;
  private View uploadEditView;

  private OptionsPickerView genderPicker;
  private List<String> genderOptionList;
  private Const.Gender selectGender;

  private TimePickerView birthdayPicker;
  private String selectBirthday;

  private OptionsPickerView areaPicker;
  private List<String> provinceOptionList;
  private List<List<String>> cityOptionList;
  private List<List<List<String>>> areaOptionList;
  private boolean parsingProvince = false;
  private ParseProvinceJsonTask provinceJsonTask = new ParseProvinceJsonTask();
  private String selectArea;

  private OptionsPickerView heightPicker;
  private List<Integer> heightOptionList;
  Integer selectHeight;

  private OptionsPickerView weightPicker;
  private List<Integer> weightOptionList;
  Integer selectWeight;

  private EditUserProfileFragmentListener listener;

  private EditUserProfileFragmentArchivesObserver archivesObserver =
      new EditUserProfileFragmentArchivesObserver();

  private OptionsPickerView.OnOptionsSelectListener genderPickerListener =
      new OptionsPickerView.OnOptionsSelectListener() {
    @Override
    public void onOptionsSelect(int options1, int options2, int options3, View v) {
      if (genderOptionList == null || options1 < 0 || options1 >= genderOptionList.size()) return;

      selectGender = Const.Gender.fromString(genderOptionList.get(options1));
      if (selectGender != null) {
        genderDisplayTv.setText(selectGender.getName());
      }
    }
  };

  private TimePickerView.OnTimeSelectListener birthdayPickerListener =
      new TimePickerView.OnTimeSelectListener() {
    @Override
    public void onTimeSelect(Date date, View v) {
      if (date == null) return;

      selectBirthday = KharazimUtils.formatDate(date);
      birthdayDisplayTv.setText(selectBirthday);
    }
  };

  private OptionsPickerView.OnOptionsSelectListener areaPickerListener =
      new OptionsPickerView.OnOptionsSelectListener() {
        @Override
        public void onOptionsSelect(int options1, int options2, int options3, View v) {
          selectArea = provinceOptionList.get(options1) +
              cityOptionList.get(options1).get(options2) +
              areaOptionList.get(options1).get(options2).get(options3);
          areaDisplayTv.setText(selectArea);
        }
      };

  private OptionsPickerView.OnOptionsSelectListener heightPickerListener =
      new OptionsPickerView.OnOptionsSelectListener() {
        @Override
        public void onOptionsSelect(int options1, int options2, int options3, View v) {
          if (heightOptionList == null || options1 < 0 || options1 >= heightOptionList.size()
              || heightOptionList.get(options1) == null) {
            return;
          }

          selectHeight = heightOptionList.get(options1);
          heightDisplayTv.setText(String.valueOf(selectHeight));
        }
      };

  private OptionsPickerView.OnOptionsSelectListener weightPickerListener =
      new OptionsPickerView.OnOptionsSelectListener() {
        @Override
        public void onOptionsSelect(int options1, int options2, int options3, View v) {
          if (weightOptionList == null || options1 < 0 || options1 >= weightOptionList.size()
              || weightOptionList.get(options1) == null) {
            return;
          }
          selectWeight = weightOptionList.get(options1);
          weightDisplayTv.setText(String.valueOf(selectWeight));
        }
      };

  @Override
  protected void onInflated(View contentView, Bundle savedInstanceState) {
    initView(contentView);
    initListeners();

    invalidateUserProfile(KharazimApplication.getArchives().getCurrentUserProfile());
    invalidateHealthCondition(KharazimApplication.getArchives().getCurrentHealthCondition());

    KharazimApplication.getArchives().addObserver(archivesObserver);
  }

  private void initView(View contentView) {

  }

  private void initListeners() {
    editHeadIconView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showImagePicker();
      }
    });

    editGenderView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showGenderPicker();
      }
    });

    editBirthdayView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showBirthdayPicker();
      }
    });

    editAreaView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showAreaPicker();
      }
    });

    editHeightView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showHeightPicker();
      }
    });

    editWeightView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showWeightPicker();
      }
    });

    uploadEditView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Map<String, Object> userProfileParams = new HashMap<>();

        if (!TextUtils.isEmpty(editNicknameEv.getText())) {
          userProfileParams.put(ArchivesService.ParamsKey.PARAMS_KEY_NICKNAME,
              editNicknameEv.getText());
        }

        if (selectGender != null) {
          userProfileParams.put(ArchivesService.ParamsKey.PARAMS_KEY_SEX, selectGender.getName());
        }

        if (selectBirthday != null) {
          userProfileParams.put(ArchivesService.ParamsKey.PARAMS_KEY_BIRTHDAY, selectBirthday);
        }

        if (selectArea != null) {
          userProfileParams.put(ArchivesService.ParamsKey.PARAMS_KEY_ADDRESS, selectArea);
        }

        if (!userProfileParams.isEmpty()) {
          KharazimApplication.getArchives().uploadCurrentUserProfile(userProfileParams, null);
        }

        Map<String, Object> healthConditionParams = new HashMap<>();
        if (selectHeight != null) {
          healthConditionParams.put(ArchivesService.ParamsKey.PARAMS_KEY_HEIGHT, selectHeight);
        }

        if (selectWeight != null) {
          healthConditionParams.put(ArchivesService.ParamsKey.PARAMS_KEY_WEIGHT, selectWeight);
        }

        if (!healthConditionParams.isEmpty()) {
          KharazimApplication.getArchives().uploadCurrentHealthCondition(
              healthConditionParams, null);
        }

        if (listener != null) {
          listener.onEditUpload();
        }
      }
    });
  }

  public void setListener(EditUserProfileFragmentListener listener) {
    this.listener = listener;
  }

  public void onPickedImage(ArrayList<ImageItem> images) {
    if (images == null || images.isEmpty()) return;

    try {
      Bitmap image = Picasso.with(getActivity())
          .load(Uri.parse(images.get(0).path)).get();

      KharazimApplication.getArchives().uploadHeadImage(KharazimUtils.bitmapToBytes(image), null);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void showImagePicker() {
    if (listener != null) {
      listener.onRequestImagePicker();
    }
  }

  private void showGenderPicker() {
    getGenderPicker().show();
  }

  private OptionsPickerView getGenderPicker() {
    if (genderPicker == null) {
      genderOptionList = new ArrayList<>();
      for (int i = 0; i < Const.Gender.values().length; ++i) {
        genderOptionList.add(Const.Gender.values()[i].getName());
      }
      genderPicker = new OptionsPickerView.Builder(getActivity(), genderPickerListener).build();
      genderPicker.setPicker(genderOptionList);
    }
    return genderPicker;
  }

  private void showBirthdayPicker() {
    getBirthdayPicker().show();
  }

  private TimePickerView getBirthdayPicker() {
    if (birthdayPicker == null) {
      birthdayPicker = new TimePickerView.Builder(getActivity(), birthdayPickerListener)
          .setType(TimePickerView.Type.YEAR_MONTH_DAY).build();
    }
    return birthdayPicker;
  }

  private void showAreaPicker() {
    if (!parsingProvince) {
      getAreaPicker().show();
    }
  }

  private OptionsPickerView getAreaPicker() {
    if (areaPicker == null) {
      areaPicker = new OptionsPickerView.Builder(getActivity(), areaPickerListener).build();
      areaPicker.setPicker(provinceOptionList, cityOptionList, areaOptionList);
    }
    return areaPicker;
  }

  private void showHeightPicker() {
    getHeightPicker().show();
  }

  private OptionsPickerView getHeightPicker() {
    if (heightPicker == null) {
      heightOptionList = new ArrayList<>();
      for (int i = Const.MIN_HEIGHT_VALUE; i <= Const.MAX_HEIGHT_VALUE; ++i) {
        heightOptionList.add(i);
      }
      heightPicker = new OptionsPickerView.Builder(getActivity(), heightPickerListener).build();
      heightPicker.setPicker(heightOptionList);
    }

    return heightPicker;
  }

  private void showWeightPicker() {
    getWeightPicker().show();
  }

  private OptionsPickerView getWeightPicker() {
    if (weightPicker == null) {
      weightOptionList = new ArrayList<>();
      for (int i = Const.MIN_WEIGHT_VALUE; i <= Const.MAX_WEIGHT_VALUE; ++i) {
        weightOptionList.add(i);
      }
      weightPicker = new OptionsPickerView.Builder(getActivity(), weightPickerListener).build();
      weightPicker.setPicker(weightOptionList);
    }

    return weightPicker;
  }

  private void invalidateUserProfile(UserProfile userProfile) {
    if (userProfile == null) return;

    headIconIv.loadNetworkImage(userProfile.getHeadimg(), R.drawable.icon_kharazim_image_logo);
    editNicknameEv.setText(userProfile.getNickname());
    if (TextUtils.isEmpty(userProfile.getSex())) {
      genderDisplayTv.setText(R.string.archives_edit_undefine_text);
    } else {
      genderDisplayTv.setText(userProfile.getSex());
    }
    if (TextUtils.isEmpty(userProfile.getBirthday())) {
      birthdayDisplayTv.setText(R.string.archives_edit_undefine_text);
    } else {
      birthdayDisplayTv.setText(userProfile.getBirthday());
    }
    if (TextUtils.isEmpty(userProfile.getAddress())) {
      areaDisplayTv.setText(R.string.archives_edit_undefine_text);
    } else {
      areaDisplayTv.setText(userProfile.getAddress());
    }
  }

  private void invalidateHealthCondition(HealthCondition healthCondition) {
    if (healthCondition == null) return;

    if (healthCondition.getStature() > Const.MAX_HEIGHT_VALUE
        || healthCondition.getStature() < Const.MIN_HEIGHT_VALUE) {
      heightDisplayTv.setText(R.string.archives_edit_undefine_text);
    } else {
      heightDisplayTv.setText(String.valueOf(healthCondition.getStature()));
    }

    if (healthCondition.getWeight() > Const.MAX_WEIGHT_VALUE
        || healthCondition.getWeight() < Const.MIN_WEIGHT_VALUE) {
      weightDisplayTv.setText(R.string.archives_edit_undefine_text);
    } else {
      weightDisplayTv.setText(String.valueOf(healthCondition.getWeight()));
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
    KharazimApplication.getArchives().updateCurrentHealthCondition(null);

    if (provinceOptionList == null && !parsingProvince) {
      parsingProvince = true;
      provinceJsonTask.execute();
    }
  }

  private class EditUserProfileFragmentArchivesObserver extends SimpleArchivesObserver {
    @Override
    public void onUserProfileUpdated(String userId, UserProfile userProfile) {
      if (userProfile == null) return;

      invalidateUserProfile(userProfile);
    }

    @Override
    public void onHealthConditionUpdated(String userId, HealthCondition healthCondition) {
      if (healthCondition == null) return;

      invalidateHealthCondition(healthCondition);
    }

    @Override
    public void onUserProfileUpload(String userId) {
      requestLoad();
    }

    @Override
    public void onHealthConditionUpload(String userId) {
      requestLoad();
    }
  }

  private class ParseProvinceJsonTask extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... params) {

      StringBuilder stringBuilder = new StringBuilder();
      try {
        AssetManager assetManager = KharazimApplication.getAppContext().getAssets();
        BufferedReader bf = new BufferedReader(new InputStreamReader(
            assetManager.open(Const.PROVINCE_DATA_FILE_NAME)));
        String line;
        while ((line = bf.readLine()) != null) {
          stringBuilder.append(line);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
      String jsonData = stringBuilder.toString();

      ArrayList<ProvinceInfo> provinceInfos = new ArrayList<>();
      try {
        JSONArray data = new JSONArray(jsonData);

        for (int i = 0; i < data.length(); i++) {
          ProvinceInfo entity = GsonFactory.getGson().fromJson(data.optJSONObject(i).toString(),
              ProvinceInfo.class);
          provinceInfos.add(entity);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }

      provinceOptionList = new ArrayList<>();
      cityOptionList = new ArrayList<>();
      areaOptionList = new ArrayList<>();

      for (int i = 0; i < provinceInfos.size(); i++) {
        List<String> cityList = new ArrayList<>();
        List<List<String>> provinceAreaList = new ArrayList<>();

        for (int c = 0; c < provinceInfos.get(i).getCity().size(); c++) {
          String cityName = provinceInfos.get(i).getCity().get(c).getName();
          cityList.add(cityName);

          ArrayList<String> cityAreaList = new ArrayList<>();

          if (provinceInfos.get(i).getCity().get(c).getArea() == null
              || provinceInfos.get(i).getCity().get(c).getArea().size() == 0) {
            cityList.add("");
          } else {

            for (int d = 0; d < provinceInfos.get(i).getCity().get(c).getArea().size(); d++) {
              String AreaName = provinceInfos.get(i).getCity().get(c).getArea().get(d);

              cityAreaList.add(AreaName);
            }
          }
          provinceAreaList.add(cityAreaList);
        }

        provinceOptionList.add(provinceInfos.get(i).getName());
        cityOptionList.add(cityList);
        areaOptionList.add(provinceAreaList);
      }

      return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
      parsingProvince = false;
    }
  }

  public interface EditUserProfileFragmentListener {

    void onEditUpload();

    void onRequestImagePicker();
  }
}
