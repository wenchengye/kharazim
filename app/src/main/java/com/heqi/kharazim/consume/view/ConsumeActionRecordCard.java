package com.heqi.kharazim.consume.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.R;
import com.heqi.kharazim.consume.model.ConsumeCourseRecord;

/**
 * Created by overspark on 2017/4/1.
 */

public class ConsumeActionRecordCard extends FrameLayout {

  private TextView acupointNameTv;
  private TextView consumeTimeTv;

  public ConsumeActionRecordCard(Context context) {
    super(context);
  }

  public ConsumeActionRecordCard(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ConsumeActionRecordCard(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public static ConsumeActionRecordCard newInstance(ViewGroup parent) {
    return null;
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    initView();
  }

  private void initView() {

  }

  public void setData(ConsumeCourseRecord.ConsumeActionRecord data) {
    if (data == null) return;

    if (acupointNameTv != null) {
      acupointNameTv.setText(data.getAcupointname());
    }

    if (consumeTimeTv != null) {
      consumeTimeTv.setText(String.format(KharazimApplication.getAppContext().getString(
          R.string.consumer_record_action_time_format), data.getCptime()));
    }
  }
}
