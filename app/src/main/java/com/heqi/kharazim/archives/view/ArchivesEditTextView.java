package com.heqi.kharazim.archives.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.heqi.kharazim.KharazimApplication;
import com.heqi.kharazim.R;

/**
 * Created by overspark on 2017/5/12.
 */

public class ArchivesEditTextView extends RelativeLayout {

  private EditText editText;
  private View deleteBtn;

  public ArchivesEditTextView(Context context) {
    super(context);
  }

  public ArchivesEditTextView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public ArchivesEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();

    initView();
  }

  private void initView() {

    editText = (EditText) findViewById(R.id.archives_edit_text);
    deleteBtn = findViewById(R.id.archives_edit_text_delete_button);

    if (editText != null) {
      this.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          InputMethodManager imm = (InputMethodManager)
              KharazimApplication.getAppContext().getSystemService(Context.INPUT_METHOD_SERVICE);
          if (imm != null) {
            editText.requestFocus();
            imm.showSoftInput(editText, 0);
            editText.setSelection(editText.length());
          }
        }
      });
    }

    if (deleteBtn != null) {
      deleteBtn.setVisibility(View.GONE);

      deleteBtn.setOnClickListener(new OnClickListener() {
        @Override
        public void onClick(View v) {
          editText.setText(null);
        }
      });

      if (editText != null) {
        editText.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {

          }

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {
            deleteBtn.setVisibility(TextUtils.isEmpty(s) ? View.GONE : View.VISIBLE);
          }

          @Override
          public void afterTextChanged(Editable s) {

          }
        });
      }
    }
  }

  public String getText() {
    return editText != null ? editText.getText().toString() : "";
  }

  public EditText getEditText() {
    return editText;
  }

  public void addTextChangedListener(TextWatcher watcher) {
    if (editText != null) {
      editText.addTextChangedListener(watcher);
    }
  }
}
