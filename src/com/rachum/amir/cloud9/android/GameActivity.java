/**
 * 
 */
package com.rachum.amir.cloud9.android;

import android.app.Activity;
import android.os.Bundle;

/**
 * @author Rachum
 *
 */
public class GameActivity extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MainGamePanel(this));
    }
    

}