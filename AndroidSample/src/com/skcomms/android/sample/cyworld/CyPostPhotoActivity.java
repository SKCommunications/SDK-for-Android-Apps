package com.skcomms.android.sample.cyworld;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.skcomms.android.sample.R;
import com.skcomms.android.sample.util.XmlParser;
import com.skcomms.android.sdk.ApiRequest;
import com.skcomms.android.sdk.RequestListener;
import com.skcomms.android.sdk.common.SDKException;
import com.skcomms.android.sdk.common.URLConfig;

public class CyPostPhotoActivity extends Activity implements OnClickListener{

	private final String TAG = getClass().getName();
	
	private static final int REQ_PHOTO = 0x00001;
	private Button buttonPhotoChoice = null;
	private Button buttonPhotoUpload = null;
	private ImageView imageViewPhoto = null;
	private EditText editTextTitle = null;
	private EditText editTextContent = null;

	private String sFullPath = null;
	private String photoUrl = null;
	private ProgressDialog mSpinner;
	
	private Map<String,String> folderMap = new HashMap<String,String>();
	
	private String iContentType = "4";
	
	private Handler mHandler;
	
	private Spinner spinnerFolder = null;
	private Spinner spinnerContentType = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cy_post_photo);
		
		mHandler = new Handler();
		
		buttonPhotoChoice = (Button) findViewById(R.id.button_photo_choice);
		buttonPhotoChoice.setOnClickListener(this);
		buttonPhotoUpload = (Button) findViewById(R.id.button_dataset_upload);
		buttonPhotoUpload.setOnClickListener(this);
		
		imageViewPhoto = (ImageView) findViewById(R.id.img_photo);
		editTextTitle = (EditText) findViewById(R.id.editView_input_title);
		editTextContent = (EditText) findViewById(R.id.editView_input_content);
		
		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyy년 MM월 dd일의 일상", Locale.KOREA );
		Date currentTime = new Date ( );
		String dTime = formatter.format ( currentTime );
		editTextTitle.setText(dTime);
		editTextContent.requestFocus();
	
		mSpinner = new ProgressDialog(this);
        mSpinner.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mSpinner.setMessage("Updating...");
        mSpinner.show();
        
        spinnerFolder = (Spinner) findViewById(R.id.spinner_folder_list);
        
        spinnerContentType = (Spinner) findViewById(R.id.spinner_content_type);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.content_type_array, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinnerContentType.setAdapter(adapter);
	    spinnerContentType.setPromptId(R.string.ContentType);
        
        setFolderList();
	}
	
	public void setFolderList() {
		Map<String,String> params = new HashMap<String,String>();
		params.put("menuType", "2");
		ApiRequest req = new ApiRequest(getApplicationContext());
		try{
			req.request(URLConfig.MINIHOME_GET_FOLDERLIST, params, ApiRequest.HTTP_POST , new FolderReqListener());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case REQ_PHOTO:
				if (resultCode == RESULT_OK) {
					Uri imgUri = data.getData();
					try {
						Bitmap photoBmp = Images.Media.getBitmap(
								getContentResolver(), imgUri);
						if (photoBmp != null) {
							imageViewPhoto.setImageBitmap(photoBmp);
							sFullPath = getRealPathFromURI(imgUri);
							Log.d(TAG, "sFullPath: " + sFullPath);
						}
					} catch (Exception e) {
					}
				}
				break;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_photo_choice: 
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
			intent.setType("image/*");
			Intent chosenIntent = Intent.createChooser(intent, null);
			startActivityForResult(chosenIntent, REQ_PHOTO);
			break;
		case R.id.button_dataset_upload: 
			Log.d(TAG, "POST Image");
	        mSpinner.show();
			new DataAsyncTask().execute(null, null, null);
			break;
		}
	}
	
	private String getRealPathFromURI(Uri contentUri) {
		String result = null;
		if (contentUri != null) {
			try {
				String[] proj = { MediaStore.Images.Media.DATA };
				Cursor cursor = managedQuery(contentUri, proj, // Which columns
						null, // WHERE clause; which rows to return (all rows)
						null, // WHERE clause selection arguments (none)
						null); // Order-by clause (ascending by name)
				int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				cursor.moveToFirst();

				result = cursor.getString(column_index);
			} catch (Exception e) {
				result = null;
			}
		}

		return result;
	}
			   
	private class DataAsyncTask extends AsyncTask<Void, Void, Void> {
		File file = null;
		String response = "";
		String resultMsg = null;
		
		@Override
		protected Void doInBackground(Void... Void) {
			try{
				ApiRequest req = new ApiRequest(getApplicationContext());
				file = new File(sFullPath);
				Log.d(TAG, "file: " + file.toURI().toString());

				response = req.request(URLConfig.MINIHOME_UPLOAD_PHOTO, file, ApiRequest.HTTP_POST);
				Log.d(TAG, "strResponse: " + response);
				
				String[] KEYLIST = {"AttachNm"};
        		XmlParser xmlparser = new XmlParser();
        		ArrayList<HashMap<String, String>> mRowList = xmlparser.getparseData(response , "Data" , KEYLIST);
        		
        		for(int i=0; i<mRowList.size(); i++){
        			photoUrl = mRowList.get(i).get(KEYLIST[0]);
        		}
				
				Log.d(TAG, "photoUrl: " + photoUrl);

				Editable title = editTextTitle.getText();
				Editable content = editTextContent.getText();
				String folder = spinnerFolder.getSelectedItem().toString();
				int item = spinnerContentType.getSelectedItemPosition();
				switch(item){
				case 0:
					iContentType = "0";
					break;
				case 1:
					iContentType = "1";
					break;
				case 2:
				default:
					iContentType = "4";
					break;
				}
				
				if (photoUrl != null) {
					
					Map<String,String> params = new HashMap<String,String>();
					params.put("title", title.toString());
					params.put("content", content.toString());
					params.put("photoUrls", photoUrl);
					params.put("itemOpen", iContentType);
					params.put("isScrapOpen", "false");
					params.put("isSearchOpen", "false");
					params.put("folderNo", folderMap.get(folder));
					
					response = req.request(
							URLConfig.MINIHOME_CREATE_ALBUM_ITEM, params,
							"POST");
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
			ab.setMessage(R.string.ContentPostOK);
			Log.d(TAG, "resultMsg : " + getString(R.string.ContentPostOK));
		}
		else{
			ab.setMessage(msg);
		}
		ab.setPositiveButton(R.string.OK, null);
		ab.setCancelable(false);
		ab.show();
	}
	
    private final class FolderReqListener implements RequestListener {

		@Override
		public void onComplete(final String response) {
			mSpinner.dismiss();
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                	try{
                		String[] KEYLIST = {"folderName", "folderNo"};
                		XmlParser xmlparser = new XmlParser();
                		ArrayList<HashMap<String, String>> mRowList = xmlparser.getparseData(response , "Folder" , KEYLIST);
                		
                		List<String> list = new ArrayList<String>();
        				
        				for(int i=0; i<mRowList.size(); i++){
        					String sFolderName = mRowList.get(i).get(KEYLIST[0]);
        					String sFolderNumber = mRowList.get(i).get(KEYLIST[1]);
							folderMap.put(sFolderName, sFolderNumber);
							list.add(sFolderName);
        				}
                		
        				ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
        						android.R.layout.simple_spinner_item, list);
        			    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        			    spinnerFolder.setAdapter(adapter);
        			    spinnerFolder.setPromptId(R.string.FolderSelect);
        			}catch(Exception e){
        				e.printStackTrace();
        			}
                }
            });
		}

		@Override
		public void onSDKException(SDKException e) {
			mSpinner.dismiss();
		}

		@Override
		public void onMalformedURLException(MalformedURLException ex) {
			mSpinner.dismiss();
		}

		@Override
		public void onIOException(IOException ex) {
			mSpinner.dismiss();
		}
    }
}
