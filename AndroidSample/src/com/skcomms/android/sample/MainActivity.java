package com.skcomms.android.sample;

import java.io.IOException;
import java.net.MalformedURLException;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.skcomms.android.sdk.OAuthManager;
import com.skcomms.android.sdk.RequestListener;
import com.skcomms.android.sdk.SCDialog;
import com.skcomms.android.sdk.common.SDKException;
import com.skcomms.android.sdk.common.SDKLogUtil;

public class MainActivity extends ListActivity{

	private final String TAG = getClass().getName();
	
	private OAuthManager oAuthManager;
	
	private Button btnOAuthLogin;
	
	private RequestListener requestlistener = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		try{
			setConsumer();
			SDKLogUtil.setDebug(true);
			
			requestlistener = new RequestListener() {
				@Override
				public void onComplete(String message) {
					setOAuthBtnText(R.string.Logout);
				}
				@Override
				public void onSDKException(SDKException e) {
					Log.i(TAG ,"onSDKException",e);
				}

				@Override
				public void onMalformedURLException(MalformedURLException ex) {
					Log.i(TAG ,"onMalformedURLException",ex);
				}

				@Override
				public void onIOException(IOException ex) {
					Log.i(TAG ,"onIOException",ex);
				}
			};
			
			ArrayAdapter <CharSequence> menuAdapter = ArrayAdapter.createFromResource(
	                this, R.array.content_sample_menu_array, android.R.layout.simple_list_item_1);
			setListAdapter(menuAdapter);
	        
			/**
			 * oAuth Login 을 수행하는 Activity를 실행한다.
			 * NateOpenAPI 를 이용하기 위한 AccessToken & AccessTokenSecret 을 획득한다.
			 */
			btnOAuthLogin = (Button) findViewById(R.id.btn_oauth_login);
			btnOAuthLogin.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					
					if( !oAuthManager.isLogin(getApplicationContext()) ){
						new SCDialog(MainActivity.this , requestlistener ).show();
					}
					else{
						oAuthManager.onLogout(getApplicationContext());
						Log.i(TAG , "Login : " + oAuthManager.isLogin(getApplicationContext()) );
						setOAuthBtnText(R.string.Login);
					}
				}
			});
			isOAuthLogin();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void setOAuthBtnText(int id){
		btnOAuthLogin.setText(id);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = null;
        switch (position) {
        case 0:			// 싸이월드-미니홈피 API 샘플
			if (isOAuthLogin()) {
				intent = new Intent();
				intent.setClassName(v.getContext(), "com.skcomms.android.sample.cyworld.CyActivity");
			}
        	break;
        case 1:			// 네이트온 API 샘플
        	if (isOAuthLogin()) {
        		intent = new Intent();
        		intent.setClassName(v.getContext(), "com.skcomms.android.sample.nateon.NateOnActivity");
        	}
        	break;
        case 2:			// API Explorer
        	if (isOAuthLogin()) {
        		intent = new Intent();
        		intent.setClassName(v.getContext(), "com.skcomms.android.sample.explorer.ExplorerActivity");
        	}
        	break;
        case 3:			// 네이트 검색 API 샘플
        	intent = new Intent();
	        intent.setClassName(v.getContext(), "com.skcomms.android.sample.search.NateSearchActivity");
        	break;
        default:
        	break;
        }
        
		if (intent != null) {
			startActivity(intent);
		} else {
			AlertDialog.Builder ab = new AlertDialog.Builder(this);
			ab.setMessage(R.string.LoginMsg);
			ab.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					new SCDialog(MainActivity.this , requestlistener ).show();
				}
			});
			ab.setCancelable(false);
			ab.show();
		}
	}
	
	private boolean isOAuthLogin(){
		boolean result = false;
		try{
			if( oAuthManager.isLogin(getApplicationContext()) ){
				result = true;
				Log.i(TAG , "Token : " + oAuthManager.getOAuthNateSession(getApplicationContext()).getToken());
				Log.i(TAG , "TokenSecret : " + oAuthManager.getOAuthNateSession(getApplicationContext()).getTokenSecret());
				setOAuthBtnText(R.string.Logout);
			}
			else{
				result = false;
				setOAuthBtnText(R.string.Login);
			}
		}catch(Exception e){
			result = false;
		}
		
		Log.i(TAG , "Login : " + result );
		
		return result;
	}
	
	private void setConsumer() throws Exception{
		final String consumerKey = getString(R.string.consumer_key);
		final String consumerSecret = getString(R.string.consumer_key_secret);
		
		oAuthManager = OAuthManager.getInstance();
		oAuthManager.setOAuthConsumer(getApplicationContext(), consumerKey, consumerSecret);
	}
}
