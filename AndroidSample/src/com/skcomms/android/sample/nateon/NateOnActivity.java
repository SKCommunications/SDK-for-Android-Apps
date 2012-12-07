package com.skcomms.android.sample.nateon;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
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

public class NateOnActivity extends Activity implements OnScrollListener {

	private final String TAG = getClass().getName();
	public static final String NATEINFO = "nateInfo";
	public static final String NATEID = "nateId";
	String resultMsg = null;

	// 리스트뷰 선언
	private ListView listView;
	// 데이터를 연결할 Adapter
	private NOSocialGraphAdapter noAdapter;

	// 데이터를 담을 자료구조
	private ArrayList<NOSocialItem> mNOSocialList;

	private boolean mLockListView;
	private LayoutInflater mInflater;
	
	private ApiRequest apirequest = null;
	private int firstIndex = 0;
	private int lastIndex = 0;
	private int maxIndex = 0;
	
	private DrawableManager mDrawableDownloader;
	
	private View footerView = null;
	
	// API 호출결과를 담을 객체.
	private ArrayList<HashMap<String, String>> mRowList;
	private String[] KEYLIST = {"id", "name", "image_url"};


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);

		apirequest = new ApiRequest(getApplicationContext());
		
		mDrawableDownloader = new DrawableManager();
		
		mLockListView = true;
		
		mNOSocialList = new ArrayList<NOSocialItem>();
		
		//어댑터와 리스트뷰 동기화 
		listView = (ListView) findViewById(android.R.id.list);
		noAdapter = new NOSocialGraphAdapter(this, mNOSocialList); 

		// 푸터를 등록합니다. setAdapter 이전에 해야 합니다. 
        mInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        footerView = mInflater.inflate(R.layout.listview_footer, null);
        listView.addFooterView(footerView);
        
        // 스크롤 리스너를 등록합니다. onScroll에 추가구현을 해줍니다.
        listView.setOnScrollListener(this);
        listView.setAdapter(noAdapter);
        
        // 버디리스트 항목 클릭 리스너를 등록합니다.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	public void onItemClick(
        			AdapterView<?> parent, View view, int position, long id) {
        		Intent intent = new Intent();
        		Bundle bundle = new Bundle();
        		bundle.putString(NATEID, mNOSocialList.get(position).getNID());
        		intent.putExtra(NATEINFO, bundle);
        		
        		intent.setClassName(view.getContext(), "com.skcomms.android.sample.nateon.NateOnSendNoteActivity");
        		startActivity(intent);
        	}
		});
		
		new DataAsyncTask().execute();


	}
	

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		Log.d(TAG, "lastIndex: "+ lastIndex + ", maxIndex:" + maxIndex);
		int count = totalItemCount - visibleItemCount;
		// 스크롤할 Content 가 있으면 Async Job 을 수행합니다 
		if (firstVisibleItem >= count && totalItemCount != 0 && mLockListView == false && lastIndex <= maxIndex) {
			new DataAsyncTask().execute();
		}
		
		// 더이상 스크롤할 Content 가 없으면, Dialog로 없음을 알리고, Async Job 수행을 중지합니다.
		if(lastIndex != 0 && lastIndex >= maxIndex && mLockListView == false) {
			resultMsg = "NotFound";
			footerView.setVisibility(View.INVISIBLE);
			onDialogView(resultMsg);
			// Async Job 수행을 중지하기 위해서 Lock 을 겁니다.
			mLockListView = true;
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	}
	
	
	// 네이트온 버디리스트 정보를 담을 Class 입니다. 
	class NOSocialItem {

		private String m_NID;
		private String m_Name;
		private String m_ThumbnailUrl;

		public NOSocialItem(Context context, String p_NID, String p_Name, String p_ThumbnailUrl) {
			m_NID = p_NID;
			m_Name = p_Name;
			m_ThumbnailUrl = p_ThumbnailUrl;
		}

		public String getNID() {
			return m_NID;
		}

		public String getName() {
			return m_Name;
		}

		public String getThumbnailUrl() {
			return m_ThumbnailUrl;
		}

	}
	
	private class NOSocialGraphAdapter extends BaseAdapter {
		// 레이아웃 XML을 읽어들이기 위한 객체
		private LayoutInflater mInflater;
		private ArrayList<NOSocialItem> mRowList;
		
		public class ViewHolder {
			TextView name;
			ImageView thumbnail;
		}
		
		public NOSocialGraphAdapter(Context context, ArrayList<NOSocialItem> rowList) {
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
			// API 호출시 onScroll 에서 호출하는것을 방지하기 위해 lock 을 겁니다.
			mLockListView = true;
			try {
				firstIndex = lastIndex;
				lastIndex += 10;
				/* 네이트온 친구목록 API 를 호출 합니다 */
				Map<String, String> params = new HashMap<String, String>();
				params.put("range", firstIndex + "," + lastIndex);
				Log.d(TAG, "range: " + firstIndex + "~" + lastIndex);
				params.put("order", "name.asc");

				String result = apirequest.request(
						URLConfig.NATEON_GET_BUDDYINFOS, params, "POST");

				Log.d(TAG, "api result: " + result);

				/* API 호출결과 : 버디 총 카운트를 가져옵니다.(최초1회만 실행합니다) body/totalcount */
				if (firstIndex == 0) {
					ArrayList<HashMap<String, String>> mBody = null;
					String[] TOTAL = { "total_count" };
					try {
						XmlParser xmlparser = new XmlParser();
						mBody = xmlparser.getparseData(result, "body", TOTAL);
					} catch (Exception e) {
						Log.d(TAG, e.getMessage());
					}
					HashMap<String, String> dBody = mBody.get(0);
					maxIndex = Integer.parseInt(dBody.get(TOTAL[0]));
					Log.d(TAG, "total buddy count: " + maxIndex);
				}

				/* 버디리스트 파싱: 버디리스트가 없을때는 파싱하지 않습니다. */
				if ((lastIndex-10) <= maxIndex) {
					String KEY_BUDDY_ITEM = "buddy";
					try {
						XmlParser xmlparser = new XmlParser();
						mRowList = xmlparser.getparseData(result,
								KEY_BUDDY_ITEM, KEYLIST);
					} catch (Exception e) {
						Log.d(TAG, e.getMessage());
					}

					int childCount = mRowList.size();
					Log.d(TAG, "childCount: " + childCount);

					for (int i = 0; i < childCount; i++) {
						HashMap<String, String> data = mRowList.get(i);
						String NateId = data.get(KEYLIST[0]);
						String Name = data.get(KEYLIST[1]);
						String ProfileUrl = data.get(KEYLIST[2]);
						NOSocialItem noSocialData = new NOSocialItem(
								getApplicationContext(), NateId, Name,
								ProfileUrl);
						mNOSocialList.add(noSocialData);
					}
					// 작업이 완료되었음으로 lock 을 해제 합니다.
					mLockListView = false;

				} else {
					Log.d(TAG, "No more Buddy List");
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
			// 작업이 완료되었음으로 View 에 Data 가 변경되었음을 알립니다.
			noAdapter.notifyDataSetChanged();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
	}
	
	private void onDialogView(String msg) {
		Log.d(TAG, "resultMsg : " + msg);
		AlertDialog.Builder ab = new AlertDialog.Builder(this);
		if( msg == "NotFound" ){
			ab.setMessage(R.string.ContentNateOnBuddyOver);
			Log.d(TAG, "resultMsg : " + getString(R.string.ContentNateOnBuddyOver));
		}
		else{
			ab.setMessage(msg);
		}
		ab.setPositiveButton(R.string.OK, null);
		ab.setCancelable(false);
		ab.show();
	}

}
