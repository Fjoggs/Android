package no.hist.vegardfj.oving5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import no.hist.itfag.books.R;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class BookActivity extends Activity {
	private DBAdapter db;
	private TextView textView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_book);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		readFile(R.raw.forfattere);
		textView = (TextView) findViewById(R.id.textView_backgroundColor);
	}

	@Override
	public void onResume() {
		super.onResume();
		displaySharedPreferences();
		updateBackgroundColors();
	}

	@Override
	public void onDestroy() {
		db.close();
		super.onDestroy();
	}

	public void onClickViewPreferences(View v) {
		Intent intent = new Intent(BookActivity.this, Preferences.class);
		startActivity(intent);
	}
	
	public void onClickViewAuthors(View v) {
		db = new DBAdapter(this);
		db.open();
		Cursor cursor = db.getAllAuthors();
		showAuthors(cursor);
	}
	
	public void onClickViewBooks(View v) {
		db = new DBAdapter(this);
		db.open();
		Cursor cursor = db.getAllBooks();
		showAuthors(cursor);
	}

	private void displaySharedPreferences() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(BookActivity.this);
		String backgroundColor = prefs.getString("backColor", "White");
		textView.setText("Background Color: "+backgroundColor);	
	}
	
	private void updateBackgroundColors() {
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(BookActivity.this);
		String backgroundColor = prefs.getString("backColor", "White");
		ListView listView = ((ListFragment) this.getFragmentManager().findFragmentById(R.id.frag1)).getListView();
		if (backgroundColor.equalsIgnoreCase("red")) {
			// 0xAARRGGBB
			listView.setBackgroundColor(Color.RED);
		} else if (backgroundColor.equalsIgnoreCase("green")) {
			// 0xAARRGGBB
			listView.setBackgroundColor(Color.GREEN);
		} else if (backgroundColor.equalsIgnoreCase("blue")) {
			// 0xAARRGGBB
			listView.setBackgroundColor(Color.BLUE);
		} else if (backgroundColor.equalsIgnoreCase("white")){
			// 0xAARRGGBB
			listView.setBackgroundColor(Color.WHITE);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_book, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	private void readFile(int id) {
		db = new DBAdapter(this);
		db.open();
		try {
			InputStream is = getResources().openRawResource(id);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(is));
			String line = reader.readLine();
			String[] temp = new String[2];
			while (line != null) {
				long dbId;
				temp = line.split(",\\s*");
				dbId = db.insert(temp[0], temp[1]);
				Log.d("Linje 1: ", temp[0]);
				Log.d("Linje 2: ", temp[1]);
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Cursor c = db.getAllBookAuthors();
		Cursor c = db.getBooksByAuthor("Knut Hamsun");
		showAuthors(c);
	}

	public void showAuthors(Cursor c) {
		ListFragment frag = (ListFragment) (this.getFragmentManager()
				.findFragmentById(R.id.frag1));
		String[] columns = new String[] { c.getColumnName(0),
				c.getColumnName(1) };
		int[] to = new int[] { R.id.listLayout_textView1, R.id.listLayout_textView2 };
		SimpleCursorAdapter sca = new SimpleCursorAdapter(this,
				R.layout.list_layout, c, columns, to);
		frag.setListAdapter(sca);
	}

	public String getAllInColumn(Cursor c, int column) {
		StringBuffer res = new StringBuffer("");
		if (c.moveToFirst()) {
			do {
				res.append(c.getString(column) + "\n");

			} while (c.moveToNext());
		}
		return res.toString();
	}

}
