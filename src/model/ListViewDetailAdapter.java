package model;

import java.util.ArrayList;

import com.example.courseviewer.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class ListViewDetailAdapter extends ArrayAdapter<String> {
	ArrayList<String> model;
	Context context;

	public ListViewDetailAdapter(Context context, ArrayList<String> resource) {
		super(context, R.layout.simple_row_detail, resource);
		// TODO Auto-generated constructor stub
		this.context = context;
		this.model = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		convertView = inflater.inflate(R.layout.simple_row_detail, parent, false);
		TextView name = (TextView) convertView.findViewById(R.id.simple_row);
		CheckBox cb = (CheckBox) convertView.findViewById(R.id.checkBox1);
		name.setText(model.get(position));
		/*if (model[position].getValue() == 1)
			cb.setChecked(true);
		else
			cb.setChecked(false);*/
		return convertView;
	}
}