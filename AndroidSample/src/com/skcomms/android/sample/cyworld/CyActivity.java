package com.skcomms.android.sample.cyworld;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.skcomms.android.sample.R;
import com.skcomms.android.sdk.ApiRequest;
import com.skcomms.android.sdk.common.URLConfig;

public class CyActivity extends ListActivity {

	private final String TAG = getClass().getName();
	
	public static final String HOMEINFO = "homeinfo";
	public static final String TARGETID = "targetId";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		
		ArrayAdapter <CharSequence> menuAdapter = ArrayAdapter.createFromResource(
                this, R.array.content_cyworld_menu_array, android.R.layout.simple_list_item_1);
		setListAdapter(menuAdapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
			
		String targetID = "";
		try{
			ApiRequest req = new ApiRequest(getApplicationContext());
			Map<String,String> paramsMap = new HashMap<String,String>();
			paramsMap.put("output", "json");
			String response = req.request(URLConfig.MINIHOME_GET_HOMEINFO, paramsMap );
			JSONObject jsonResult = new JSONObject(response);
			targetID = jsonResult.getString("id");
			Log.d(TAG,"targetId : " + targetID);
		}catch(Exception e){
			Log.e(TAG, "", e);
		}
		
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString(TARGETID, targetID);
		intent.putExtra(HOMEINFO, bundle);
        switch (position) {
        case 0:
	        intent.setClassName(v.getContext(), "com.skcomms.android.sample.cyworld.CySocialGraphActivity");
	     	break;
        case 1:     
	        intent.setClassName(v.getContext(), "com.skcomms.android.sample.cyworld.CyPostPhotoActivity");
        	break;
        case 2:
        	intent.setClassName(v.getContext(), "com.skcomms.android.sample.cyworld.CyWriteDiaryActivity");
        	break;
        default:
        	break;
        }
        
		if (intent != null) {
			startActivity(intent);
		}
	}
}
