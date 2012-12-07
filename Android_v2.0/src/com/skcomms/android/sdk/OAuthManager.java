package com.skcomms.android.sdk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.basic.DefaultOAuthProvider;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;

import com.skcomms.android.sdk.common.Error;
import com.skcomms.android.sdk.common.SDKException;
import com.skcomms.android.sdk.common.SDKLogUtil;
import com.skcomms.android.sdk.common.URLConfig;

/**
 * OAuth 인증을 위한 기능을 제공한다.
 * @link http://devsquare.nate.com
 */
public class OAuthManager {

	/**
	 * Android Log Tag 명
	 */
	private final String TAG = getClass().getName();
	/**
	 * OAuthManager Static 변수 
	 */
	private static OAuthManager oauthManager = null;
	/**
	 * SharedPreferences 에 저장하는 이름
	 */
	private static final String PREFERENCE_NAME = "NATE_OAUTHDATA";
	/**
	 * 설정한 ConsumerInfo 를 이용하여 Access Token 획득을 위한 HTTP 요청을 처리하는 SignPost 인터페이스
	 */
	private OAuthConsumer mConsumer = null;
	/**
	 * OAuth 1.0 a 서비스로부터 Access Token 획득하기 위한 SignPost 인터페이스
	 */
	private OAuthProvider mProvider = null;
	/**
	 * OAuth Login Callback URI ( "nate://nate" )
	 */
	public static final Uri CALLBACK_URI = Uri.parse("nate://nate");
	/**
	 * OAuth "oauth_verifier" 파싱을 위한 key 값
	 */
	public static final String OAUTH_VERIFIER = "oauth_verifier";
	/**
	 * SharedPreferences Consumer-Key Key 값
	 */
	public static final String KEY_CONSUMER_KEY = "OAUTH_CONSUMER_KEY";
	/**
	 * SharedPreferences Consumer Secret Key 값
	 */
	public static final String KEY_CONSUMER_SECRET = "OAUTH_CONSUMER_SECRET";
	/**
	 * SharedPreferences Access Token Key 값
	 */
	public static final String KEY_AUTHED_ACCESS_TOKEN = "AUTHED_ACCESS_TOKEN";
	/**
	 * SharedPreferences Access Token Secret Key 값
	 */
	public static final String KEY_AUTHED_ACCESS_TOKEN_SECRET = "AUTHED_ACCESS_TOKEN_SECRET";
	
	/**
	 * OAuthManager 인스턴스 객체를 획득한다.
	 * @return
	 */
	public static OAuthManager getInstance() {

		synchronized (OAuthManager.class) {
			if (oauthManager == null) {
				oauthManager = new OAuthManager();
			}
		}
		return oauthManager;
	}

	/**
	 * 사용자 인증을 위한 Authorize url 을 획득한다.
	 * @param context oAuthManager 를 호출하는 Activity의 Context
	 * @param consumerKey 서비스 프로바이더에게 컨슈머 자신임을 인증하기 위한 키
	 * @param consumerSecret 컨슈머의 컨슈머 키 소유권한이 있는지 인증하기 위한 키
	 * @return AuthorizationURL
	 * @throws SDKException
	 */
	public String getAuthorizationURL(Context context , String consumerKey , String consumerSecret) throws SDKException{
		
		if( consumerKey == null || consumerSecret == null){
			throw new SDKException(Error.ERR_INPUT_ARG_NULL);
		}
		
		String authorizationUrl = null;
		
		SDKLogUtil.d( TAG, "consumer_key : " + consumerKey);
		SDKLogUtil.d( TAG, "consumer_secret : " + consumerSecret);
		
		// Consumer key  & Consumer Secret 을 저장한다.
		HashMap<String,String> param = new HashMap<String,String>();
		param.put(KEY_CONSUMER_KEY, consumerKey);
		param.put(KEY_CONSUMER_SECRET, consumerSecret);
		setSharePreferences(context , param);
		
		// consumer 정보를 설정한다.
		mConsumer = new DefaultOAuthConsumer(consumerKey,consumerSecret);
		// OAuth 인증을 위한 RequsetToken & Access Token  & Authorized URL 을 설정한다.
		mProvider = new DefaultOAuthProvider(
				URLConfig.NATE_OPENAPI_GET_REQUEST_TOKEN,
				URLConfig.NATE_OPENAPI_GET_ACCESS_TOKEN,
				URLConfig.NATE_OPENAPI_AUTHORIZED_URL);
		try {
			// Authorized URL 을 획득
			authorizationUrl = mProvider.retrieveRequestToken(mConsumer,
					OAuthManager.CALLBACK_URI.toString());
		} catch (Exception e) {
			throw new SDKException(e);
		}
		
		return authorizationUrl;
	}
	
	/**
	 * Authorize 에 성공할 경우, OpenAPI를 이용하기 위한 Access Token  & Access Token Secret 획득한다.
	 * 이 획득한 값은 Android SharedPreferences 에 저장되어 관리된다.
	 * @param context oAuthManager 를 호출하는 Activity의 Context
	 * @param url webview Load 된 url
	 * @return true : Access Token  획득 성공 , false : Access Token  획득 실패 or oauth_verifier 중
	 */
	public boolean getAccessToken( Context context , String url ) {
		
		Uri uri = Uri.parse(url);
		
		SDKLogUtil.d( TAG, "url : " + url);
		
		// webview Load 된 uri 이 Callback uri 와 같은지 비교
		if (url != null
				&& OAuthManager.CALLBACK_URI.getScheme().equals(uri.getScheme())) {
			
			// url 에서 oauth_verifier 값을 가져온다.
			String verifier = uri.getQueryParameter(OAuthManager.OAUTH_VERIFIER);
			String accessToken = null;
			String accessTokenSecret = null;
			
			SDKLogUtil.d( TAG, "verifier : " + verifier);

			try {
				// oauth_verifier값을 검증 하여 Access Token  값을 획득한다.
				mProvider.retrieveAccessToken (mConsumer, verifier);

				accessToken = mConsumer.getToken();
				accessTokenSecret = mConsumer.getTokenSecret();

				// 획득한 Access Token  & Access Token Secret 을 저장한다.
				setOAuthNateSession(context, accessToken, accessTokenSecret);
				
				SDKLogUtil.d(TAG, "Access Token : " + accessToken);
				SDKLogUtil.d(TAG, "Access Token Secret : " + accessTokenSecret);
				return true;
				
			} catch (Exception e) {
				SDKLogUtil.e( TAG , "Access Token Fail" , e);
			}
		} 
		return false;
	}
	
	/**
	 * OAuth Consumer 정보를 설정한다.
	 * @param context OAuthManger 를 호출한 Activity Context
	 * @param consumerKey
	 * @param consumerSecret
	 * @throws SDKException
	 */
	public void setOAuthConsumer(Context context , String consumerKey , String consumerSecret ) throws SDKException{
		
		if( context == null ){
			throw new SDKException(Error.ERR_CONTEXT_NULL);
		}
		if( consumerKey == null || consumerSecret == null ){
			throw new SDKException(Error.ERR_INPUT_ARG_NULL);
		}
		
		// Access Token  & Access Token Secret 을 저장한다.
		HashMap<String,String> param = new HashMap<String,String>();
		
		param.put(KEY_CONSUMER_KEY, consumerKey);
		param.put(KEY_CONSUMER_SECRET, consumerSecret);
		
		setSharePreferences(context , param);
	}

	/**
	 * OAuth 인증에 성공하여 획득한 Access Token  & Access Token Secret 을 Android SharedPreferences 에 저장한다.
	 * @param context	OAuthManger 를 호출한 Activity Context
	 * @param accessToken	Access Token 
	 * @param accessTokenSecret	Access Token Secret
	 * @throws SDKException
	 */
	public void setOAuthNateSession(Context context , String accessToken , String accessTokenSecret ) throws SDKException{
		
		if( context == null ){
			throw new SDKException(Error.ERR_CONTEXT_NULL);
		}
		if( accessToken == null || accessTokenSecret == null ){
			throw new SDKException(Error.ERR_INPUT_ARG_NULL);
		}
		
		// Access Token  & Access Token Secret 을 저장한다.
		HashMap<String,String> param = new HashMap<String,String>();
		
		param.put(KEY_AUTHED_ACCESS_TOKEN, accessToken);
		param.put(KEY_AUTHED_ACCESS_TOKEN_SECRET, accessTokenSecret);
		
		setSharePreferences(context , param);
	}
	
	/**
	 * Android SharedPreferences 에 저장해 놓은 Access Token  & Access Token Secret 값을 획득한다.
	 * @param context OAuthManger 를 호출한 Activity Context
	 * @return
	 * @throws SDKException
	 */
	public NateSession getOAuthNateSession(Context context) throws SDKException{
		
		if( context == null ){
			throw new SDKException(Error.ERR_CONTEXT_NULL);
		}

		NateSession nateSession = new NateSession();
		
		// SharedPreferences 에서 획득 할 value 의 key 를 설정한다.
		List<String> keyList = new ArrayList<String>();
		keyList.add(KEY_AUTHED_ACCESS_TOKEN);
		keyList.add(KEY_AUTHED_ACCESS_TOKEN_SECRET);
		keyList.add(KEY_CONSUMER_KEY);
		keyList.add(KEY_CONSUMER_SECRET);
		
		// SharedPreferences 에서 value 를 획득하여, NateSession 에 설정한다.
		HashMap<String,String> result = getSharePreferences(context, keyList);
		if( result != null ){
			nateSession.setToken( result.get(KEY_AUTHED_ACCESS_TOKEN ) );
			nateSession.setTokenSecret( result.get(KEY_AUTHED_ACCESS_TOKEN_SECRET ) );
			nateSession.setConsumerKey( result.get(KEY_CONSUMER_KEY ) );
			nateSession.setConsumerSecret( result.get(KEY_CONSUMER_SECRET ) );
		}
		
		return nateSession;
	}
	
	/**
	 * Access Token & Access Token Secret 이 존재하는 확인 한다.
	 * @param context OAuthManger 를 호출한 Activity Context
	 * @return
	 */
	public boolean isLogin(Context context){
		boolean login = true;
		
		if( context == null ){
			login = false;
		}
		
		try{
			NateSession nateSession = getOAuthNateSession(context);
			login = nateSession.isLogin();
		}
		catch(Exception e){
			login = false;
		}
		
		return login;
	}
	
	/**
	 * OAuth Logout 을 처리 한다.
	 * Application SharedPreferences 에 저장되어 있는 Consumer 정보 & Access Token 정보를 삭제한다.
	 * @param context
	 */
	public void onLogout(Context context) {
		
		HashMap<String,String> param = new HashMap<String,String>();
		
		param.put(KEY_AUTHED_ACCESS_TOKEN, null);
		param.put(KEY_AUTHED_ACCESS_TOKEN_SECRET, null);
//		param.put(KEY_CONSUMER_KEY, null);
//		param.put(KEY_CONSUMER_SECRET, null);	
		
		setSharePreferences(context , param);
	}
	
	/**
	 * SharedPreferences 에 data 를 key-value 형식으로 저장한다.
	 * @param context OAuthManger 를 호출한 Activity Context
	 * @param param SharedPreferences 에 저장할 key-value Map
	 */
	public void setSharePreferences(Context context, HashMap<String,String> param) {
		// Application 의 SharedPreferences 를 획득
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				PREFERENCE_NAME, Activity.MODE_PRIVATE);

		// Key-Value 설정을 위한 SharedPreferences.Editor 를 획득.
		Editor sharedPreferencesEditor = sharedPreferences.edit();
		
		// SharedPreferences 에 param을 저장한다.
		Iterator<Entry<String, String>> iterator = param.entrySet().iterator();
		while (iterator.hasNext()) {
	        Entry<String, String> entry = iterator.next();
	        sharedPreferencesEditor.putString((String)entry.getKey(), (String)entry.getValue() );
	    }
		sharedPreferencesEditor.commit();
	}
	
	/**
	 * SharedPreferences 에 저장되어 있는 data 를 획득한다.
	 * @param context OAuthManger 를 호출한 Activity Context
	 * @param key SharedPreferences 에서 획득할 data 의 키 List
	 * @return
	 */
	public HashMap<String,String> getSharePreferences(Context context, List<String> key ) {
		// Application 의 SharedPreferences 를 획득
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				PREFERENCE_NAME, Activity.MODE_PRIVATE);
		
		if(key == null || key.size() <= 0)
			return null;
		
		HashMap<String,String> param = new HashMap<String,String>();
		
		for (String skey : key) {
			param.put(skey, sharedPreferences.getString(skey , "") );
		}
		
		return param;
	}
}
