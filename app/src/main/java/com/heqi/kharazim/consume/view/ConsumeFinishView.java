package com.heqi.kharazim.consume.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.heqi.kharazim.R;
import com.heqi.kharazim.consume.model.ConsumeCourseRecord;
import com.heqi.kharazim.ui.adapter.DataAdapter;

/**
 * Created by overspark on 2017/4/1.
 */

public class ConsumeFinishView extends RelativeLayout {

  private TextView consumeTimeTv;
  private TextView kharazimPointTv;
  private TextView consumeCountTv;
  private ListView actionList;
  private ConsumeStarView starView;
  private View confirmBtn;
  private ConsumeActionListAdapter adapter = new ConsumeActionListAdapter();

  private ConsumeCourseRecord record;

  private ConsumeFinishViewListener listener;

  public ConsumeFinishView(Context context) {
    super(context);
  }

  public ConsumeFinishView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public ConsumeFinishView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    initView();
  }

  private void initView() {
    consumeTimeTv = (TextView) findViewById(R.id.consumer_finish_view_progress_time_tv);
    kharazimPointTv = (TextView) findViewById(R.id.consumer_finish_view_kharazim_point_tv);
    consumeCountTv = (TextView) findViewById(R.id.consumer_finish_view_count_tv);
    actionList = (ListView) findViewById(R.id.consumer_finish_view_acupoint_list_view);
    confirmBtn = findViewById(R.id.consumer_finish_view_confirm_btn);

    FrameLayout holder = (FrameLayout) findViewById(R.id.consumer_finish_view_star_view_holder);
    if (holder != null) {
      starView = ConsumeStarView.newInstance(holder);
      if (starView != null) {
        holder.addView(starView);
      }
    }


    if (confirmBtn != null) {
      confirmBtn.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          if (listener != null) {
            listener.onConfirmPressed(starView != null ? starView.getStar() : 0);
          }
        }
      });
    }

    if (actionList != null) {
      actionList.setAdapter(adapter);
    }
  }

  public void setData(ConsumeCourseRecord record) {
    if (record == null) return;

    this.record = record;
    if (this.consumeTimeTv != null) {
      this.consumeCountTv.setText(String.valueOf(this.record.getTotalConsumeTimeInMinute()));
    }

    if (this.kharazimPointTv != null) {
      this.kharazimPointTv.setText(String.valueOf(this.record.getKharazimPoint()));
    }

    if (this.consumeCountTv != null) {
      this.consumeCountTv.setText(String.valueOf(this.record.getAcupointCount()));
    }

    adapter.setData(this.record.getProgresslist());
  }

  public void setListener(ConsumeFinishViewListener listener) {
    this.listener = listener;
  }

  private static class ConsumeActionListAdapter
      extends DataAdapter<ConsumeCourseRecord.ConsumeActionRecord> {
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      ConsumeActionRecordCard view;
      if (convertView instanceof ConsumeActionRecordCard) {
        view = (ConsumeActionRecordCard) convertView;
      } else {
        view = ConsumeActionRecordCard.newInstance(parent);
      }

      view.setData(getItem(position));
      return view;
    }
  }

  public interface ConsumeFinishViewListener {
    void onConfirmPressed(int star);
  }

}
