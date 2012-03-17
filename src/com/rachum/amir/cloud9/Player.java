package com.rachum.amir.cloud9;

import java.util.logging.Logger;


public abstract class Player implements GameEventListener {
    protected String name;
    private int score;
	protected Hand hand;
	protected final Logger logger = Logger.getLogger(this.getClass().getName());
    
	public abstract Move play(GameState state);
	public abstract boolean pay(GameState state);
    
	public void gameStart(final Deck deck) {
        hand = new Hand(deck);
        score = 0;
		hand.draw(6);
        logger.info("Player " + this + "drew " + this.hand.getCards());
	}
    
	@Override
	public void roundEnd() {
		hand.draw(1);
	}
    
	public String getName() {
		return name;
	}
    
	public void score(final int points) {
		score += points;
	}
		
	public int getScore() {
		return score;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
