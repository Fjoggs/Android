package no.hist.stud.vegardfj.oving4;

import com.actionbarsherlock.view.MenuItem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Fragment2 extends Fragment implements OnItemClickListener {
	private ListView listView;
	private String[] countries = {"Norge", "Danmark", "Sverige"};
	private ArrayAdapter<String> adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
    	adapter = new ArrayAdapter<String>(getActivity(), R.layout.listview_style_1, R.id.fragment2_listview_textview, countries);
        View view = inflater.inflate(R.layout.fragment2_layout, container, false);
        listView = (ListView) view.findViewById(R.id.fragment2_listview);
		listView.setOnItemClickListener(this);
		listView.setAdapter(adapter);
		return view;
    }
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
		// TODO Auto-generated method stub
		Log.d("Id", String.valueOf(id));
		Intent intent = new Intent("intent.country.index");
		intent.putExtra("countryIndex", id);
		LocalBroadcastManager.getInstance(getActivity()).sendBroadcast(intent);
	}
}

//public boolean onClickNext(MenuItem item){
//	if(getCurrentIndex() == 4){
//		setCurrentIndex(0);
//		imgView.setImageResource(R.drawable.flag_norway);
//		textView.setText(countryDesc[currentIndex]);
//	} else if (getCurrentIndex() == 0){
//		setCurrentIndex(1);
//		imgView.setImageResource(R.drawable.flag_denmark);
//		textView.setText(countryDesc[currentIndex]);
//	} else if (getCurrentIndex() == 1){
//		setCurrentIndex(2);
//		imgView.setImageResource(R.drawable.flag_sweden);
//		textView.setText(countryDesc[currentIndex]);
//	} else if (getCurrentIndex() == 2){
//		setCurrentIndex(0);
//		imgView.setImageResource(R.drawable.flag_norway);
//		textView.setText(countryDesc[currentIndex]);
//	}
//}
//public boolean onClickPrevious(MenuItem item){
//	if(getCurrentIndex() == 4){
//		setCurrentIndex(2);
//		imgView.setImageResource(R.drawable.flag_sweden);
//		textView.setText(countryDesc[currentIndex]);
//	} else if (getCurrentIndex() == 0){
//		setCurrentIndex(2);
//		imgView.setImageResource(R.drawable.flag_sweden);
//		textView.setText(countryDesc[currentIndex]);
//	} else if (getCurrentIndex() == 1){
//		setCurrentIndex(0);
//		imgView.setImageResource(R.drawable.flag_norway);
//		textView.setText(countryDesc[currentIndex]);
//	} else if (getCurrentIndex() == 2){
//		setCurrentIndex(1);
//		imgView.setImageResource(R.drawable.flag_denmark);
//		textView.setText(countryDesc[currentIndex]);
//	}
//}