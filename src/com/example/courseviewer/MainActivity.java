package com.example.courseviewer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import model.ConnectionDetector;
import model.CourseInfo;
import model.Subject;
import model.Terms;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity  {
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	
	public int prevTerm;
	public int curTerm;
	public int nextTerm;
	public int selectedTerm;
	public boolean isInternetPresent = false;
	public ArrayAdapter<String> adapter;
	
	// Connection detector class
	public ConnectionDetector cd;
	private AutoCompleteTextView subject;
	private ArrayList<String> items = null;

	private RadioGroup radgroup;

	private Button b1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_course);
		
		cd = new ConnectionDetector(getApplicationContext());
		isInternetPresent = cd.isConnectingToInternet();
		if (isInternetPresent) {
			new AddStringTask().execute();
		} else {
			// not connected, make a message
			Toast.makeText(
					getApplicationContext(),
					"Internet is not connected, Please Check your Internet Status",
					Toast.LENGTH_LONG).show();
		}
		
		items = new ArrayList<String>();
		
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, items);
		subject = (AutoCompleteTextView) findViewById(R.id.subject_text);
		subject.setAdapter(adapter);
		
		radgroup = (RadioGroup) findViewById(R.id.radioGroup);

	}

	public void onCheck(View v) {
		// get which term is selected
		for (int i = 0; i < radgroup.getChildCount(); i++) {
			RadioButton rd = (RadioButton) radgroup.getChildAt(i);

			if (rd.isChecked()) {
				String txt = rd.getText().toString();

				if (txt.equals("Previous Term")) {
					selectedTerm = prevTerm;
				} else if (txt.equals("Current Term")) {
					selectedTerm = curTerm;
				} else if (txt.equals("Next Term")) {
					selectedTerm = nextTerm;
				}
				break;
			}
		}
		// get subject and catalog
		String subject = ((EditText) findViewById(R.id.subject_text)).getText()
				.toString();
		String catalog = ((EditText) findViewById(R.id.catalog_text)).getText()
				.toString();

		Intent intent = new Intent(this, CourseListActivity.class);

		intent.putExtra("subject", subject);
		intent.putExtra("catalog", catalog);
		intent.putExtra("term", selectedTerm);

		// if course catalog number is not specified, use subject only
		boolean value = true;

		if (catalog.isEmpty())
			value = false;
		intent.putExtra("isCourseNum", value);
		if (subject.isEmpty()) {
			Toast.makeText(getApplicationContext(),
					"Subject is not specified, Please Enter the Subject",
					Toast.LENGTH_LONG).show();
		} else {
			if (!isInternetPresent) {
				Toast.makeText(
						getApplicationContext(),
						"Internet is not connected, Please Check your Internet Status",
						Toast.LENGTH_LONG).show();
			} else {
				startActivity(intent);
			}
		}
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
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

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
			Terms terms = Terms.listTerms();
			List<String> st = Subject.listSubjects();
			
			prevTerm = terms.getPrevTerm();
			curTerm = terms.getCurTerm();
			nextTerm = terms.getNextTerm();
			
			for (String item : st) {
					publishProgress(item);
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
