/**
 * @desc API 호출결과를 단순히 보여주는 View Sample 입니다. API응답포맷에 따라 XML/JSON 결과를 화면에 그래도 보여줍니다 
 * @author sk
 * @date 2012.10.17
 */
package com.skcomms.android.sample.search;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.skcomms.android.sample.R;
import com.skcomms.android.sample.util.DrawableManager;
import com.skcomms.android.sample.util.XmlParser;
import com.skcomms.android.sdk.ApiRequest;
import com.skcomms.android.sdk.common.SDKException;
import com.skcomms.android.sdk.common.URLConfig;

public class WhyIssueActivity extends Activity implements OnItemClickListener {

	private final String TAG = getClass().getName();
	// 리스트뷰 선언
	private ListView listView;
	// 데이터를 연결할 Adapter
	private SearchAdapter searchAdapter;
	// 데이터를 담을 자료구조
	private ArrayList<HashMap<String, String>> mRowList;
	
	private DrawableManager mDrawableDownloader;
	
	private String[] KEYLIST = { "title", "keyword", "issue_date", "url", "image_url" };
	
	private ProgressDialog mSpinner;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		
		mSpinner = new ProgressDialog(this);
        mSpinner.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mSpinner.setMessage("Loading...");
        mSpinner.show();
		
		// 어댑터와 리스트뷰 초기화
        mRowList = new ArrayList<HashMap<String, String>>();
		listView = (ListView) findViewById(android.R.id.list);
		mDrawableDownloader = new DrawableManager();
		
		DataAsyncTask task = new DataAsyncTask();
		task.execute(null, null, null);
		
		listView.setOnItemClickListener(this);
	}
	
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) 
	 {
		final HashMap<String, String> data = searchAdapter.getItem(position);
		AlertDialog.Builder alert = new AlertDialog.Builder(this);
        WebView webview = new WebView(getApplicationContext());
        webview.loadUrl(data.get(KEYLIST[3]));
        webview.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                view.loadUrl(url);
                return true;
            }
        });
        alert.setTitle(data.get(KEYLIST[0]));
        alert.setView(webview);
        alert.show();
	}
	
	private class SearchAdapter extends BaseAdapter {
		// 레이아웃 XML을 읽어들이기 위한 객체
		private LayoutInflater mInflater;
		private ArrayList<HashMap<String, String>> mRowList;
		
		public SearchAdapter(Context context, ArrayList<HashMap<String, String>> rowList) {

			mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			mRowList = rowList;
		}

		// 보여지는 스타일을 자신이 만든 xml로 보이기 위한 구문
		@Override
		public View getView(int position, View v, ViewGroup parent) {
			View view = null;
			
			Log.i(TAG, "position : " + position);
			
			if (v == null) {
				view = mInflater.inflate(R.layout.why_issue_list_row, null);
				
			} else {
				view = v;
			}
			final HashMap<String, String> data = getItem(position);

			if (data != null) {
				TextView issue_rank = (TextView) view.findViewById(R.id.issue_rank);
				issue_rank.setText(Integer.toString(position + 1));
				TextView issue_title = (TextView) view.findViewById(R.id.issue_title);
				issue_title.setText(Html.fromHtml("<a href='#'>"+data.get(KEYLIST[0])+"</a>"));
				
				try {
					ImageView imageView = (ImageView) view.findViewById(R.id.issue_image);
					if(data.get(KEYLIST[4]) == null || data.get(KEYLIST[4]) == "") {
						mDrawableDownloader.fetchDrawableOnThread("http://img.nate.com/search/2009/v1/ico/ico_why2.gif", imageView);
					}else {
						mDrawableDownloader.fetchDrawableOnThread(data.get(KEYLIST[4]), imageView);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return view;
		}

		@Override
		public int getCount()
		{
			return mRowList.size();
		}

		@Override
		public HashMap<String, String> getItem(int position)
		{
			return mRowList.get(position);
		}

		@Override
		public long getItemId(int position)
		{
			return position;
		}
	}
	
	private class DataAsyncTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... Void) {
			ApiRequest req = new ApiRequest(getApplicationContext());
			Map<String,String> params = new HashMap<String,String>();
			ArrayList<HashMap<String, String>> result = null;
			try {
				params.put("page","1");
				params.put("page_size","10");
				params.put("list_type","D");
				//params.put("key","20121128");
				
				String response = req.request(URLConfig.NATESEARCH_SEARCH_HOTISSUE, params, "GET");
				
				if( response != null) {
					String KEY_ISSUE_ITEM = "why_issue_item";
					XmlParser xmlparser = new XmlParser();
					result = xmlparser.getparseData(response , KEY_ISSUE_ITEM , KEYLIST);

					Log.d(TAG, "result.size() : " + result.size());
					
					for (int index = 0; index < result.size(); index++) {
						mRowList.add(result.get(index));
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (SDKException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onCancelled() {
		}

		@Override
		protected void onPostExecute(Void result) {
			
			Handler mHandler = new Handler();
			mHandler.post(new Runnable() {
	            @Override
	            public void run() {
	            	searchAdapter = new SearchAdapter(getApplicationContext(), mRowList);
	    			listView.setAdapter(searchAdapter);
	    			searchAdapter.notifyDataSetChanged();
	    			mSpinner.dismiss();	
	            }
	        });
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
	}
}
