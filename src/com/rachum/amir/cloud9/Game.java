/**
 * 
 */
package com.rachum.amir.cloud9;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Rachum
 *
 */
public class Game {
	private final List<Player> players;
	private final Deck deck;
	private final Iterator<Player> pilotIterator;
	private final Logger logger = Logger.getLogger(this.getClass().getName());
    
	private enum LevelOutcome {SUCCESS, CRASH;}
	
	public Game(final List<Player> players) {
		super();
		this.players = players;
		deck = new Deck();
		for (final Player player : players) {
            player.gameStart(deck);
		}
		pilotIterator = new InfiniteIterator(players);
	}


	public Player play() {
		while (true) { //TODO: until the game ends
			final List<Player> remainingPlayers = new LinkedList<Player>(players);
			for (final CloudLevel level : CloudLevel.gameLevels()) {
                logger.log(Level.INFO, "Playing level " + level, true);
                logger.info("Current players are: " + remainingPlayers);
                logger.info("Current scores: " + scoreboard());
				if (remainingPlayers.isEmpty()) {
                    for (final Player player : players) {
                    	player.roundEnd();
                    }
					break; //Move to next level
				}
                
				if (level.getDiceNumber() == 0) {
					for (final Player player : remainingPlayers) {
						player.score(level.getScore());
						player.roundEnd();
					}
                    break;
				}
                
				Player pilot = pilotIterator.next();
				while (!remainingPlayers.contains(pilot)) {
					pilot = pilotIterator.next();
				}
                logger.info("Current Pilot: " + pilot);
                
                final LevelOutcome outcome = playLevel(level, remainingPlayers, pilot);
                if (outcome == LevelOutcome.CRASH) {
                    for (final Player player : players) {
                    	player.roundEnd();
                    }
                	break;
                }
				
			}
            for (final Player player : players) {
            	if (player.getScore() > 49) {
            		logger.info("Game Over! " + player + "has a score of "
            				+ player.getScore());
                    return player;
            	}
            }
		}
	}

	public LevelOutcome playLevel(final CloudLevel level,
			final Collection<Player> remainingPlayers, final Player pilot) {
		final Collection<Card> diceRoll = Dice.roll(level.getDiceNumber());
		//TODO: allow each player to decide...
		
		final List<Player> newRemainingPlayers = new LinkedList<Player>();
		newRemainingPlayers.addAll(remainingPlayers);
		for (final Player player : remainingPlayers) { //TODO: order important
			if (player != pilot) {
                final GameState state = new GameState(pilot, diceRoll,
                		remainingPlayers);
				final Move move = player.play(state);
				if (move == Move.LEAVE) {
					player.score(level.getScore());
					newRemainingPlayers.remove(player);
					logger.info("Player " + player.getName() +
							" leaves and scores " + level.getScore() +
							" points to a total of " + player.getScore());
				}
			}
		}
		remainingPlayers.retainAll(newRemainingPlayers);
		
        final GameState state = new GameState(pilot, diceRoll,
        		remainingPlayers);
        
		if (pilot.pay(state)) {
			logger.info("The pilot has payed " +
			" and we are moving on the the next level.");
            return LevelOutcome.SUCCESS;
		} else {
			//We crash and move to next round
			logger.info("Awwww... we crashed...");
            return LevelOutcome.CRASH;
		}
		
	}
	
	private Map<Player, Integer> scoreboard() {
		final Map<Player, Integer> scoreboard = new HashMap<Player, Integer>();
		for (final Player player : players) {
			scoreboard.put(player, player.getScore());
        }
        return scoreboard;
	}
}
