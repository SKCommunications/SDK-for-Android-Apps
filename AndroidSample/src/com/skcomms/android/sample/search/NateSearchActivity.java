package com.skcomms.android.sample.search;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.skcomms.android.sample.R;

public class NateSearchActivity extends ListActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listview);
		ArrayAdapter <CharSequence> menuAdapter = ArrayAdapter.createFromResource(
                this, R.array.content_search_menu_array, android.R.layout.simple_list_item_1);
		setListAdapter(menuAdapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Intent intent = new Intent();
		switch(position) {
		case 0:
			intent.setClassName(v.getContext(), "com.skcomms.android.sample.search.AutoCompleteActivity");
			startActivity(intent);
			break;
		case 1:
			intent.setClassName(v.getContext(), "com.skcomms.android.sample.search.WhyIssueActivity");
			startActivity(intent);
			break;
		default:
			break;
		}
	}


}
