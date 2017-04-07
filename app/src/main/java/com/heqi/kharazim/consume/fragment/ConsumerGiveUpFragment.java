package com.heqi.kharazim.consume.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.heqi.kharazim.R;
import com.heqi.kharazim.config.Intents;
import com.heqi.kharazim.ui.fragment.async.AsyncLoadFragment;

/**
 * Created by overspark on 2017/4/7.
 */

public class ConsumerGiveUpFragment extends AsyncLoadFragment {

  private TextView consumeTimeTv;
  private TextView consumeCountTv;
  private View confirmBtn;

  private ConsumeGiveUpFragmentListener listener;

  @Override
  protected void onInflated(View contentView, Bundle savedInstanceState) {
    initView(contentView);
  }

  private void initView(View contentView) {
    consumeTimeTv = (TextView) contentView.findViewById(R.id.consumer_give_up_progress_time_tv);
    consumeCountTv = (TextView) contentView.findViewById(R.id.consumer_give_up_count_tv);
    confirmBtn = contentView.findViewById(R.id.consumer_give_up_confirm_btn);

    if (confirmBtn != null) {
      confirmBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (listener != null) {
            listener.onConfirmPressed();
          }
        }
      });
    }

    setContent(getArguments().getInt(Intents.EXTRA_CONSUME_TIME_MINUTES),
        getArguments().getInt(Intents.EXTRA_CONSUME_ACTION_COUNT));

    contentView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {}
    });
  }

  @Override
  protected int getLayoutResId() {
    return R.layout.consumer_give_up_fragment_layout;
  }

  @Override
  protected void onPrepareLoading() {

  }

  @Override
  protected void onStartLoading() {

  }

  private void setContent(int consumeTime, int consumeCount) {
    if (consumeTimeTv != null) {
      consumeTimeTv.setText(String.valueOf(consumeTime));
    }

    if (consumeCountTv != null) {
      consumeCountTv.setText(String.valueOf(consumeCount));
    }
  }

  public void setListener(ConsumeGiveUpFragmentListener listener) {
    this.listener = listener;
  }

  public interface ConsumeGiveUpFragmentListener {
    void onConfirmPressed();
  }
}
