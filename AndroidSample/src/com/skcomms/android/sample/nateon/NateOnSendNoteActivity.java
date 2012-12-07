package com.skcomms.android.sample.nateon;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.skcomms.android.sample.R;
import com.skcomms.android.sdk.ApiRequest;
import com.skcomms.android.sdk.common.URLConfig;

public class NateOnSendNoteActivity extends Activity implements OnClickListener{
	private final String TAG = getClass().getName();
	private String nateId = null;
	
	private Button buttonSendNote = null;
	private EditText editBuddyText = null;
	private EditText editTextMsg = null;
	private ProgressDialog mSpinner;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.nateon_send_note);
		
		Intent intent = getIntent();
		
		// 네이트온 ID 정보를 Bundle 에서 가져옵니다. 
		if(intent != null) {
			Bundle bundle = intent.getBundleExtra(NateOnActivity.NATEINFO);
			if(bundle != null) {
				nateId = bundle.getString(NateOnActivity.NATEID);
				Log.d(TAG, "nateId: " + nateId);
			}
		}
		
		mSpinner = new ProgressDialog(this);
        mSpinner.setMessage("Updating...");
		
		buttonSendNote = (Button) findViewById(R.id.button_send_note);
		buttonSendNote.setOnClickListener(this);
		
		editBuddyText = (EditText) findViewById(R.id.editView_input_title);
		editBuddyText.setText("to: " + nateId);
		
		editTextMsg = (EditText) findViewById(R.id.editView_input_content);
		editTextMsg.requestFocus();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_send_note: 
			Log.d(TAG, "POST Note");
	        mSpinner.show();
			new DataAsyncTask().execute();
			break;
		}
	}
	
	private class DataAsyncTask extends AsyncTask<Void, Void, Void> {
		String response = "";
		String resultMsg = null;
		
		@Override
		protected Void doInBackground(Void... Void) {
			try{
				Editable sendMsg = editTextMsg.getText();
				ApiRequest req = new ApiRequest(getApplicationContext());
				Map<String, String> params = new HashMap<String, String>();
				params.put("ref", nateId);
				params.put("body", sendMsg.toString());

				response = req.request(URLConfig.NATEON_SEND_NOTE,
						params, ApiRequest.HTTP_POST);
				Log.d(TAG, "strResponse: " + response);				

			}
			catch(Exception e){
				resultMsg = e.getMessage();
			}
			return null;
		}

		@Override
		protected void onCancelled() {
		}

		@Override
		protected void onPostExecute(Void result) {
			onDialogView(resultMsg);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
	}
	
	private void onDialogView(String msg) {
		
		if( mSpinner != null ) {
			mSpinner.dismiss();
		}
		Log.d(TAG, "resultMsg : " + msg);
		AlertDialog.Builder ab = new AlertDialog.Builder(this);
		if( msg == null ){
			ab.setMessage(R.string.ContentSendNoteOK);
			editTextMsg.setText(null);
			editTextMsg.requestFocus();
			Log.d(TAG, "resultMsg : " + getString(R.string.ContentSendNoteOK));
		}
		else{
			ab.setMessage(msg);
		}
		ab.setPositiveButton(R.string.OK, null);
		ab.setCancelable(false);
		ab.show();
	}


	

}
