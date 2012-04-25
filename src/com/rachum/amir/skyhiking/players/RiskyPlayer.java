package com.rachum.amir.skyhiking.players;


import com.rachum.amir.skyhiking.Game;
import com.rachum.amir.skyhiking.Move;
import com.rachum.amir.skyhiking.MoveHandler;

public class RiskyPlayer extends AlwaysPayPlayer {
    
	public RiskyPlayer(final String name) {
		super(name);
	}

	@Override
	public void play(final MoveHandler handler, final Game context) {
        handler.move(Move.STAY);
	}
}
