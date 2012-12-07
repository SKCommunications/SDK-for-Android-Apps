package com.skcomms.android.sdk.common;

import android.util.Log;

public class SDKLogUtil {

	private static boolean DEBUG = false;
	
	/**
	 * Nate SDK Library Log 기록 설정
	 * @param debug
	 */
	public static void setDebug(boolean debug){
		SDKLogUtil.DEBUG = debug;
	}
	
	/**
	 * 자세한 Exception과 함께 에러메시지를 출력한다.
	 * @param tag
	 * @param msg
	 * @param ex
	 */
	public static void e(String tag, String msg, Exception ex) {
		Log.e(tag, msg, ex);
	}
	
	/**
	 * 자세한 Exception과 함께 에러메시지를 출력한다.
	 * @param tag
	 * @param msg
	 */
	public static void e(String tag, String msg) {
		Log.e(tag, msg);
	}
	
	/**
	 * DEBUG메시지를 출력한다.
	 * @param tag
	 * @param msg
	 */
	public static void d(String tag, String msg) {
		if( DEBUG )
			Log.d(tag, msg);
	}
}
