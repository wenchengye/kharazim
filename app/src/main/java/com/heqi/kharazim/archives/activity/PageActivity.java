package com.heqi.kharazim.archives.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.heqi.kharazim.R;
import com.heqi.kharazim.config.Intents;

/**
 * Created by overspark on 2017/5/17.
 */

public class PageActivity extends FragmentActivity {

  private WebView webView;
  private TextView titleTv;
  private ImageView backBtn;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.page_activity_layout);
    init();

    handleIntent(getIntent());
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);

    handleIntent(intent);
  }

  private void init() {

    webView = (WebView) findViewById(R.id.page_webview);
    titleTv = (TextView) findViewById(R.id.explore_header_title_text_view);
    backBtn = (ImageView) findViewById(R.id.explore_header_left_button);

    backBtn.setImageResource(R.drawable.icon_header_navigate_back);


    backBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });
  }

  private void handleIntent(Intent intent) {
    if (intent == null) return;

    if (intent.hasExtra(Intents.EXTRA_PAGE_URL) && webView != null) {
      webView.loadUrl(intent.getStringExtra(Intents.EXTRA_PAGE_URL));
    }

    if (intent.hasExtra(Intents.EXTRA_PAGE_TITLE) && titleTv != null) {
      titleTv.setText(intent.getStringExtra(Intents.EXTRA_PAGE_TITLE));
    }
  }
}
