package com.heqi.kharazim;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Environment;

import com.android.volley.toolbox.ByteArrayPool;
import com.heqi.base.utils.SystemUtil;
import com.heqi.image.ImageManager;
import com.heqi.image.view.AsyncImageView;
import com.heqi.kharazim.archives.ArchivesService;
import com.heqi.kharazim.archives.ArchivesServiceImpl;
import com.heqi.kharazim.third.imagepicker.SimpleImageLoader;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;

import java.io.File;

/**
 * Created by wenchengye on 16/8/28.
 */
public class KharazimApplication extends Application {

  private static final int BYTE_ARRAY_MAX_SIZE = 128 * 1024; // 128K
  private static final String IMAGE_CACHE_FOLDER = "ImageCache";
  private static final int BITMAP_MAX_FILE_CACHE_SIZE = 64 * 1024 * 1024; // 64M
  private static final float BITMAP_MEMORY_CACHE_SIZE_SCALE_BELOW_64 = 0.05f;
  private static final float BITMAP_MEMORY_CACHE_SIZE_SCALE_ABOVE_64 = 0.1f;
  private static final int IMAGE_NETWORK_THREAD_POOL_SIZE = 3;
  private static final int IMAGE_LOCAL_THREAD_POOL_SIZE = 1;
  private static final int IMAGE_PICK_SELECT_SIZE = 1;
  private static final int IMAGE_PICK_CLIP_SIZE = 800; //800px
  private static final int IMAGE_PICK_SAVE_SIZE = 1000; //1000px

  //TODO this singleton may cause muti-process problem.
  //static appContext is assigned in every process's initApp();
  private static Context appContext;
  private static ByteArrayPool byteArrayPool;
  private static ImageManager imageManager;
  private static ArchivesService archives;

  public static Context getAppContext() {
    return appContext;
  }

  private static void setAppContext(Context context) {
    appContext = context;
  }

  public static ArchivesService getArchives() {
    return archives;
  }

  public static ImagePicker getImagePicker() {
    return ImagePicker.getInstance();
  }

  private static void initByteArrayPool() {
    byteArrayPool = new ByteArrayPool(BYTE_ARRAY_MAX_SIZE);
  }

  private static void initImageView() {
    AsyncImageView.setImageManagerHolder(new AsyncImageView.ImageManagerHolder() {
      @Override
      public ImageManager getImageManager() {
        return KharazimApplication.getImageManager();
      }
    });
  }

  public static synchronized ImageManager getImageManager() {
    if (imageManager == null) {
      com.heqi.image.Config config = new com.heqi.image.Config() {
        @Override
        public Context getContext() {
          return appContext;
        }

        @Override
        public String getFileCacheDir() {
          File cacheDir = null;
          if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cacheDir = SystemUtil.getDeviceExternalCacheDir(appContext);
          }
          if (cacheDir == null) {
            cacheDir = appContext.getCacheDir();
          }
          return cacheDir.getPath() + "/" + IMAGE_CACHE_FOLDER;
        }

        @Override
        public Resources getResources() {
          return null;
        }

        @Override
        public int getFileCacheSize() {
          return BITMAP_MAX_FILE_CACHE_SIZE;
        }

        @Override
        public int getMemoryCacheSize() {
          int memoryClass = ((ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE))
              .getMemoryClass();
          if (memoryClass <= 64) {
            return Math.round(memoryClass * 1024 * 1024 * BITMAP_MEMORY_CACHE_SIZE_SCALE_BELOW_64);
          } else {
            return Math.round(memoryClass * 1024 * 1024 * BITMAP_MEMORY_CACHE_SIZE_SCALE_ABOVE_64);
          }
        }

        @Override
        public int getNetworkThreadPoolSize() {
          return IMAGE_NETWORK_THREAD_POOL_SIZE;
        }

        @Override
        public int getLocalThreadPoolSize() {
          return IMAGE_LOCAL_THREAD_POOL_SIZE;
        }
      };
      imageManager = new ImageManager(appContext, config, byteArrayPool);
    }
    return imageManager;
  }

  private static void initArchives() {
    archives = new ArchivesServiceImpl(appContext);
  }

  private static void initImagePicker() {
    ImagePicker imagePicker = ImagePicker.getInstance();
    imagePicker.setImageLoader(new SimpleImageLoader());
    imagePicker.setShowCamera(true);
    imagePicker.setCrop(true);
    imagePicker.setSaveRectangle(true);
    imagePicker.setSelectLimit(IMAGE_PICK_SELECT_SIZE);
    imagePicker.setStyle(CropImageView.Style.RECTANGLE);
    imagePicker.setFocusWidth(IMAGE_PICK_CLIP_SIZE);
    imagePicker.setFocusHeight(IMAGE_PICK_CLIP_SIZE);
    imagePicker.setOutPutX(IMAGE_PICK_SAVE_SIZE);
    imagePicker.setOutPutY(IMAGE_PICK_SAVE_SIZE);
  }

  @Override
  public void onCreate() {
    super.onCreate();

    initApp();
  }

  /**
   * initialize application.
   */
  private void initApp() {

    /** catch application exception */
    Thread.setDefaultUncaughtExceptionHandler(new KharazimUncaughtExceptionHandler(
        getApplicationContext()));

    setAppContext(this);

    initByteArrayPool();
    initImageView();
    initArchives();
    initImagePicker();
  }

}
