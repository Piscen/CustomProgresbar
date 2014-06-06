package com.example.customprogresbar;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity {
	RoundProgressBar item1,item2,item3,item4;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		item1 = (RoundProgressBar) findViewById(R.id.ProgressBar1);
		item2 = (RoundProgressBar) findViewById(R.id.ProgressBar2);
		item3 = (RoundProgressBar) findViewById(R.id.ProgressBar3);
		item4 = (RoundProgressBar) findViewById(R.id.ProgressBar4);

		item1.setProgress(28);
		item2.setProgress(0);
		item3.setProgress(100);
		
		new CountDownTimer(10000,100) {

			@Override
			public void onTick(long millisUntilFinished) {
				item4.setProgress(100-(int) millisUntilFinished/100);
			}

			@Override
			public void onFinish() {
				item4.setProgress(100);
			}

		}.start();

	}

}
