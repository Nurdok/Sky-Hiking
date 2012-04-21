package com.rachum.amir.skyhiking;




public class RiskyPlayer extends Player {
    
	public RiskyPlayer(final String name) {
		super(name);
	}

	@Override
	public void play(final MoveHandler handler, final Game context) {
        handler.move(Move.STAY);
	}

	@Override
	public void pay(final PayHandler handler, final Game context) {
        if (hand.contains(context.diceRoll)) {
        	hand.discard(context.diceRoll);
            handler.pay(true, context.diceRoll);
        } else if (hand.contains(Card.WILD)) {
            hand.discard(Card.WILD);
            handler.pay(true, context.diceRoll); //FIXME: change to wild
        } else {
        	handler.pay(false, null);
        }
	}
}
