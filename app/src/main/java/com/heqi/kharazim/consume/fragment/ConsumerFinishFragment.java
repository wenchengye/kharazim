package com.heqi.kharazim.consume.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.heqi.kharazim.R;
import com.heqi.kharazim.config.Intents;
import com.heqi.kharazim.consume.model.ConsumeCourseRecord;
import com.heqi.kharazim.consume.view.ConsumeActionRecordCard;
import com.heqi.kharazim.consume.view.ConsumeStarView;
import com.heqi.kharazim.ui.adapter.DataAdapter;
import com.heqi.kharazim.ui.fragment.async.AsyncLoadFragment;

/**
 * Created by overspark on 2017/4/7.
 */

public class ConsumerFinishFragment extends AsyncLoadFragment {

  private TextView consumeTimeTv;
  private TextView kharazimPointTv;
  private TextView consumeCountTv;
  private ListView actionList;
  private ConsumeStarView starView;
  private View confirmBtn;
  private ConsumerFinishFragment.ConsumeActionListAdapter adapter =
      new ConsumerFinishFragment.ConsumeActionListAdapter();

  private ConsumeCourseRecord record;

  private ConsumerFinishFragment.ConsumeFinishFragmentListener listener;
  private int orientation = Configuration.ORIENTATION_PORTRAIT;

  @Override
  protected void onInflated(View contentView, Bundle savedInstanceState) {
    initView(contentView);
  }

  @Override
  protected int getLayoutResId() {
    if (this.orientation == Configuration.ORIENTATION_PORTRAIT) {
      return R.layout.consumer_finish_fragment_portrait_layout;
    } else {
      return R.layout.consumer_finish_fragment_landscape_layout;
    }
  }

  @Override
  protected void onPrepareLoading() {

  }

  @Override
  protected void onStartLoading() {

  }

  private void initView(View contentView) {
    consumeTimeTv = (TextView) contentView.findViewById(R.id.consumer_finish_view_progress_time_tv);
    kharazimPointTv = (TextView) contentView.findViewById(
        R.id.consumer_finish_view_kharazim_point_tv);
    consumeCountTv = (TextView) contentView.findViewById(R.id.consumer_finish_view_count_tv);
    actionList = (ListView) contentView.findViewById(R.id.consumer_finish_view_acupoint_list_view);
    confirmBtn = contentView.findViewById(R.id.consumer_finish_view_confirm_btn);

    FrameLayout holder = (FrameLayout) contentView.findViewById(
        R.id.consumer_finish_view_star_view_holder);
    if (holder != null) {
      starView = ConsumeStarView.newInstance(holder);
      if (starView != null) {
        holder.addView(starView);
      }
    }


    if (confirmBtn != null) {
      confirmBtn.setOnClickListener(new View.OnClickListener() {
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

    setData((ConsumeCourseRecord) getArguments().getSerializable(Intents.EXTRA_CONSUME_RECORD));

    contentView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
      }
    });
  }

  private void setData(ConsumeCourseRecord record) {
    if (record == null) return;

    this.record = record;
    if (this.consumeTimeTv != null) {
      this.consumeTimeTv.setText(String.valueOf(this.record.getTotalConsumeTimeInMinute()));
    }

    if (this.kharazimPointTv != null) {
      this.kharazimPointTv.setText(String.valueOf(this.record.getKharazimPoint()));
    }

    if (this.consumeCountTv != null) {
      this.consumeCountTv.setText(String.valueOf(this.record.getAcupointCount()));
    }

    adapter.setData(this.record.getProgresslist());
  }

  public void setListener(ConsumeFinishFragmentListener listener) {
    this.listener = listener;
  }

  public void setOrientation(int orientation) {
    this.orientation = orientation;
  }

  public interface ConsumeFinishFragmentListener {
    void onConfirmPressed(int star);
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
}
