package no.hist.stud.vegardfj.oving4;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

public class Fragment1 extends Fragment {
	private TextView textView;
	private ImageView imgView;
	private final int INDEX_OF_NORWAY = 0;
	private final int INDEX_OF_DENMARK = 1;
	private final int INDEX_OF_SWEDEN = 2;
	private int currentIndex = 4;
	private final String[] countryDesc = { "Norge er bra", "Danmark er dritt",
			"Sverige er helt ok" };

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment1_layout, container,
				false);
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("CountryChanger.country.index");
		intentFilter.addAction("Fragment2.country.index");
		LocalBroadcastManager.getInstance(getActivity()).registerReceiver(
				messageReceiver, intentFilter);		
		textView = (TextView) view.findViewById(R.id.fragment1_textview);
		imgView = (ImageView) view.findViewById(R.id.fragment1_large_imageview);
		imgView.setScaleType(ScaleType.FIT_XY);
		return view;
	}

	private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if(intent.getAction().equalsIgnoreCase("Fragment2.country.index")){				
				Log.d("Fragment2", "Er du her?");
				int listIndex = (int) intent.getLongExtra("Fragment2_countryIndex",	0);
				if (listIndex == INDEX_OF_NORWAY) {
					imgView.setImageResource(R.drawable.flag_norway);
					textView.setText(countryDesc[listIndex]);
				} else if (listIndex == INDEX_OF_DENMARK) {
					imgView.setImageResource(R.drawable.flag_denmark);
					textView.setText(countryDesc[listIndex]);
				} else if (listIndex == INDEX_OF_SWEDEN) {
					imgView.setImageResource(R.drawable.flag_sweden);
					textView.setText(countryDesc[listIndex]);
				}
				
			} else if(intent.getAction().equalsIgnoreCase("CountryChanger.country.index")) {				
				Log.d("CountryChanger", "Er du her?");
				int listIndex = intent.getIntExtra("CountryChanger_countryIndex", 0);
				if (listIndex == INDEX_OF_NORWAY) {
					imgView.setImageResource(R.drawable.flag_norway);
					textView.setText(countryDesc[listIndex]);
				} else if (listIndex == INDEX_OF_DENMARK) {
					imgView.setImageResource(R.drawable.flag_denmark);
					textView.setText(countryDesc[listIndex]);
				} else if (listIndex == INDEX_OF_SWEDEN) {
					imgView.setImageResource(R.drawable.flag_sweden);
					textView.setText(countryDesc[listIndex]);
				}
			}
		}
	};

	public void setIntentIndex() {
		Intent intent = new Intent("Fragment1.country.index");
		intent.putExtra("Fragment1_currentIndex", currentIndex);
		LocalBroadcastManager.getInstance(getActivity()).sendBroadcastSync(
				intent);
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}
}
