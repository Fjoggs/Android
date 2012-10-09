package no.hist.stud.vegardfj.oving4;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class CountryChanger extends SherlockFragmentActivity {
	private int index = 4;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		LocalBroadcastManager.getInstance(getApplicationContext())
				.registerReceiver(messageReceiver,
						new IntentFilter("Fragment2.country.index"));
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.activity_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent("CountryChanger.country.index");
		switch (item.getItemId()) {
		case R.id.button_next:
			Log.d("Test1", "Button Next");
			if (getIndex() == 4) {
				intent.putExtra("CountryChanger_currentIndex", 0);
				setIndex(0);
				Log.d("Indexverdi:", String.valueOf(getIndex()));
			} else if (getIndex() == 0) {
				intent.putExtra("CountryChanger_currentIndex", 1);
				setIndex(1);
			} else if (getIndex() == 1) {
				intent.putExtra("CountryChanger_currentIndex", 2);
				setIndex(2);
			} else if (getIndex() == 2) {
				intent.putExtra("CountryChanger_currentIndex", 0);
				setIndex(0);
			}
			LocalBroadcastManager.getInstance(getApplicationContext())
					.sendBroadcast(intent);
			break;
		case R.id.button_previous:
			Log.d("Test2", "Button Previous");
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	private BroadcastReceiver messageReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			setIndex(intent.getIntExtra("Fragment1_currentIndex", 4));
		}
	};

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
}
