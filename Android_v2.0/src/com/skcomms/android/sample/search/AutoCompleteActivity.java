package com.skcomms.android.sample.search;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.skcomms.android.sample.R;
import com.skcomms.android.sdk.ApiRequest;
import com.skcomms.android.sdk.common.SDKException;
import com.skcomms.android.sdk.common.URLConfig;

public class AutoCompleteActivity extends Activity {
	private final String TAG = getClass().getName();
	private AutoCompleteTextView tvAutoComplete;
	private ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.natesearch_autocomplete);
	}

	@Override
	protected void onStart() {
		super.onStart();
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
		tvAutoComplete = (AutoCompleteTextView) findViewById(R.id.natesearch_autocomplete);
		adapter.setNotifyOnChange(true);
		tvAutoComplete.setAdapter(adapter);
		
		// 텍스트입력 변경 리스너 등록.
		tvAutoComplete.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
				if (((AutoCompleteTextView) tvAutoComplete).isPerformingCompletion()) {
		            return;
		        }
				adapter.clear();
				GetNateAutoComplete task  = new GetNateAutoComplete();
				task.execute(null, null, null);
			}
		});

	}
	
	private class GetNateAutoComplete extends AsyncTask<Void, Void, ArrayList<String>> {

		@Override
		protected ArrayList<String> doInBackground(Void... args) {

			ArrayList<String> predictionsArr = new ArrayList<String>();

			// 자동완성 API 호출
	        ApiRequest req = new ApiRequest(getApplicationContext());
	        String response = null;
	        Map<String,String> params = new HashMap<String,String>();
			
	        try {
				params.put("q", tvAutoComplete.getText().toString());
				params.put("rp","nopa");
				params.put("rf","json");
				params.put("qe","utf8");
				params.put("re","utf8");
				
				response = req.request(URLConfig.NATESEARCH_AUTOCOMPLETE, params, "GET");
				JSONObject jsonResult = new JSONObject(response);
				JSONArray kwds = null;
				if (!jsonResult.isNull("kwds")) {
					kwds = (JSONArray) jsonResult.get("kwds");
					for (int i = 0; i < kwds.length(); i++) {
						predictionsArr.add(kwds.getJSONArray(i).getString(0));
					}
				}
				Log.d(TAG, "result: " + response);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (SDKException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
				
			return predictionsArr;
		}

		@Override
		protected void onPostExecute(ArrayList<String> result) {
			adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item);
			adapter.setNotifyOnChange(true);
			tvAutoComplete.setAdapter(adapter);

			for (String string : result) {
				adapter.add(string);
				adapter.notifyDataSetChanged();
			}
		}
	}
}
