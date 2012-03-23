package com.rachum.amir.cloud9.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MenuActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void beginGame(final View view) {
    	final Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
   }
}