/**
 * 
 */
package com.rachum.amir.cloud9.android;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.rachum.amir.cloud9.Game;
import com.rachum.amir.cloud9.Move;
import com.rachum.amir.cloud9.MoveHandler;
import com.rachum.amir.cloud9.PayHandler;
import com.rachum.amir.cloud9.Player;

/**
 * @author Rachum
 *
 */
public class HumanPlayer extends Player {
    
	private final Context top;

	public HumanPlayer(final String name, final Context top) {
		super(name);
		this.top = top;
	}

	@Override
	public void play(final MoveHandler handler, final Game context) {
		// TODO Auto-generated method stub
    	final Button stay = new Button(top);
        stay.setText("Stay");
        stay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
                handler.move(Move.STAY);
			}
		});
        
    	final Button leave = new Button(top);
        stay.setText("Leave");
        stay.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(final View v) {
                handler.move(Move.LEAVE);
			}
		});
	}
    
	@Override
	public void pay(final PayHandler handler, final Game context) {
		// TODO Auto-generated method stub
        handler.pay(false, null);

	}

}
