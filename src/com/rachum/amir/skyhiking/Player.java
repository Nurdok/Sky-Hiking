package com.rachum.amir.skyhiking;



public abstract class Player implements GameEventListener {
    public Player(final String name) {
		super();
		this.name = name;
	}

	protected String name;
    private int score;
	protected Hand hand;
    
	public abstract void play(final MoveHandler handler, final Game context);
	public abstract void pay(final PayHandler handler, Game context);
    
	public void gameStart(final Deck deck) {
        hand = new Hand(deck);
        score = 0;
		hand.draw(6);
	}
    
    @Override
    public void handleEvent(final GameEvent event, EventHandler eventHandler) {
    	switch (event.type) {
    	case ROUND_END:
    		hand.draw(1);
    	}
    	eventHandler.done();
    };
    
    
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
		return getName();
	}
	public Hand getHand() {
		return hand;
	}
}
