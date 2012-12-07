package com.skcomms.android.sample.explorer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.skcomms.android.sample.R;
import com.skcomms.android.sdk.ApiRequest;
import com.skcomms.android.sdk.common.SDKException;

public class ExplorerActivity extends Activity implements OnClickListener {

	private final String TAG = getClass().getName();
	private String[] endPointURL = null;
	private String[] endPointParam = null;

	private ArrayAdapter<CharSequence> adapterEnpoint;
	private Spinner spin = null;

	private Button btnQueryString = null;
	private Button btnExplorerSubmin = null;

	private EditText tvEndPointURL = null;
	private EditText etKey = null;
	private EditText etValue = null;
	private TextView tvResult = null;

	private Dialog dialog = null;
	private ProgressDialog mSpinner;
	private RadioGroup radioGroup = null;
	private RadioButton radioPost = null;
	private RadioButton radioGet = null;

	private ListView paramListView = null;
	private View divider = null;
	private ParamListAdapter adapter = null;
	
	private int lastPosition = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.apiexplorer);
		spin = (Spinner) findViewById(R.id.apispinner);
		tvEndPointURL = (EditText) findViewById(R.id.explorer_endpointurl);
		tvResult = (TextView) findViewById(R.id.txtData);
		btnQueryString = (Button) findViewById(R.id.querystring);
		btnExplorerSubmin = (Button) findViewById(R.id.explorer_sumit);
		radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
		radioPost = (RadioButton) findViewById(R.id.radioBtnPost);
		radioGet = (RadioButton) findViewById(R.id.radioBtnGet);

		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		endPointURL = getResources().getStringArray(R.array.content_endpoint_url_array);
		endPointParam = getResources().getStringArray(R.array.content_endpoint_url_array_param);

		spin.setPromptId(R.string.Explorer_endpointHint);

		adapterEnpoint = ArrayAdapter.createFromResource(this,
				R.array.content_endpoint_name_array,
				android.R.layout.simple_spinner_item);

		adapterEnpoint.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spin.setAdapter(adapterEnpoint);
		spin.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				String endpointUrl = "";
				String method = "post";
				StringTokenizer tokenizer = new StringTokenizer(endPointURL[position], "&");
				if (tokenizer.hasMoreTokens()) {
					method = tokenizer.nextToken();
					endpointUrl = tokenizer.nextToken();
					Log.d(TAG, "method : " + method);
					Log.d(TAG, "endpointUrl : " + endpointUrl);
				}
				if ("post".equals(method)) {
					radioPost.setChecked(true);
				} else {
					radioGet.setChecked(true);
				}
				tvEndPointURL.setText(endpointUrl);
			}
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});
		
		btnQueryString.setOnClickListener(this);
		btnExplorerSubmin.setOnClickListener(this);
	}

	private void initDialogQueryParam() {

		if (dialog == null) {
			dialog = new Dialog(ExplorerActivity.this);
			dialog.setContentView(R.layout.querystring_list);
			dialog.setTitle("Query Param Lists");

			dialog.setCancelable(true);
			dialog.setCanceledOnTouchOutside(true);

			List<QueryStringParam> paramList = new ArrayList<QueryStringParam>();

			paramListView = (ListView) dialog.findViewById(android.R.id.list);
			adapter = new ParamListAdapter(ExplorerActivity.this, R.layout.querystring_list_row, paramList);

			paramListView.setAdapter(adapter);

			ImageButton btnParamAdd = (ImageButton) dialog.findViewById(R.id.param_add);
			Button btnParamOK = (Button) dialog.findViewById(R.id.querystring_ok);
			etKey = (EditText) dialog.findViewById(R.id.param_key_add);
			etValue = (EditText) dialog.findViewById(R.id.param_value_add);

			btnParamAdd.setOnClickListener(this);
			btnParamOK.setOnClickListener(this);

			divider = (View) dialog.findViewById(R.id.param_divider);
		} else {
			etKey.setText("");
			etValue.setText("");
		}
	}
	
	private void addItem(String key, String value) {
		QueryStringParam param = new QueryStringParam(key, value);
		adapter.add(param);
		adapter.notifyDataSetChanged();
		etKey.setText("");
		etValue.setText("");
		if (adapter.getCount() > 0) {
			divider.setVisibility(View.VISIBLE);
		}
	}

	public void onQueryParamTokenizer() {
		
		int selectPosition = spin.getSelectedItemPosition();
		
		if (adapter != null && lastPosition != selectPosition) {
			
			lastPosition = selectPosition;
			
			String key = "";
			String value = "";
			
			try{
				String params = endPointParam[selectPosition];
				
				Log.d(TAG, "url : " + endPointURL[selectPosition] );
				Log.d(TAG, "param : " + endPointParam[selectPosition] );

				for (String elements : params.trim().split("&")) {
					
					StringTokenizer tokenizer = new StringTokenizer(elements, "=");
					while (tokenizer.hasMoreTokens()) {
						key = tokenizer.nextToken();
						try {
							value = tokenizer.nextToken();
						} catch (NoSuchElementException e) {
							value = "";
						}
						Log.d(TAG, "key : " + key );
						Log.d(TAG, "value : " + value );
						addItem(key,value);
					}
				}
			} catch (NoSuchElementException e) {
			}
		}
	}
	

	@Override
	public void onClick(View v) {
		int selectPosition = 0;
		switch (v.getId()) {
		case R.id.param_add:
			String key = etKey.getText().toString();
			String value = etValue.getText().toString();
			if (key.length() > 0) {
				addItem(key, value);
			}
			break;
		case R.id.querystring_ok:
			dialog.dismiss();
			break;
		case R.id.querystring:
			selectPosition = spin.getSelectedItemPosition();
			if (lastPosition != selectPosition) {
				dialog = null;
			}
			initDialogQueryParam();
			onQueryParamTokenizer();
			dialog.show();
			break;
		case R.id.explorer_sumit:
			if (tvEndPointURL.getText().toString().length() > 0) {
				
				selectPosition = spin.getSelectedItemPosition();
				if (lastPosition != selectPosition) {
					dialog = null;
					initDialogQueryParam();
					onQueryParamTokenizer();
				}
				
				tvResult.setText("");
				RequestData request = new RequestData();

				int checkedRadioButton = radioGroup.getCheckedRadioButtonId();

				request.setMethod(checkedRadioButton);
				request.setEndpoint(tvEndPointURL.getText().toString());

				HashMap<String, String> queryMap = new HashMap<String, String>();
				if (adapter != null) {
					int index = adapter.getCount();
					for (int position = 0; position < index; position++) {
						queryMap.put(adapter.getItem(position).getKey(), adapter.getItem(position).getValue());
					}
					Log.d(TAG, "queryMap.toString() : " + queryMap.toString());
				}
				request.setQuerystring(queryMap);
				ExplorerTask task = new ExplorerTask();
				task.execute(request, null, null);

				mSpinner = new ProgressDialog(this);
				mSpinner.requestWindowFeature(Window.FEATURE_NO_TITLE);
				mSpinner.setMessage("Loading...");
				mSpinner.show();
			}

			break;
		}
	}

	public class QueryStringParam {
		private String key = "";
		private String value = "";

		public QueryStringParam(String key, String value) {
			this.key = key;
			this.value = value;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
	}

	public class ParamListAdapter extends ArrayAdapter<QueryStringParam> {

		public class ViewHolder {
			QueryStringParam queryParam;
			TextView key;
			TextView value;
			ImageButton btnRemove;
		}

		private List<QueryStringParam> items;
		private int layoutResourceId;
		private Context context;

		public ParamListAdapter(Context context, int layoutResourceId, List<QueryStringParam> items) {
			super(context, layoutResourceId, items);
			this.layoutResourceId = layoutResourceId;
			this.context = context;
			this.items = items;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;

			if (convertView == null) {
				LayoutInflater inflater = ((Activity) context)
						.getLayoutInflater();
				convertView = inflater.inflate(layoutResourceId, parent, false);

				holder = new ViewHolder();
				holder.queryParam = items.get(position);
				holder.btnRemove = (ImageButton) convertView.findViewById(R.id.remove);
				holder.btnRemove.setTag(holder.queryParam);

				holder.key = (EditText) convertView.findViewById(R.id.param_key);
				holder.value = (EditText) convertView.findViewById(R.id.param_value);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.btnRemove.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					adapter.remove(items.get(position));
					adapter.notifyDataSetChanged();

					if (adapter.getCount() < 1) {
						divider.setVisibility(View.INVISIBLE);
					}
				}
			});
			
			holder.key.setOnFocusChangeListener(new OnFocusChangeListener() {
				public void onFocusChange(View v, boolean hasFocus) {
					if (!hasFocus){
						final EditText Caption = (EditText) v;
						items.get(position).key = Caption.getText().toString();
					}
				}
			});
			
			holder.value.setOnFocusChangeListener(new OnFocusChangeListener() {
				public void onFocusChange(View v, boolean hasFocus) {
					if (!hasFocus){
						final EditText Caption = (EditText) v;
						items.get(position).value = Caption.getText().toString();
					}
				}
			});
			
			holder.key.setText(items.get(position).getKey());
			holder.value.setText(items.get(position).getValue());

			convertView.setTag(holder);

			return convertView;
		}
	}

	private class RequestData {
		String endpoint;
		int method;
		HashMap<String, String> querystring;

		public String getEndpoint() {
			return endpoint;
		}
		public void setEndpoint(String endpoint) {
			this.endpoint = endpoint;
		}
		public int getMethod() {
			return method;
		}
		public void setMethod(int method) {
			this.method = method;
		}
		public HashMap<String, String> getQuerystring() {
			return querystring;
		}
		public void setQuerystring(HashMap<String, String> querystring) {
			this.querystring = querystring;
		}
	}

	private class ExplorerTask extends AsyncTask<RequestData, Void, String> {

		@Override
		protected String doInBackground(RequestData... requestData) {

			String result = "";
			String method = "";
			try {
				ApiRequest req = new ApiRequest(getApplicationContext());

				switch (requestData[0].getMethod()) {
				case R.id.radioBtnGet:
					method = ApiRequest.HTTP_GET;
					break;
				case R.id.radioBtnPost:
				default:
					method = ApiRequest.HTTP_POST;
					break;
				}
				result = req.request(requestData[0].getEndpoint(), requestData[0].getQuerystring(), method);
			} catch (MalformedURLException e) {
				result = e.getMessage();
			} catch (SDKException e) {
				result = e.getMessage();
			} catch (IOException e) {
				result = e.getMessage();
			} catch (Exception e) {
				result = e.getMessage();
			}
			return result;
		}
		@Override
		protected void onPostExecute(String result) {
			mSpinner.dismiss();
			tvResult.setText(result);
		}
	}
}
