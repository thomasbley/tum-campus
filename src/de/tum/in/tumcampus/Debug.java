package de.tum.in.tumcampus;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Debug extends Activity implements View.OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.debug);

		Button b = (Button) findViewById(R.id.debugCafeterias);
		b.setOnClickListener(this);

		b = (Button) findViewById(R.id.debugCafeteriasMenus);
		b.setOnClickListener(this);

		b = (Button) findViewById(R.id.debugNews);
		b.setOnClickListener(this);
		
		b = (Button) findViewById(R.id.debugNewsItems);
		b.setOnClickListener(this);
	}

	public void DebugReset() {
		TextView tv = (TextView) findViewById(R.id.debug);
		tv.setText("");
	}

	public void DebugStr(String s) {
		TextView tv = (TextView) findViewById(R.id.debug);
		tv.setMovementMethod(new ScrollingMovementMethod());
		tv.append(s + "\n");
	}

	public void DebugSQL(String query) {
		DebugReset();
		SQLiteDatabase db = SQLiteDatabase.openDatabase(
				this.getDatabasePath("database.db").toString(), null,
				SQLiteDatabase.OPEN_READONLY);

		Cursor c = db.rawQuery(query, null);
		while (c.moveToNext()) {
			for (int i = 0; i < c.getColumnCount(); i++) {
				DebugStr(c.getColumnName(i) + ": " + c.getString(i));
			}
			DebugStr("");
		}
		c.close();
		db.close();
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.debugCafeterias) {
			DebugSQL("SELECT * FROM cafeterias ORDER BY id");
		}

		if (v.getId() == R.id.debugCafeteriasMenus) {
			DebugSQL("SELECT * FROM cafeterias_menus ORDER BY id");
		}

		if (v.getId() == R.id.debugNews) {
			DebugSQL("SELECT * FROM news ORDER BY id");
		}
		
		if (v.getId() == R.id.debugNewsItems) {
			DebugSQL("SELECT * FROM news_items ORDER BY feedId");
		}
	}
}