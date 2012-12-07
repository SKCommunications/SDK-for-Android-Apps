/**
 * @desc OpenAPI 를 http request 
 * @author sk
 * @date 2012.10.17
 */
package com.skcomms.android.sdk;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Map;

import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

import org.apache.http.message.BasicNameValuePair;

import android.content.Context;

import com.skcomms.android.sdk.common.Error;
import com.skcomms.android.sdk.common.SDKException;
import com.skcomms.android.sdk.common.SDKLogUtil;

public class ApiRequest {
	private final String TAG = getClass().getName();
	private Context context;

	public final static String HTTP_POST = "POST";
	public final static String HTTP_GET = "GET";

	/**
	 * ApiRequest 생성자
	 * @param context
	 */
	public ApiRequest(Context context) {
		this.context = context;
	}

	/**
	 * http request 호출. 
	 * OAuth 인증에 성공한 data 를 이용하여 Request Header에 셋팅 후, POST 형태로 호출된 결과를 리턴.
	 * @param endpointUrl OpenAPI endpointUrl
	 * @return endpointUrl 호출 결과를 반환한다.
	 * @throws SDKException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public String request(String endpointUrl) throws SDKException,
			MalformedURLException, IOException {
		Map<String, String> params = null;
		return request(endpointUrl, params, HTTP_POST);
	}

	/**
	 * http request 호출. 
	 * OAuth 인증에 성공한 data 를 이용하여 Request Header에 셋팅 후, POST 형태로 호출된 결과를 리턴
	 * @param endPointUrl OpenAPI endpointUrl
	 * @param params 요청 매개변수
	 * @return endpointUrl 호출 결과를 반환한다.
	 * @throws SDKException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public String request(String endPointUrl, Map<String, String> params)
			throws SDKException, MalformedURLException, IOException {
		return request(endPointUrl, params, HTTP_POST);
	}

	/**
	 * http request 호출. 
	 * OAuth 인증에 성공한 data 를 이용하여 Request Header에 셋팅 후, 호출된 결과를 리턴
	 * @param endPointUrl OpenAPI endpointUrl
	 * @param params 요청 매개변수
	 * @param httpMethod POST or GET ( ApiRequest.HTTP_POST / ApiRequest.HTTP_GET )
	 * @return endpointUrl 호출 결과를 반환한다.
	 * @throws SDKException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public String request(String endPointUrl, Map<String, String> params,
			String httpMethod) throws SDKException, MalformedURLException,
			IOException {

		if (endPointUrl == null || params == null || httpMethod == null) {
			throw new SDKException(Error.ERR_INPUT_ARG_NULL);
		}
		
		String result = null;
		int responseCode = 500;
		String key = null, secret = null, token = null, tokenSecret = null;

		HttpRequest httpRequest = new HttpRequest();
		OAuthManager oAuthManager = new OAuthManager();
		NateSession nSession = new NateSession();
		try {
			nSession = oAuthManager.getOAuthNateSession(context);
			key = nSession.getConsumerKey();
			secret = nSession.getConsumerSecret();
			token = nSession.getToken();
			tokenSecret = nSession.getTokenSecret();
		} catch (SDKException e) {
			SDKLogUtil.e(TAG, httpRequest.getResponseBody(), e);
		}

		CommonsHttpOAuthConsumer consumer = new CommonsHttpOAuthConsumer(key,
				secret);
		consumer.setTokenWithSecret(token, tokenSecret);
		httpRequest.setOAuthConsumer(consumer);

		try {
			if (HTTP_GET.equals(httpMethod)) {
				StringBuffer sb = new StringBuffer();
				for (Map.Entry<?, ?> entry : params.entrySet()) {
					if (sb.length() > 0) {
						sb.append("&");
					}
					sb.append(String.format("%s=%s", entry.getKey().toString(), entry.getValue().toString()));
				}
				responseCode = httpRequest.sendGetRequest(endPointUrl, sb.toString());
			} else if (HTTP_POST.equals(httpMethod)) {
				ArrayList<BasicNameValuePair> paramList = new ArrayList<BasicNameValuePair>();
				for (Map.Entry<?, ?> entry : params.entrySet()) {
					String paramKey = entry.getKey().toString();
					String paramValue = entry.getValue().toString();
					SDKLogUtil.d(TAG, "key: " + paramKey);
					SDKLogUtil.d(TAG, "value: " + paramValue);
					paramList.add(new BasicNameValuePair(paramKey, paramValue));
				}
				responseCode = httpRequest.sendPostRequest(endPointUrl, paramList);
				SDKLogUtil.d(TAG, "responseCode: " + responseCode);
			}
			result = httpRequest.getResponseBody();
			
			if (responseCode == 200) {
				SDKLogUtil.d(TAG, "response Body: " + result);
			} else {
				throw new SDKException(result);
			}
		} catch (Exception e) {
			SDKLogUtil.e(TAG, "Error in response due to status code = " + responseCode);
			SDKLogUtil.e(TAG, "Error in response Body: " + httpRequest.getResponseBody());
		}
		return result;
	}

	/**
	 * http request 호출. 
	 * OAuth 인증에 성공한 data 를 이용하여 Request Header에 셋팅 후, file을 전송.
	 * @param endPointUrl OpenAPI endpointUrl
	 * @param file 전송할 targer file
	 * @param httpMethod POST or GET ( ApiRequest.HTTP_POST / ApiRequest.HTTP_GET )
	 * @throws SDKException
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public String request(String endPointUrl, File file, String httpMethod)
			throws SDKException, MalformedURLException, IOException,
			FileNotFoundException {

		if (endPointUrl == null || file == null || httpMethod == null) {
			throw new SDKException(Error.ERR_INPUT_ARG_NULL);
		}
		
		String result = "";
		int responseCode = 500;
		String key = null, secret = null, token = null, tokenSecret = null;

		HttpRequest httpRequest = new HttpRequest();
		OAuthManager oAuthManager = new OAuthManager();
		NateSession nSession = new NateSession();
		try {
			nSession = oAuthManager.getOAuthNateSession(context);
			key = nSession.getConsumerKey();
			secret = nSession.getConsumerSecret();
			token = nSession.getToken();
			tokenSecret = nSession.getTokenSecret();
		} catch (SDKException e) {
			SDKLogUtil.e(TAG, httpRequest.getResponseBody(), e);
		}

		CommonsHttpOAuthConsumer consumer = new CommonsHttpOAuthConsumer(key, secret);
		consumer.setTokenWithSecret(token, tokenSecret);
		httpRequest.setOAuthConsumer(consumer);

		try {
			if (HTTP_POST.equals(httpMethod)) {
				responseCode = httpRequest.sendPostFileRequest(endPointUrl, file);

				SDKLogUtil.d(TAG, "responseCode: " + responseCode);
				SDKLogUtil.d(TAG, "response Body: " + httpRequest.getResponseBody());
			}
			result = httpRequest.getResponseBody();
			
			if (responseCode == 200) {
				SDKLogUtil.d(TAG, "response Body: " + result);
			} else {
				throw new SDKException(result);
			}
		} catch (Exception e) {
			SDKLogUtil.e(TAG, "Error in response due to status code = " + responseCode);
			SDKLogUtil.e(TAG, "Error in response Body: " + httpRequest.getResponseBody());
		}
		return result;
	}

	/**
	 * http request 호출. 
	 * OAuth 인증에 성공한 data 를 이용하여 Request Header에 셋팅 후, POST 형태로 비동기로 호출된 결과를 Listener를 통해 전달한다.
	 * @param endpointUrl OpenAPI endpointUrl
	 * @param listener 
	 * @throws SDKException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public void request(String endPointUrl, RequestListener listener)
			throws SDKException, MalformedURLException, IOException {
		request(endPointUrl, null, HTTP_POST, listener);
	}

	/**
	 * http request 호출. 
	 * OAuth 인증에 성공한 data 를 이용하여 Request Header에 셋팅 후, POST 형태로 비동기로 호출된 결과를 Listener를 통해 전달한다.
	 * @param endpointUrl OpenAPI endpointUrl
	 * @param params 요청 매개 변수
	 * @param listener
	 * @throws SDKException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public void request(String endPointUrl, Map<String, String> params,
			RequestListener listener) throws SDKException,
			MalformedURLException, IOException {
		request(endPointUrl, params, HTTP_POST, listener);
	}

	/**
	 * http request 호출. 
	 * OAuth 인증에 성공한 data 를 이용하여 Request Header에 셋팅 후, 비동기로 호출된 결과를 Listener를 통해 전달한다.
	 * @param endpointUrl OpenAPI endpointUrl
	 * @param params 요청 매개 변수
	 * @param httpMethod POST or GET ( ApiRequest.HTTP_POST / ApiRequest.HTTP_GET )
	 * @param listener
	 * @throws SDKException
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public void request(final String endPointUrl,
			final Map<String, String> params, final String httpMethod,
			final RequestListener listener)
			throws SDKException, MalformedURLException, IOException {

		if (listener == null) {
			throw new SDKException(Error.ERR_LISTENER_NOT_SET);
		}

		new Thread() {
			@Override
			public void run() {
				try {
					String response = ApiRequest.this.request(endPointUrl,
							params, httpMethod);
					listener.onComplete(response);
				} catch (MalformedURLException e) {
					listener.onMalformedURLException(e);
				} catch (IOException e) {
					listener.onIOException(e);
				} catch (Exception e) {
					listener.onSDKException(new SDKException(e.getMessage()));
				}
			}
		}.start();
	}
}
