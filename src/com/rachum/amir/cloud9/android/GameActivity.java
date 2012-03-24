/**
 * 
 */
package com.rachum.amir.cloud9.android;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rachum.amir.cloud9.GameEvent;

/**
 * @author Rachum
 *
 */
public class GameActivity extends Activity {
    private TextView log;
    private LinearLayout layout;
    private GameEvent event;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new MainGamePanel(this));
    }
}
