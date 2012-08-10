package com.rachum.amir.skyhiking.android;

import java.io.IOException;
import java.io.InputStream;
import us.feras.mdv.MarkdownView;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class RulesActivity extends Activity {

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rules);
		MarkdownView rules1 = (MarkdownView) findViewById(R.id.rules1);
		rules1.loadMarkdownUrl("file:///android_asset/rules1.rst");
		rules1.setBackgroundColor(0); //transparent
		MarkdownView rules2 = (MarkdownView) findViewById(R.id.rules2);
		rules2.loadMarkdownUrl("file:///android_asset/rules2.rst");
		rules2.setBackgroundColor(0); //transparent
		ImageView levels = (ImageView) findViewById(R.id.levelsImg);
		levels.setImageBitmap(getBitmapFromAsset("levels.PNG"));
	}
	
	private Bitmap getBitmapFromAsset(String name) {
		try {
			AssetManager mgr = getAssets();
			InputStream is = mgr.open(name);
			Bitmap bitmap = BitmapFactory.decodeStream(is);
			return bitmap;
		} catch (IOException e) {
			e.printStackTrace();
			finish();
			return null;
		}
	}
}
