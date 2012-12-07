package com.skcomms.android.sdk;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

public class HttpRequest {

	private String responseBody = "";

	private CommonsHttpOAuthConsumer consumer = null;

	/** Default Constructor */
	public HttpRequest() {
	}

	public HttpRequest(CommonsHttpOAuthConsumer consumer) {
		this.consumer = consumer;
	}

	public HttpURLConnection getConnection(String url) throws IOException,
			OAuthMessageSignerException, OAuthExpectationFailedException,
			OAuthCommunicationException {
		try {
			URL u = new URL(url);

			HttpURLConnection uc = (HttpURLConnection) u.openConnection();

			if (consumer != null) {
				try {
					consumer.sign(uc);
				} catch (OAuthMessageSignerException e) {
					throw e;

				} catch (OAuthExpectationFailedException e) {
					throw e;

				} catch (OAuthCommunicationException e) {
					throw e;
				}
				uc.connect();
			}
			return uc;
		} catch (IOException e) {
			throw e;
		}
	}

	public int sendGetRequest(String url, String params) throws IOException,
			OAuthMessageSignerException, OAuthExpectationFailedException,
			OAuthCommunicationException {

		int responseCode = 500;
		try {
			HttpResponse response = null;

			HttpGet request = new HttpGet(url + "?" + params);
			consumer.sign(request);

			HttpClient httpClient = new DefaultHttpClient();
			response = httpClient.execute(request);
			responseCode = processHttpResponse(response);
		} catch (MalformedURLException ex) {
			throw new IOException(url + " is not valid");
		} catch (IOException ie) {
			throw new IOException("IO Exception " + ie.getMessage());
		}
		return responseCode;
	}

	private int processHttpResponse(HttpResponse response) throws IOException {
		int responseCode;
		responseCode = response.getStatusLine().getStatusCode();

		if (200 == responseCode || 400 == responseCode || 404 == responseCode) {

			Reader reader = null;
			try {
				reader = new InputStreamReader(response.getEntity().getContent());

				StringBuffer sb = new StringBuffer();
				{
					int read;
					char[] cbuf = new char[1024];
					while ((read = reader.read(cbuf)) != -1)
						sb.append(cbuf, 0, read);
				}

				setResponseBody(sb.toString());

			} finally {
				if (reader != null) {
					try {
						reader.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return responseCode;
	}

	public int sendPostRequest(String url,
			ArrayList<BasicNameValuePair> paramList) throws IOException,
			OAuthMessageSignerException, OAuthExpectationFailedException,
			OAuthCommunicationException {

		int responseCode = 500;
		try {

			HttpResponse response = null;

			HttpParams parms = new BasicHttpParams();
			HttpProtocolParams.setVersion(parms, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(parms, HTTP.UTF_8);
			HttpProtocolParams.setUseExpectContinue(parms, false);
			// HttpProtocolParams.setUserAgent( parms, getUserAgent() );
			HttpConnectionParams.setConnectionTimeout(parms, 2000);
			HttpConnectionParams.setSoTimeout(parms, 2000);

			UrlEncodedFormEntity ent = new UrlEncodedFormEntity(paramList, HTTP.UTF_8);

			HttpPost request = new HttpPost(url);

			request.setParams(parms);
			// request.setHeader( "Authorization", "OAuth" );
			request.setEntity(ent);
			consumer.sign(request);

			HttpClient httpClient = new DefaultHttpClient();
			response = httpClient.execute(request);
			responseCode = processHttpResponse(response);
		} catch (MalformedURLException ex) {
			throw new IOException(url + " is not valid");
		} catch (IOException ie) {
			throw new IOException("IO Exception " + ie.getMessage());
		}
		return responseCode;
	}

	/**
	 * Return the Response body
	 * 
	 * @return String
	 */
	public String getResponseBody() {
		return responseBody;
	}

	/**
	 * Setter
	 * 
	 * @param responseBody
	 */
	public void setResponseBody(String responseBody) {
		if (null != responseBody) {
			this.responseBody = responseBody;
		}
	}

	/**
	 * Set the oAuth consumer
	 * 
	 * @param consumer
	 */
	public void setOAuthConsumer(CommonsHttpOAuthConsumer consumer) {
		this.consumer = consumer;
	}

	public int sendPostFileRequest(String url, File file)
			throws OAuthMessageSignerException,
			OAuthExpectationFailedException, OAuthCommunicationException,
			IOException {
		int responseCode = 500;
		try {

			HttpResponse response = null;

			HttpParams parms = new BasicHttpParams();
			HttpProtocolParams.setVersion(parms, HttpVersion.HTTP_1_1);
			HttpConnectionParams.setConnectionTimeout(parms, 2000);
			HttpConnectionParams.setSoTimeout(parms, 2000);

			HttpPost request = new HttpPost(url);

			MultipartEntity mpEntity = new MultipartEntity();
			ContentBody cbFile = new FileBody(file, "image/jpeg");
			mpEntity.addPart("file", cbFile);

			consumer.sign(request);
			request.setEntity(mpEntity);

			HttpClient httpClient = new DefaultHttpClient();
			response = httpClient.execute(request);
			responseCode = processHttpResponse(response);
		} catch (MalformedURLException ex) {
			throw new IOException(url + " is not valid");
		} catch (IOException ie) {
			throw new IOException("IO Exception " + ie.getMessage());
		}
		return responseCode;
	}
}