package com.rachum.amir.skyhiking.android;

import com.rachum.amir.skyhiking.Move;
import com.rachum.amir.skyhiking.android.R;
import com.rachum.amir.skyhiking.players.Player;

import android.content.Context;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PlayerStatusDisplay extends LinearLayout {
	private final TextView playerName;
	private final ImageView statusImage;
	private final Player player;
	Context context;
	
	public PlayerStatusDisplay(Context context, Player player) {
		super(context);
		this.context = context;
		this.player = player;
		playerName = new TextView(context);
		this.setPadding(dpToPx(7, Dimension.WIDTH), 
					    dpToPx(14, Dimension.HEIGHT), 
					    dpToPx(7, Dimension.WIDTH), 
					    dpToPx(0, Dimension.HEIGHT));
		playerName.setPadding(dpToPx(0, Dimension.WIDTH), 
							  dpToPx(0, Dimension.HEIGHT), 
							  dpToPx(14, Dimension.WIDTH), 
							  dpToPx(0, Dimension.HEIGHT));
		playerName.setTextSize(18);
		statusImage = new ImageView(context);
		setPlaying(true);
		unsetStatus();
		updateScore();
		addView(playerName);
		addView(statusImage);
	}
	
	enum Dimension {
		HEIGHT, WIDTH;
	}
	
	private int dpToPx(int dp, Dimension dimension) { 
		// Get the screen's density scale 
		final float scale = getResources().getDisplayMetrics().density; 
		// Convert the dp to pixels, based on density scale
		int px = (int) (dp * scale + 0.5f); 
		if (dimension != null) {
			int ref, dim;
			switch (dimension) {
			case HEIGHT:
				dim = getResources().getDisplayMetrics().heightPixels; 
				ref = 800;
				break;
			case WIDTH:
				dim = getResources().getDisplayMetrics().widthPixels; 
				ref = 480;
				break;
			default:
				dim = 1;
				ref = 1;
			}
			px = (int) (px * ((float) dim / ref));
		}
		
		return px;
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
		statusImage.getLayoutParams().height = dpToPx(20, null);
		statusImage.getLayoutParams().width = dpToPx(20, null);
	}
	
	public void setPlaying(boolean playing) {
		if (playing) {
			playerName.setTextColor(Color.BLACK);
		} else {
			playerName.setTextColor(Color.GRAY);
		}
	}
	
	public void setWon() {
		playerName.setTextAppearance(getContext(), R.style.BoldText);
		playerName.setShadowLayer(dpToPx(2, null), dpToPx(0, null), 
								  dpToPx(0, null), Color.BLACK);
		playerName.setTextColor(Color.GREEN);
	}
	
	public void setPilot() {
		statusImage.setVisibility(VISIBLE);
		statusImage.setImageResource(R.drawable.pilot); //TODO: change to pilot image
		statusImage.getLayoutParams().height = dpToPx(20, null);
		statusImage.getLayoutParams().width = dpToPx(20, null);
	}
	
	public void unsetStatus() {
		statusImage.setVisibility(INVISIBLE);
	}
	
	public void updateScore() {
		int cardCount = player.getHand().getCards().size();
		int score = player.getScore();
		playerName.setText(player.getName() + " (" + cardCount + "C / " + 
						   score + "P)");
	}
}