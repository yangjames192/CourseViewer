package com.example.courseviewer;


import model.ConnectionDetector;
import model.Terms;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class CourseFragment extends Fragment {
	public int prevTerm;
	public int curTerm;
	public int nextTerm;
	public int selectedTerm;
	public boolean isInternetPresent = false;
    
    // Connection detector class
    public ConnectionDetector cd;
	private AutoCompleteTextView subject;
	private static final String[] items = { "css" ,"lorem", "ipsum", "dolor", "sit",
			"amet", "consectetuer", "adipiscing", "elit", "morbi", "vel",
			"ligula", "vitae", "arcu", "aliquet", "mollis", "etiam", "vel",
			"erat", "placerat", "ante", "porttitor", "sodales", "pellentesque",
			"augue", "purus" };
	
	private RadioGroup radgroup;
	
	private Button b1;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_course,container, false);
		
		
		
		//b1 = (Button) v.findViewById(R.id.check);
		/*b1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
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
				String subject = ((EditText) v.findViewById(R.id.subject_text))
						.getText().toString();
				String catalog = ((EditText) v.findViewById(R.id.catalog_text))
						.getText().toString();

				Intent intent = new Intent(getActivity(),
						CourseListActivity.class);

				intent.putExtra("subject", subject);
				intent.putExtra("catalog", catalog);
				intent.putExtra("term", selectedTerm);

				// if course catalog number is not specified, use subject only
				boolean value = true;

				if (catalog.isEmpty())
					value = false;
				intent.putExtra("isCourseNum", value);
				if (subject.isEmpty()) {
					Toast.makeText(
							getActivity().getApplicationContext(),
							"Subject is not specified, Please Enter the Subject",
							Toast.LENGTH_LONG).show();
				} else {
					if (!isInternetPresent) {
						Toast.makeText(
								getActivity().getApplicationContext(),
								"Internet is not connected, Please Check your Internet Status",
								Toast.LENGTH_LONG).show();
					} else {
						startActivity(intent);
					}
				}
			}
		});*/
		
		return v;
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
		String subject = ((EditText) v.findViewById(R.id.subject_text))
				.getText().toString();
		String catalog = ((EditText) v.findViewById(R.id.catalog_text))
				.getText().toString();

		Intent intent = new Intent(getActivity(), CourseListActivity.class);

		intent.putExtra("subject", subject);
		intent.putExtra("catalog", catalog);
		intent.putExtra("term", selectedTerm);

		// if course catalog number is not specified, use subject only
		boolean value = true;

		if (catalog.isEmpty())
			value = false;
		intent.putExtra("isCourseNum", value);
		if (subject.isEmpty()) {
			Toast.makeText(getActivity().getApplicationContext(),
					"Subject is not specified, Please Enter the Subject",
					Toast.LENGTH_LONG).show();
		} else {
			if (!isInternetPresent) {
				Toast.makeText(
						getActivity().getApplicationContext(),
						"Internet is not connected, Please Check your Internet Status",
						Toast.LENGTH_LONG).show();
			} else {
				startActivity(intent);
			}
		}
	}

	class AddStringTask extends AsyncTask<Void, String, Void> {
		@Override
		protected Void doInBackground(Void... unused) {
			Terms terms = Terms.listTerms();
			
			prevTerm = terms.getPrevTerm();
			curTerm = terms.getCurTerm();
			nextTerm = terms.getNextTerm();
			
			return (null);
		}

		@Override
		protected void onProgressUpdate(String... item) {
			
		}

		@Override
		protected void onPostExecute(Void unused) {
			
		}
	}
	
}