package com.example.courseviewer;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import model.*;

public class CourseListDetailActivity extends Activity {
	
	public ListViewDetailAdapter adapter;
	public String subject;
	public String catalog;
	public boolean isCatalogNum;
	public int term;
	private ArrayList<String> model=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_course_list);
		
		Intent intent = getIntent(); // gets the previously created intent
		subject = intent.getStringExtra("subject"); 
		catalog = intent.getStringExtra("catalog");
		term = intent.getIntExtra("term", 0);
		
		isCatalogNum = intent.getBooleanExtra("isCourseNum", false);
		model=new ArrayList<String>();
		new AddStringTask().execute();
		String displayStr = subject + " " + catalog;
		TextView textView=  (TextView)findViewById(R.id.textView1);
		textView.setText(displayStr);
		
		adapter = new ListViewDetailAdapter(this, model);
		ListView list = (ListView)findViewById(R.id.listView);
		list.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
	
	class AddStringTask extends AsyncTask<Void, String, Void> {
		@Override
		protected Void doInBackground(Void... unused) {
			List<CourseInfo> courseInfo = CourseInfo.coursesFactory(subject, catalog, term, isCatalogNum);
			
			for (CourseInfo item : courseInfo) {
				String str = item.courseInfo2();
				publishProgress(str);
				
			}
			return (null);
		}

		@Override
		protected void onProgressUpdate(String... item) {
			adapter.add(item[0]);
		}

		@Override
		protected void onPostExecute(Void unused) {
			
		}
	}
}
