package com.rachum.amir.cloud9;


public class RiskyPlayer extends Player {
    
	public RiskyPlayer(final String name) {
		super();
		this.name = name;
	}

	@Override
	public Move play(final GameState state) {
		return Move.STAY;
	}

	@Override
	public boolean pay(final GameState state) {
        if (hand.contains(state.diceRoll)) {
        	hand.discard(state.diceRoll);
            logger.info("Player " + this + " payed " + state.diceRoll);
            return true;
        } else if (state.remainingPlayers.size() == 1 &&
        		hand.contains(Card.WILD)) {
            hand.discard(Card.WILD);
            logger.info("Player " + this + " payed wild.");
            return true;
        }
        logger.info("Player " + this + " did not have the cards to pay.");
        return false;
	}
}
