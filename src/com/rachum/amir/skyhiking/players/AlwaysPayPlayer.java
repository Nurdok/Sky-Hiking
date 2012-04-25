package com.rachum.amir.skyhiking.players;

import java.util.Arrays;

import com.rachum.amir.skyhiking.Card;
import com.rachum.amir.skyhiking.Game;
import com.rachum.amir.skyhiking.MoveHandler;
import com.rachum.amir.skyhiking.PayHandler;

public abstract class AlwaysPayPlayer extends Player {

	public AlwaysPayPlayer(String name) {
		super(name);
	}

	@Override
	public void pay(final PayHandler handler, final Game context) {
	    if (hand.contains(context.diceRoll)) {
	    	hand.discard(context.diceRoll);
	        handler.pay(true, context.diceRoll);
	    } else if (hand.contains(Card.WILD)) {
	        hand.discard(Card.WILD);
	        handler.pay(true, Arrays.asList(Card.WILD)); //FIXME: change to wild
	    } else {
	    	handler.pay(false, null);
	    }
	}


}
