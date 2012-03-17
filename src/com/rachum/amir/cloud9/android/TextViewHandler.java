/**
 * 
 */
package com.rachum.amir.cloud9.android;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

import android.widget.TextView;

/**
 * @author Rachum
 *
 */
public class TextViewHandler extends Handler {
    private final TextView textView;
    
	public TextViewHandler(final TextView textView) {
		this.textView = textView;
	}

	/* (non-Javadoc)
	 * @see java.util.logging.Handler#close()
	 */
	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see java.util.logging.Handler#flush()
	 */
	@Override
	public void flush() {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see java.util.logging.Handler#publish(java.util.logging.LogRecord)
	 */
	@Override
	public void publish(final LogRecord record) {
        if (record.getParameters() != null &&
        		(Boolean) record.getParameters()[0] == true) {
			this.textView.setText("");
		}
		this.textView.append(record.getMessage() + "\n");
	}

}
