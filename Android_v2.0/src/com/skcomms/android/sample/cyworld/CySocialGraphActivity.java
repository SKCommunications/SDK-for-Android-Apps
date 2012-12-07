package com.skcomms.android.sample.cyworld;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
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

public class CySocialGraphActivity extends Activity implements OnScrollListener{

	private final String TAG = getClass().getName();

	// 리스트뷰 선언
	private ListView listView;
	// 데이터를 연결할 Adapter
	private CySocialGraphAdapter cyAdapter;

	// 데이터를 담을 자료구조
	private ArrayList<CySocialItem> mRowList;
	private ArrayList<CySocialItem> mCySocialList;

	private String sTID = "";
	
	private boolean mLockListView;
	private LayoutInflater mInflater;
	
	private int displayIndex = 0;
	
	private ApiRequest req = null;
	
	private DrawableManager mDrawableDownloader;
	
	private View footerView = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		
		Intent intent = getIntent();

		if (intent != null) {
			Bundle bundle = intent.getBundleExtra(CyActivity.HOMEINFO);
			if(bundle != null) {
				sTID = bundle.getString(CyActivity.TARGETID);
				Log.d(TAG,"targetId : " + sTID);
			}
		}
		
		req = new ApiRequest(getApplicationContext());
		
		mDrawableDownloader = new DrawableManager();
		
		mLockListView = true;
		
		mRowList = new ArrayList<CySocialItem>();
		mCySocialList = new ArrayList<CySocialItem>();
		
		// 어댑터와 리스트뷰 초기화
		listView = (ListView) findViewById(android.R.id.list);
		cyAdapter = new CySocialGraphAdapter(this, mRowList);
		
		// 푸터를 등록합니다. setAdapter 이전에 해야 합니다. 
        mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        footerView = mInflater.inflate(R.layout.listview_footer, null);
        listView.addFooterView(footerView);
        
        // 스크롤 리스너를 등록합니다. onScroll에 추가구현을 해줍니다.
        listView.setOnScrollListener(this);
        listView.setAdapter(cyAdapter);
		
		new DataAsyncTask().execute();
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount){
		int count = totalItemCount - visibleItemCount;
		if (firstVisibleItem >= count && totalItemCount != 0 && mLockListView == false) {
			getItemsIndex(5);
		}	
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	}
	
	private void getItemsIndex(int size) {
		int index = 0;
		if (displayIndex >= mCySocialList.size()) {
			footerView.setVisibility(View.INVISIBLE);
			return;
		} else {
			footerView.setVisibility(View.VISIBLE);
			if (size > mCySocialList.size()) {
				index = mCySocialList.size();
			} else {
				index = displayIndex + size;
				if (index >= mCySocialList.size()) {
					index = mCySocialList.size();
				}
			}
			addItems(index);
		}
	}
	
	/**
	 * 임의의 방법으로 일촌 리스트를 추가합니다.
	 * @param size
	 */
	private void addItems(final int size){
		
		// 아이템을 추가하는 동안 중복 요청을 방지하기 위해 락을 걸어둡니다.
		mLockListView = true;
		
		final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
            	// 모든 데이터를 로드하여 적용하였다면 어댑터에 알리고 리스트뷰의 락을 해제합니다.
				cyAdapter.notifyDataSetChanged();
				mLockListView = false;
            }
        };

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
	        		StringBuffer tidList = new StringBuffer();

	        		for(int i = displayIndex; i < size ; i++){
						if (tidList.length() > 0) {
							tidList.append(",");
						}
						tidList.append(mCySocialList.get(i).getTID());
					}
	        		
	        		Log.d(TAG, "mCySocialList.size() : " + mCySocialList.size());
	        		Log.d(TAG, "count : " + displayIndex);
	        		Log.d(TAG, "iSize : " + size);
	        		Log.i(TAG, "tidList : " + tidList.toString());

	        		Map<String, String> params = new HashMap<String, String>();
	        		params.put("targetIds", tidList.toString());
	        		params.put("fieldNames", "thumbnailUrl");
	        		params.put("profileThumb", "1");

					String response = req.request(URLConfig.CYWORLD_CYS_GET_PROFILE, params, "POST");

					if (response != null) {
						
						String[] KEYLIST = {"thumbnailUrl"};
		        		XmlParser xmlparser = new XmlParser();
		        		ArrayList<HashMap<String, String>> dataSet = xmlparser.getparseData(response , "CyProfile" , KEYLIST);
						
		        		for (int i = 0; i < dataSet.size(); i++) {
							String thumbUrl = dataSet.get(i).get(KEYLIST[0]);
							Log.d(TAG, "thumbnailUrl[" + displayIndex + "]: " + thumbUrl);

							mCySocialList.get(displayIndex).setThumbnailUrl(thumbUrl);
							mRowList.add(mCySocialList.get(displayIndex));
							displayIndex += 1;
						}
		        		
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
                Message message = handler.obtainMessage(1, "");
                handler.sendMessage(message);
            }
        };
        thread.start();
		
		
	}
	
	private class CySocialGraphAdapter extends BaseAdapter {
		// 레이아웃 XML을 읽어들이기 위한 객체
		private LayoutInflater mInflater;
		private ArrayList<CySocialItem> mRowList;
		
		public class ViewHolder {
			TextView name;
			ImageView thumbnail;
		}
		
		public CySocialGraphAdapter(Context context, ArrayList<CySocialItem> rowList) {

			mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			mRowList = rowList;
		}

		// 보여지는 스타일을 자신이 만든 xml로 보이기 위한 구문
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.relation_list_row, null);
				
				holder = new ViewHolder();
				holder.thumbnail = (ImageView) convertView.findViewById(R.id.relation_thumbnail);
				holder.name = (TextView) convertView.findViewById(R.id.relation_name);
				
			} else {
				holder = (ViewHolder)convertView.getTag();
			}

			holder.name.setText(mRowList.get(position).getName());
			try {
				mDrawableDownloader.fetchDrawableOnThread(mRowList.get(position).getThumbnailUrl(), holder.thumbnail);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			convertView.setTag(holder);
			return convertView;
		}

		@Override
		public int getCount()
		{
			return mRowList.size();
		}

		@Override
		public Object getItem(int position)
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
			try {
				Map<String, String> params = new HashMap<String, String>();
				params.put("tid", sTID);

				String result = req.request(
						URLConfig.CYWORLD_CYS_GET_MYCYS_DETAIL, params, "POST");

				Log.d(TAG, "api result: " + result);

				String[] KEYLIST = {"did","didname","relationname","groupNo"};
        		XmlParser xmlparser = new XmlParser();
        		ArrayList<HashMap<String, String>> mRowList = xmlparser.getparseData(result , "OneDegEntity" , KEYLIST);
        		
        		Log.d(TAG, "childCount: " + mRowList.size());
        		
        		for(int i=0; i<mRowList.size(); i++){
        			String TID = mRowList.get(i).get(KEYLIST[0]);
					String Name = mRowList.get(i).get(KEYLIST[1]);
					CySocialItem cySocialData = new CySocialItem(getApplicationContext(), TID, Name);
					mCySocialList.add(cySocialData);
        		}

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
			getItemsIndex(10);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
	}
	
	public class CySocialItem {
		private String m_TID;
		private String m_Name;
		private String m_ThumbnailUrl;

		public CySocialItem(Context context, String p_TID, String p_Name) {
			m_TID = p_TID;
			m_Name = p_Name;
		}

		public String getTID() {
			return m_TID;
		}

		public String getName() {
			return m_Name;
		}

		public String getThumbnailUrl() {
			return m_ThumbnailUrl;
		}

		public void setThumbnailUrl(String p_ThumbnailUrl) {
			m_ThumbnailUrl = p_ThumbnailUrl;
		}
	}
}
