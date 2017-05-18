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
 * Created by overspark on 2017/5/17.
 */

public class ArchivesLoginTypeView extends RelativeLayout {

  private TextView loginTypeNameTv;
  private ImageView selectedIv;
  private Const.LoginType loginType;
  private boolean selected;

  public ArchivesLoginTypeView(Context context) {
    super(context);
  }

  public ArchivesLoginTypeView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ArchivesLoginTypeView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public static ArchivesLoginTypeView newInstance(ViewGroup parent) {
    return (ArchivesLoginTypeView) ViewUtils.newInstance(parent, R.layout.archives_login_type_card);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    loginTypeNameTv = (TextView) findViewById(R.id.login_type_name_tv);
    selectedIv = (ImageView) findViewById(R.id.login_selected_iv);
  }

  public Const.LoginType getData() {
    return loginType;
  }

  public void setData(Const.LoginType loginType) {
    this.loginType = loginType;
    if (this.loginType != null) {
      loginTypeNameTv.setText(loginType.getName());
    }
  }

  public boolean getSelected() {
    return selected;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
    selectedIv.setImageResource(selected ? R.drawable.icon_explore_detail_course_finish :
        R.drawable.icon_explore_detail_course_not_finish);
  }
}
