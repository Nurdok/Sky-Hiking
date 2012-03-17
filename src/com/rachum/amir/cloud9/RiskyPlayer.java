package com.rachum.amir.cloud9;

import java.util.Collection;
import java.util.List;


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
        } else if (hand.contains(Card.WILD)) {
            hand.discard(Card.WILD);
            logger.info("Player " + this + " payed wild.");
            return true;
        }
        logger.info("Player " + this + " did not have the cards to pay.");
        return false;
	}

	@Override
	public void roundBegin() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameBegin(final List<Player> players) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void levelBegin(final List<Player> players) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void levelEnd() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void diceRolled(final Player pilot, final Collection<Card> diceRoll) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move(final Player player, final Move move) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pay(final Player pilot, final boolean didPay, final Collection<Card> cards) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void gameEnd(final Player player) {
		// TODO Auto-generated method stub
		
	}
}
