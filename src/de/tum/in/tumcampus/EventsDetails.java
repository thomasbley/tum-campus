package de.tum.in.tumcampus;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import de.tum.in.tumcampus.models.EventManager;

public class EventsDetails extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.events_details);

		EventManager em = new EventManager(this, "database.db");
		Cursor c = em.getFromDb(getIntent().getStringExtra("id"));

		if (c.moveToNext()) {
			String description = c.getString(c.getColumnIndex("description"));
			String image = c.getString(c.getColumnIndex("image"));

			String[] weekDays = "So,Mo,Di,Mi,Do,Fr,Sa".split(",");

			setTitle(c.getString(c.getColumnIndex("name")));

			String infos = weekDays[c.getInt(c.getColumnIndex("weekday"))];
			infos += ", " + c.getString(c.getColumnIndex("start_de")) + " - "
					+ c.getString(c.getColumnIndex("end_de")) + "\n";
			infos += c.getString(c.getColumnIndex("location"));
			infos += c.getString(c.getColumnIndex("link"));

			TextView tv = (TextView) findViewById(R.id.infos);
			tv.setText(infos);

			tv = (TextView) findViewById(R.id.description);
			tv.setText(description);

			ImageView iv = (ImageView) findViewById(R.id.image);
			iv.setImageURI(Uri.parse(image));

			double ratio = (double) iv.getDrawable().getIntrinsicWidth()
					/ (double) iv.getDrawable().getIntrinsicHeight();

			iv.getLayoutParams().width = 350;
			iv.getLayoutParams().height = (int) Math.floor(350 / ratio);
		}
		em.close();
	}
}