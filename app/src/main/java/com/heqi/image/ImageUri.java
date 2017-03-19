package com.heqi.image;

import java.io.Serializable;

/**
 * Created by wenchengye on 16/10/11.
 */
public class ImageUri implements Serializable {

  private static final long serialVersionUID = -1694117415411279865L;

  private String imageUri;
  private ImageUriType imageUriType;

  public ImageUri(String imageUri, ImageUriType urlType) {
    this.imageUri = imageUri;
    this.imageUriType = urlType;
  }

  public String getImageUri() {
    return imageUri;
  }

  public ImageUriType getImageUriType() {
    return imageUriType;
  }

  public enum ImageUriType {
    /**
     * uri should be image http url
     */
    NETWORK,
    /**
     * uri should be package name
     */
    APP_ICON,
    /**
     * uri should be local image resource id
     */
    LOCAL_IMAGE_RES,
    /**
     * uri should be local video path
     */
    VIDEO_THUMBNAIL,
    /**
     * uri should be local apk path
     */
    APK_ICON,
    /**
     * unspecified uri type
     */
    UNSPECIFIED,
  }
}
