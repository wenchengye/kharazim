package com.heqi.kharazim.consume.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by overspark on 2017/4/1.
 */

public class ConsumeGiveUpView extends RelativeLayout {

  private TextView consumeTimeTv;
  private TextView consumeCountTv;
  private View confirmBtn;

  private ConsumeGiveUpViewListener listener;

  public ConsumeGiveUpView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public ConsumeGiveUpView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ConsumeGiveUpView(Context context) {
    super(context);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    initView();
  }

  public void setContent(int consumeTime, int consumeCount) {
    if (consumeTimeTv != null) {
      consumeTimeTv.setText(String.valueOf(consumeTime));
    }

    if (consumeCountTv != null) {
      consumeCountTv.setText(String.valueOf(consumeCount));
    }
  }

  public void setListener(ConsumeGiveUpViewListener listener) {
    this.listener = listener;
  }

  private void initView() {
    if (confirmBtn != null) {
      confirmBtn.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          if (listener != null) {
            listener.onConfirmPressed();
          }
        }
      });
    }
  }

  public interface ConsumeGiveUpViewListener {
    void onConfirmPressed();
  }


}
