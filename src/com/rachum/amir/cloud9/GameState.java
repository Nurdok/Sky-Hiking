package com.rachum.amir.cloud9;

import java.util.Collection;

public class GameState {
    
    public GameState(final Player pilot, final Collection<Card> diceRoll,
			final Collection<Player> remainingPlayers) {
		super();
		this.pilot = pilot;
		this.diceRoll = diceRoll;
		this.remainingPlayers = remainingPlayers;
	}
    
	public Player pilot;
	public Collection<Card> diceRoll;
	public Collection<Player> remainingPlayers;
}
