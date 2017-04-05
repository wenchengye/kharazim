package com.heqi.kharazim.consume.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.R;
import com.heqi.kharazim.consume.model.ConsumeCourseRecord;
import com.heqi.kharazim.utils.ViewUtils;

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
    return (ConsumeActionRecordCard) ViewUtils.newInstance(parent,
        R.layout.consumer_finish_acupoint_card);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    initView();
  }

  private void initView() {
    acupointNameTv = (TextView) findViewById(R.id.consumer_finish_acupoint_card_title);
    consumeTimeTv = (TextView) findViewById(R.id.consumer_finish_acupoint_card_count);

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
