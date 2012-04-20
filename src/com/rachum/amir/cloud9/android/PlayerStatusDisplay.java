package com.rachum.amir.cloud9.android;

import com.rachum.amir.cloud9.Move;
import com.rachum.amir.cloud9.Player;

import android.content.Context;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PlayerStatusDisplay extends LinearLayout {
	private final TextView playerName;
	private final ImageView statusImage;
	private final Player player;
	
	public PlayerStatusDisplay(Context context, Player player) {
		super(context);
		this.player = player;
		playerName = new TextView(context);
		statusImage = new ImageView(context);
		unsetStatus();
		updateScore();
		addView(playerName);
		addView(statusImage);
	}
	
	public void setMove(Move move) {
		statusImage.setVisibility(VISIBLE);
		switch (move) {
		case LEAVE:
			statusImage.setImageResource(R.drawable.leave);
			break;
		case STAY:
			statusImage.setImageResource(R.drawable.stay);
			break;
		}
		statusImage.getLayoutParams().height = 20;
		statusImage.getLayoutParams().width = 20;
	}
	
	public void setPilot() {
		statusImage.setVisibility(VISIBLE);
		statusImage.setImageResource(R.drawable.ic_launcher); //TODO: change to pilot image
		statusImage.getLayoutParams().height = 20;
		statusImage.getLayoutParams().width = 20;
	}
	
	public void unsetStatus() {
		statusImage.setVisibility(INVISIBLE);
	}
	
	public void updateScore() {
		playerName.setText(playerName + " (" + player.getScore() + ")");
	}
}