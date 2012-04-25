package com.rachum.amir.skyhiking.players;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.rachum.amir.skyhiking.Game;
import com.rachum.amir.skyhiking.Move;
import com.rachum.amir.skyhiking.MoveHandler;
import com.rachum.amir.skyhiking.PayHandler;
import com.rachum.amir.util.range.Range;

public class RandomPlayer extends AlwaysPayPlayer {

	public RandomPlayer(String name) {
		super(name);
	}

	@SuppressWarnings("unused")
	@Override
	public void play(MoveHandler handler, Game context) {
		List<Move> options = new LinkedList<Move>();
		options.add(Move.LEAVE);
		for (int i : new Range(4)) {
			options.add(Move.STAY);
		}
		Collections.shuffle(options);
		handler.move(options.get(0));
	}

}
