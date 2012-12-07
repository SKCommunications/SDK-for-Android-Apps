package com.skcomms.android.sdk;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.skcomms.android.sample.R;
import com.skcomms.android.sdk.common.SDKException;
import com.skcomms.android.sdk.common.SDKLogUtil;

public class SCDialog extends Dialog {

	private final String TAG = getClass().getName();
	
	private final FrameLayout.LayoutParams layoutParams =
        new FrameLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                         ViewGroup.LayoutParams.FILL_PARENT);

    private String authorizationUrl;
    private ProgressDialog mSpinner;
    private WebView webview;
    private FrameLayout view;
    private ImageView closeImage;
    
    private RequestListener requestlistener;
    
    private OAuthManager oAuthManager;
    
    private int margin = 10;
    
    public SCDialog(Context context , RequestListener requestlistener ) {
        this(context, requestlistener, android.R.style.Theme_Translucent_NoTitleBar);
    }
    
    public SCDialog(Context context , RequestListener requestlistener, int theme ) {
    	this(context, requestlistener, theme , null);
    }
    
    public SCDialog(Context context , RequestListener requestlistener, int theme, WebView webview ) {
    	super(context, theme);
    	oAuthManager = OAuthManager.getInstance();
    	this.requestlistener = requestlistener;
    	this.webview = webview;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        mSpinner = new ProgressDialog(getContext());
        mSpinner.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mSpinner.setMessage("Loading...");
        
		try {
			NateSession nSession = oAuthManager.getOAuthNateSession(getContext());
			final String consumerKey = nSession.getConsumerKey();
			final String consumerSecret = nSession.getConsumerSecret();
			
			if (webview == null) {
				final Handler handler = new Handler() {
		            @Override
		            public void handleMessage(Message message) {
		            	authorizationUrl = (String) message.obj;
		            	initView();
		            }
		        };

		        Thread thread = new Thread() {
		            @Override
		            public void run() {
		            	String authorizationUrl = null;
						try {
							authorizationUrl = oAuthManager.getAuthorizationURL(getContext(), consumerKey, consumerSecret);
						} catch (SDKException e) {
							SDKLogUtil.e(TAG, "get authorizationUrl Error", e);
						}
		                Message message = handler.obtainMessage(1, authorizationUrl);
		                handler.sendMessage(message);
		            }
		        };
		        thread.start();
			} else {
				initView();
			}
		} catch (Exception e) {

		}
    }
    
    private void initView() {
        view = new FrameLayout(getContext());
        view.setLayoutParams(layoutParams);

        // Dialog Close 버튼 설정
        closeImage = new ImageView(getContext());
        closeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	SCDialog.this.dismiss();
            }
        });
        Drawable crossDrawable = getContext().getResources().getDrawable(R.drawable.cancel_icon);
        closeImage.setImageDrawable(crossDrawable);
        closeImage.setVisibility(View.INVISIBLE);
        
        // OAuth Webivew 설정
        LinearLayout webViewContainer = new LinearLayout(getContext());
		if (webview == null) {
        	webview = new WebView(getContext());
            webview.setVerticalScrollBarEnabled(false);
            webview.setHorizontalScrollBarEnabled(false);
            webview.setWebViewClient(new SCDialog.SCWebViewClient());
            webview.getSettings().setJavaScriptEnabled(true);
            webview.loadUrl(authorizationUrl);
            webview.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
            webview.setVisibility(View.INVISIBLE);
            webview.getSettings().setSavePassword(false);
        }
        webViewContainer.setPadding(margin, margin, margin, margin);
        webViewContainer.addView(webview);
        
        view.addView(webViewContainer);;
        
        view.addView(closeImage, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        addContentView(view, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
    }

    private class SCWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(final WebView view, final String url) {
        	SDKLogUtil.d(TAG, "URL : " + url);

			final Handler handler = new Handler() {
	            @Override
	            public void handleMessage(Message message) {
	            	boolean bResult = (Boolean) message.obj;
	            	
	            	if(bResult){
	            		SCDialog.this.dismiss();
	    				requestlistener.onComplete("");
	            	}else{
	            		view.loadUrl(url);
	            	}
	            }
	        };

	        Thread thread = new Thread() {
	            @Override
	            public void run() {
	            	boolean bResult = false;
					bResult = oAuthManager.getAccessToken(getContext(), url);
	                Message message = handler.obtainMessage(1, bResult);
	                handler.sendMessage(message);
	            }
	        };
	        thread.start();
			
            return true;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            SCDialog.this.dismiss();
            String errorMsg = errorCode + description + failingUrl;
            requestlistener.onSDKException(new SDKException(errorMsg));
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            mSpinner.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            mSpinner.dismiss();
            webview.setVisibility(View.VISIBLE);
            closeImage.setVisibility(View.VISIBLE);
        }
    }
}
