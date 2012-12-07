package com.skcomms.android.sdk;

/**
 * Oauth 인증을 통해서 발급받은 AccessToken & AccessToken Secret 값을 갖는다.
 * 
 */
public class NateSession {

	/**
	 * AccessToken
	 */
	private String token;
	/**
	 * AccessToken Secret
	 */
	private String tokenSecret;
	
	/**
	 * ConsumerKey
	 */
	private String consumerKey;
	
	/**
	 * ConsumerSecret
	 */
	private String consumerSecret;

	/**
	 * AccessToken 을 획득한다.
	 * @return
	 */
	public String getToken() {
		return token;
	}

	/**
	 * AccessToken Secret 을 획득한다.
	 * @return
	 */
	public String getTokenSecret() {
		return tokenSecret;
	}

	/**
	 * AccessToken 을 설정한다.
	 * @param token
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * AccessToken Secret 을 설정한다.
	 * @param tokenSecret
	 */
	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}
	
	/**
	 * ConsumerKey 를 획득한다.
	 * @return
	 */
	public String getConsumerKey() {
		return consumerKey;
	}

	/**
	 * ConsumerKey 를 설정한다.
	 * @param consumerKey
	 */
	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	/**
	 * ConsumerSecret 를 획득한다.
	 * @return
	 */
	public String getConsumerSecret() {
		return consumerSecret;
	}

	/**
	 * ConsumerSecret 를 설정한다.
	 * @param consumerSecret
	 */
	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	/**
	 * AccessToken & AccessTokenSecret 값을 확인하여 Login 유지 상태 여부를 판단한다.
	 * @return
	 */
	public boolean isLogin(){
		if( token == null || tokenSecret == null || "".equals(token) || "".equals(tokenSecret))
			return false;
		return true;
	}

}