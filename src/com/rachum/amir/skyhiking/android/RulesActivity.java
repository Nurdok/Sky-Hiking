package com.rachum.amir.skyhiking.android;

import us.feras.mdv.MarkdownView;

import android.app.Activity;
import android.os.Bundle;

public class RulesActivity extends Activity {

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rules);
		MarkdownView rules1 = (MarkdownView) findViewById(R.id.rules);
		rules1.loadMarkdownUrl("file:///android_asset/rules.rst");
		rules1.setBackgroundColor(0); //transparent
	}
}
