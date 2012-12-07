package com.skcomms.android.sdk;

import java.io.IOException;
import java.net.MalformedURLException;

import com.skcomms.android.sdk.common.SDKException;

public interface RequestListener {

  public void onComplete(String response);

  public void onSDKException(SDKException e);

  public void onMalformedURLException(MalformedURLException ex);

  public void onIOException(IOException ex);

}