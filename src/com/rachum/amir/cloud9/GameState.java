package com.rachum.amir.cloud9;

import java.util.Collection;
import java.util.List;

public class GameState {
    
	public enum State {
		GAME_BEGIN, GAME_END, ROUND_BEGIN, ROUND_END, LEVEL_BEGIN,
		LEVEL_END, MOVE, PAY, DICE_ROLLED;
	}
    
	public Player pilot = null;
	public Collection<Card> diceRoll = null;
	public List<Player> remainingPlayers = null;
    public State state = null;
    public CloudLevel level = null;
    public Boolean didPay = null;
	public Player currentPlayer;
}
