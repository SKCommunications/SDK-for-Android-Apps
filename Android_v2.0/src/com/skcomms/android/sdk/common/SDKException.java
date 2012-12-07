package com.skcomms.android.sdk.common;

public final class SDKException extends Exception {

	private static final long serialVersionUID = 1L;

	public SDKException(String msg) {
        super(msg);
    }

    public SDKException(Exception cause) {
        super(cause);
    }
}