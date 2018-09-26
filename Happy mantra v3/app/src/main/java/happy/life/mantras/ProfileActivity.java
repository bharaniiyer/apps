package happy.life.mantras;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import info.androidhive.expandablelistview.R;

public class ProfileActivity extends AppCompatActivity {

	TextView mantrabt,festbt;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(R.style.AppTheme);
		setContentView(R.layout.profile);

		mantrabt = (TextView) findViewById(R.id.mantraa);
		festbt = (TextView) findViewById(R.id.festival);

		mantrabt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent fest = new Intent(ProfileActivity.this, MainActivity.class);
				startActivity(fest);
			}
		});

		festbt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent soon = new Intent(ProfileActivity.this, FestActivity.class);
				startActivity(soon);
			}
		});

	}
}
