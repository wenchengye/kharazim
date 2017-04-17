package com.heqi.kharazim.archives.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heqi.kharazim.R;
import com.heqi.kharazim.config.Const;
import com.heqi.kharazim.utils.ViewUtils;

/**
 * Created by overspark on 2017/4/13.
 */

public class ArchivesUserAimView extends RelativeLayout {

  private TextView aimNameTv;
  private ImageView selectedIv;
  private Const.Aim aim;
  private boolean selected;

  public ArchivesUserAimView(Context context) {
    super(context);
  }

  public ArchivesUserAimView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ArchivesUserAimView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public static ArchivesUserAimView newInstance(ViewGroup parent) {
    return (ArchivesUserAimView) ViewUtils.newInstance(parent, R.layout.archives_user_aim_card);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    aimNameTv = (TextView) findViewById(R.id.aim_name_tv);
    selectedIv = (ImageView) findViewById(R.id.aim_selected_iv);
  }

  public void setData(Const.Aim aim) {
    this.aim = aim;
    if (this.aim != null) {
      aimNameTv.setText(aim.getName());
    }
  }

  public Const.Aim getData() {
    return aim;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
    selectedIv.setImageResource(selected ? R.drawable.icon_explore_detail_course_finish :
        R.drawable.icon_explore_detail_course_not_finish);
  }

  public boolean getSelected() {
    return selected;
  }
}
