package com.heqi.kharazim.third.imagepicker;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.lzy.imagepicker.loader.ImageLoader;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * Created by overspark on 2017/4/13.
 */

public class SimpleImageLoader implements ImageLoader {


  private static final long serialVersionUID = -3198672388226672412L;

  @Override
  public void displayImage(Activity activity,
                           String path,
                           ImageView imageView,
                           int width,
                           int height) {
    Picasso.with(activity)
        .load(Uri.fromFile(new File(path)))
        .resize(width, height)
        .centerInside()
        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
        .into(imageView);
  }

  @Override
  public void clearMemoryCache() {

  }
}
